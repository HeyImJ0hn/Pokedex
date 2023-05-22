package dam_a47471.pokedex.model

import androidx.room.*

@Entity(
    tableName = "pokemon",
    foreignKeys = [
        ForeignKey(
            entity = RegionEntity::class, parentColumns = ["region_id"],
            childColumns = ["region_id"]
        )
    ],
)
data class PokemonEntity(
    @PrimaryKey
    @ColumnInfo(name = "pkId")
    var pkId: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "imageUrl")
    var imageUrl: String,
    @ColumnInfo(name = "region_id")
    var regionId: Int
)

@Entity(tableName = "pokemon_region")
data class RegionEntity(
    @PrimaryKey
    @ColumnInfo(name = "region_id")
    var id: Int,
    @ColumnInfo(name = "region_name")
    var name: String,
    @ColumnInfo(name = "bg")
    val bg: Int,
    @ColumnInfo(name = "starters")
    val starters: Int
)

data class RegionWithPokemons(
    @Embedded
    val region: RegionEntity,
    @Relation(
        parentColumn = "region_id",
        entityColumn = "region_id"
    )
    val pokemon: List<PokemonEntity>
)

@Entity(tableName = "pokemon_type")
data class TypeEntity(
    @PrimaryKey
    @ColumnInfo(name = "typeId")
    var typeId: Int,
    @ColumnInfo(name = "type_name")
    var name: String,
    @ColumnInfo(name = "icon")
    val icon: Int,
    @ColumnInfo(name = "color")
    val color: Int
)

@Entity(primaryKeys = ["pkId", "typeId"])
data class PokemonTypesCrossRef(
    val pkId: Int,
    val typeId: Int
)

data class PokemonWithTypes(
    @Embedded val pokemon: PokemonEntity,
    @Relation(
        parentColumn = "pkId",
        entityColumn = "typeId",
        associateBy = Junction(PokemonTypesCrossRef::class)
    )
    val types: List<TypeEntity>
)

@Entity(tableName = "pokemon_details")
data class PokemonDetailsEntity(
    @PrimaryKey
    @ColumnInfo(name = "details_id")
    var id: Int,
    @ColumnInfo(name = "weight")
    var weight: Float,
    @ColumnInfo(name = "height")
    var height: Float,
    @ColumnInfo(name = "ability_name")
    var ability: String,
)

@Entity(tableName = "pokemon_stats")
data class PokemonStatsEntity(
    @PrimaryKey
    @ColumnInfo(name = "stats_id")
    var id: Int,
    @ColumnInfo(name = "hp")
    var hp: Int,
    @ColumnInfo(name = "attack")
    var atk: Int,
    @ColumnInfo(name = "defense")
    var def: Int,
    @ColumnInfo(name = "special_attack")
    var spAtk: Int,
    @ColumnInfo(name = "special_defense")
    var spDef: Int,
    @ColumnInfo(name = "speed")
    var spd: Int
)

data class PokemonWithDetailsAndStats(
    @Embedded val pokemon: PokemonEntity,
    @Relation(
        parentColumn = "pkId",
        entityColumn = "details_id"
    )
    val detail: PokemonDetailsEntity,
    @Relation(
        parentColumn = "pkId",
        entityColumn = "stats_id"
    )
    val stats: PokemonStatsEntity
)