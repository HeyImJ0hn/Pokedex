package dam_a47471.pokedex.data

import androidx.annotation.DrawableRes

data class PokemonRegion(
    var id: Int,
    var name: String,
    @DrawableRes val bg: Int,
    @DrawableRes val starters: Int
)
