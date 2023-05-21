package dam_a47471.pokedex.model.persistent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dam_a47471.pokedex.model.RegionEntity

@Dao
interface RegionDao {
    @Query("SELECT * FROM pokemon_region")
    fun getRegions() : List<RegionEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRegion(region: RegionEntity)
    @Query("SELECT COUNT(*) FROM pokemon_region")
    fun count(): Int
}
