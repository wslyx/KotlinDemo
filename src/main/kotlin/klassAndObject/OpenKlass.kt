package top.superyaxi.klassAndObject

open class C // 开放类，允许被继承

class D : C() // D 继承自 C

// 为 C 定义扩展函数
fun C.foo() = "c"

// 为 D 定义扩展函数（但不会被 printFoo 调用）
fun D.foo() = "d"

// 参数类型声明为 C → 调用 C.foo()
fun printFoo(c: C) {
    println(c.foo()) // 输出 "c"
}

fun main() {
    val c = C()
    val d = D()
    printFoo(c) // 输出 "c"
    printFoo(d) // 输出 "c"
}

/*
代码执行分析：
fun main(arg: Array<String>) {
    printFoo(D()) // 输出："c"
}

关键点：扩展函数是静态解析的（基于声明类型，而非运行时类型）。
    printFoo(c: C) 的参数类型是 C，在编译时已确定为 C 类型。
    尽管实际传入 D()（子类对象），但扩展函数 foo() 的调用取决于参数声明类型 C，因此调用 fun C.foo() = "c"。

扩展函数特性：
    非多态性:
        不像类的成员函数具备多态（动态绑定），扩展函数在编译时根据接收者类型确定。
    优先级规则:
        若类已有同名成员函数，成员函数优先于扩展函数。但此例中 C 无成员函数 foo()，故使用扩展。
 */