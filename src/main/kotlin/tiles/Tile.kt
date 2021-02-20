package tiles

data class Tile(var zone: Zone? = null, var space: Space? = null, var room: Room? = null, var baseFeature: Feature? = null)

enum class Direction {
    None, North, South, East, West
}