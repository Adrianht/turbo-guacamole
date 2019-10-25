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
  
  def Task1b(sumArray: Array[Int]): Int = {
    var sum : Int = 0
    for(value <- sumArray){
      sum += value
    }
    return sum
  }
  println(Task1b(Array(1,2,3,4,5)))
}