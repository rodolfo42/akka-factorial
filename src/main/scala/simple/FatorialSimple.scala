package simple

import common.Common._

object Factorial extends App {
  val factorials = allFactorials
  var size = factorials.size
  var sum = BigInt(0)

  val bench = start

  for (num <- factorials) {
      sum = factor(num) + sum
      size -= 1
      progress(size)
  }

  bench.stop
  displaySum(sum)
}
