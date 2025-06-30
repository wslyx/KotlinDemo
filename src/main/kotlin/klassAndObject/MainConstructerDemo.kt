package top.superyaxi.klassAndObject

class DemoDataKlass constructor(var a: Int, var b: String, var c: Boolean) {
    init {
        println("DemoDataKlass init")
        println("a: $a, b: $b, c: $c")
    }
    override fun toString(): String {
        return "DemoDataKlass(a=$a, b='$b', c=$c)"
    }
}

fun main() {
    val demoDataKlass = DemoDataKlass(1, "STRING DEMO", true)
    println(demoDataKlass)
}
