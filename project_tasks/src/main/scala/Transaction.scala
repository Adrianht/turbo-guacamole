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
        /*
        doTransaction models a single transaction from one account to another.
        */

        def doTransaction(): Unit =  {
            val withdrawalStatus: Either[Unit, String] = from withdraw amount
            if (withdrawalStatus.isLeft) {
                val depositStatus: Either[Unit, String] = to deposit amount
                if (depositStatus.isLeft) {
                    this.status = TransactionStatus.SUCCESS
                }
            /*
            widthdrawalStatus is a value retrieved after an attempt to widthdraw an amount from an account and deposit it to
            another. Only if both succeed, will the TransactionStatus be transformed into SUCCESS.
            */
            } else {
                this.synchronized {attempt += 1}
                if(attempt >= allowedAttemps) {
                    this.status = TransactionStatus.FAILED
                } else {
                    this.status = TransactionStatus.PENDING
                }
            }
            /*
            If the transaction attempt as described above fails, a synchronized value (attempt) is increased. The code above
            checks whether the transaction is within its allowed attempts and changes its status to PENDING if it is within
            the bounds and FAILED if it exceeds it.
            */
        }

        this.synchronized {
            if (status == TransactionStatus.PENDING) {
                doTransaction
            }
        }
        /*
        This code retries the transaction as long as the current TransactionStatus is PENDING, allowing for multiple attempts.
        It is synchronized to make sure the executed attempts do not exceed what is intended, as is the addition of `attempts += 1`
        */
    }
}
