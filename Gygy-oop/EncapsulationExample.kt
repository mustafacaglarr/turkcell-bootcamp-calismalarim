class BankAccount(private var balance: Int) {
    fun deposit(amount: Int) {
        if (amount > 0) {
            balance += amount
            println("Para yatırıldı: $amount, Bakiye: $balance")
        } else {
            println("Geçersiz miktar")
        }
    }

    fun withdraw(amount: Int) {
        if (amount > 0 && amount <= balance) {
            balance -= amount
            println("Para çekildi: $amount, Bakiye: $balance")
        } else {
            println("Yetersiz bakiye veya geçersiz miktar")
        }
    }

    fun showBalance() {
        println("Güncel bakiye: $balance")
    }
}

fun main() {
    val account = BankAccount(100)
    account.showBalance()
    account.deposit(50)
    account.withdraw(30)
}
