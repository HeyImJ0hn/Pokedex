package dam_a47471.pokedex.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dam_a47471.pokedex.data.Pokemon
import dam_a47471.pokedex.data.PokemonDetail
import dam_a47471.pokedex.data.PokemonRegion
import dam_a47471.pokedex.data.PokemonType
import dam_a47471.pokedex.data.mocks.PokemonMockData

class PokemonDomain {

    fun getAllRegions(): LiveData<List<PokemonRegion>> {
        return MutableLiveData<List<PokemonRegion>>(PokemonMockData.regions)
    }

    fun getAllPokemons(): LiveData<List<Pokemon>> {
        return MutableLiveData<List<Pokemon>>(PokemonMockData.pokemons)
    }

    fun getPokemonsByRegion(region: PokemonRegion): LiveData<List<Pokemon>> {
        return MutableLiveData<List<Pokemon>>(PokemonMockData.pokemons.filter {
            it.region == region
        })
    }

    fun getPokemonTypes(): List<PokemonType> {
        return ArrayList<PokemonType>(PokemonMockData.pokemonTypeMock)
    }

    fun getPokemonDetail(pokemon: Pokemon): LiveData<PokemonDetail> {
        return MutableLiveData(PokemonMockData.pokemonDetail.find {
            it.pokemon == pokemon })
    }

}