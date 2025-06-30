package top.superyaxi

inline fun <reified T> getReturnTypeName(): T {
    println("\n\t"+T::class.java.name)
    return when (T::class) {
        String::class -> "aaa" as T
        Int::class -> 111 as T
        Boolean::class -> false as T
        else -> throw UnsupportedOperationException("No default value for type ${T::class}")
    }
}

fun main() {

    // 使用
    val test1 = getReturnTypeName<String>() // 返回 "java.lang.String"
    println("\n\t"+test1)

    val test2: Int = getReturnTypeName()
    println("\n\t"+test2)

}
