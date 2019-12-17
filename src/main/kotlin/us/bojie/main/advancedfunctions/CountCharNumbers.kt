package us.bojie.main.advancedfunctions

import java.io.File

fun main() {
    File("build.gradle").readText()
        .toCharArray()
        .filterNot(Char::isWhitespace)
        .groupBy { it }
        .map {
            it.key to it.value.size
        }.let {
            println(it)
        }
}
