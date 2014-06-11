package akka

import common.Common._
import common.Benchmark

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

object Factorial extends App {
  val factorials = allFactorials
  val bench = start

  val system = ActorSystem("factorial")

  val collector = system.actorOf(Props(new FactorialCollector(factorials, bench)), "collector")
}

class FactorialCollector(factorials: List[Int], bench: Benchmark) extends Actor with ActorLogging {
  var size = factorials.size
  var sum = BigInt(0)

  for (num <- factorials) {
    context.actorOf(Props(new FactorialCalculator)) ! num
  }

  def receive = {
    case (num: Int, fac: BigInt) => {
      sum = sum + fac
      size -= 1
      progress(size)

      if (size == 0) {
        bench.stop
        displaySum(sum)
        context.system.shutdown()
      }
    }
  }
}

class FactorialCalculator extends Actor {
  def receive = {
    case num: Int => sender ! (num, factor(num))
  }
}
