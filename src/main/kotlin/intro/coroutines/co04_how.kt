package intro.coroutines

import kotlinx.coroutines.runBlocking
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import kotlin.coroutines.suspendCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun <T : Any> CompletionStage<T>.await() = suspendCoroutine<T> { continuation ->
  this.whenComplete { value, exception ->
    if (exception == null)
      continuation.resume(value as T)
    else
      continuation.resumeWithException(exception)
  }
}

fun main(args: Array<String>) {
  val future = CompletableFuture.supplyAsync<String> {
    Thread.sleep(1000L)
    "World"
  }

  runBlocking<Unit> {
    println("Hello, ${future.await()}")
  }
}




