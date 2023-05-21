package dam_a47471.pokedex.model.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDetailResponse(
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "weight") val weight: Float?,
    @field:Json(name = "height") val height: Float?,
    @field:Json(name = "stats") val stats: List<PokemonStats>?,
    @field:Json(name = "types") val types: List<PokemonTypeResponse>?,
    @field:Json(name = "abilities") val abilities: List<PokemonAbilities>?
)

@JsonClass(generateAdapter = true)
data class PokemonStats(
    @field:Json(name = "base_stat") val baseStat: Int?,
    @field:Json(name = "effort") val effort: Int?,
    @field:Json(name = "stat") val statName: PokemonStatDescription?
)

@JsonClass(generateAdapter = true)
data class PokemonStatDescription(
    @field:Json(name = "name") val statName: String?
)

@JsonClass(generateAdapter = true)
data class PokemonAbilities(
    @field:Json(name = "ability") val ability: PokemonAbility?,
    @field:Json(name = "is_hidden") val is_hidden: Boolean?,
    @field:Json(name = "slot") val slot: Int?
)

@JsonClass(generateAdapter = true)
data class PokemonAbility(
    @field:Json(name = "name") val name: String?,
)