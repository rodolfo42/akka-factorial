package akkafutures

import common.Common._
import common.Benchmark

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class Start

object Factorial extends App {
  val factorials = allFactorials
  val bench = start

  val system = ActorSystem("factorial")

  val collector = system.actorOf(Props(new FactorialCollector(factorials, bench)), "collector")
  collector ! Start
}

class FactorialCollector(factorials: List[Int], bench: Benchmark) extends Actor with ActorLogging {
  var size = factorials.size
  var sum = BigInt(0)

  def receive = {
      case Start => {
          val timeout = Timeout(30 seconds)

          val futures: List[Future[Any]] = for (num <- factorials)
              yield ask(context.actorOf(Props(new FactorialCalculator)), num)(timeout)

          val sum = Future.reduce(futures)( (a, b) => (a, b) match {
              case (x: BigInt, y: BigInt) => progress(2); x + y
          } )

          def end(x: BigInt) = {
              displaySum(x)
              context.system.shutdown()
          }

          sum map {
              case x: BigInt => end(x)
              case _ => println("???")
          }
      }
  }
}

class FactorialCalculator extends Actor {
  def receive = {
    case num: Int => factor(num)
  }
}
