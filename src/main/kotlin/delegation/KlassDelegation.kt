package top.superyaxi.delegation

/*
    类委托示例
 */

// 创建接口
interface Base {
    fun print()
}

// 实现此接口的被委托的类
class BaseImpl(val x: Int) : Base {
    override fun print() { print(x) }
}

// 通过关键字 by 建立委托类
class Derived(servant: Base) : Base by servant

fun main(args: Array<String>) {
    val servant = BaseImpl(10)
    // 将master的任务委派给servant
    val master = Derived(servant)
    master.print() // 输出 10
}
