class Bank(val allowedAttempts: Integer = 3) {

    val transactionQueue: TransactionQueue = new TransactionQueue()
    private val processedTransactions: TransactionQueue = new TransactionQueue()
    var processCount: Int = 0

    def thread[F](f: => F): Thread = {
        val t = new Thread( new Runnable {
            def run() {
                f
            }
        })
        t.start()
        t
    }

    def addTransactionToQueue(from: Account, to: Account, amount: Double): Unit = {
        val trans = new Transaction(this.transactionQueue, this.processedTransactions, from, to, amount, allowedAttempts)
        println(s"Created transaction $trans")
        transactionQueue.push(trans)
        thread(processTransactions)
    }
        // TODO
        // project task 2
        // create a new transaction object and put it in the queue
        // spawn a thread that calls processTransactions

    private def processTransactions: Unit = {
        val transaction = transactionQueue.pop
        this.synchronized {
            processCount = processCount + 1
            println(s"Ready to process ${processCount}")
        }
        val t = thread(transaction.run)
        Thread.sleep(100)
        if (transaction.synchronized { transaction.status == TransactionStatus.PENDING }) {
            //println("kom vi hit====?A??")
            //println(transaction.status)
            transactionQueue.push(transaction)
            thread(processTransactions)
        }
        else {
            processedTransactions.push(transaction)
        }
    }
        // TODO
        // project task 2
        // Function that pops a transaction from the queue
        // and spawns a thread to execute the transaction.
        // Finally do the appropriate thing, depending on whether
        // the transaction succeeded or not

    def addAccount(initialBalance: Double): Account = {
        new Account(this, initialBalance)
    }

    def getProcessedTransactionsAsList: List[Transaction] = {
        processedTransactions.iterator.toList
    }

}
