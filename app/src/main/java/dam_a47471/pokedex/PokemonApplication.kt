package dam_a47471.pokedex

import android.app.Application
import dam_a47471.pokedex.PokemonContainer

class PokemonApplication : Application() {
    /**
     * Provides centralised Repository throughout the app
     */
    lateinit var pkContainer : PokemonContainer
    override fun onCreate() {
        super.onCreate()
        pkContainer = PokemonContainer(applicationContext)
    }
}
