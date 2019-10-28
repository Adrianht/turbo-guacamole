object Task1 extends App {

  // Task 1A
  def Task1a {
    var myArray : Array[Int] = Array.empty[Int]
    for(i <- 1 to 50){
      myArray = myArray :+ i
    }
    myArray.foreach{println}
  }
  Task1a
  /*
  This function defines an array, and uses a for loop starting at 1 and endring on 50. For each iteration, myArray adds another
  element of value i so that values of 1 to 50 are added to the array. Each eleement is then printed for demonstrative purposes.
  */

  // Task 1B
  def Task1b(sumArray: Array[Int]): Int = {
    var sum : Int = 0
    for(value <- sumArray){
      sum += value
    }
    return sum
  }
  println(Task1b(Array(1,2,3,4,5)))
  /*
  This function takes an array as parameter and iterates through each element in the list using `for(value <- sumArray)`. Each
  element is then added onto the previously defined sum-value. This values is then returned.
  */

  // Task 1C
  def recursiveListAdd(list: Array[Int]): Int = {
    if (list.length == 0) 0
    else list.last + recursiveListAdd(list.init)
  }
  println(recursiveListAdd(Array(1,2,3,4,5)))
  /*
  This function adds the last element of the list to a recursive function of all elements of the list, but the last element. The
  function iterates recursively until the list is empty (with base element returned as 0), then adds every .last-element and their
  functions until the original function is completed.
  */

  // Task 1D
  def recursiveFib(nth: Int): BigInt = {
    if (nth == 1) 0
    else if (nth == 2) 1
    else recursiveFib(nth-1) + recursiveFib(nth-2)
  }
  println(recursiveFib(6))
  /*
  The Fibonacci sequence is defined by adding the two previous values in a sequence to generate the third. This function checks if
  the current number is 1 or 2 (base elements, and returns 0 and 1 respectively). Any numbers higher than these will recursively use
  the same function to find the addition of lower numbers.

  BigInt supports all discrete numbers given enough memory available, while Int has a max of 2^31 - 1 which is why BigInt is used.
  */
}
