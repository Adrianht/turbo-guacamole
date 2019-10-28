
object Main extends App {

    def thread(body: => Unit): Thread = {
        val t = new Thread {
            override def run() = body
        }
        t.start
        t
    }

    val bank = new Bank()

    val acc1 = bank.addAccount(100)
    val acc2 = bank.addAccount(200)

    acc1 transferTo(acc2, 50)

    while (bank.getProcessedTransactionsAsList.size != 1) {
      Thread.sleep(100)
    }
    println("Finished with:")
    println(bank.transactionQueue.isEmpty)
    println(bank.getProcessedTransactionsAsList.size)
    println(bank.getProcessedTransactionsAsList(0).status)
  
}