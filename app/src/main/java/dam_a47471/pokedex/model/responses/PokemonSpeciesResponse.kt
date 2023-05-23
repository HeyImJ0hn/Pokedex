package dam_a47471.pokedex.model.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonSpeciesResponse(
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "egg_groups") val eggGroups: List<PokemonGenericResponse>?,
    @field:Json(name = "evolution_chain") val evoChain: PokemonUrlResponse?,
    @field:Json(name = "flavor_text_entries") val textEntries: List<PokemonFlavorTextEntry>?
)

@JsonClass(generateAdapter = true)
data class PokemonFlavorTextEntry(
    @field:Json(name = "flavor_text") val flavorText: String?
)