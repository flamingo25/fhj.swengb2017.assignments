package at.fhj.swengb.apps.calculator

import org.scalatest.WordSpecLike

import scala.util.{Failure, Success}

/**
  * A specification for a reverse polish notation calculator.
  */
class MyRpnCalculatorSpec extends WordSpecLike {

  val EmptyCal: RpnCalculator = RpnCalculator()

  "Reverse Polish Notation Calculator" should {
    "be able to add a Val" in {
      assert(EmptyCal.push(Val(0.0)).isSuccess)
    }

    "be able to put two vals on the stack" in {
      RpnCalculator().push(Val(1)) match {
        case Success(cal) =>
          assert(cal.stack.nonEmpty)
          assert(cal.stack.size == 1)
          assert(cal.stack(0) == Val(2))
        case Failure(exception) => fail(exception.getMessage)
      }

    }
  }
}