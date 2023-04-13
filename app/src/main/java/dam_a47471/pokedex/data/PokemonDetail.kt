package dam_a47471.pokedex.data

data class PokemonDetail(
    var pokemon: Pokemon,
    var description: String,
    var types:List<PokemonType>?,
    var weight:Double?,
    var height: Double?,
    var stats: PokemonStats?,
    var evolution: List<PokemonEvolution>?
)

