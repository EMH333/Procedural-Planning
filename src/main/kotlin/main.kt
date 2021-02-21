import generation.GenerationConfig
import generation.Generator
import generation.ZoneConfig
import generation.createMap

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
    val booleanNoise = createMap(50,8, 20)
    println(booleanNoise)
}