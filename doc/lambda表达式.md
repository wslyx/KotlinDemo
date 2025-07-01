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