package top.superyaxi.coroutineDemo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        println("主线程上下文:" + this.coroutineContext)
        launch(Dispatchers.Default) {
            println("Default协程上下文:" + this.coroutineContext)
        }
        launch(Dispatchers.IO) {
            println("IO协程上下文:" + this.coroutineContext)
        }
        launch(Dispatchers.Unconfined) {
            println("Unconfined协程上下文:" + this.coroutineContext)
        }
    }
}
