package generation

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.recover
import com.github.michaelbull.result.unwrap
import tiles.*

class Generator {
    fun generateMap(config: GenerationConfig): Grid {
        val grid = Grid()
        grid.generateGrid(config.map_size)

        //very simple zone generation
        val zoneIter = config.zones.iterator()
        val zoneGrid = BooleanGrid(config.map_size)
        zoneGrid.allowAll() // allow placement anywhere

        val zoneDistributionPreData = HashMap<String, Pair<Int, Int>>()
        config.zones.forEach {
            zoneDistributionPreData[it.id] = Pair(it.min_area, it.max_area)
        }
        val zoneDistributionRec = EqualDistribution.distributeWithMaxAndMin(zoneGrid.countOfOpenPos(), zoneDistributionPreData)

        while (zoneIter.hasNext()) {
            val zoneConfig = zoneIter.next()
            val zone = Zone(zoneConfig.id)


            //Try recommended area, then max, then min
            val tryRec = zoneDistributionRec[zoneConfig.id]!!
            var output = zoneGrid.findArea(tryRec)
            output.onFailure {
                output = zoneGrid.findArea(zoneConfig.max_area)
            }
            output.onFailure {
                output = zoneGrid.findArea(zoneConfig.min_area)
            }
            val area = output.unwrap()

            //add zone to grid
            for (a in GridPositionIterator(area.first, area.second)) {
                if(a.col > 50 || a.row > 50){
                    println("IDK")
                }
                val gridPos = grid.getGridPos(a.col, a.row)
                if (gridPos.tile == null) {
                    gridPos.tile = Tile(zone = zone)
                } else {
                    gridPos.tile!!.zone = zone
                }
                grid.setGridPos(a.col, a.row, gridPos.tile!!)
            }

            //add to boolean grid
            zoneGrid.addToBlocklist(area.first, area.second)

            /*for (y in 0..config.map_size) {
                //don't go on any longer if max area will be reached
                if (y > zoneConfig.max_area) break

                val gridPos = grid.getGridPos(x, y)
                if (gridPos.tile == null) {
                    gridPos.tile = Tile(zone = zone)
                } else {
                    gridPos.tile!!.zone = zone
                }
                grid.setGridPos(x, y, gridPos.tile!!)
            }*/
        }

        return grid
    }
}