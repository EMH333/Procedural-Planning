import generation.*
import java.awt.EventQueue

fun main(args: Array<String>) {
    println("Hello World")
    val gen = Generator()
    val config = GenerationConfig(
        5,
        arrayOf(
            ZoneConfig(
                id = "short",
                max_area = 3
            ),
            ZoneConfig(
                id = "Normal"
            )
        ),
        arrayOf(),
        arrayOf(),
        arrayOf(),
    )
    val grid = gen.generateMap(config)
    println(grid)
    println("\n\n\n\n\n")
    //var booleanNoise = createMap(10,8, 20)
    //booleanNoise = groupMap(booleanNoise, 4)
    //println(booleanNoise.findArea(3).unwrap())
    //println(booleanNoise)

    EventQueue.invokeLater {
        val game = GraphicalInterface(grid)
        game.isVisible = true
    }
}