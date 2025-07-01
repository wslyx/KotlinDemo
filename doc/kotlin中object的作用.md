在 Kotlin 中，`object` 是一个关键字，主要用于创建**单例对象**和**匿名内部类的对象**。它解决了 Java 中需要手动实现单例模式的问题，并提供了一种简洁的语法。

### 1. 单例对象（Object Declaration）
这是最常见的用法：声明一个**立即创建且全局唯一的单例对象**（线程安全）。

```kotlin
object DatabaseManager {
    init {
        println("DatabaseManager initialized")
    }
    
    fun connect() {
        println("Database connected")
    }
}

// 使用：
fun main() {
    DatabaseManager.connect()  // 直接通过类名调用
    // 输出： 
    // DatabaseManager initialized
    // Database connected
}
```

特点：
- 立即初始化（首次访问时初始化）
- 线程安全
- 不能有构造函数
- 可以继承类和接口

### 2. 伴生对象（Companion Object）
作为类的"静态成员"的容器，替代 Java 的 `static` 关键字。

```kotlin
class User(val name: String) {
    companion object {
        private const val DEFAULT_NAME = "Guest"
        
        fun createGuest(): User {
            return User(DEFAULT_NAME)
        }
    }
}

// 使用：
fun main() {
    val guest = User.createGuest() // 类似静态方法调用
    println(guest.name) // 输出：Guest
}
```

特点：
- 在类加载时初始化
- 可以有名称（默认名称 `Companion`）
- 可以实现接口

### 3. 对象表达式（Object Expression）
替代 Java 的匿名内部类，可继承类或实现接口。

```kotlin
interface ClickListener {
    fun onClick()
}

fun main() {
    // 创建匿名对象
    val myListener = object : ClickListener {
        override fun onClick() {
            println("Button clicked!")
        }
    }
    
    myListener.onClick() // 输出：Button clicked!
    
    // 还可声明临时属性：
    val adHocObject = object {
        val x = 10
        val y = 20
    }
    println(adHocObject.x + adHocObject.y) // 输出：30
}
```

特点：
- 立即执行（类似匿名类）
- 可访问当前作用域的变量（闭包）
- 不支持返回类型（类型是隐式的）

### 关键区别
| **特性**          | 单例对象                 | 伴生对象                 | 对象表达式               |
|--------------------|--------------------------|--------------------------|--------------------------|
| **创建时机**       | 首次访问时初始化         | 类加载时初始化           | 立即执行                 |
| **数量**           | 全局唯一实例             | 每个类一个               | 每次表达式创建新实例     |
| **命名**           | 必须命名                 | 可选命名（默认Companion）| 匿名                     |
| **继承/实现**      | 支持                     | 支持                     | 支持                     |

### 典型使用场景
- 单例对象：全局配置、工具类、服务管理
- 伴生对象：工厂方法、类常量、替代Java静态成员
- 对象表达式：事件监听器、临时对象、实现接口的快捷方式

### 示例：单例实现接口
```kotlin
interface Logger {
    fun log(message: String)
}

object FileLogger : Logger {
    override fun log(message: String) {
        println("Writing to log file: $message")
    }
}

fun main() {
    FileLogger.log("System started") // 输出：Writing to log file: System started
}
```

总之，Kotlin 的 `object` 关键字通过三种形态简化了面向对象设计中的常用模式，是语言设计实用性的典范。