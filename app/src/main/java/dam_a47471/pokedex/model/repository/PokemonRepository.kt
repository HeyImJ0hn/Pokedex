package dam_a47471.pokedex.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dam_a47471.pokedex.data.*
import dam_a47471.pokedex.data.mocks.PokemonMockData
import dam_a47471.pokedex.model.PokemonDetailsEntity
import dam_a47471.pokedex.model.PokemonEntity
import dam_a47471.pokedex.model.PokemonTypesCrossRef
import dam_a47471.pokedex.model.PokemonWithDetailsAndStats
import dam_a47471.pokedex.model.network.PokemonApi
import dam_a47471.pokedex.model.persistent.DetailDao
import dam_a47471.pokedex.model.persistent.PokemonDao
import dam_a47471.pokedex.model.persistent.StatsDao
import dam_a47471.pokedex.model.persistent.TypeDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlin.random.Random

class PokemonRepository(
    private val pokemonApi: PokemonApi,
    private val pokemonDao: PokemonDao,
    private val typeDao: TypeDao,
    private val detailDao: DetailDao,
    private val statsDao: StatsDao
) {
    suspend fun getPokemonsByRegion(region: PokemonRegion): LiveData<List<Pokemon>> {
        try {
            val regionWithPokemons = pokemonDao.getPokemonByRegion(region.id)
            if (regionWithPokemons.pokemon.isEmpty()) {
                val pkByRegionResponse = pokemonApi.fetchPokemonByRegionId(region.id)
                val regexToGetId = "/([^/]+)/?\$".toRegex()
                val pokemons = pkByRegionResponse.pokemons.map {
                    var pkId = it.url?.let { it1 -> regexToGetId.find(it1)?.value }
                    pkId = pkId?.removeSurrounding("/")
                    val pkDetailResponse =
                        pkId?.let { pokemonApi.fetchPokemonDetailById(pkId.toInt()) }
                    PokemonMapper.toPokemonModel(it, region, pkDetailResponse!!)
                }
                CoroutineScope(Dispatchers.Default).async {
                    savePokemonsinDB(pokemons)
                }
                return MutableLiveData(pokemons)
            } else {
                val pks = regionWithPokemons.pokemon.map {
                    val pkWithTypes = pokemonDao.getTypesByPokemon(it.pkId)
                    PokemonMapper.toPokemonModel(it, region, pkWithTypes.types)
                }
                return MutableLiveData(pks)
            }
        } catch (e: java.lang.Exception) {
            Log.e("ERROR", e.toString())
        }
        return MutableLiveData()
    }

    suspend fun getDetailsByPokemon(pokemon: Pokemon): LiveData<PokemonWithDetailsAndStats> {
        try {
            if (detailDao.getDetailsCountByPokemonId(pokemon.id) <= 0) {
                val detailResponse = pokemonApi.fetchPokemonDetailById(pokemon.id)
                val statsMap = mutableMapOf(
                    "hp" to 0,
                    "attack" to 0,
                    "defense" to 0,
                    "special-attack" to 0,
                    "special-defense" to 0,
                    "speed" to 0
                )

                detailResponse.stats!!.forEach { stat ->
                    val statName = stat.stat!!.name
                    val baseStat = stat.baseStat!!

                    if (statsMap.containsKey(statName)) {
                        statsMap[statName] = baseStat
                    }
                }

                val stats = PokemonStats(
                    pokemon.id,
                    statsMap["hp"]!!,
                    statsMap["attack"]!!,
                    statsMap["defense"]!!,
                    statsMap["special-attack"]!!,
                    statsMap["special-defense"]!!,
                    statsMap["speed"]!!
                )
                val detail = PokemonDetail(
                    pokemon.id,
                    "",
                    detailResponse.height,
                    detailResponse.weight,
                    detailResponse.abilities?.get(0)!!.ability!!.name,
                    generateSequence {
                        PokemonEvolution(
                            1, PokemonMockData.pokemons.random(), false, 0, "", 0, ""
                        )
                    }.take(Random.nextInt(1, 3)).toList()
                )
                val detailsEntity = PokemonMapper.toDetailsEntity(detail)
                val statsEntity = PokemonMapper.toStatsEntity(stats)
                detailDao.insertDetail(detailsEntity)
                statsDao.insertStats(statsEntity)
                val pokemonEntity = PokemonEntity(pokemon.id, pokemon.name, pokemon.imageUrl, pokemon.region!!.id)
                return MutableLiveData(PokemonWithDetailsAndStats(pokemonEntity, detailsEntity, statsEntity))
            } else {
                val detailsEntity = detailDao.getDetailsById(pokemon.id)
                val statsEntity = statsDao.getStatsById(pokemon.id)
                val pokemonEntity = PokemonEntity(pokemon.id, pokemon.name, pokemon.imageUrl, pokemon.region!!.id)
                return MutableLiveData(PokemonWithDetailsAndStats(pokemonEntity, detailsEntity, statsEntity))
            }
        } catch (e: java.lang.Exception) {
            Log.e("ERROR", e.toString())
        }
        return MutableLiveData()
    }

    suspend fun getAllRegions(): LiveData<List<PokemonRegion>> {
        try {
            val regionsResponse = pokemonApi.fetchRegionList()
            val regions = regionsResponse.results?.map {
                PokemonMapper.toRegionModel(it)
            }
            return MutableLiveData<List<PokemonRegion>>(regions)
        } catch (e: java.lang.Exception) {
            Log.e("ERROR", e.toString())
        }
        return MutableLiveData<List<PokemonRegion>>()
    }

    private suspend fun savePokemonsinDB(pokemons: List<Pokemon>) {
        val types = pokemons.flatMap { it.types }.distinct()
        types.forEach {
            val type = PokemonMapper.toTypeEntity(it)
            typeDao.insertType(type)
        }
        pokemons.forEach { it ->
            val pk = PokemonMapper.toPokemonEntity(it)
            pk?.let { it1 -> pokemonDao.insertPokemon(it1) }
            it.types.forEach {
                pokemonDao.insertPokemonWithType(PokemonTypesCrossRef(pk!!.pkId, it.id))
            }
        }
    }
}