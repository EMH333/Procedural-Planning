package generation

import tiles.Grid
import tiles.Tile
import tiles.Zone

class Generator {
    fun generateMap(config: GenerationConfig): Grid {
        val grid = Grid()
        grid.generateGrid(config.map_size)

        //very simple zone generation
        val zoneIter = config.zones.iterator()
        for (x in 0..config.map_size) {
            if (!zoneIter.hasNext()) {
                break
            }
            val zoneConfig = zoneIter.next()
            val zone = Zone(zoneConfig.id)
            for (y in 0..config.map_size) {
                //don't go on any longer if max area will be reached
                if (y > zoneConfig.max_area) break

                val gridPos = grid.getGridPos(x, y)
                if (gridPos.tile == null) {
                    gridPos.tile = Tile(zone = zone)
                } else {
                    gridPos.tile!!.zone = zone
                }
                grid.setGridPos(x, y, gridPos.tile!!)
            }
        }

        return grid
    }
}