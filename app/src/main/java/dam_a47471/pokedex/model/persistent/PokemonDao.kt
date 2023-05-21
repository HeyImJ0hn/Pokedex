package dam_a47471.pokedex.model.persistent

import androidx.room.*
import dam_a47471.pokedex.model.PokemonEntity
import dam_a47471.pokedex.model.PokemonTypesCrossRef
import dam_a47471.pokedex.model.PokemonWithTypes
import dam_a47471.pokedex.model.RegionWithPokemons

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemon(pokemon: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonWithType(pkType: PokemonTypesCrossRef)

    @Transaction
    @Query("SELECT * FROM pokemon_region WHERE region_id = :regionId")
    fun getPokemonByRegion(regionId: Int): RegionWithPokemons

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pkId = :pokemonId")
    fun getTypesByPokemon(pokemonId: Int): PokemonWithTypes
}
