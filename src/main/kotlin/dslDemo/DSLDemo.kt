package top.superyaxi.dslDemo

// 定义通用接口
interface View {
    fun layout(width: Int, height: Int)
}

interface ComponentBuilder {
    fun build(): View
}

// PageList 实现
class PageListView : View {
    private val pages = mutableListOf<View>()
    private var currentIndex = 0

    fun addPage(page: View) {
        pages.add(page)
    }

    fun scrollToPage(index: Int) {
        currentIndex = index.coerceIn(0, pages.size - 1)
        println("切换到页面: $currentIndex")
    }

    override fun layout(width: Int, height: Int) {
        println("布局PageList视图 ($width x $height)")
        pages.forEachIndexed { i, view ->
            if (i == currentIndex) {
                println("显示页面[$i]")
                view.layout(width, height)
            }
        }
    }
}

// PageList构建器
class PageListBuilder {
    private val builders = mutableListOf<ComponentBuilder>()

    fun addPage(builder: ComponentBuilder) {
        builders.add(builder)
    }

    fun build(): PageListView {
        val pageListView = PageListView()
        builders.forEach {
            pageListView.addPage(it.build())
        }
        return pageListView
    }
}

// 示例页面构建器
class HomePageBuilder : ComponentBuilder {
    override fun build(): View = object : View {
        override fun layout(width: Int, height: Int) {
            println("首页布局: $width x $height")
        }
    }
}

class EmptyPageBuilder(private val title: String) : ComponentBuilder {
    override fun build(): View = object : View {
        override fun layout(width: Int, height: Int) {
            println("$title 页面布局: $width x $height")
        }
    }
}

// DSL函数
fun pageList(block: PageListBuilder.() -> Unit): PageListView {
    val builder = PageListBuilder()
    builder.block()
    return builder.build()
}

// 使用示例
fun main() {
    val titles = listOf("首页", "视频", "发现", "消息", "我")

    // 创建PageList
    val appTab = pageList {
        // 添加首页
        addPage(HomePageBuilder())

        // 添加其他页面
        for (i in 1 until titles.size) {
            addPage(EmptyPageBuilder(titles[i]))
        }
    }

    // 初始化布局
    println("\n=== 初始布局 ===")
    appTab.layout(1080, 720)

    // 切换页面
    println("\n=== 切换到'发现'页面 ===")
    appTab.scrollToPage(2)
    appTab.layout(1080, 1920)
}