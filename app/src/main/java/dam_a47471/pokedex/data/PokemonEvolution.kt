package dam_a47471.pokedex.data

data class PokemonEvolution(
    var id: Int,
    var pokemon: Pokemon,
    var isBaby: Boolean,
    var minLevel: Int?,
    var item: String?,
    var minHappiness: Int?,
    var time: String?
)
