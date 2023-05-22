package dam_a47471.pokedex.model.persistent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dam_a47471.pokedex.model.PokemonDetailsEntity

@Dao
interface DetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetail(pokemon: PokemonDetailsEntity)

    @Transaction
    @Query("SELECT * FROM pokemon_details WHERE details_id = :detailsId")
    fun getDetailsById(detailsId: Int): PokemonDetailsEntity

    @Query("SELECT COUNT(*) FROM pokemon_details WHERE details_id = :pokemonId")
    fun getDetailsCountByPokemonId(pokemonId: Int): Int
}