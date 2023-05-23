package dam_a47471.pokedex.model.persistent

import androidx.room.*
import dam_a47471.pokedex.model.PokemonEvolutionEntity

@Dao
interface EvolutionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvolution(pokemon: PokemonEvolutionEntity)

    @Transaction
    @Query("SELECT * FROM pokemon_evolution WHERE pokemon_id = :pokemonId")
    fun getEvolutionsByPokemonId(pokemonId: Int): List<PokemonEvolutionEntity>

    @Transaction
    @Query("SELECT * FROM pokemon_evolution WHERE evolution_id = :evolutionId")
    fun getEvolutionsById(evolutionId: Int): List<PokemonEvolutionEntity>
}