import scala.collection.mutable.Queue

object TransactionStatus extends Enumeration {
    val SUCCESS, PENDING, FAILED = Value
}

class TransactionQueue {

    val dataStruct: Queue[Transaction] = Queue.empty[Transaction]

    def pop: Transaction = this.synchronized {dataStruct.dequeue}

    def isEmpty: Boolean =  this.synchronized { dataStruct.isEmpty }

    def push(t: Transaction): Unit = this.synchronized {dataStruct.enqueue(t)}

    def peek: Transaction = this.synchronized {dataStruct(0)}

    def iterator: Iterator[Transaction] = this.synchronized {dataStruct.toIterator}
}

class Transaction(val transactionsQueue: TransactionQueue,
                val processedTransactions: TransactionQueue,
                val from: Account,
                val to: Account,
                val amount: Double,
                val allowedAttemps: Int) extends Runnable {

    var status: TransactionStatus.Value = TransactionStatus.PENDING
    var attempt = 0

    override def run: Unit = {

        def doTransaction(): Unit =  {
            val withdrawalStatus: Either[Unit, String] = from withdraw amount
            if (withdrawalStatus.isLeft) {
                val depositStatus: Either[Unit, String] = to deposit amount
                if (depositStatus.isLeft) {
                    this.status = TransactionStatus.SUCCESS
                }
            } else {
                this.synchronized {attempt += 1}
                if(attempt >= allowedAttemps) {
                    this.status = TransactionStatus.FAILED
                } else {
                    this.status = TransactionStatus.PENDING
                }
            }
        }

        this.synchronized {
            if (status == TransactionStatus.PENDING) {
                doTransaction
            }
        }
    }
}
