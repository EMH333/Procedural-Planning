package generation

import tiles.Grid
import tiles.GridPos

class BooleanGrid(val size: Int, fillFalse: Boolean = true) {
    private val grid: ArrayList<ArrayList<Boolean>> = ArrayList()
    private var initalized: Boolean = !fillFalse

    init {
        if(!initalized) {
            for (x in 0..size) {
                grid.add(ArrayList())
                for (y in 0..size) {
                    grid[x].add(false)
                }
            }
            initalized = true
        }
    }

    constructor(bg:BooleanGrid) : this(bg.size, false) {
        for (x in 0..size) {
            grid.add(ArrayList())
            for (y in 0..size) {
                val old = bg.getPos(x,y)
                grid[x].add(old)
            }
        }
        initalized = true
    }

    fun setPos(x: Int, y: Int) {
        grid[x][y] = true
    }

    fun getPos(x: Int, y: Int): Boolean {
        return grid[x][y]
    }

    fun getPos(gridPos: GridPos): Boolean {
        return grid[gridPos.col][gridPos.row]
    }

    fun forEachWithStableGrid(action: (set:Boolean, pos: GridPos, oldGrid:BooleanGrid) -> Boolean){
        val stable = BooleanGrid(this)
        grid.forEachIndexed { x, it ->
            it.forEachIndexed{y, b ->
                grid[x][y] = action(b, GridPos(y,x), stable)
            }
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (y in 0..size) {
            for (x in 0..size) {
                if (getPos(x,y)) {
                    sb.append("x")
                }else{
                    sb.append(" ")
                }
            }
            sb.append("\n")
        }
        return sb.toString()
    }
}