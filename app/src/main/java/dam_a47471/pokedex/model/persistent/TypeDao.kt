package dam_a47471.pokedex.model.persistent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import dam_a47471.pokedex.model.TypeEntity

@Dao
interface TypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertType(type: TypeEntity)
}
