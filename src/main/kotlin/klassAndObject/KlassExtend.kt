package top.superyaxi.klassAndObject

class User(var name:String) {
    override fun toString(): String {
        return "User { name: $name }"
    }
}

/**扩展函数**/
fun User.print(){
    println("用户名是 $name")
}

// 不属于 User 本身，但是可以像反射获取到的成员函数一样调用 User 的实例
typealias UserExternFun = User.() -> Unit

fun main(arg:Array<String>) {
    fun helloExtend(): UserExternFun = {
        println("hello $name")
    }

    fun wordExtend(): UserExternFun = {
        println("word $name")
    }

    val user = User("Runoob")
    user.print()
    println("类对象输出:$user")

    //反射获取print函数
    val print = User::print
    print.invoke(user)

    helloExtend().invoke(user)
    wordExtend()(user)
}
