package top.superyaxi.coroutineDemo

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val deferred01 = async {
            return@async "Hello"
        }
        val deferred02 = async {
            return@async "World"
        }
        // 获取async的返回值
        println(deferred01.await())
        println(deferred02.await())
    }
}
