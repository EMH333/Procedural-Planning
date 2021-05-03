package tiles

import java.lang.Error

class GridPositionIterator(private val topLeft: GridPos, private val bottomRight: GridPos) : Iterator<GridPos> {
    private var currentGridPos: GridPos = topLeft.left(Int.MIN_VALUE)

    init {
        if (topLeft.col > bottomRight.col || topLeft.row < bottomRight.row) {
            throw Error("Iterable params are not in order")
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