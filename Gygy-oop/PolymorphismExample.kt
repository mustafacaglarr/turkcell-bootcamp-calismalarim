open class Shape {
    open fun draw() {
        println("Şekil çiziliyor")
    }
}

class Circle : Shape() {
    override fun draw() {
        println("Daire çiziliyor")
    }
}

class Square : Shape() {
    override fun draw() {
        println("Kare çiziliyor")
    }
}

fun main() {
    val shapes: List<Shape> = listOf(Circle(), Square())
    for (shape in shapes) {
        shape.draw()
    }
}
