package generation

import tiles.Grid
import tiles.GridPos

class BooleanGrid(val size: Int, fillFalse: Boolean = true) {
    private val grid: ArrayList<ArrayList<Boolean>> = ArrayList()
    private var initalized: Boolean = !fillFalse
    private val blocklist: ArrayList<Pair<GridPos,GridPos>> = ArrayList()

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
        if(isPosInBlocklist(GridPos(y,x))){
            return false
        }
        return grid[x][y]
    }

    fun getPos(gridPos: GridPos): Boolean {
        if(isPosInBlocklist(gridPos)){
            return false
        }
        return grid[gridPos.col][gridPos.row]
    }

    fun addToBlocklist(first: GridPos, second: GridPos){
        val row1: Int
        val row2: Int
        val col1: Int
        val col2: Int
        if(first.col > second.col){
            col1 = first.col
            col2 = second.col
        }else{
            col2 = first.col
            col1 = second.col
        }

        if(first.row > second.row){
            row1 = first.row
            row2 = second.row
        }else{
            row2 = first.row
            row1 = second.row
        }
        blocklist.add(Pair(GridPos(row1, col1), GridPos(row2, col2)))
    }

    private fun isPosInBlocklist(pos: GridPos): Boolean {
        for (square in blocklist){
            if(pos.col <= square.first.col && pos.col >= square.second.col){
                if(pos.row <= square.first.row && pos.row >= square.second.row){
                    return true
                }
            }
        }
        return false
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