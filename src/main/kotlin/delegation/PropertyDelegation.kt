package top.superyaxi.delegation

import kotlin.reflect.KProperty

// 定义包含属性委托的类
class Example {
    var p: String by Delegate()
}

// 委托的类
class Delegate {
    // 定义Map存储属性值
    private val map = mutableMapOf<String, String>()

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        println("$thisRef, 这里委托了 ${property.name} 属性")
        // 返回实际的属性值
        var value = "未设置"
        try {
            value = map.getValue(property.name)
        } catch (exception: NoSuchElementException) {

        }
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$thisRef 的 ${property.name} 属性赋值为 $value")
        map[property.name] = value
    }
}

fun main(args: Array<String>) {
    val e = Example()
    println(e.p)     // 访问该属性，调用 getValue() 函数

    e.p = "Runoob"   // 调用 setValue() 函数
    println(e.p)
}
