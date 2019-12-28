package us.bojie.main.anno

import java.lang.IllegalArgumentException
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor


data class UserVO(
    val login: String,
    val avatarUrl: String
)

data class UserDTO(
    var id: Int,
    var login: String,
    var avatarUrl: String,
    var url: String,
    var htmlUrl: String
)

fun main() {
    val userDTO = UserDTO(
        7081069,
        "jbj88817",
        "https://avatars2.githubusercontent.com/u/7081069?v=4",
        "https://api.github.com/users/jbj88817",
        "https://github.com/jbj88817"
    )

    val userVO: UserVO = userDTO.mapAs()
    println(userVO)

    val userMap = mapOf(
        "id" to 7081069,
        "login" to "jbj88817",
        "avatarUrl" to "https://avatars2.githubusercontent.com/u/7081069?v=4",
        "url" to "https://api.github.com/users/jbj88817"
    )

    val userVOFromMap: UserVO = userMap.mapAs()
    println(userVOFromMap)
}

inline fun <reified From : Any, reified To : Any> From.mapAs(): To {
    return From::class.memberProperties.map { it.name to it.get(this) }
        .toMap().mapAs()
}

inline fun <reified To : Any> Map<String, Any?>.mapAs(): To {
    return To::class.primaryConstructor!!.let {
        it.parameters.map { parameter ->
            parameter to (this[parameter.name] ?: if (parameter.type.isMarkedNullable) null
            else throw IllegalArgumentException("${parameter.name} is required but missing"))
        }.toMap()
            .let(it::callBy)
    }
}
