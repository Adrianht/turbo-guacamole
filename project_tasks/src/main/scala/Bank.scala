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

    /*
    This function simply creates a Transaction object and pushes it to the transactionQueue, then starts a new thread for executing
    the newly created transaction using the processTransactions function.
    */

    private def processTransactions: Unit = {
        val transaction = transactionQueue.pop
        val t = thread(transaction.run)
        t.join
        if (transaction.synchronized { transaction.status == TransactionStatus.PENDING }) {
            transactionQueue.push(transaction)
            thread(processTransactions)
        }
        else {
            processedTransactions.push(transaction)
        }
    }

    /*
    The processTransactions function pops (finds the topmost, i.e. the oldest) transaction in its current queue, then runs its main
    function in a new thread. The .join called on the thread is done to make sure that the thread can finish calculating its result
    before checking whether the transaction was sucessful or not. If the status is PENDING (see transaction.scala), it will be pushed
    back into the transactionQueue. Then a new thread for processing transactions is spawned. If the transaction is failed or
    successful, it is placed into the processedTransactions list.
    */

    def addAccount(initialBalance: Double): Account = {
        new Account(this, initialBalance)
    }

    def getProcessedTransactionsAsList: List[Transaction] = {
        processedTransactions.iterator.toList
    }

}
