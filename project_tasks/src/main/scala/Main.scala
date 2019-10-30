
object Main extends App {

    def thread(body: => Unit): Thread = {
        val t = new Thread {
            override def run() = body
        }
        t.start
        t
    }

    val bank = new Bank()

    val acc1 = new Account(bank, 3000)
    val acc2 = new Account(bank, 5000)
    val first = Main.thread {
        for (i <- 0 until 100) {
            bank addTransactionToQueue(acc1, acc2, 30)
        }
    }
    val second = Main.thread {
        for (i <- 0 until 100) {
            bank addTransactionToQueue(acc2, acc1, 23)
        }
    }
    first.join()
    second.join()

    while (bank.getProcessedTransactionsAsList.size != 200) {
        Thread.sleep(100)
    }

    assert((acc1.getBalanceAmount == 2300) && (acc2.getBalanceAmount == 5700))

  
}