import exceptions._

class Account(val bank: Bank, initialBalance: Double) {

    class Balance(var amount: Double) {}

    val balance = new Balance(initialBalance)

    // TODO
    // for project task 1.2: implement functions
    // for project task 1.3: change return type and update function bodies
    def withdraw(amount: Double): Either[Unit, String] = this.synchronized { 
      if(amount > balance.amount || 0 > amount) return Right("Can't withdraw more than what your balance is") 
      Left(balance.amount -= amount)
    }
    /*
    Checks if the withdraw amount is positive and within the bounds of the account balance, then returns an Either
    based on whether the withdraw was successful or not.
    */

    def deposit (amount: Double): Either[Unit, String] = this.synchronized {
      if(amount <= 0) return Right("Can't deposit negative amount")
      Left(balance.amount += amount)
    }
    /*
    Deposits the amount specified to the account, and returns an Either if the deposit was successful (as long as the
    amount is not negative or zero).
    */

    def getBalanceAmount: Double = balance.amount;
    /*
    Returns the account balance.
    */

    def transferTo(account: Account, amount: Double) = {
        bank addTransactionToQueue (this, account, amount)
    }


}
