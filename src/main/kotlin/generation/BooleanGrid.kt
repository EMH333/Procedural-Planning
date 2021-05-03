package generation

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import tiles.GridPos
import kotlin.math.min
import kotlin.math.sqrt

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
        val square = findSquareArea(fixedArea)
        val first = square.first
        if (first.col == -1) {
            return Err(Error("No square with that size"))
        }
        //if perfect fit
        val areaSide = sqrt(fixedArea.toDouble()).toInt()
        if (areaSide * areaSide == fixedArea) {
            return Ok(Pair(first, first.down(areaSide).right(areaSide)))
        }
        var second = first.down(areaSide).right(areaSide, this.size-1)
        val areaGood = fun(): Boolean { return first.area(second) > fixedArea }
        while (areaGood()) {
            second = second.left()
            if (!areaGood()) {
                second.right()
                break
            }
            second = second.up()
            if (!areaGood()) {
                second.down()
                break
            }
        }
        return Ok(Pair(first, second))
    }

    /**
     * finds a square at least the size of the one specified and a pair containing the max size and it's start point
     * The points returned are the top left of the square
     */
    private fun findSquareArea(area: Int): Pair<GridPos, Pair<Int, GridPos>> {
        var maxSide = 0
        var maxPos = GridPos(-1, -1)
        var found = GridPos(-1, -1)
        val dp = Array(size) { IntArray(size) }
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (!getPos(i, j)) continue
                if (i == 0 || j == 0) dp[i][j] = 1 else {
                    dp[i][j] = min(dp[i][j - 1], dp[i - 1][j])
                    dp[i][j] = 1 + min(dp[i][j], dp[i - 1][j - 1])
                }
                if (dp[i][j] > maxSide) {
                    maxSide = dp[i][j]
                    maxPos = GridPos(j, i)
                }
                if (dp[i][j] * dp[i][j] >= area && found.col == -1) {
                    found = GridPos(j, i)
                }
            }
        }
        return Pair(found, Pair(maxSide * maxSide, maxPos))
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