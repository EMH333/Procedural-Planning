package tiles

import java.awt.Color

class Feature(val featureType: String, var facing: Direction, var subFeatures: ArrayList<Feature>) : Level() {
    override fun getColor(): Color {
        TODO("not implemented")
    }
}