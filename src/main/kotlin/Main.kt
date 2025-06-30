package top.superyaxi

import javaDemo.JvmInteraction

fun main() {
    val name = "Kotlin"

    println("Hello, $name!")

    for (i in 1..5) {
        println("i = $i")
    }

    println("--------------")

    val a: Int = getReturnTypeName()
    println(a)

    // 调用javaDemo.jvmInteraction的Java方法
    // TODO java.lang.ClassNotFoundException: javaDemo.JvmInteraction
    var jvmInteraction = JvmInteraction()
    println(jvmInteraction.test())

}
