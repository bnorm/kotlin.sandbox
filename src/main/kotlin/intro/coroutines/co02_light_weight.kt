package intro.coroutines

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import kotlin.system.measureTimeMillis


fun main(args: Array<String>) = runBlocking<Unit> {
  println(measureTimeMillis {
    val jobs = List(100_000) { // launch a lot of coroutines and list their jobs
      launch {
        delay(1000L)
        print(".")
      }
    }
    jobs.forEach { it.join() } // wait for all jobs to complete
    println()
  })
}
















//
