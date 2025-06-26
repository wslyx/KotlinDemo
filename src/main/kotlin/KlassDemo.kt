package top.superyaxi

class KlassDemo {

    // 静态属性
    companion object {
        var x: Int = 0
        var y: String = ""
        var z: Boolean = false
    }

    // 几个类型的属性
    var a: Int = 0
    var b: String = ""
    var c: Boolean = false
    var d: Float = 0.0f
    var e: Double = 0.0

    // 构造函数
    constructor(a: Int, b: String, c: Boolean, d: Float, e: Double) {
        this.a = a
        this.b = b
        this.c = c
        this.d = d
        this.e = e
    }

    fun print() {
        println("a = $a, b = $b, c = $c, d = $d, e = $e")
    }

    fun printStatic() {
        println("x = $x, y = $y, z = $z")
    }

    fun printAll() {
        print()
        printStatic()
    }

}

fun main() {
    val demo = KlassDemo(1, "hello", true, 1.0f, 1.0)
    demo.printAll()
}