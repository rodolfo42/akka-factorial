package common

import scala.annotation.tailrec

object Common {
	def allFactorials =
		List(
			10000,
			20000,
			30000,
			40000,
			50000,
			60000,
			70000,
			80000,
			90000,
			10000,
			21000,
			32000,
			43000,
			54000,
			65000,
			76000,
			87000,
			98000
		)

	def factor(num: Int) = factorTail(num, 1)

	@tailrec
	private def factorTail(num: Int, acc: BigInt): BigInt = {
		(num, acc) match {
			case (0, a) => a
			case (n, a) => factorTail(n - 1, n * a)
		}
	}

	def start = Benchmark()

	def progress(left: Int) = println(s"Left to calculate: ${left}")

	def displaySum(sum: BigInt) = println(s"Final sum is ${sum.bitLength} bits long")
}

case class Benchmark {
	val startTime = System.nanoTime

	def stop = println(s"Finished in ${(System.nanoTime - startTime) / 1E9} seconds")
}
