package tiles

class Grid {
    public val grid: ArrayList<ArrayList<GridPos>> = ArrayList(ArrayList())
    fun getGridPos(x: Int, y: Int): GridPos {
        return grid[x][y]
    }

    fun setGridPos(x: Int, y: Int, tile: Tile) {
        grid[x][y].tile = tile
    }

    fun generateGrid(size: Int) {
        for (x in 0..size) {
            for (y in 0..size) {
                grid[x][y] = GridPos(y, x)
            }
        }
    }
}

data class GridPos(val row: Int, val col: Int, var tile: Tile? = null) {
    fun posEquals(gridPos: GridPos): Boolean {
        return row == gridPos.row && col == col
    }
}