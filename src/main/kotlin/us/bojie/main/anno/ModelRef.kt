package us.bojie.main.anno

import java.lang.IllegalArgumentException
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor


@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class FieldName(val name: String)

@Target(AnnotationTarget.CLASS)
annotation class MappingStrategy(val klass: KClass<out NameStrategy>)

interface NameStrategy {
    fun mapTo(name: String): String
}

object UnderScoreToCamel : NameStrategy {
    // html_url -> htmlUrl
    override fun mapTo(name: String): String {
        return name.toCharArray().fold(StringBuilder()) { acc, c ->
            when (acc.lastOrNull()) {
                ' ' -> acc[acc.lastIndex] = c.toUpperCase()
                else -> acc.append(c)
            }
            acc
        }.toString()
    }
}

object CamelToUnderScore : NameStrategy {
    override fun mapTo(name: String): String {
        return name.toCharArray().fold(StringBuilder()) { acc, c ->
            when {
                c.isUpperCase() -> acc.append('_').append(c.toLowerCase())
                else -> acc.append(c)
            }
            acc
        }.toString()
    }
}

@MappingStrategy(CamelToUnderScore::class)
data class UserVO(
    val login: String,
//    @FieldName("avatar_url")
    val avatarUrl: String
)

data class UserDTO(
    var id: Int,
    var login: String,
    var avatar_url: String,
    var url: String,
    var html_url: String
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
        "avatar_url" to "https://avatars2.githubusercontent.com/u/7081069?v=4",
        "html_url" to "https://github.com/jbj88817",
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
            parameter to (this[parameter.name]
                ?: (parameter.findAnnotation<FieldName>()?.name?.let(this::get))
                ?: To::class.findAnnotation<MappingStrategy>()?.klass?.objectInstance?.mapTo(parameter.name!!)?.let(
                    this::get
                )
                ?: if (parameter.type.isMarkedNullable) null
                else throw IllegalArgumentException("${parameter.name} is required but missing"))
        }.toMap()
            .let(it::callBy)
    }
}
