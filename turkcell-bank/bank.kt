fun main() {
    var bakiye = 1000.0

    println("Banka uygulamasi basladi")
    bakiyeGoster(bakiye)

    bakiye = paraYatir(bakiye, 523.0)
    bakiyeGoster(bakiye)

    bakiye = paraCek(bakiye, 238.0)
    bakiyeGoster(bakiye)

    bakiye = havaleYap(bakiye, 478.0, "Ahmet")
    bakiyeGoster(bakiye)

    bakiye = faturaOde(bakiye, 222.0, "Elektrik")
    bakiyeGoster(bakiye)
}



fun bakiyeGoster(bakiye: Double) {
    println("Guncel bakiye: $bakiye TL")
}

fun paraYatir(bakiye: Double, miktar: Double): Double {
    val yeniBakiye = bakiye + miktar
    println("$miktar TL yatirildi.")
    return yeniBakiye
}

fun paraCek(bakiye: Double, miktar: Double): Double {
    return if (miktar <= bakiye) {
        val yeniBakiye = bakiye - miktar
        println("$miktar TL cekildi.")
        yeniBakiye
    } else {
        println("Yetersiz bakiye.")
        bakiye
    }
}

fun havaleYap(bakiye: Double, miktar: Double, alici: String): Double {
    return if (miktar <= bakiye) {
        val yeniBakiye = bakiye - miktar
        println("$alici hesabina $miktar TL havale gonderildi.")
        yeniBakiye
    } else {
        println("Havale icin yetersiz bakiye.")
        bakiye
    }
}

fun faturaOde(bakiye: Double, miktar: Double, faturaTipi: String): Double {
    return if (miktar <= bakiye) {
        val yeniBakiye = bakiye - miktar
        println("$faturaTipi faturasi icin $miktar TL odendi.")
        yeniBakiye
    } else {
        println("Fatura odemesi icin yetersiz bakiye.")
        bakiye
    }
}

