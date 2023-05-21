package dam_a47471.pokedex.model.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonResponse(
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "url") val url: String?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "height") val height: Float?,
    @field:Json(name = "weight") val weight: Float?,
)

@JsonClass(generateAdapter = true)
data class PokemonTypeResponse(
    @field:Json(name = "slot") val slot: Int?,
    @field:Json(name = "type") val type: PokemonGenericResponse?,
)