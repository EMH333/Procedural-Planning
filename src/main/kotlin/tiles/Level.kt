package tiles

import pointInsidePolygon
import java.awt.Color
import java.util.*
import kotlin.collections.ArrayList

abstract class Level(var points: ArrayList<GridPos> = ArrayList(), val uuid: UUID = UUID.randomUUID()) {
    abstract fun getColor(): Color
    fun isPointInside(pos: GridPos): Boolean {
        if (points.size == 1){
            return pos.posEquals(points[0])
        }
        return pointInsidePolygon(pos, points.toTypedArray())
    }
}