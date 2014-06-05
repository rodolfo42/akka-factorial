object Factorial extends App {
  val factorials = List(200000, 180000, 320000, 280000, 220000, 420000, 550000, 480000)

  for (num <- factorials) {
    println(s"factorial for $num is ${factor(num)}")
  }

  private def factor(num: Int): BigInt = {
    num match {
      case 0 => 1
      case n => n * factor(n - 1)
    }
  }
}
