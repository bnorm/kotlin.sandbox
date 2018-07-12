package testing


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










object Record_v1 : TwitterApi {
  val tweets = mutableListOf<String>()

  override fun postTweet(user: String, tweet: String) {
    tweets.add("Tweet from $user: $tweet")
  }
}

fun test_01_v1() {
  val twetter = Tweeter(Record_v1, "Brian Norman")

  twetter.tweet("Hello, Atavium!")
  assert(Record_v1.tweets.size == 1)
  assert(Record_v1.tweets[0] == "Tweet from Brian Norman: Hello, Atavium!")
}

fun test_02_v1() {
  val twetter = Tweeter(Record_v1, "Jim Lester")

  twetter.tweet("Hello, Atavium!")
  assert(Record_v1.tweets.size == 2)
  assert(Record_v1.tweets[1] == "Tweet from Jim Lester: Hello, Atavium!")
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
  val twetter = Tweeter(recording, "Brian Norman")

  twetter.tweet("Hello, Atavium!")
  assert(recording.tweets.size == 1)
  assert(recording.tweets[0] == Tweet_v2("Brian Norman", "Hello, Atavium!"))
}

fun test_02_v2() {
  val recording = Record_v2()
  val twetter = Tweeter(recording, "Jim Lester")

  twetter.tweet("Hello, Atavium!")
  assert(recording.tweets.size == 1)
  assert(recording.tweets[0] == Tweet_v2("Jim Lester", "Hello, Atavium!"))
}










//










//