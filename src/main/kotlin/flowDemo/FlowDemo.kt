package top.superyaxi.flowDemo

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

suspend fun main() {
    // 使用flow构建器创建
    val numberFlow = flow {
        for (i in 1..3) {
            delay(100)
            emit(i)
        }
    }

    // 集合转Flow
    val listAsFlow = listOf(1, 2, 3).asFlow()

    val numberFlow2 = numberFlow
        .map { it * 2 } // 转换值
        .filter { it > 3 } // 过滤值
        .catch { e -> // 异常处理
            emit(-1)
        }

    numberFlow2.collect {
        println("numberFlow2 $it")
    }

    numberFlow2.onEach {
        println("onEach $it")
    }.collect {
        println("numberFlow2 onEach collect $it")
    }

    // 在协程中收集
    numberFlow.collect { value ->
        println("numberFlow Received $value")
    }

    listAsFlow.collect { value ->
        println("listAsFlow Received $value")
    }
}
