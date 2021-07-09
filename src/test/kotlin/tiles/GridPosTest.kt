package tiles

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class GridPosTest {

    @Test
    fun posEquals() {
        val pos1 = GridPos(0,0, Tile())
        val pos2 = GridPos(0,0, Tile())
        val pos3 = GridPos(0,1)
        assertTrue(pos1.posEquals(pos2))
        assertTrue(pos2.posEquals(pos1))
        assertFalse(pos1.posEquals(pos3))
    }

    @Test
    fun area() {
        val pos1 = GridPos(0,0)
        val pos2 = GridPos(1,1)
        assertEquals(4, pos1.area(pos2))

        val pos3 = GridPos(0,0)
        assertEquals(1, pos1.area(pos3))

        val pos4 = GridPos(3,3)
        assertEquals(16, pos1.area(pos4))

        assertEquals(30, GridPos(1,29).area(GridPos(30,29)))
        assertEquals(60, GridPos(1,29).area(GridPos(30,30)))
        assertEquals(90, GridPos(1,31).area(GridPos(30,29)))
    }
}