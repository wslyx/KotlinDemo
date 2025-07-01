package top.superyaxi.delegation

import kotlin.properties.Delegates

fun nameChange(oldName: String, newName: String) {
    println("属性 name 的旧值：$oldName -> 新值：$newName")
    println("多行测试")
}

class User {
    // Delegates.observable() 函数接受两个参数: 第一个是初始化值, 第二个是属性值变化事件的响应器(handler)。
    // 在属性赋值后会执行事件的响应器(handler)，它有三个参数：被赋值的属性、旧值和新值
    var name: String by Delegates.observable("初始值") {
        prop, old, new -> (nameChange(old, new))
    }
    var age: Int by Delegates.observable(0) {
        prop, old, new -> println("属性 ${prop.name} 的旧值：$old -> 新值：$new")
    }
    var height: Float by Delegates.observable(0f, {
        prop, old, new -> println("属性 ${prop.name} 的旧值：$old -> 新值：$new")
    })
}

fun main(args: Array<String>) {
    val user = User()
    user.name = "第一次赋值"
    user.name = "第二次赋值"
    user.age = 18
    user.age = 19
    user.height = 1.8f
    user.height = 1.9f
}
