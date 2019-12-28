package us.bojie.main.anno

import com.google.gson.Gson
import okhttp3.OkHttpClient

data class User(
    var login: String,
    var location: String,
    var bio: String
)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Api(val url: String)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Path(val url: String = "")


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Get(val url: String = "")

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class PathVariable(val name: String = "")

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Query(val name: String = "")

@Api("https://api.github.com")
interface GitHubApi {

    @Api("users")
    interface Users {
        @Get("{name}")
        fun get(name: String): User

        @Get("{name}/followers")
        fun followers(name: String): List<User>
    }

    @Api("repos")
    interface Repos {
        @Get("{owner}/{repo}/forks")
        fun forks(owner: String, repo: String)
    }
}

object RetorApi {
    const val PATH_PATTERN = """(\{\w+)\})"""

    val okHttp = OkHttpClient()
    val gson = Gson()

    inline fun <reified T> create(): T {

    }
}

fun main() {
    val usersApi = RetorApi.create<GitHubApi.Users>()
    println(usersApi.get("jbj88817"))
    println(usersApi.followers("jbj88817").map { it.login })
}
