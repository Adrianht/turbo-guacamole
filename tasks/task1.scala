object Task1 extends App {
  var myArray = Array.empty[Int]
  for(i <- 1 to 50){
    myArray = myArray :+ i
  }
  myArray.foreach{println}

  def recursiveListAdd(list: Array[Int]): Int = {
    if (list.length == 0) 0
    else list.last + recursiveListAdd(list.init)
  }
  println(recursiveListAdd(Array(1,2,3,4,5)))

  def recursiveFib(nth: Int): BigInt = {
    /*
    BigInt supports all discrete numbers given enough memory available, while
    Int has a max of 2^31 - 1

    */
    if (nth == 1) 0
    else if (nth == 2) 1
    else recursiveFib(nth-1) + recursiveFib(nth-2)
  }
  println(recursiveFib(6))
}

