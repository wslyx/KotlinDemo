package top.superyaxi.designPattern

// 1. 基础组件定义 - 使用通用类型解决冲突
sealed class Component {
    abstract val name: String
    abstract val children: List<Component>
    abstract val properties: Map<String, Any> // 使用通用类型

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

// 2. 具体组件实现 - 统一属性类型
class ContainerComponent(
    private val customProperties: Map<String, Any> = emptyMap(),
    override val children: List<Component> = emptyList()
) : Component() {
    override val name = "Container"
    override val properties: Map<String, Any>
        get() = customProperties
}

class TextComponent(
    val content: String,
    private val styleProperties: Map<String, Any> = emptyMap()
) : Component() {
    override val name = "Text"
    override val children: List<Component> = emptyList()
    override val properties: Map<String, Any>
        get() = mapOf("content" to content) + styleProperties
}

class ImageComponent(
    val src: String,
    private val styleProperties: Map<String, Any> = emptyMap()
) : Component() {
    override val name = "Image"
    override val children: List<Component> = emptyList()
    override val properties: Map<String, Any>
        get() = mapOf("src" to src) + styleProperties
}

// 3. DSL 构建器
class ComponentBuilder {
    private val children = mutableListOf<Component>()

    fun text(text: String, block: (TextBuilder.() -> Unit)? = null) {
        children.add(TextBuilder(text).apply { block?.invoke(this) }.build())
    }

    fun image(src: String, block: (ImageBuilder.() -> Unit)? = null) {
        children.add(ImageBuilder(src).apply { block?.invoke(this) }.build())
    }

    fun container(
        block: (ContainerBuilder.() -> Unit)? = null
    ) {
        children.add(ContainerBuilder().apply { block?.invoke(this) }.build())
    }

    fun build(): ContainerComponent {
        return ContainerComponent(children = children)
    }
}

// 4. 实现类似 ctx.tabBar().invoke(this) 的功能
typealias ViewBuilder = ComponentBuilder.() -> Unit

fun tabBarBuilder(): ViewBuilder {
    return {
        container {
            text("首页")
            text("视频")
            text("发现")
            text("消息")
            text("我")
        }
    }
}

// 5. 专用构建器 - 解决冲突问题
class TextBuilder(private val text: String) {
    private val properties = mutableMapOf<String, Any>()

    fun font(size: Int) = apply { properties["fontSize"] = size }
    fun color(hex: String) = apply { properties["color"] = hex }
    fun bold() = apply { properties["fontWeight"] = "bold" }

    fun build(): TextComponent {
        // 转换为只读Map解决类型冲突
        return TextComponent(text, properties.toMap())
    }
}

class ImageBuilder(private val src: String) {
    private val properties = mutableMapOf<String, Any>()

    fun width(value: Int) = apply { properties["width"] = value }
    fun height(value: Int) = apply { properties["height"] = value }
    fun resizeMode(mode: String) = apply { properties["resizeMode"] = mode }

    fun build(): ImageComponent {
        // 转换为只读Map解决类型冲突
        return ImageComponent(src, properties.toMap())
    }
}

class ContainerBuilder {
    private val children = mutableListOf<Component>()
    private val properties = mutableMapOf<String, Any>()

    fun direction(value: String) = apply { properties["direction"] = value }

    fun text(text: String, block: (TextBuilder.() -> Unit)? = null) {
        children.add(TextBuilder(text).apply { block?.invoke(this) }.build())
    }

    fun image(src: String, block: (ImageBuilder.() -> Unit)? = null) {
        children.add(ImageBuilder(src).apply { block?.invoke(this) }.build())
    }

    fun container(block: (ContainerBuilder.() -> Unit)? = null) {
        children.add(ContainerBuilder().apply { block?.invoke(this) }.build())
    }

    fun build(): ContainerComponent {
        return ContainerComponent(properties.toMap(), children)
    }
}

// 6. 主程序 - 修复所有类型冲突
fun main() {
    val app = ComponentBuilder().apply {
        // 使用类似 ctx.tabBar().invoke(this) 的调用方式
        val tabBar = tabBarBuilder()
        tabBar.invoke(this)

        container {
            direction("row")
            image("logo.png") {
                width(64)
                height(64)
                resizeMode("contain")
            }

            text("KuiklyUI 演示") {
                font(32)
                color("#4A86E8")
                bold()
            }
        }

        container {
            direction("column")
            text("系统特性:") {
                color("#333333")
            }

            container {
                direction("row")
                text("• 类型安全构建器")
                text("• DSL语法")
            }

            text("• 响应式UI") {
                font(18)
                color("#0099FF")
            }
        }
    }.build()

    // 打印UI结构树
    println("\n🌳 完整的UI树结构:")
    app.printTree()
}
