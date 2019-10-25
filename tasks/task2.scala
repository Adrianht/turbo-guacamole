object Task2 extends App {

  def genThread[F](f: => F): Thread = {
    new Thread( new Runnable { def run() { f } } )
  }

  def printInThread(statement : String) : Unit = {
    println(statement)
  }

  val newThread : Thread = genThread(printInThread("test"))
  newThread.start()

  //Task 2d
  /*
  A deadlock is a scenario where two concurrent processes want tries to access two related resources which in turn
  needs to access each other. Both of the processes will then be locked, waiting for the other process to finish using
  its resource.
  This is because only one process can trigger the evaluation of a lazy val, while the other awaits for the evaluated result to be saved.
  To prevent deadlocks, you can prevent any of:
    Mutual Exclusion
    Hold and Wait
    No preemption
    Circular wait
  i.e., one can instantiate values immediately so that the resource can be accessed without locking it.
  One can also check whether a resource request will put the system in deadlock, and thus deny or await the request until
  the deadlock will be avoided.
  */



  def deadlockExample {
    val thread1: Thread = genThread(println(A.attr1))
    val thread2: Thread = genThread(println(B.attr3))
    thread1.start()
    thread2.start()
  }
  deadlockExample //Uncomment this to test deadlock.
}
object A {
  lazy val attr1 = B.attr3
  lazy val attr2 = "If this string is reached, the threads did not lock the resource in the wanted order. R un again."
}

object B {
  lazy val attr3 = A.attr2
}
