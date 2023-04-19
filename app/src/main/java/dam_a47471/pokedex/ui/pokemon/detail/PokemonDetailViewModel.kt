package dam_a47471.pokedex.ui.pokemon.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dam_a47471.pokedex.data.Pokemon
import dam_a47471.pokedex.data.PokemonDetail
import dam_a47471.pokedex.domain.PokemonDomain

class PokemonDetailViewModel : ViewModel() {
    private val pokemonDomain = PokemonDomain()
    private lateinit var pokemon: LiveData<Pokemon>
    private lateinit var pokemonDetail: LiveData<PokemonDetail>
    fun getPokemonDetail(pokemon: Pokemon): LiveData<PokemonDetail> {
        pokemonDetail = pokemonDomain.getPokemonDetail(pokemon)
        return pokemonDetail
    }
}
