package us.bojie.main.string_ops

operator fun String.minus(right: Any?): String = this.replaceFirst(right.toString(), "")
operator fun String.times(right: Int): String {
    return (1..right).joinToString("") {
        this
    }
}

operator fun String.div(right: Any?): Int {
    val right = right.toString()
    return this.windowed(right.length, 1) {
        it == right
    }.count { it }
}


fun main() {
    val v = "HelloWorld"

    println(v - "World")
    println(v * 2)
    println("*" * 20)
    println(v / 3)
    println(v / "ld")
}