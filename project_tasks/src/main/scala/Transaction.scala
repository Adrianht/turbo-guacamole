import exceptions._
import scala.collection.mutable.Queue

object TransactionStatus extends Enumeration {
  val SUCCESS, PENDING, FAILED = Value
}

class TransactionQueue {

    // TODO
    // project task 1.1
    // Add datastructure to contain the transactions
    val dataStruct: Queue[Transaction] = Queue.empty[Transaction]

    // Remove and return the first element from the queue
    def pop: Transaction = dataStruct.dequeue

    // Return whether the queue is empty
    def isEmpty: Boolean = dataStruct.isEmpty

    // Add new element to the back of the queue
    def push(t: Transaction): Unit = dataStruct.enqueue(t)

    // Return the first element from the queue without removing it
    def peek: Transaction = dataStruct(0)

    // Return an iterator to allow you to iterate over the queue
    def iterator: Iterator[Transaction] = dataStruct.toIterator
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

        def doTransaction() = {
            //println("vi kom til transaction")
            // TODO - project task 3
            // Extend this method to satisfy requirements.
            if ((from withdraw amount).isLeft) TransactionStatus.SUCCESS else {
                attempt +=1
                //println(attempt)
                //println(allowedAttemps)
                if(attempt >= allowedAttemps){
                    //println("hvorfor ikke hit")
                    this.status = TransactionStatus.FAILED
                }
                else
                    //println("hvorfor hit")                    
                    this.status = TransactionStatus.PENDING
            }
            if ((to deposit amount).isLeft) TransactionStatus.SUCCESS else {
                attempt +=1
                if(attempt >= allowedAttemps){
                    this.status = TransactionStatus.FAILED
                }
                else this.status = TransactionStatus.PENDING
            }
            //println(this.status)
        }

        // TODO - project task 3
        // make the code below thread safe
        this synchronized {
            if (status == TransactionStatus.PENDING) {
                doTransaction
                Thread.sleep(50) // you might want this to make more room for
                                // new transactions to be added to the queue
            }
        }


    }
}
