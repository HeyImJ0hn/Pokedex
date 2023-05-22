package dam_a47471.pokedex.model.persistent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dam_a47471.pokedex.model.PokemonStatsEntity

@Dao
interface StatsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStats(stats: PokemonStatsEntity)

    @Transaction
    @Query("SELECT * FROM pokemon_stats WHERE stats_id = :statsId")
    fun getStatsById(statsId: Int): PokemonStatsEntity
}