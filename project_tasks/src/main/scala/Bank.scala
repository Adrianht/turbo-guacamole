class Bank(val allowedAttempts: Integer = 3) {

    val transactionQueue: TransactionQueue = new TransactionQueue()
    private val processedTransactions: TransactionQueue = new TransactionQueue()

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
        transactionQueue.push(trans)
        thread(processTransactions)
    }

    private def processTransactions: Unit = {
        val transaction = transactionQueue.pop
        val t = thread(transaction.run)
        Thread.sleep(100)
        if (transaction.synchronized { transaction.status == TransactionStatus.PENDING }) {
            transactionQueue.push(transaction)
            thread(processTransactions)
        }
        else {
            processedTransactions.push(transaction)
        }
    }

    def addAccount(initialBalance: Double): Account = {
        new Account(this, initialBalance)
    }

    def getProcessedTransactionsAsList: List[Transaction] = {
        processedTransactions.iterator.toList
    }

}
