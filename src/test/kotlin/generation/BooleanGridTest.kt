package generation

import com.github.michaelbull.result.unwrap
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import tiles.GridPos

internal class BooleanGridTest {

    @Test
    fun blocklistTest() {
        val grid = BooleanGrid(5)
        grid.setPos(0, 0)
        grid.setPos(4, 4)
        grid.addToBlocklist(GridPos(0, 0), GridPos(2, 2))

        assertFalse(grid.getPos(0, 0))
        assertTrue(grid.getPos(4, 4))
    }

    @Test
    fun findAreaTest() {
        val grid = BooleanGrid(5)
        grid.allowAll()
        val result = grid.findArea(4)
        assertTrue(result.component2() == null)
        assertTrue(result.component1() != null)

        assertTrue(result.unwrap().first.area(result.unwrap().second) >= 4) // area greater than requested
    }

    @Test
    fun countOfPosTest() {
        val grid = BooleanGrid(5)
        assertEquals(0, grid.countOfOpenPos())
        grid.allowAll()
        assertEquals(36, grid.countOfOpenPos()) //TODO: This hints at a larger problem
    }
}