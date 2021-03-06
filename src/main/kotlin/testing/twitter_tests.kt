@file:Suppress(
  "UNUSED_PARAMETER",
  "UNUSED_VARIABLE",
  "UNREACHABLE_CODE",
  "ClassName",
  "FunctionName",
  "unused"
)

package testing










//










//










class Tweeter(
  private val api: TwitterApi,
  private val user: String
) {

  fun tweet(tweet: String) {
    api.postTweet(user, tweet)
  }
}

interface TwitterApi {
  fun postTweet(user: String, tweet: String)
}










//










//










class Record_v1 : TwitterApi {
  val tweets = mutableListOf<String>()

  override fun postTweet(user: String, tweet: String) {
    tweets.add("Tweet from $user: $tweet")
  }
}

fun test_01_v1() {
  val api = Record_v1()
  val tweeter = Tweeter(api, "Brian Norman")

  tweeter.tweet("Hello, Atavium!")
  assert(api.tweets.size == 1)
  assert(api.tweets[0] == "Tweet from Brian Norman: Hello, Atavium!")
}

fun test_02_v1() {
  val api = Record_v1()
  val tweeter = Tweeter(api, "Jim Lester")

  tweeter.tweet("Hello, Atavium!")
  assert(api.tweets.size == 1)
  assert(api.tweets[0] == "Tweet from Jim Lester: Hello, Atavium!")
}


// What are some of the problems here?










//










//









data class Tweet_v2(
  val user: String,
  val tweet: String
)

class Record_v2 : TwitterApi {
  val tweets = mutableListOf<Tweet_v2>()

  override fun postTweet(user: String, tweet: String) {
    tweets.add(Tweet_v2(user, tweet))
  }
}

fun test_01_v2() {
  val recording = Record_v2()
  val tweeter = Tweeter(recording, "Brian Norman")

  tweeter.tweet("Hello, Atavium!")
  assert(recording.tweets.size == 1)
  assert(recording.tweets[0] == Tweet_v2("Brian Norman", "Hello, Atavium!"))
}

fun test_02_v2() {
  val recording = Record_v2()
  val tweeter = Tweeter(recording, "Jim Lester")

  tweeter.tweet("Hello, Atavium!")
  assert(recording.tweets.size == 1)
  assert(recording.tweets[0] == Tweet_v2("Jim Lester", "Hello, Atavium!"))
}










//










//