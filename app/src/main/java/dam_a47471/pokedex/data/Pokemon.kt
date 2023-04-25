package dam_a47471.pokedex.data

data class Pokemon(
    var id: Int,
    var name: String,
    var imageUrl: String,
    var region: PokemonRegion?,
    var types: List<PokemonType>
) : java.io.Serializable
