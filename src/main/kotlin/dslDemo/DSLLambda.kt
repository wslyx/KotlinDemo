package top.superyaxi.dslDemo

// 1. 定义组件基类和接收者类
abstract class Component {
    abstract fun display(indent: Int = 0)
}

class Window(val title: String) {
    private val components = mutableListOf<Component>()

    fun panel(block: Panel.() -> Unit) {
        components.add(Panel().apply(block))
    }

    fun display() {
        println("Window: $title")
        components.forEach { it.display(1) }
    }
}

class Panel : Component() {
    private val children = mutableListOf<Component>()
    var layout: String = "Flow"

    fun button(block: Button.() -> Unit) {
        children.add(Button().apply(block))
    }

    fun textField(block: TextField.() -> Unit) {
        children.add(TextField().apply(block))
    }

    override fun display(indent: Int) {
        val prefix = "  ".repeat(indent)
        println("${prefix}Panel (layout: $layout)")
        children.forEach { it.display(indent + 1) }
    }
}

class Button : Component() {
    var text: String = "Button"
    var onClick: () -> Unit = {}

    override fun display(indent: Int) {
        val prefix = "  ".repeat(indent)
        println("${prefix}Button: '$text'")
    }
}

class TextField : Component() {
    var placeholder: String = "Enter text"
    var value: String = ""

    override fun display(indent: Int) {
        val prefix = "  ".repeat(indent)
        println("${prefix}TextField: '${value.ifEmpty { placeholder }}'")
    }
}

// 2. DSL 构建函数
fun window(title: String, block: Window.() -> Unit): Window {
    return Window(title).apply(block)
}

fun main() {
    // 3. 使用多层嵌套带接收者的 Lambda
    val appWindow = window("My Application") {
        panel {
            layout = "Grid"

            button {
                text = "Login"
                onClick = { println("Login button clicked!") }
            }

            textField {
                placeholder = "Username"
                value = "admin"
            }

            button {
                text = "Help"
                onClick = {
                    // 访问外层接收者
                    println("Panel layout is: ${this@panel.layout}")
                }
            }
        }

        panel {
            layout = "Vertical"

            textField {
                placeholder = "Search..."
            }

            button {
                text = "Search"
                onClick = {
                    // 访问最外层接收者
                    println("Window title: '${this@window.title}'")
                }
            }
        }
    }

    // 显示构建结果
    appWindow.display()

    // 模拟按钮点击
    println("\n模拟按钮点击:")
    val searchPanel = appWindow.getComponents()[1] as Panel
    val searchButton = searchPanel.getChildren()[1] as Button
    searchButton.onClick()
}

// 添加访问组件的方法（避免强制类型转换）
fun Window.getComponents(): List<Component> {
    return this::class.java.getDeclaredField("components").apply { isAccessible = true }.get(this) as List<Component>
}

fun Panel.getChildren(): List<Component> {
    return this::class.java.getDeclaredField("children").apply { isAccessible = true }.get(this) as List<Component>
}
