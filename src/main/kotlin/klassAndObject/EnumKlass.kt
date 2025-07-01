package top.superyaxi.klassAndObject

enum class Color {
    RED,BLACK,BLUE,GREEN,WHITE
}

// 默认包含属性 ordinal(索引) name(实例名称)
// 其他自定义属性像data class一样，在构造函数括号中定义
enum class Color2 constructor (val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    println("Enum Demo 01")
    println("Color.RED: ${Color.RED}")
    println("Color.BLUE: ${Color.BLUE}")
    println("Color.GREEN: ${Color.GREEN}")
    println("Color.BLACK: ${Color.BLACK}")
    println("Color.WHITE: ${Color.WHITE}")
    println("Enum Demo 02")
    println("Color2.RED: ${Color2.RED}")
    println("Color2.GREEN: ${Color2.GREEN}")
    println("Color2.BLUE: ${Color2.BLUE}")
    println("Color2.RED.rgb: ${Color2.RED.rgb.toHexString()}")
    println("Color2.GREEN.rgb: ${Color2.GREEN.rgb.toHexString()}")
    println("Color2.BLUE.rgb: ${Color2.BLUE.rgb.toHexString()}")
    // 索引
    println("Color2.RED的ordinal索引:${Color2.RED.ordinal}")
    // name
    println("Color2.RED的name:${Color2.RED.name}")
}
