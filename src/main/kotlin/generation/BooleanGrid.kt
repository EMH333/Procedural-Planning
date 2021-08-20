package generation

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import tiles.GridPos
import kotlin.collections.ArrayList

class BooleanGrid(val size: Int, fillFalse: Boolean = true) {
    private val grid: ArrayList<ArrayList<Boolean>> = ArrayList()
    private var initalized: Boolean = !fillFalse
    private val blocklist: ArrayList<Pair<GridPos, GridPos>> = ArrayList()

    init {
        if (!initalized) {
            for (x in 0 until size) {
                grid.add(ArrayList())
                for (y in 0 until size) {
                    grid[x].add(false)
                }
            }
            initalized = true
        }
    }

    constructor(bg: BooleanGrid) : this(bg.size, false) {
        for (x in 0 until size) {
            grid.add(ArrayList())
            for (y in 0 until size) {
                val old = bg.getPos(x, y)
                grid[x].add(old)
            }
        }
        initalized = true
    }

    /***
     * Set all squares to true so it is a valid spot
     */
    fun allowAll() {
        for (x in 0 until size) {
            for (y in 0 until size) {
                setPos(x, y)
            }
        }
    }

    fun setPos(x: Int, y: Int) {
        grid[x][y] = true
    }

    fun getPos(x: Int, y: Int): Boolean {
        if (isPosInBlocklist(GridPos(y, x))) {
            return false
        }
        return grid[x][y]
    }

    fun getPos(gridPos: GridPos): Boolean {
        if (isPosInBlocklist(gridPos)) {
            return false
        }
        return grid[gridPos.col][gridPos.row]
    }

    fun addToBlocklist(first: GridPos, second: GridPos) {
        val row1: Int
        val row2: Int
        val col1: Int
        val col2: Int
        if (first.col > second.col) {
            col1 = first.col
            col2 = second.col
        } else {
            col2 = first.col
            col1 = second.col
        }

        if (first.row > second.row) {
            row1 = first.row
            row2 = second.row
        } else {
            row2 = first.row
            row1 = second.row
        }
        blocklist.add(Pair(GridPos(row1, col1), GridPos(row2, col2)))
    }

    fun findArea(area: Int): Result<Pair<GridPos, GridPos>, Error> {
        val fixedArea = if (area <= 0) {
            1
        } else {
            area
        }
        val rect = findMaxRect()

        //make sure the rect is big enough
        if (rect.first < fixedArea) {
            return Err(Error("There is no space big enough"))
        }

        val first = rect.second.first
        var second = rect.second.second
        //if perfect fit
        if (first.area(second) == fixedArea) {
            return Ok(Pair(first, second))
        }

        // else resize till we are just above the acceptable area
        val areaGood = fun(): Boolean { return first.area(second) > fixedArea }
        while (areaGood()) {
            second = second.left(first.col)
            if (!areaGood()) {
                second = second.right()
                break
            }
            second = second.down(1, first.row)
            if (!areaGood()) {
                second = second.up()
                break
            }
        }
        return Ok(Pair(first, second))
    }

    /**
     * Returns a pair w/ max area and a set of points (lower left, upper right)
     */
    private fun findMaxRect(): Pair<Int, Pair<GridPos, GridPos>> {
        var maxArea: Int = -1
        var bestLl = GridPos(-1, -1)
        var bestUr = GridPos(-1, -1)
        forEach { set, pos ->
            if (set) {
                val out = recurseFindMaxRect(pos.row, pos.col)
                if (pos.area(out) > maxArea) {
                    bestLl = pos
                    bestUr = out
                    maxArea = pos.area(out)
                }
            }
        }
        return Pair(maxArea, Pair(bestLl, bestUr))
    }

    // returns grid position of max area based off offset given
    private fun recurseFindMaxRect(rowOffset: Int, colOffset: Int): GridPos {
        var maxHeight = 0
        for (y in rowOffset + 1 until size) {
            if (getPos(colOffset, y)) {
                maxHeight++
            } else {
                break
            }
        }

        var maxWidth = 0
        if (colOffset + 1 < size && getPos(colOffset + 1, rowOffset)) {
            val out = recurseFindMaxRect(rowOffset, colOffset + 1)
            //if out row is deeper than current then we know we can update width
            if (out.row >= rowOffset + maxHeight) {
                maxWidth = out.col - colOffset
            }
        }

        return GridPos(rowOffset + maxHeight, colOffset + maxWidth)
    }

    private fun isPosInBlocklist(pos: GridPos): Boolean {
        for (square in blocklist) {
            if (pos.col <= square.first.col && pos.col >= square.second.col) {
                if (pos.row <= square.first.row && pos.row >= square.second.row) {
                    return true
                }
            }
        }
        return false
    }

    fun forEach(action: (set: Boolean, pos: GridPos) -> Unit) {
        grid.forEachIndexed { x, it ->
            it.forEachIndexed { y, b ->
                action(b, GridPos(y, x))
            }
        }
    }

    fun forEachWithStableGrid(action: (set: Boolean, pos: GridPos, oldGrid: BooleanGrid) -> Boolean) {
        val stable = BooleanGrid(this)
        grid.forEachIndexed { x, it ->
            it.forEachIndexed { y, b ->
                grid[x][y] = action(b, GridPos(y, x), stable)
            }
        }
    }

    fun countOfOpenPos(): Int {
        var count = 0
        for (x in 0 until size) {
            for (y in 0 until size) {
                if (getPos(x, y)) {
                    count++
                }
            }
        }
        return count
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (y in 0 until size) {
            for (x in 0 until size) {
                if (getPos(x, y)) {
                    sb.append("x")
                } else {
                    sb.append(" ")
                }
            }
            sb.append("\n")
        }
        return sb.toString()
    }
}