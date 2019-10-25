object Task2 extends App {

  def genThread[F](f: => F) = {
    new Thread( new Runnable { def run() { f } } )
  }

  def printInThread(statement : String) : Unit = {
    println(statement)
  }

  val newThread : Thread = genThread(printInThread("test"))
  newThread.start();

}