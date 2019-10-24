object Task1 extends App {
  var myArray = Array.empty[Int]
  for(i <- 1 to 50){
    myArray = myArray :+ i
  }
  myArray.foreach{println}
}