package generation

//placement priority
typealias Priority = Int
//placement count
typealias GenCount = Int

typealias ZoneID = String
typealias SpaceID = String
typealias FeatureID = String
typealias RoomID = String


data class GenerationConfig(
    val map_size: Int,
    val zones: Array<ZoneConfig>,
    val spaces: Array<SpaceConfig>,
    val rooms: Array<RoomConfig>,
    val features: Array<FeatureConfig>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GenerationConfig

        if (map_size != other.map_size) return false
        if (!zones.contentEquals(other.zones)) return false
        if (!spaces.contentEquals(other.spaces)) return false
        if (!rooms.contentEquals(other.rooms)) return false
        if (!features.contentEquals(other.features)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = map_size
        result = 31 * result + zones.contentHashCode()
        result = 31 * result + spaces.contentHashCode()
        result = 31 * result + rooms.contentHashCode()
        result = 31 * result + features.contentHashCode()
        return result
    }
}


data class ZoneConfig(
    val id: ZoneID,
    val min_count: GenCount = 0,
    val max_count: GenCount = GenCount.MAX_VALUE,
    val min_area: Int = 0,
    val max_area: Int = Int.MAX_VALUE,
    val must_be_near: Map<ZoneID, Priority>? = null,
    val must_not_be_near: Map<ZoneID, Priority>? = null,
)


data class SpaceConfig(
    val id: SpaceID,
    val generate_in: Map<ZoneID, Priority>,
    val min_count: GenCount = 0,
    val max_count: GenCount = GenCount.MAX_VALUE,
    val min_area: Int = 0,
    val max_area: Int = Int.MAX_VALUE,
    val must_be_near: Map<SpaceID, Priority>? = null,
    val must_not_be_near: Map<SpaceID, Priority>? = null,
)


data class RoomConfig(
    val id: RoomID,
    val min_count: GenCount,
    val max_count: GenCount,
    val generate_in: HashMap<SpaceID, Priority>,
    val min_area: Int,
    val max_area: Int,
    val must_be_near: Map<RoomID, Priority>,
    val must_not_be_near: Map<RoomID, Priority>,
)


data class FeatureConfig(
    val id: FeatureID,
    val min_count: GenCount,
    val max_count: GenCount,
    val generate_in: Map<RoomID, Priority>,
    val must_be_near: Map<FeatureID, Priority>,
    val must_not_be_near: Map<FeatureID, Priority>,
)