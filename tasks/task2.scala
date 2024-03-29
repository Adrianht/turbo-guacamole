object Task2 extends App {

  // Task 2A
  def genThread[F](f: => F): Thread = {
    new Thread( new Runnable {
        def run() {
          f
        }
      })
  }

  /*
  This function takes a another function as parameter and returns a Thread using a Runnable object that implements `run` and contains
  the code to be ran. Thus, the thread contains the running function and will not be started until .start() is called.
  */

  // Task 2B

  // Removed in favor of task 2C, uncomment to test
  /*
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

  */

  /*
  By running the program, prints of "0", "1", and "2" happens seemingly randomly. What happens is that the two
  counter threads and the print thread all perform at the same time, so the actual print can happen before,
  during or after the counters have increased the counter variable. Additionally, you don't know which of the
  counters added 1 to the variable first. This can be an issue in many situations. For instance, a bank needs
  to handle many requests at the same time. If transactions are not atomic (fully completed or not at all),
  the final result can end up being wrong if two threads use the same variable(s) for different computations.
  One thread can use a variable multiple times during a sequence of calculations, but the variable is changed
  before this sequence is finished. This is an example of a race condition, where two concurrent threads
  compete for the same resources which prevents the functionality to operate properly. It is part of what's
  called lost updates-problems, because the printThread has access to the counter before both counter threads
  have finished adding their values.
  */

  // Task 2C
  private var counter: Int = 0

  def increaseCounter(): Unit = this.synchronized {
    counter += 1
  }

  def printCounter() : Unit = {
    println("Thread safe: " + counter)
  }

  val counterThread1 : Thread = genThread(increaseCounter)
  val counterThread2 : Thread = genThread(increaseCounter)
  val printThread : Thread = genThread(printCounter)

  counterThread1.start();
  counterThread2.start();
  printThread.start();

  /*
  In order to make the increaseCounter function thread-safe, it is made synchronous by adding the this.synchronized keyword. However,
  as the printThread function is not thread-safe itself, the wrong numbers will still be printed. This could be remedied by calling
  .join() on the counterThread-functions, in order to merge them into the main thread before the printThread is started.
  */

  //Task 2d
  def deadlockExample {
    val thread1: Thread = genThread(println(A.attr1))
    val thread2: Thread = genThread(println(B.attr3))
    thread1.start()
    thread2.start()
  }
  //deadlockExample //Uncomment this to test deadlock.
}

object A {
  lazy val attr1 = B.attr3
  lazy val attr2 = "If this string is reached, the threads did not lock the resource in the wanted order. R un again."
}

object B {
  lazy val attr3 = A.attr2
}

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
the deadlock will be avoided. A deadlock could also be prevented establishing a total order between resources when acquiring them, creating
a plan for resource management.
*/
