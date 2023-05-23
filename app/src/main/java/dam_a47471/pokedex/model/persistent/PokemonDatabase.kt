package dam_a47471.pokedex.model.persistent

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dam_a47471.pokedex.data.PokemonDetail
import dam_a47471.pokedex.model.*

@Database(
    entities = [PokemonEntity::class, RegionEntity::class, TypeEntity::class,
        PokemonTypesCrossRef::class, PokemonDetailsEntity::class, PokemonStatsEntity::class, PokemonEvolutionEntity::class], version = 1, exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun regionDao(): RegionDao
    abstract fun typeDao(): TypeDao
    abstract fun detailDao(): DetailDao
    abstract fun statsDao(): StatsDao
    abstract fun evoDao(): EvolutionDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: PokemonDatabase? = null
        fun getInstance(context: Context): PokemonDatabase {
            if (instance != null) return instance!!
            synchronized(this) {
                instance = Room
                    .databaseBuilder(context, PokemonDatabase::class.java, "pokedex_dabase")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}