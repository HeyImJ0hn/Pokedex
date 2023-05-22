package dam_a47471.pokedex.data

data class PokemonDetail(
    var id: Int,
    var description: String,
    var weight:Float?,
    var height: Float?,
    var ability: String?,
    var evolution: List<PokemonEvolution>?
)

