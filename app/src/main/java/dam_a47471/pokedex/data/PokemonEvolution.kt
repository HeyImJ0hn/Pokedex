package dam_a47471.pokedex.data

import dam_a47471.pokedex.model.PokemonEntity

data class PokemonEvolution(
    var id: Int,
    var pokemon: Pokemon,
    var isBaby: Boolean,
    var minLevel: Int?,
    var item: String?,
    var minHappiness: Int?,
    var time: String?
)
