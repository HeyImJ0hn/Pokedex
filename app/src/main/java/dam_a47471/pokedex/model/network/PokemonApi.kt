package dam_a47471.pokedex.model.network

import dam_a47471.pokedex.model.responses.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int = 200,
        @Query("offset") offset: Int = 0
    ): PokemonListBaseResponse<PokemonResponse>

    @GET("region")
    suspend fun fetchRegionList(): PokemonListBaseResponse<PokemonRegionsResponse>

    @GET("generation/{id}")
    suspend fun fetchPokemonByRegionId(@Path("id") id: Int): PokemonByRegionResponse

    @GET("pokemon/{id}")
    suspend fun fetchPokemonDetailById(@Path("id") id: Int): PokemonDetailResponse

    @GET("pokemon-species/{id}")
    suspend fun fetchPokemonSpeciesById(@Path("id") id: Int): PokemonSpeciesResponse

    @GET("evolution-chain/{id}")
    suspend fun fetchEvolutionChainById(@Path("id") id: Int): PokemonEvolutionChainResponse

    @GET("type")
    suspend fun fetchPokemonTypes(): PokemonListBaseResponse<PokemonGenericResponse>
}
