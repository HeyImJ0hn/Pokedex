package dam_a47471.pokedex.data

import kotlin.random.Random

data class PokemonStats(
    var id: Int = 0,
    var hp:Int= Random.nextInt(1,300),
    var attack:Int= Random.nextInt(1,300),
    var defense:Int= Random.nextInt(1,300),
    var specialAttack: Int= Random.nextInt(1, 300),
    var specialDefense: Int= Random.nextInt(1,300),
    var speed: Int= Random.nextInt(1,300),
    var exp:Int= 0,
    var maxHp: Int= 300,
    var maxSpeed: Int= 300,
    var maxAttack: Int= 300,
    var maxDefense: Int= 300,
    var maxSpecialAttack: Int= 300,
    var maxSpecialDefense: Int= 300,
    var maxExp: Int= 300
)
