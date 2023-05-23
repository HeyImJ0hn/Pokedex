package dam_a47471.pokedex.model.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonEvolutionChainResponse(
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "chain") val chain: PokemonChainResponse?
)

@JsonClass(generateAdapter = true)
data class PokemonChainResponse(
    @field:Json(name = "evolution_details") val evoDetails: List<PokemonEvoDetails>?,
    @field:Json(name = "evolves_to") val evolvesTo: List<PokemonChainResponse>?,
    @field:Json(name = "species") val species: PokemonGenericResponse?
)

@JsonClass(generateAdapter = true)
data class PokemonEvoDetails(
    @field:Json(name = "min_level") val minLevel: Int?,
    @field:Json(name = "min_happiness") val minHappiness: Int?,
    @field:Json(name = "item") val item: String?,
    @field:Json(name = "time_of_day") val time: String?
)