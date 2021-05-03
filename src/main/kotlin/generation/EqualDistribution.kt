package generation

import java.lang.Integer.max
import java.lang.Integer.min

class EqualDistribution {
    companion object {
        /**
         * Attempt to find a distribution for the total space where each item has a decent amount of space
         * the items corresponds to <name, <min, max>> and returns <name, recommended space>
         */
        fun distributeWithMaxAndMin(totalSpace: Int, items: Map<String, Pair<Int, Int>>): Map<String, Int> {
            val out = HashMap<String, Int>()
            //if they would all fit in the space when using max
            val totalMax = items.map { max(it.value.first, it.value.second).toLong() }.sum()
            if (totalMax <= totalSpace) {
                items.forEach {
                    out[it.key] = max(it.value.first, it.value.second)
                }
                return out
            }

            //otherwise we need to start at the minimum for each space and go from there
            items.forEach {
                out[it.key] = min(it.value.first, it.value.second)
            }
            var spaceRemaining: Int = totalSpace
            val tooBig = ArrayList<String>()
            while (spaceRemaining > 0) {
                //find item that has least space till max
                val smallestDifference = out.mapValues {
                    val item = items[it.key]!!
                    val max = max(item.first, item.second)
                    max - it.value
                }.filterNot { tooBig.contains(it.key) }.minByOrNull { it.value }

                //take that item and add one to the it's allocated space
                if (smallestDifference != null) {
                    out.replace(smallestDifference.key, (out[smallestDifference.key]?.plus(1)!!))
                    if (out[smallestDifference.key]!! >= max(items[smallestDifference.key]!!.first, items[smallestDifference.key]!!.second)) {
                        tooBig.add(smallestDifference.key)
                    }
                    spaceRemaining -= 3 //Try and be conservative here
                }
            }
            return out
        }
    }
}