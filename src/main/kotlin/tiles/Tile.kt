package tiles

data class Tile(var zone: Zone?, var space:Space?, var room:Room?, var baseFeature:Feature?)

enum class Direction {
    None, North, South, East, West
}