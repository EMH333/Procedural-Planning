package tiles

import java.awt.Color

class Room(val roomType: String, val indoors: Boolean) : Level() {
    override fun getColor(): Color {
        TODO("not implemented")
    }
}