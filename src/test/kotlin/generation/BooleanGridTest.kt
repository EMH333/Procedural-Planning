package generation

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import tiles.GridPos

internal class BooleanGridTest {

    @Test
    fun blocklistTest() {
        val grid = BooleanGrid(5)
        grid.setPos(0,0)
        grid.setPos(4,4)
        grid.addToBlocklist(GridPos(0,0), GridPos(2,2))

        assertFalse(grid.getPos(0,0))
        assertTrue(grid.getPos(4,4))
    }
}