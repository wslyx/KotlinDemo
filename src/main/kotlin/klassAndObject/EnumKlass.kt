package top.superyaxi.klassAndObject

enum class Color {
    RED,BLACK,BLUE,GREEN,WHITE
}

enum class Color2(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    println("Enum Demo 01")
    println(Color.RED)
    println(Color.BLUE)
    println(Color.GREEN)
    println(Color.BLACK)
    println(Color.WHITE)
    println("Enum Demo 02")
    println(Color2.RED)
    println(Color2.GREEN)
    println(Color2.BLUE)
    println(Color2.RED.rgb.toHexString())
    println(Color2.GREEN.rgb.toHexString())
    println(Color2.BLUE.rgb.toHexString())
}
