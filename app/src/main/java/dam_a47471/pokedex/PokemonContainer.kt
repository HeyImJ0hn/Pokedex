package dam_a47471.pokedex

import android.content.Context
import dam_a47471.pokedex.domain.PokemonDomain
import dam_a47471.pokedex.model.network.NetworkModule
import dam_a47471.pokedex.model.network.PokemonApi
import dam_a47471.pokedex.model.persistent.PokemonDatabase
import dam_a47471.pokedex.model.repository.PokemonMapper
import dam_a47471.pokedex.model.repository.PokemonRepository
import dam_a47471.pokedex.model.repository.RegionRepository

class PokemonContainer(private val context: Context) {
    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: PokemonContainer? = null
        fun getInstance(context: Context): PokemonContainer {
            if (instance != null)
                return instance!!
            synchronized(this) {
                return PokemonContainer(context)
            }
        }
    }

    val pokemonClient: PokemonApi = NetworkModule.initPokemonRemoteService()
    val pokemonDomain: PokemonDomain = PokemonDomain()
    val regionRepository: RegionRepository
    val pokemonRepository: PokemonRepository
    val pokemonDBManager: PokemonDatabase = PokemonDatabase.getInstance(context)

    init {
        pokemonRepository = PokemonRepository(
            pokemonClient, pokemonDBManager.pokemonDao(),
            pokemonDBManager.typeDao()
        )
        regionRepository = RegionRepository(pokemonClient, pokemonDBManager.regionDao())
        PokemonMapper.initialize(context)
    }
}