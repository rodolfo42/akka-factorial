package akka

import common._

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

object Factorial extends App {
  val factorials = Common.factorials
  val benchmark = Common.start

  val system = ActorSystem("factorial")

  val collector = system.actorOf(Props(new FactorialCollector(factorials, benchmark)), "collector")
}

class FactorialCollector(factorials: List[Int], benchmark: Benchmark) extends Actor with ActorLogging {
  var size = factorials.size

  for (num <- factorials) {
    context.actorOf(Props(new FactorialCalculator)) ! num
  }

  def receive = {
    case (num: Int, fac: BigInt) => {
      size -= 1
      Common.left(size)

      if (size == 0) {
        benchmark.stop
        context.system.shutdown()
      }
    }
  }
}

class FactorialCalculator extends Actor {
  def receive = {
    case num: Int => sender ! (num, Common.factor(num))
  }
}
