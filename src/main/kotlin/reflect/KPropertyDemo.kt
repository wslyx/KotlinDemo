package reflect

import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.* // 导入扩展函数
import kotlin.reflect.jvm.isAccessible

data class Person(val name: String, var age: Int, private var id: Int = 0) {
    fun greet() = "Hello, I'm $name!"
}

fun main() {
    // 1. 获取 KClass 实例
    val personClass = Person::class

    // 2. 创建实例
    val person = personClass.primaryConstructor!!.call("Alice", 30, 0) // 使用主构造器
    val john = Person("John", 23) // 无参构造器 (name=null, age=0, id=0)

    // 3. 获取属性
    val memberProperties = personClass.memberProperties

    val nameProperty = memberProperties.find { it.name == "name" }!!
    println("John's name: ${nameProperty.get(john)}") // John's name: null

    val ageProperty = memberProperties.find { it.name == "age" } as KMutableProperty1<Person, Int>
    ageProperty.set(person, 31) // 使用反射设置 age
    println("Alice is now ${person.age}") // Alice is now 31

    // 4. 调用方法
    val greetFunction = personClass.functions.find { it.name == "greet" }!!
    val greeting = greetFunction.call(person)
    println(greeting) // Hello, I'm Alice!

    // 5. 访问私有属性 (需要 isAccessible)
    val idProperty = memberProperties.find { it.name == "id" }!!
    idProperty.isAccessible = true // 绕过访问控制
    println("Alice's ID: ${idProperty.get(person)}") // Alice's ID: 0 (默认值)

    // 6. 使用 KType (检查 name 属性的类型)
    val nameType = nameProperty.returnType
    println("Name type: ${nameType}") // kotlin.String
    println("Is name nullable? ${nameType.isMarkedNullable}") // Is name nullable? false
}