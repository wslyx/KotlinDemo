package top.superyaxi.klassAndObject

import java.util.*

// 实现Comparable接口定义排序规则
data class Person(
    val name: String,
    val age: Int,
    val joinDate: Date = Date()  // 默认值为当前时间
) : Comparable<Person> {

    // 重写compareTo方法：先按年龄倒序，年龄相同按姓名字典序，最后按加入日期
    override fun compareTo(other: Person): Int {
        // 第一优先级：年龄倒序（年龄大的排前面）
        if (this.age != other.age) {
            return other.age - this.age  // 注意：降序排列
        }

        // 第二优先级：姓名升序
        val nameCompare = this.name.compareTo(other.name, ignoreCase = true)
        if (nameCompare != 0) return nameCompare

        // 第三优先级：加入日期倒序（最新加入的排前面）
        return other.joinDate.compareTo(this.joinDate)
    }
}

fun main() {
    // 创建测试数据（使用固定时间戳确保测试可重复）
    val calendar = Calendar.getInstance().apply {
        set(2023, Calendar.JANUARY, 1)
    }

    // 创建可变的日期对象用于测试
    val date1 = calendar.time
    calendar.add(Calendar.DAY_OF_MONTH, 1)
    val date2 = calendar.time
    calendar.add(Calendar.DAY_OF_MONTH, 1)
    val date3 = calendar.time

    val persons = listOf(
        Person("Alice", 30, date1),
        Person("Bob", 25),
        Person("charlie", 30, date2),
        Person("Alice", 30, date3),
        Person("david", 25)
    )

    println("========== 排序前 ==========")
    persons.forEach { println(it) }

    // 使用sorted()进行排序（依赖Comparable实现）
    val sortedPersons = persons.sorted()

    // 使用reversed()演示反转排序
    val reversedList = sortedPersons.reversed()

    println("\n========== 排序后 ==========")
    sortedPersons.forEach { println(it) }

    println("\n========== 反转排序 ==========")
    reversedList.forEach { println(it) }
}