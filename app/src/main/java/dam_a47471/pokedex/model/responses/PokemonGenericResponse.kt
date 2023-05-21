package dam_a47471.pokedex.model.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonGenericResponse(
    @field:Json(name = "url") val url: String?,
    @field:Json(name = "name") val name: String,
)
