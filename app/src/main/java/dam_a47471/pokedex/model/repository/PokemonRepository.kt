package dam_a47471.pokedex.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dam_a47471.pokedex.data.Pokemon
import dam_a47471.pokedex.data.PokemonRegion
import dam_a47471.pokedex.model.PokemonTypesCrossRef
import dam_a47471.pokedex.model.network.PokemonApi
import dam_a47471.pokedex.model.persistent.PokemonDao
import dam_a47471.pokedex.model.persistent.TypeDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class PokemonRepository(
    private val pokemonApi: PokemonApi,
    private val pokemonDao: PokemonDao,
    private val typeDao: TypeDao
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