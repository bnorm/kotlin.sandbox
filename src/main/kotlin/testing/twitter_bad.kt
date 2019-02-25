@file:Suppress(
  "UNUSED_PARAMETER",
  "UNUSED_VARIABLE",
  "UNREACHABLE_CODE",
  "ClassName",
  "FunctionName",
  "unused"
)

package testing

import okhttp3.OkHttpClient
import okhttp3.Request










//










//










var unitTest = false
val tweetRecord = mutableListOf<String>()

class Tweeter_Bad_v1 {
  fun tweet(tweet: String) {
    if (unitTest) {
      tweetRecord.add(tweet)
    } else {
      val client = OkHttpClient()
      val request = buildTweetRequest(tweet, "Brian Norman")
      client.newCall(request).execute()
    }
  }

  private fun buildTweetRequest(tweet: String, user: String): Request = TODO()
}

fun bad_v1(args: Array<String>) {
  unitTest = true
  val tweeter = Tweeter_Bad_v1()
  tweeter.tweet("Hello, Atavium!")
  println(tweetRecord.contains("Hello, Atavium!"))
}










//










//










class Tweeter_Bad_v2 {
  fun tweet(tweet: String) {
    val client = OkHttpClient()
    val request = buildTweetRequest(tweet, "Brian Norman")
    client.newCall(request).execute()
  }

  private fun buildTweetRequest(tweet: String, user: String): Request = TODO()
}

fun bad_v2(args: Array<String>) {
  val tweeter = Tweeter_Bad_v2()
  tweeter.tweet("Hello, Atavium!")
}




