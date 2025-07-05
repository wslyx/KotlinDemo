package top.superyaxi.klassAndObject

data class DataKlass (
    var a: Int = 999,
    var b: String = "STRING DEMO",
    var c: Boolean = false
) {
    // 额外的属性
    var d: Float = 1.1f
    var e: Double = 2.2
    var f: Char = 'a'
    override fun toString(): String {
        return "数据类DEMO (a=$a, b=$b, c=$c, d=$d, e=$e, f=$f)"
    }
}

fun main() {
    val dataKlass = DataKlass()
    dataKlass.a = 1000
    dataKlass.b = "NEW STRING DEMO"
    dataKlass.c = true
    dataKlass.d = 1.6f
    // toString() 测试
    println(dataKlass)
    // 复制，同 Java Clone
    val copyDataKlass = dataKlass.copy()
    // equals() 测试
    println("equals测试:" + copyDataKlass.equals(dataKlass))
    // hashCode() 测试
    println("hashCode测试{ copy:" + copyDataKlass.hashCode() + " dataKlass:" + dataKlass.hashCode() + " }")
    // 内存地址比较
    println("copyDataKlass与dataKlass内存地址比较:" + (copyDataKlass === dataKlass))
    println("自内存比较:" + (copyDataKlass === copyDataKlass))
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