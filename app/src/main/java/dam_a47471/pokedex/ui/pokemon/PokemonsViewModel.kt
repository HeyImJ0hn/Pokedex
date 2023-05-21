package dam_a47471.pokedex.ui.pokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam_a47471.pokedex.data.Pokemon
import dam_a47471.pokedex.data.PokemonRegion
import dam_a47471.pokedex.domain.PokemonDomain
import dam_a47471.pokedex.model.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonsViewModel : ViewModel() {
    private val pokemonDomain = PokemonDomain()
    private lateinit var listPokemons: LiveData<List<Pokemon>>
    private var _pokemonRepository: PokemonRepository? = null
    private var _regions: MutableLiveData<List<PokemonRegion>> = MutableLiveData()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    fun initViewMode(pkRepository: PokemonRepository) {
        _pokemonRepository = pkRepository
    }

    fun getPokemonsByRegion(region: PokemonRegion): LiveData<List<Pokemon>> {
        val pokemons = MutableLiveData<List<Pokemon>>()
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.Default) {
            val r = _pokemonRepository?.getPokemonsByRegion(region)
            withContext(Dispatchers.Main) {
                if (r != null) {
                    pokemons.postValue(r.value)
                    _isLoading.value = false
                }
            }
        }
        return pokemons
    }
}
