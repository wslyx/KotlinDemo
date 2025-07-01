package coroutineDemo

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock

class H2O {
    // 使用显式计数器替代 Channel.size
    private var hCount = 0
    private var oCount = 0
    private val mutex = Mutex()
    private val outputMutex = Mutex()
    private val hQueue = Channel<suspend () -> Unit>(Channel.UNLIMITED)
    private val oQueue = Channel<suspend () -> Unit>(Channel.UNLIMITED)
    private val releaseBarrier = Semaphore(permits = 2, acquiredPermits = 0)

    suspend fun hydrogen(releaseHydrogen: suspend () -> Unit) {
        mutex.withLock {
            hCount++
            hQueue.send(releaseHydrogen)
            tryOutput()
        }
        // 等待氧原子完成
        releaseBarrier.acquire()
    }

    suspend fun oxygen(releaseOxygen: suspend () -> Unit) {
        mutex.withLock {
            oCount++
            oQueue.send(releaseOxygen)
            tryOutput()
        }
        // 两个氢原子可以继续
        releaseBarrier.release()
        releaseBarrier.release()
    }

    private suspend fun tryOutput() {
        // 检查是否满足输出条件
        if (hCount >= 2 && oCount >= 1) {
            // 重置计数器
            hCount -= 2
            oCount -= 1

            // 从队列中取出任务
            val h1 = hQueue.receive()
            val h2 = hQueue.receive()
            val o = oQueue.receive()

            // 输出水分子的原子
            outputMutex.withLock {
                listOf(h1, h2, o).shuffled().forEach { it() }
                println(" → 分子完成")
            }
        }
    }
}

fun main() = runBlocking {
    val h2o = H2O()
    val waterCount = 4
    val input = "H".repeat(waterCount * 2) + "O".repeat(waterCount)

    println("创建 ${waterCount} 个水分子 (输入: $input)")

    // 创建所有原子协程
    val jobs = input.map { atom ->
        when (atom) {
            'H' -> launch(Dispatchers.Default) {
                h2o.hydrogen { print("H") }
            }
            'O' -> launch(Dispatchers.Default) {
                h2o.oxygen { print("O") }
            }
            else -> error("非法原子类型")
        }
    }

    // 等待所有原子完成任务
    jobs.joinAll()
    println("\n全部 ${waterCount} 个水分子输出完成!")
}