import exceptions._

class Account(val bank: Bank, initialBalance: Double) {

    class Balance(var amount: Double) {}

    val balance = new Balance(initialBalance)

    // for project task 1.2: implement functions
    // for project task 1.3: change return type and update function bodies
    def withdraw(amount: Double): Either[Unit, String] = this.synchronized { 
      if(amount > balance.amount || 0 > amount) return Right("Cant withdraw more than what your balance is")
      Left(balance.amount -= amount)
    }

    def deposit (amount: Double): Either[Unit, String] = this.synchronized {
      if(amount <= 0) return Right("Cant deposit negative amount")
      Left(balance.amount += amount)
    }

    def getBalanceAmount: Double = balance.amount;

    def transferTo(account: Account, amount: Double): Unit = {
        bank addTransactionToQueue (this, account, amount)
    }


}
