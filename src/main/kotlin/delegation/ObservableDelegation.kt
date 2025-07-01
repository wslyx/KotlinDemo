package top.superyaxi.delegation

import kotlin.properties.Delegates

class User {
    // Delegates.observable() 函数接受两个参数: 第一个是初始化值, 第二个是属性值变化事件的响应器(handler)。
    // 在属性赋值后会执行事件的响应器(handler)，它有三个参数：被赋值的属性、旧值和新值
    var name: String by Delegates.observable("初始值") {
            // 被赋值的属性、旧值、新值
            prop, old, new ->
        println("属性 $prop 的旧值：$old \t\t-> 新值：$new")
    }
}

fun main(args: Array<String>) {
    val user = User()
    user.name = "第一次赋值"
    user.name = "第二次赋值"
}
