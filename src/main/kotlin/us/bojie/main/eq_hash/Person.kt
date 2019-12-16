package us.bojie.main.eq_hash

class Person(var age: Int, var name: String) {
    override fun equals(other: Any?): Boolean {
        val other = (other as? Person)?: return false
        return other.age == age && other.name == name
    }

    override fun hashCode(): Int {
        return 1 + 7 * age + 13 * name.hashCode()
    }
}

fun main() {
    val persons = HashSet<Person>()

//    (0..5).forEach { _ ->
//        persons += Person(20, "Bo")
//    }

    val person = Person(20, "Bo")
    persons += person

    // a moment later
    println(persons.size)
//    person.age++
//    persons -= person

    val person2 = Person(person.age + 1, person.name)
    persons -= person

    println(persons.size)
}