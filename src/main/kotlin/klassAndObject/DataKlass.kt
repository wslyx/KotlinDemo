package top.superyaxi.klassAndObject

data class DataKlass (
    var a: Int = 999,
    var b: String = "STRING DEMO",
    var c: Boolean = false
) {
    override fun toString(): String {
        return "数据类DEMO (a=$a, b=$b, c=$c)"
    }
}

fun main() {
    val dataKlass = DataKlass()
    dataKlass.a = 1000
    dataKlass.b = "NEW STRING DEMO"
    dataKlass.c = true
    println(dataKlass)
}
