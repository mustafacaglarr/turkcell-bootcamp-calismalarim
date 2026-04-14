abstract class Vehicle {
    abstract fun drive()
}

class Car : Vehicle() {
    override fun drive() {
        println("Araba sürülüyor")
    }
}

class Bike : Vehicle() {
    override fun drive() {
        println("Bisiklet sürülüyor")
    }
}

fun main() {
    val car: Vehicle = Car()
    val bike: Vehicle = Bike()
    car.drive()
    bike.drive()
}
