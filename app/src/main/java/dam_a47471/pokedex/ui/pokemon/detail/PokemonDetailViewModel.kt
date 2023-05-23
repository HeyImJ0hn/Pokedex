package dam_a47471.pokedex.ui.pokemon.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam_a47471.pokedex.data.Pokemon
import dam_a47471.pokedex.model.PokemonWithDetailsAndStats
import dam_a47471.pokedex.model.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonDetailViewModel : ViewModel() {
    private lateinit var pokemon: LiveData<Pokemon>
    private var _pokemonRepository: PokemonRepository? = null

    fun initViewMode(pkRepository: PokemonRepository) {
        _pokemonRepository = pkRepository
    }

    fun getPokemonDetail(pokemon: Pokemon): LiveData<PokemonWithDetailsAndStats> {
        val pokemonDetail: MutableLiveData<PokemonWithDetailsAndStats> = MutableLiveData()
        viewModelScope.launch(Dispatchers.Default) {
            val r = _pokemonRepository?.getDetailsByPokemon(pokemon)!!
            withContext(Dispatchers.Main) {
                pokemonDetail.postValue(r.value)
            }
        }
        return pokemonDetail
    }
}
