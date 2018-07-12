package intro.coroutines

import kotlinx.coroutines.experimental.runBlocking
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.function.BiConsumer
import kotlin.coroutines.experimental.suspendCoroutine

suspend fun <T : Any> CompletionStage<T>.await() = suspendCoroutine<T> { continuation ->
  this.whenComplete(object : BiConsumer<T?, Throwable?> {
    override fun accept(value: T?, exception: Throwable?) {
      if (exception == null)
        continuation.resume(value as T)
      else
        continuation.resumeWithException(exception)
    }
  })
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




