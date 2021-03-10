package tiles

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Grid {
    public val grid: ArrayList<ArrayList<GridPos>> = ArrayList()
    fun getGridPos(x: Int, y: Int): GridPos {
        return grid[x][y]
    }

    fun setGridPos(x: Int, y: Int, tile: Tile) {
        grid[x][y].tile = tile
    }

    fun generateGrid(size: Int) {
        for (x in 0..size) {
            grid.add(x, ArrayList())
            grid[x].ensureCapacity(size)
            for (y in 0..size) {
                grid[x].add(GridPos(y, x))
            }
        }
    }

    override fun toString(): String {
        return "Grid(grid=$grid)"
    }
}

data class GridPos(val row: Int, val col: Int, var tile: Tile? = null) {
    fun posEquals(gridPos: GridPos): Boolean {
        return row == gridPos.row && col == gridPos.col
    }

    fun left(min: Int = 0): GridPos {
        return GridPos(row, max(col - 1, min), tile)
    }

    fun right(distance: Int = 1, max: Int = Int.MAX_VALUE): GridPos {
        return GridPos(row, min(col + distance, max), tile)
    }

    fun up(max: Int = Int.MAX_VALUE): GridPos {
        return GridPos(min(row + 1, max), col, tile)
    }

    fun down(distance: Int = 1, min: Int = 0): GridPos {
        return GridPos(max(row - distance, min), col, tile)
    }

    fun area(gridPos: GridPos): Int {
        return (abs(row - gridPos.row) + 1) * (abs(col - gridPos.col) + 1)
    }
}