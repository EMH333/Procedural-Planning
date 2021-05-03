package tiles

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class GridPositionIteratorTest {

    @Test
    fun hasNext() {
        val iterator = GridPositionIterator(GridPos(1, 0), GridPos(0, 1))
        for (i in 0..3) {
            assertTrue(iterator.hasNext())
            iterator.next()
        }
        assertFalse(iterator.hasNext())
    }

    @Test
    operator fun next() {
        val iterator = GridPositionIterator(GridPos(1, 0), GridPos(0, 1))
        val list = ArrayList<GridPos>()
        for (i in 0..5) {
            if (iterator.hasNext()) {
                list.add(iterator.next())
            }
        }
        assertEquals(arrayListOf(GridPos(1, 0), GridPos(1, 1), GridPos(0, 0), GridPos(0, 1)), list)
    }

    @Test
    fun weirdCase() {
        val iterator = GridPositionIterator(GridPos(4, 2), GridPos(1, 5))
        val list = ArrayList<GridPos>()
        for (i in 0..20) {
            if (iterator.hasNext()) {
                list.add(iterator.next())
            }
        }
        assertEquals(16, list.size)
    }
}