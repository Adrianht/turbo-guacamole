object Task2 extends App {

  // Task 2A
  def genThread[F](f: => F) = {
    new Thread( new Runnable {
        def run() {
          f
        }
      })
  }

  // Task 2B
  private var counter: Int = 0

  def increaseCounter(): Unit = {
    counter += 1
  }

  def printCounter(counter : Int) : Unit = {
    println(counter)
  }

  val printThread : Thread = genThread(printCounter(counter))
  val counterThread1 : Thread = genThread(increaseCounter())
  val counterThread2 : Thread = genThread(increaseCounter())

  counterThread1.start();
  counterThread2.start();
  printThread.start();

  /*
  By running the program, prints of "0", "1", and "2" happens seemingly randomly. What happens is that the two
  counter threads and the print thread all perform at the same time, so the actual print can happen before,
  during or after the counters have increased the counter variable. Additionally, you don't know which of the
  counters added 1 to the variable first. This can be an issue in many situations. For instance, a bank needs
  to handle many requests at the same time. If transactions are not atomic (fully completed or not at all),
  the final result can end up being wrong if two threads use the same variable(s) for different computations.
  One thread can use a variable multiple times during a sequence of calculations, but the variable is changed
  before this sequence is finished. This is called the lost updates-problem, because the printThread has access
  to the counter before both counter threads have finished adding their values.
  */
}
