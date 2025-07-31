package reflect

import java.beans.PropertyDescriptor
import kotlin.reflect.full.memberProperties

// 1. 定义类似Java Bean的类
data class User(
    var name: String = "",
    var age: Int = 0,
    var isActive: Boolean = false  // 注意布尔属性规范
) : java.io.Serializable

fun main() {
    // 2. 创建对象实例
    val user = User("Alice", 25, true)
    println("初始状态: $user")  // 初始状态: User(name=Alice, age=25, isActive=true)
    
    // 3. 使用PropertyDescriptor获取属性描述符
    val clazz = user.javaClass
    val properties = listOf("name", "age", "active")  // 布尔属性用字段名(无'is'前缀)
    
    properties.forEach { propName ->
        // 4. 创建属性描述符
        val pd = PropertyDescriptor(propName, clazz)
        
        println("\n属性: '$propName'")
        println("Getter: ${pd.readMethod?.name}")
        println("Setter: ${pd.writeMethod?.name}")
        
        // 5. 使用反射读取属性值
        val currentValue = pd.readMethod?.invoke(user)
        println("当前值: $currentValue (${currentValue?.javaClass?.simpleName})")
        
        // 6. 修改属性值
        when (propName) {
            "name" -> pd.writeMethod?.invoke(user, "Bob")
            "age" -> pd.writeMethod?.invoke(user, 30)
            "active" -> pd.writeMethod?.invoke(user, false)
        }
    }
    
    // 7. 显示修改后的结果
    println("\n修改后: $user")  // 修改后: User(name=Bob, age=30, isActive=false)
    
    // 8. 额外：使用Kotlin反射对比
    println("\nKotlin反射结果:")
    User::class.memberProperties.forEach { prop ->
        println("${prop.name}: ${prop.get(user)} (${prop.returnType})")
    }
}