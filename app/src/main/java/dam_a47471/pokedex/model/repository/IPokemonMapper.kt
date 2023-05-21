package dam_a47471.pokedex.model.repository

import dam_a47471.pokedex.data.Pokemon
import dam_a47471.pokedex.data.PokemonRegion
import dam_a47471.pokedex.data.PokemonType
import dam_a47471.pokedex.model.PokemonEntity
import dam_a47471.pokedex.model.RegionEntity
import dam_a47471.pokedex.model.TypeEntity
import dam_a47471.pokedex.model.responses.*

interface IPokemonMapper {
    fun toRegionModel(
        response: PokemonRegionsResponse
    ): PokemonRegion

    fun toPokemonModel(
        response: PokemonResponse, region: PokemonRegion, detail:
        PokemonDetailResponse
    ): Pokemon

    fun toPokemonTypeModel(response: PokemonTypeResponse): PokemonType

    fun toPokemonTypeModel(entity: TypeEntity): PokemonType

    fun toRegionModel(
        entity: RegionEntity
    ): PokemonRegion

    fun toRegionEntity(
        region: PokemonRegion
    ): RegionEntity

    fun toTypeEntity(response: PokemonGenericResponse): TypeEntity

    fun toTypeEntity(type: PokemonType): TypeEntity

    fun toPokemonEntity(pokemon: Pokemon): PokemonEntity?

    fun toPokemonModel(
        entity: PokemonEntity,
        region: PokemonRegion,
        types: List<TypeEntity>
    ): Pokemon
}
