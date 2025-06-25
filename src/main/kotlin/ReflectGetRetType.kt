package top.superyaxi

inline fun <reified T> getTypeName(): String {
    return T::class.java.name // 可以直接访问类型信息
}

fun main() {
    // 使用
    val typeName = getTypeName<String>() // 返回 "java.lang.String"
    println("\n\t"+typeName)
}
