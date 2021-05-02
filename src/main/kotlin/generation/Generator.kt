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
        for (x in 0..config.map_size) {
            if (!zoneIter.hasNext()) {
                break
            }
            val zoneConfig = zoneIter.next()
            val zone = Zone(zoneConfig.id)


            //if max area doesn't work, find with min area instead
            var output = zoneGrid.findArea(zoneConfig.max_area)
            output = output.recover {
                zoneGrid.findArea(zoneConfig.min_area).unwrap()
            }
            val area = output.unwrap()

            //add zone to grid
            for(a in GridPositionIterator(area.first, area.second)){
                val gridPos = grid.getGridPos(a.col,a.row)
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