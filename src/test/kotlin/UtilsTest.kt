import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import tiles.GridPos

internal class UtilsTest {

    @Test
    fun pointInsidePolygon() {
        assertTrue(
            pointInsidePolygon(
                GridPos(0, 0), arrayOf(
                    GridPos(1, 1),
                    GridPos(-1, 1),
                    GridPos(-1, -1),
                    GridPos(1, -1)
                )
            )
        )
        assertTrue(
            pointInsidePolygon(
                GridPos(1, 1), arrayOf(
                    GridPos(1, 1),
                    GridPos(-1, 1),
                    GridPos(-1, -1),
                    GridPos(1, -1)
                )
            )
        )
        assertFalse(
            pointInsidePolygon(
                GridPos(1, 2), arrayOf(
                    GridPos(1, 1),
                    GridPos(-1, 1),
                    GridPos(-1, -1),
                    GridPos(1, -1)
                )
            )
        )
    }

    @Test
    fun areaOfPolygon() {
        val points = listOf(
            GridPos(1, 1),
            GridPos(-1, 1),
            GridPos(-1, -1),
            GridPos(1, -1)
        )
        assertEquals(4, areaOfPolygon(points).toInt())
    }
}