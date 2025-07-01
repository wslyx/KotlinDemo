在 Kotlin 中，**lambda 表达式并不限制只能写一行**。你可以编写包含多行代码的 lambda 表达式，只需注意以下几点：

---

### ✅ 多行 Lambda 的写法
```kotlin
val multiLineLambda = { 
    println("第一行")
    val result = 10 * 5
    println("计算结果：$result")
    result // 最后一行作为返回值
}
```

---

### ✅ 关键规则
1. **隐式返回值**  
   **最后一行表达式**的值会作为 Lambda 的返回值（不需要写 `return`）：
   ```kotlin
   val sum = { a: Int, b: Int ->
       val total = a + b
       total // 相当于 return total（禁止显式写 return）
   }
   println(sum(2, 3)) // 输出：5
   ```

2. **显式 `return` 的限制**  
   在 Lambda 中直接写 `return` 会**退出外层函数**（而非仅退出 Lambda）：
   ```kotlin
   fun test() {
       listOf(1, 2, 3).forEach {
           if (it == 2) return // 直接退出 test() 函数！
           println(it)
       }
       println("这里不会执行")
   }
   // 输出：1
   ```

---

### ✅ 需要显式返回值的场景
通过标签或 `run` 实现 Lambda 内部的局部返回：
```kotlin
listOf(1, 2, 3).forEach {
    if (it == 2) return@forEach // 仅退出当前 Lambda
    println(it)
}
// 输出：1 3
```

---

### ✅ 单行简化写法
当 Lambda **只有一行**时，可省略参数名和箭头（用 `it` 代替）：
```kotlin
val doubled = listOf(1, 2, 3).map { it * 2 } 
// 等效于 { item -> item * 2 }
```

---

### 总结
| 情况               | 是否合法 | 示例                                     |
|--------------------|----------|------------------------------------------|
| **多行 Lambda**     | ✅       | `{ a -> println(a); a*2 }`             |
| **单行 Lambda**     | ✅       | `{ it * 2 }`                          |
| **显式写 `return`** | ❌       | 会意外退出外层函数（除非配合标签）     |
| **最后一行作为返回值** | ✅       | 不需要 `return` 关键字                  |

根据需求自由选择单行或多行写法，Kotlin 的 Lambda 非常灵活！ 🚀

在 Kotlin 中，lambda 表达式有丰富的语法规则和特性，以下全面归纳了关键语法规则：

---

### 🧩 1. 基本结构
```kotlin
val lambda: (参数类型) -> 返回类型 = { 参数 -> 表达式 }
```
示例：
```kotlin
val sum = { a: Int, b: Int -> a + b }
```

---

### 🎯 2. 参数类型规则
- **显式声明**：
  ```kotlin
  val greet: (String) -> Unit = { name -> println("Hello $name") }
  ```
- **类型推断**（声明时可省略参数类型）：
  ```kotlin
  val cube: (Int) -> Int = { num -> num * num * num }
  ```
- **隐式参数名`it`**（单参数时）：
  ```kotlin
  list.map { it * 2 }  // it 代表当前元素
  ```

---

### 🔄 3. 多语句 Lambda
最后一行作为返回值：
```kotlin
val processor = { data: String ->
    println("Processing $data")
    val processed = data.uppercase().reversed()
    processed  // 返回值
}
```

---

### ⚠️ 4. 返回语句的特殊性
| 场景 | 行为 | 示例 |
|------|------|------|
| **`return`不带标签** | 退出外层函数 | `fun main() { list.forEach { return } }` |
| **带标签的`return@`** | 仅退出当前 lambda | `list.forEach { return@forEach }` |
| **默认返回** | 最后一行作为返回值 | `{ 1; 2 }` 返回 2 |

---

### 🚪 5. 尾随 Lambda（Trailing Lambda）
当 lambda 是函数的**最后一个参数**时：
```kotlin
// 标准调用
run({ println("Executing") })

// 尾随 lambda 语法
run { 
    println("Clean syntax")
    "Result" 
}
```

---

### 👥 6. 参数规则
- **无参数**：使用空参数列表或省略
  ```kotlin
  val getAnswer = { -> 42 }  // 或 { 42 }
  ```
- **多个参数**：
  ```kotlin
  val join = { s1: String, s2: String -> "$s1-$s2" }
  ```
- **下划线`_`忽略参数**：
  ```kotlin
  map.forEach { _, value -> print(value) }
  ```

---

### 🔧 7. Lambda 作为函数参数
```kotlin
fun calculate(a: Int, b: Int, op: (Int, Int) -> Int): Int {
    return op(a, b)
}

// 调用
calculate(5, 3) { x, y -> x * y }  // 尾随 lambda
```

---

### 🧪 8. 带接收者的 Lambda
（类似扩展函数）
```kotlin
val appendDot: StringBuilder.() -> Unit = {
    this.append(".")  // this 指接收者对象
}

StringBuilder("Hi").appendDot()  // 结果: "Hi."
```

---

### 🪄 9. 限定作用域（`with`/`apply`）
```kotlin
val personData = with(person) {
    // 此处可直接访问 person 的属性
    "${name}, ${age}"
}
```

---

### 🧬 10. Lambda 的实例化
Lambda 会编译成 `FunctionN` 接口实例：
```kotlin
val isEven: (Int) -> Boolean = { it % 2 == 0 }
println(isEven is Function1<Int, Boolean>)  // true
```

---

### 📦 11. 内联优化（`inline`）
使用 `inline` 可避免 Lambda 的运行时开销：
```kotlin
inline fun safeExecute(block: () -> Unit) {
    try { block() } catch (e: Exception) { /*...*/ }
}

// 编译后代码会直接插入调用处
```

---

### 🧮 12. Lambda 类型别名
增强可读性：
```kotlin
typealias ClickHandler = (View) -> Unit

fun setOnClick(handler: ClickHandler) {
    //...
}
```

---

### 🔀 13. Lambda 的 Java 互操作
```java
// Java 代码中调用 Kotlin Lambda
ktObject.setListener(param -> System.out.println(param));
```

---

### ⚡ 重要注意事项
1. **Lambda 中的局部变量**  
   访问的局部变量会成为 Lambda 的"捕获变量"（隐式`final`）
   ```kotlin
   var counter = 0
   val inc = { counter++ }  // 编译器自动包装
   ```

2. **Lambda vs 匿名函数**  
   匿名函数用 `fun` 关键字，支持显式`return`：
   ```kotlin
   list.map(fun(item): String { return item.toString() })
   ```

3. **不可变约束**  
   Lambda 内部无法修改闭包变量的引用（只能修改对象内容）

---

### 💡 最佳实践
1. **优先使用尾随 lambda 语法**  
   `view.setOnClickListener { ... }` 比 `view.setOnClickListener({ ... })` 更清晰

2. **避免多层嵌套**  
   深层嵌套时考虑提取函数：
   ```kotlin
   data.flatMap { element ->  // 2层嵌套
       element.items.map { item ->  // ← 提取这个内部 lambda
           item.toResult()
       } 
   }
   ```

---

Kotlin 的 lambda 语法在保持简洁的同时，通过丰富的规则确保了类型安全和表达力，是函数式编程的核心工具！🚀