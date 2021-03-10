package generation

import kotlin.math.roundToInt
import kotlin.random.Random

/**
 * Creates a noise map with a size, the patches will be more filled in the bigger follow is, and the trigger determines how much is filled
 */
fun createMap(size: Int, follow: Int = 8, trigger: Int = 0, seed: Int = (Math.random() * 1000000).roundToInt()): BooleanGrid {
    val grid = BooleanGrid(size)
    val random = Random(seed)
    for (y in 0..size) {
        for (x in 0..size) {
            var n = random.nextInt(-100, 100)

            if (y >= 1 && grid.getPos(x, y - 1)) {
                n += follow
            }
            if (x >= 1 && grid.getPos(x - 1, y)) {
                n += follow
            }
            if (y >= 1 && x >= 1 && grid.getPos(x - 1, y - 1)) {
                n += follow
            }

            if (n > trigger) {
                grid.setPos(x, y)
            }
        }
    }

    return grid
}

fun groupMap(grid: BooleanGrid, minAround: Int = 4): BooleanGrid {
    val size = grid.size
    grid.forEachWithStableGrid { set, pos, oldGrid ->
        var count = if (set) {
            1
        } else {
            0
        }

        if (oldGrid.getPos(pos.up(size))) count++
        if (oldGrid.getPos(pos.down(1))) count++
        if (oldGrid.getPos(pos.left())) count++
        if (oldGrid.getPos(pos.right(1, size))) count++
        if (oldGrid.getPos(pos.up(size).left())) count++
        if (oldGrid.getPos(pos.down(1).right(1, size))) count++
        if (oldGrid.getPos(pos.left().down(1))) count++
        if (oldGrid.getPos(pos.right(1, size).up(size))) count++

        count > minAround
    }

    return grid
}