package top.superyaxi.nullSafe

fun main() {
    // 1. 声明可空变量
    val name: String? = null
    val age: Int? = 25
    val address: String? = "New York"

    // 2. 安全调用操作符 ?.
    println("Name length: ${name?.length}")  // 输出 null 而不是崩溃
    println("Address uppercase: ${address?.uppercase()}") // 正常调用

    // 3. Elvis 操作符 ?: 提供默认值
    val safeName = name ?: "Unknown"
    val nameLength = name?.length ?: 0
    println("Safe name: $safeName") // 输出 "Unknown"

    // 4. 非空断言 !! (慎用)
    try {
        val forceLength = name!!.length // 抛出 NullPointerException
    } catch (e: NullPointerException) {
        println("Caught NPE: ${e.message}")
    }

    // 5. 安全类型转换 as?
    val anyObject: Any? = "Hello"
    val str: String? = anyObject as? String
    val num: Int? = anyObject as? Int // 返回 null 而不是 ClassCastException
    println("Casted string: $str, Casted int: $num")

    // 6. 配合条件判断
    if (address != null) {
        println("Address length is ${address.length}") // 自动智能转换为非空类型
    }

    // 7. 集合过滤可空元素
    val list = listOf(1, 2, null, 4)
    val nonNullList: List<Int> = list.filterNotNull()
    println("Non-null list: $nonNullList") // [1, 2, 4]

    testMethods()
}

class User(val name: String, val age: Int)

// 8. let 安全调用作用域函数
fun processUser(user: User?) {
    user?.let {
        println("Processing ${it.name} (${it.age} years old)")
    } ?: println("User is null")
}

// 9. 扩展函数处理空值
fun String?.printWithDefault(default: String) = println(this ?: default)

// 10. 返回值类型保护
fun parseNumber(str: String): Int? {
    return str.toIntOrNull()
}

// 测试各种方法
fun testMethods() {
    processUser(User("Alice", 30)) // 正常处理
    processUser(null)              // 输出"User is null"

    val nullableString: String? = null
    nullableString.printWithDefault("Default Text") // 输出"Default Text"

    val validNumber = parseNumber("42") // 返回 Int(42)
    val invalidNumber = parseNumber("abc") // 返回 null
    println("Valid number: $validNumber, Invalid number: $invalidNumber")
}