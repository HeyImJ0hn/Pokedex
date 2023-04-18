package dam_a47471.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dam_a47471.pokedex.data.PokemonRegion
import dam_a47471.pokedex.domain.PokemonDomain

class RegionViewModel : ViewModel() {

    private val _pokemonDomain = PokemonDomain()

    fun getRegions(): LiveData<List<PokemonRegion>> {
        return _pokemonDomain.getAllRegions()
    }

}