package generation

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.unwrap
import tiles.*

class Generator {
    fun generateMap(config: GenerationConfig): Grid {
        val grid = Grid()
        grid.generateGrid(config.map_size)

        generateZones(grid, config)
        generateSpaces(grid, config)

        return grid
    }

    private fun generateZones(grid: Grid, config: GenerationConfig) {
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
                val gridPos = grid.getGridPos(a.col, a.row)
                if (gridPos.tile == null) {
                    gridPos.tile = Tile(zone = zone)
                } else {
                    if(gridPos.tile!!.zone != null) {
                        throw Error("Zone is already filled")
                    }
                    gridPos.tile!!.zone = zone
                }
                grid.setGridPos(a.col, a.row, gridPos.tile!!)
            }

            //add to boolean grid
            zoneGrid.addToBlocklist(area.first, area.second)

        }
    }

    private fun generateSpaces(grid: Grid, config: GenerationConfig) {
        //TODO
        // should sort spaces by zones to go into
        // then for each zone, take the spaces and run them through equal distribution
        // and then file the zones similar to how the zones fill the grid
        // special attention will need to be paid to finding valid spots (in the requested zone) to put the spaces
    }
}