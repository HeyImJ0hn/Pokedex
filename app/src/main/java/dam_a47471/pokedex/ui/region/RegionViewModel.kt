package dam_a47471.pokedex.ui.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam_a47471.pokedex.data.PokemonRegion
import dam_a47471.pokedex.domain.PokemonDomain
import dam_a47471.pokedex.model.repository.RegionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegionViewModel : ViewModel() {
    private val _pokemonDomain = PokemonDomain()
    private var _regionRepository: RegionRepository? = null
    private var _regions: MutableLiveData<List<PokemonRegion>> = MutableLiveData()

    fun initViewMode(regionRepository: RegionRepository) {
        _regionRepository = regionRepository
    }

    fun getRegions(): LiveData<List<PokemonRegion>> {
        val regions = MutableLiveData<List<PokemonRegion>>()
        viewModelScope.launch(Dispatchers.Default) {
            val r = _regionRepository?.getRegions()
            if (r != null) {
                regions.postValue(r.value)
            }
        }
        return regions
    }
}
