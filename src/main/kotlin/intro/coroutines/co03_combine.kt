package intro.coroutines

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

suspend fun doSomethingUsefulOne(): Int {
  delay(1000L) // pretend we are doing something useful here
  return 13
}

suspend fun doSomethingUsefulTwo(): Int {
  delay(1000L) // pretend we are doing something useful here, too
  return 29
}

fun sync(args: Array<String>) = runBlocking<Unit> {
  val time = measureTimeMillis {
    val one = doSomethingUsefulOne()
    val two = doSomethingUsefulTwo()
    println("The answer is ${one + two}")
  }
  println("Completed in $time ms")
}

fun async(args: Array<String>) = runBlocking<Unit> {
  withContext()
  val time = measureTimeMillis {
    coroutineScope {
      val one = async { doSomethingUsefulOne() }
      val two = async { doSomethingUsefulTwo() }
      println("The answer is ${one.await() + two.await()}")
    }
  }
  println("Completed in $time ms")
}
