package generation

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.unwrap
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import tiles.GridPos
import java.lang.Integer.max

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
    fun largeFindAreaTest() {
        val gridSize = 50
        val requestedArea = 848
        val grid = BooleanGrid(gridSize)
        grid.allowAll()
        grid.addToBlocklist(GridPos(0, 0), GridPos(0, 2))
        val result = grid.findArea(requestedArea)

        assertTrue(result is Ok)
        var maxValue = 0
        val pos = result.unwrap()
        maxValue = max(maxValue, pos.first.row)
        maxValue = max(maxValue, pos.first.col)
        maxValue = max(maxValue, pos.second.row)
        maxValue = max(maxValue, pos.second.col)
        assertTrue(maxValue < gridSize, "Max value was: $maxValue")

        val area = result.unwrap().first.area(result.unwrap().second)
        assertTrue(area >= requestedArea, "Area was: $area") // area greater than requested
    }

    @Test
    fun noAreaLargeEnough() {
        val gridSize = 5
        val requestedArea = 848
        val grid = BooleanGrid(gridSize)
        grid.allowAll()

        val result = grid.findArea(requestedArea)

        assertTrue(result is Err)
    }

    @Test
    fun perfectArea() {
        val gridSize = 6 //TODO this may not be right still
        val requestedArea = 25
        val grid = BooleanGrid(gridSize)
        grid.allowAll()

        val result = grid.findArea(requestedArea)

        assertTrue(result is Ok)
        assertEquals(requestedArea, result.unwrap().first.area(result.unwrap().second))
    }

    //TODO: Areas still overlap so it isn't perfect. There is some bug that causes areas to ignore the block list

    @Test
    fun countOfPosTest() {
        val grid = BooleanGrid(5)
        assertEquals(0, grid.countOfOpenPos())
        grid.allowAll()
        assertEquals(25, grid.countOfOpenPos())
    }
}