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

    // DSL的核心魔法：操作符重载
    operator fun invoke(builder: ComponentBuilder) {
        builders.add(builder)
    }

    // 支持+=语法
    operator fun plusAssign(builder: ComponentBuilder) {
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

// DSL函数 - 创建自然的使用语法
fun pageList(block: PageListBuilder.() -> Unit): PageListView {
    return PageListBuilder().apply(block).build()
}

// 使用示例
fun main() {
    val titles = listOf("首页", "视频", "发现", "消息", "我")

    // 创建PageList - 两种等价的自然写法
    println("=== 第一种写法 ===")
    val appTab = pageList {
        // 直接调用方式添加页面
        this(HomePageBuilder())
        for (i in 1 until titles.size) {
            this(EmptyPageBuilder(titles[i]))
        }
    }

    println("=== 第二种写法 ===")
    val appTab2 = pageList {
        // 使用 += 操作符添加页面
        this += HomePageBuilder()
        for (i in 1 until titles.size) {
            this += EmptyPageBuilder(titles[i])
        }
    }

    println("第三种写法")
    val appTab3 = pageList {
        HomePageBuilder()
        for (i in 1 until titles.size) {
            EmptyPageBuilder(titles[i])
        }
    }

    // 初始化布局
    println("\n=== 初始布局 === Tab1")
    appTab.layout(1080, 1920)

    // 切换页面
    println("\n=== 切换到'发现'页面 ===")
    appTab.scrollToPage(2)
    appTab.layout(1080, 1920)

    // appTab2
    println("\n=== 初始布局 Tab2 ===")
    appTab2.layout(1080, 1920)

    println("\n=== 切换到'消息'页面 ===")
    appTab2.scrollToPage(3)
    appTab2.layout(1080, 1920)

    // appTab3
    println("\n=== 初始 Tab3 ===")
    appTab3.layout(1080, 1920)

    println("\n=== 添加'消息'页面 ===")
    appTab3.scrollToPage(3)
    appTab3.layout(1080, 1920)

}
