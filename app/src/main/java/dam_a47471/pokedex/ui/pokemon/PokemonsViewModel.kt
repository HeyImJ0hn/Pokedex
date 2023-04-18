package dam_a47471.pokedex.ui.pokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dam_a47471.pokedex.data.Pokemon
import dam_a47471.pokedex.data.PokemonRegion
import dam_a47471.pokedex.domain.PokemonDomain

class PokemonsViewModel : ViewModel() {
    private val pokemonDomain = PokemonDomain()
    private lateinit var listPokemons: LiveData<List<Pokemon>>
    fun getListPokemonsByRegion(region: PokemonRegion): LiveData<List<Pokemon>> {
        listPokemons = pokemonDomain.getPokemonsByRegion(region)
        return listPokemons
    }
}
