package simple

import common._

object Factorial extends App {
  val factorials = Common.factorials
  var size = factorials.size

  val benchmark = Common.start

  for (num <- factorials) {
      Common.factor(num)

      size -= 1
      Common.left(size)
  }

  benchmark.stop
}
