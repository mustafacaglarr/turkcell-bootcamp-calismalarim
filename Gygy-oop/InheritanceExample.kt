open class Animal(val name: String) {
    fun eat() {
        println("$name yemek yiyor")
    }
}

class Dog(name: String) : Animal(name) {
    fun bark() {
        println("$name havlıyor")
    }
}

fun main() {
    val dog = Dog("Karabas")
    dog.eat()
    dog.bark()
}
