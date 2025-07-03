package top.superyaxi.coroutineDemo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
        launch(Dispatchers.Default, block = myAction)
        launch(Dispatchers.Default, block = CoroutineScope::myCoroutineFunction)
        launch(Dispatchers.Default, block = myAction2)
    }
}

val myAction: suspend CoroutineScope.() -> Unit = {
    delay(1000)
    println("Executed in ${this.coroutineContext}")
}

suspend fun CoroutineScope.myCoroutineFunction() {
    // 在 CoroutineScope 上下文中执行挂起操作
    delay(1000)
    println("Executed in ${this.coroutineContext}")
}

val myAction2: suspend CoroutineScope.() -> Unit = CoroutineScope::myCoroutineFunction

