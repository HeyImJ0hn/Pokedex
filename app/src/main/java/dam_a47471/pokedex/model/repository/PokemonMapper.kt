package dam_a47471.pokedex.model.repository

import android.content.Context
import dam_a47471.pokedex.data.*
import dam_a47471.pokedex.data.PokemonStats
import dam_a47471.pokedex.data.mocks.PokemonMockData
import dam_a47471.pokedex.model.*
import dam_a47471.pokedex.model.responses.*
import kotlin.random.Random

object PokemonMapper : IPokemonMapper {
    private val regexToGetId = "/([^/]+)/?\$".toRegex()
    private lateinit var appContext: Context
    fun initialize(context: Context) {
        appContext = context.applicationContext
    }

    override fun toRegionModel(response: PokemonRegionsResponse): PokemonRegion {
        var regionId = regexToGetId.find(response.url!!)?.value
        regionId = regionId?.removeSurrounding("/")
        val bgUri = "@drawable/bg_${response.name}"
        val startersUri = "@drawable/pk_${response.name}"
        return PokemonRegion(
            regionId?.toInt() ?: 1,
            response.name.toString(),
            appContext.resources.getIdentifier(bgUri, null, appContext.packageName),
            appContext.resources.getIdentifier(
                startersUri, null, appContext.packageName
            )
        )
    }

    override fun toRegionModel(
        entity: RegionEntity
    ): PokemonRegion {
        return PokemonRegion(entity.id, entity.name, entity.bg, entity.starters)
    }

    override fun toRegionEntity(
        region: PokemonRegion
    ): RegionEntity {
        return RegionEntity(region.id, region.name, region.bg, region.starters)
    }

    override fun toPokemonModel(
        response: PokemonResponse, region: PokemonRegion, detail:
        PokemonDetailResponse
    ): Pokemon {
        var pkId = regexToGetId.find(response.url!!)?.value
        pkId = pkId?.removeSurrounding("/")
        val imgUrl =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pkId}.png"
        val types = detail.types?.map {
            toPokemonTypeModel(it)
        }
        return Pokemon(
            pkId?.toInt() ?: 1, response.name.toString(), imgUrl, region, types ?: listOf()
        )
    }

    override fun toPokemonModel(
        entity: PokemonEntity, region: PokemonRegion, types: List<TypeEntity>
    ): Pokemon {
        val pkTypes = types.map { toPokemonTypeModel(it) }
        return Pokemon(entity.pkId, entity.name, entity.imageUrl, region, pkTypes)
    }

    override fun toPokemonTypeModel(response: PokemonTypeResponse): PokemonType {
        var typeId = regexToGetId.find(response.type?.url!!)?.value
        typeId = typeId?.removeSurrounding("/")
        val colorUri = "@color/${response.type.name}"
        val iconUri = "@drawable/${response.type.name}"
        return PokemonType(
            typeId?.toInt() ?: 0,
            response.type.name,
            appContext.resources.getIdentifier(iconUri, null, appContext.packageName),
            appContext.resources.getIdentifier(colorUri, null, appContext.packageName)
        )
    }

    override fun toTypeEntity(response: PokemonGenericResponse): TypeEntity {
        var typeId = regexToGetId.find(response.url!!)?.value
        typeId = typeId?.removeSurrounding("/")
        val colorUri = "@color/${response.name}"
        val iconUri = "@drawable/${response.name}"
        return TypeEntity(
            typeId?.toInt() ?: 0, response.name, appContext.resources.getIdentifier(
                iconUri, null, appContext.packageName
            ), appContext.resources.getIdentifier(
                colorUri,
                null, appContext.packageName
            )
        )
    }

    override fun toTypeEntity(type: PokemonType): TypeEntity {
        return TypeEntity(type.id, type.name, type.icon, type.color)
    }

    override fun toPokemonTypeModel(entity: TypeEntity): PokemonType {
        return PokemonType(entity.typeId, entity.name, entity.icon, entity.color)
    }

    override fun toPokemonEntity(pokemon: Pokemon): PokemonEntity? {
        return pokemon.region?.let {
            PokemonEntity(
                pokemon.id, pokemon.name, pokemon.imageUrl, it.id
            )
        }
    }

    override fun toDetailsEntity(detail: PokemonDetail): PokemonDetailsEntity {
        return PokemonDetailsEntity(
            detail.id,
            detail.weight!!,
            detail.height!!,
            detail.ability!!,
            detail.category!!,
            detail.description
        )
    }

    override fun toStatsEntity(stats: PokemonStats): PokemonStatsEntity {
        return PokemonStatsEntity(
            stats.id,
            stats.hp,
            stats.attack,
            stats.defense,
            stats.specialAttack,
            stats.specialDefense,
            stats.speed
        )
    }

    override fun toPokemonDetail(detailsEntity: PokemonDetailsEntity): PokemonDetail {
        return PokemonDetail(
            detailsEntity.id,
            detailsEntity.description.replace("\n", " "),
            detailsEntity.weight,
            detailsEntity.height,
            detailsEntity.ability,
            detailsEntity.category,
            generateSequence {
                PokemonEvolution(
                    1, PokemonMockData.pokemons.random(), false, 0, "", 0, ""
                )
            }.take(Random.nextInt(1, 3)).toList()
        )
    }

    override fun toPokemonStats(statsEntity: PokemonStatsEntity): PokemonStats {
        return PokemonStats(
            statsEntity.id,
            statsEntity.hp,
            statsEntity.atk,
            statsEntity.def,
            statsEntity.spAtk,
            statsEntity.spAtk,
            statsEntity.spd
        )
    }

    override fun toEvolution(
        evolutionEntity: PokemonEvolutionEntity,
        pokemon: Pokemon
    ): PokemonEvolution {
        return PokemonEvolution(
            evolutionEntity.id,
            pokemon,
            false,
            evolutionEntity.minLevel,
            evolutionEntity.item,
            evolutionEntity.minHappiness,
            evolutionEntity.time
        )
    }

    override fun toEvolutionEntity(evolution: PokemonEvolution): PokemonEvolutionEntity {
        return PokemonEvolutionEntity(
            evolution.id,
            evolution.pokemon.id,
            evolution.minLevel!!,
            evolution.item!!,
            evolution.minHappiness!!,
            evolution.time!!
        )
    }
}