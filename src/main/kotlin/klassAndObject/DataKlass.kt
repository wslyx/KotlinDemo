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
    // toString() 测试
    println(dataKlass)
    // 复制，同 Java Clone
    val copyDataKlass = dataKlass.copy()
    // equals() 测试
    println("equals测试:" + copyDataKlass.equals(dataKlass))
    // hashCode() 测试
    println("hashCode测试{ copy:" + copyDataKlass.hashCode() + " dataKlass:" + dataKlass.hashCode() + " }")
    // 修改copy后的属性
    copyDataKlass.a = 1001
    println("修改copy属性后的数据类")
    // equals() 测试
    println("equals测试:" + copyDataKlass.equals(dataKlass))
    // hashCode() 测试
    println("hashCode测试{ copy:" + copyDataKlass.hashCode() + " dataKlass:" + dataKlass.hashCode() + " }")
    // componentN() 测试
    println("component1测试:" + copyDataKlass.component1())
    println("component2测试:" + copyDataKlass.component2())
    println("component3测试:" + copyDataKlass.component3())
}

/*
kotlin的==与equals()说明
 */