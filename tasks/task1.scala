object Task1 extends App {

  // Task 1)a)
  def Task1a {
    var myArray : Array[Int] = Array.empty[Int]
    for(i <- 1 to 50){
      myArray = myArray :+ i
    }
    myArray.foreach{println}
  }
  println(Task1a)


  def recursiveListAdd(list: Array[Int]): Int = {
    // Task 1c
    if (list.length == 0) 0
    else list.last + recursiveListAdd(list.init)
  }
  println(recursiveListAdd(Array(1,2,3,4,5)))

  def recursiveFib(nth: Int): BigInt = {
    // Task 1d
    /*
    BigInt supports all discrete numbers given enough memory available, while
    Int has a max of 2^31 - 1

    */
    if (nth == 1) 0
    else if (nth == 2) 1
    else recursiveFib(nth-1) + recursiveFib(nth-2)
  }
  println(recursiveFib(6))

  def Task1b(sumArray: Array[Int]): Int = {
    var sum : Int = 0
    for(value <- sumArray){
      sum += value
    }
    return sum
  }
  println(Task1b(Array(1,2,3,4,5)))
}
