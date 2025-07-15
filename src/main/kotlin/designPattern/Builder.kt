package top.superyaxi.designPattern

// 1. 基础组件定义
sealed class Component {
    // 添加层级打印支持
    abstract val name: String
    abstract val properties: Map<String, Any>
    abstract val children: List<Component>

    // 递归打印UI树
    fun printTree(indent: String = "", isLast: Boolean = true) {
        val connector = if (indent.isEmpty()) "" else if (isLast) "└── " else "├── "
        print(if (indent.isEmpty()) connector else indent + connector)

        // 打印当前节点
        val props = properties.entries.joinToString(", ") { "${it.key}=${it.value}" }
        println("$name${if (props.isNotEmpty()) " [$props]" else ""}")

        // 递归打印子节点
        val childIndent = indent + if (isLast) "    " else "│   "
        children.forEachIndexed { i, child ->
            child.printTree(childIndent, i == children.lastIndex)
        }
    }
}

// 2. 具体组件实现
class ContainerComponent(
    val direction: String? = null,
    override val children: MutableList<Component> = mutableListOf()
) : Component() {
    override val name = "Container"
    override val properties = mapOf(
        "direction" to (direction ?: "default")
    )
}

class TextComponent(
    val content: String,
    override val properties: MutableMap<String, Any> = mutableMapOf()
) : Component() {
    override val name = "Text"
    override val children: List<Component> = emptyList()
}

class ImageComponent(
    val src: String,
    override val properties: MutableMap<String, Any> = mutableMapOf()
) : Component() {
    override val name = "Image"
    override val children: List<Component> = emptyList()
}

// 3. DSL 构建器
class ComponentBuilder(private val parent: Component? = null) {
    val children = mutableListOf<Component>()

    fun text(
        text: String,
        block: TextBuilder.() -> Unit = {}
    ) {
        val builder = TextBuilder(text)
        builder.block()
        children.add(builder.build())
    }

    fun image(
        src: String,
        block: ImageBuilder.() -> Unit = {}
    ) {
        val builder = ImageBuilder(src)
        builder.block()
        children.add(builder.build())
    }

    fun container(
        direction: String? = null,
        block: ContainerBuilder.() -> Unit = {}
    ): ContainerComponent {
        val builder = ContainerBuilder(direction)
        builder.block()
        val container = builder.build(children.toList())
        children.add(container)
        return container
    }
}

// 4. 派生构建器
abstract class BaseBuilder<T : Component> {
    abstract fun build(): T
}

class TextBuilder(private val text: String) : BaseBuilder<TextComponent>() {
    private val styles = mutableMapOf<String, Any>()

    fun font(size: Int) = apply { styles["fontSize"] = size }
    fun color(hex: String) = apply { styles["color"] = hex }
    fun bold() = apply { styles["fontWeight"] = "bold" }

    override fun build() = TextComponent(text, styles)
}

class ImageBuilder(private val src: String) : BaseBuilder<ImageComponent>() {
    private val styles = mutableMapOf<String, Any>()

    fun width(value: Int) = apply { styles["width"] = value }
    fun height(value: Int) = apply { styles["height"] = value }
    fun resizeMode(mode: String) = apply { styles["resizeMode"] = mode }

    override fun build() = ImageComponent(src, styles)
}

class ContainerBuilder(private val direction: String? = null) : BaseBuilder<ContainerComponent>() {
    private val childBuilders = mutableListOf<ComponentBuilder.() -> Unit>()

    fun column(block: ComponentBuilder.() -> Unit) = apply {
        childBuilders.add(block)
    }

    fun row(block: ComponentBuilder.() -> Unit) = apply {
        childBuilders.add(block)
    }

    // 延迟构建子组件
    fun build(existingChildren: List<Component> = emptyList()): ContainerComponent {
        val children = existingChildren.toMutableList()

        childBuilders.forEach { block ->
            val builder = ComponentBuilder()
            builder.block()
            children.addAll(builder.children)
        }

        return ContainerComponent(direction, children)
    }

    override fun build(): ContainerComponent {
        TODO("Not yet implemented")
    }
}

// 5. 应用入口
fun myApp(block: ComponentBuilder.() -> Unit): Component {
    val appContainer = ContainerComponent()
    val builder = ComponentBuilder()
    builder.block()

    // 将顶级组件放入根容器
    appContainer.children.addAll(builder.children)

    return appContainer
}

// 6. 使用示例 & 打印UI树
fun main() {
    val app = myApp {
        container(direction = "row") {
            // 水平布局的组件
            image("logo.png") {
                width(64)
                height(64)
                resizeMode("contain")
            }

            text("Kotlin DSL") {
                font(24)
                color("#FF3366")
                bold()
            }
        }

        container(direction = "column") {
            // 垂直布局的组件
            text("组件特性:") {
                color("#333333")
            }

            container(direction = "row") {
                text("• 类型安全的构建器") {
                    font(16)
                }

                text("• 嵌套作用域") {
                    font(16)
                }
            }

            text("• 自动树结构打印") {
                font(16)
                color("#0099FF")
            }
        }
    }

    // 打印UI结构树
    println("\n🌳 UI组件树结构:")
    app.printTree()
}