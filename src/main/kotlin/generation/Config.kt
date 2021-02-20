package generation

//placement priority
typealias Priority = Int;
//placement count
typealias GenCount = Int;

typealias ZoneID = String;
typealias SpaceID = String;
typealias FeatureID = String;
typealias RoomID = String;


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
    val min_count: GenCount,
    val max_count: GenCount,
    val min_area: Int,
    val max_area: Int,
    val must_be_near: HashMap<ZoneID, Priority>,
    val must_not_be_near: HashMap<ZoneID, Priority>,
)


data class SpaceConfig(
    val id: SpaceID,
    val min_count: GenCount,
    val max_count: GenCount,
    val generate_in: HashMap<ZoneID, Priority>,
    val min_area: Int,
    val max_area: Int,
    val must_be_near: HashMap<SpaceID, Priority>,
    val must_not_be_near: HashMap<SpaceID, Priority>,
)


data class RoomConfig(
    val id: RoomID,
    val min_count: GenCount,
    val max_count: GenCount,
    val generate_in: HashMap<SpaceID, Priority>,
    val min_area: Int,
    val max_area: Int,
    val must_be_near: HashMap<RoomID, Priority>,
    val must_not_be_near: HashMap<RoomID, Priority>,
)


data class FeatureConfig(
    val id: FeatureID,
    val min_count: GenCount,
    val max_count: GenCount,
    val generate_in: HashMap<RoomID, Priority>,
    val must_be_near: HashMap<FeatureID, Priority>,
    val must_not_be_near: HashMap<FeatureID, Priority>,
)