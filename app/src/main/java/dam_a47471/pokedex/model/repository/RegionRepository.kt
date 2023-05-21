package dam_a47471.pokedex.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dam_a47471.pokedex.data.PokemonRegion
import dam_a47471.pokedex.model.network.PokemonApi
import dam_a47471.pokedex.model.persistent.RegionDao

class RegionRepository(
    private val pokemonApi: PokemonApi,
    private val regionDao: RegionDao
) {
    suspend fun getRegions(): LiveData<List<PokemonRegion>> {
        val hasRegions = regionDao.count()
        if (hasRegions == 10) {
            val regionsEntities = regionDao.getRegions()
            val regions = regionsEntities.map {
                PokemonMapper.toRegionModel(it)
            }
            return MutableLiveData(regions)
        }
        try {
            val regionsResponse = pokemonApi.fetchRegionList()
            val regions = regionsResponse.results?.map {
                PokemonMapper.toRegionModel(it)
            }
            regions?.forEach {
                val r = PokemonMapper.toRegionEntity(it)
                regionDao.insertRegion(r)
            }
            return MutableLiveData(regions)
        } catch (e: java.lang.Exception) {
            Log.e("ERROR", e.toString())
        }
        return MutableLiveData<List<PokemonRegion>>()
    }
}
