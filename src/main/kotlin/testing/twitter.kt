@file:Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE", "UNREACHABLE_CODE")

package testing

import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Singleton










//










//










class Tweeter_v1 {
  fun tweet(tweet: String) {
    val client = OkHttpClient()
    val request = buildTweetRequest(tweet, "Brian Norman")
    client.newCall(request).execute()
  }

  private fun buildTweetRequest(tweet: String, user: String): Request = TODO()
}

fun sample_v1(args: Array<String>) {
  val twetter = Tweeter_v1()
  twetter.tweet("Hello, Atavium!")
}










//










//










// TODO pattern - separation of concerns - extract TwitterApi_v2


class Tweeter_v2 {
  fun tweet(tweet: String) {
    val client = OkHttpClient()
    val request = buildTweetRequest(tweet, "Brian Norman")
    client.newCall(request).execute()
  }

  private fun buildTweetRequest(tweet: String, user: String): Request = TODO()
}

fun sample_v2(args: Array<String>) {
  val twetter = Tweeter_v1()
  twetter.tweet("Hello, Atavium!")
}










//










//










// TODO inversion of control - extract constructor arguments


class Tweeter_v3 {
  private val api = TwitterApi_v3()

  fun tweet(tweet: String) {
    api.postTweet("Brian Norman", tweet)
  }
}

class TwitterApi_v3 {
  private val client = OkHttpClient()

  fun postTweet(user: String, tweet: String) {
    val request = TODO()
    client.newCall(request).execute()
  }
}

fun sample_v3(args: Array<String>) {
  val twetter = Tweeter_v3()
  twetter.tweet("Hello, Atavium!")
}










//










//










// TODO mocking - convert twitter api to interface


class Tweeter_v4(
  private val api: TwitterApi_v4,
  private val user: String
) {

  fun tweet(tweet: String) {
    api.postTweet(user, tweet)
  }
}

class TwitterApi_v4(
  private val client: OkHttpClient
) {

  fun postTweet(user: String, tweet: String) {
    val request = TODO()
    client.newCall(request).execute()
  }
}

fun sample_v4(args: Array<String>) {
  val twetter = Tweeter_v4(TwitterApi_v4(OkHttpClient()), "Brian Norman")
  twetter.tweet("Hello, Atavium!")
}










//










//










// TODO dependency injection - dagger 2!

class Tweeter_v5 @Inject constructor(
  private val api: TwitterApi_v5,
  private val user: String
) {

  fun tweet(tweet: String) {
    api.postTweet(user, tweet)
  }
}

interface TwitterApi_v5 {
  fun postTweet(user: String, tweet: String)
}

class TwitterApi_Real_v5 @Inject constructor(
  private val client: OkHttpClient
) : TwitterApi_v5 {

  override fun postTweet(user: String, tweet: String) {
    val request = TODO("build request")
    client.newCall(request).execute()
  }
}

class TwitterApi_Mock_v5 @Inject constructor() : TwitterApi_v5 {
  override fun postTweet(user: String, tweet: String) {
    println("Tweet from $user: $tweet")
  }
}

fun sample_v5(args: Array<String>) {
  val twetter = Tweeter_v5(TwitterApi_Mock_v5(), "Brian Norman")
  twetter.tweet("Hello, Atavium!")
}










//










//










// Src

@Component(modules = [UserModule::class], dependencies = [TwitterApiComponent::class])
interface TwitterComponent {
  fun tweeter(): Tweeter_v5
}

@Component(modules = [HttpClientModule::class, TwitterApiModule::class])
interface TwitterApiComponent {
  fun twitterApi(): TwitterApi_v5
}

@Module
class HttpClientModule(
  private val okHttpClient: OkHttpClient = OkHttpClient()
) {
  @Provides
  fun provideHttpClient(): OkHttpClient {
    return okHttpClient
  }
}

@Module
class UserModule(
  private val user: String
) {
  @Provides
  fun provideUser(): String {
    return user
  }
}

@Module
class TwitterApiModule {
  @Provides
  fun provideTwitterApi(httpClient: OkHttpClient): TwitterApi_v5 {
    return TwitterApi_Real_v5(httpClient)
  }
}


// Test

@Component(modules = [TestTwitterApiModule::class])
interface TestTwitterApiComponent : TwitterApiComponent {
}

@Module
class TestTwitterApiModule {
  @Provides
  fun provideTwitterApi(): TwitterApi_v5 {
    return TwitterApi_Mock_v5()
  }
}


//

fun main(args: Array<String>) {
  val userModule = UserModule("Brian Norman")

  val twitterApiComponent: TwitterApiComponent = DaggerTestTwitterApiComponent.create()
  val twitterComponent: TwitterComponent = DaggerTwitterComponent.builder()
    .userModule(userModule)
    .twitterApiComponent(twitterApiComponent)
    .build()

  val twetter = twitterComponent.tweeter()
  twetter.tweet("Hello, Atavium!")
}

