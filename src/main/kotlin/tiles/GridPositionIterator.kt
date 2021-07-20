package tiles

import java.lang.Error
import kotlin.math.max
import kotlin.math.min

class GridPositionIterator(private val first: GridPos, private val second: GridPos) : Iterator<GridPos> {
    private var currentGridPos: GridPos
    private var topLeft: GridPos
    private var bottomRight: GridPos

    init {
        val rightCol = max(first.col, second.col)
        val leftCol = min(first.col, second.col)
        val topRow = max(first.row, second.row)
        val bottomRow = min(first.row, second.row)
        topLeft = GridPos(topRow, leftCol)
        bottomRight = GridPos(bottomRow, rightCol)
        currentGridPos = topLeft.left(Int.MIN_VALUE)

        if (topLeft.col > bottomRight.col || topLeft.row < bottomRight.row) {
            throw Error("Iterable params are not in order; Internal Error")
        }
    }

    override fun hasNext(): Boolean {
        return currentGridPos != bottomRight
    }

    override fun next(): GridPos {
        if (currentGridPos.col >= bottomRight.col && currentGridPos != bottomRight) {
            currentGridPos = currentGridPos.down(1, Int.MIN_VALUE).copyWithCol(topLeft.col - 1)
        }
        currentGridPos = currentGridPos.right()
        return currentGridPos
    }
}