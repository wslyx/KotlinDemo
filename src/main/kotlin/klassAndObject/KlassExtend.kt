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

fun main(arg:Array<String>){
    val user = User("Runoob")
    user.print()
    println("类对象输出:$user")
}
