package tiles

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
        return row == gridPos.row && col == col
    }
}