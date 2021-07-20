package tiles

import java.awt.Color

class Zone(val zoneType: String) : Level() {
    override fun getColor(): Color {
        TODO("not implemented")
    }

    override fun toString(): String {
        return "$zoneType-$uuid"
    }
}