package generation

class BooleanGrid(size: Int) {
    private val grid: ArrayList<ArrayList<Boolean>> = ArrayList()
    private var savedSize: Int = size

    init {
        for (x in 0..size) {
            grid.add(ArrayList())
            for (y in 0..size) {
                grid[x].add(false)
            }
        }
    }

    fun setPos(x: Int, y: Int) {
        grid[x][y] = true
    }

    fun getPos(x: Int, y: Int): Boolean {
        return grid[x][y]
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (y in 0..savedSize) {
            for (x in 0..savedSize) {
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