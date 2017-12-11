package at.fhj.swengb.apps.calculator

import java.util.NoSuchElementException

import scala.util.{Failure, Success, Try}

/**
  * Companion object for our reverse polish notation calculator.
  */
object RpnCalculator {

  /**
    * Returns empty RpnCalculator if string is empty, otherwise pushes all operations
    * on the stack of the empty RpnCalculator.
    *
    * @param s a string representing a calculation, for example '1 2 +'
    * @return
    */
  def apply(s: String): Try[RpnCalculator] = {
    try{
      if (s.isEmpty)
        Try(RpnCalculator())
      else {
        val newStack: List[Op] = s.split(" ").map(x => Op(x)).toList
        var newCalc1 = RpnCalculator()
        newCalc1 = newCalc1.push(newStack).get
        Try(newCalc1)
      }
    } catch {
      case e: Exception => Try[RpnCalculator] (throw e)
    }

  }
}

/**
  * Reverse Polish Notation Calculator.
  *
  * @param stack a datastructure holding all operations
  */
case class RpnCalculator(stack: List[Op] = Nil) {

  /**
    * By pushing Op on the stack, the Op is potentially executed. If it is a Val, it the op instance is just put on the
    * stack, if not then the stack is examined and the correct operation is performed.
    *
    * @param op
    * @return
    */
  def push(op: Op): Try[RpnCalculator] = {
      if (op.isInstanceOf[Val]) {
        Try(RpnCalculator(op :: stack))
      }
      else {
        if (stack.size < 2) {
          throw new NoSuchElementException()
        }
        else {
          val secondval: Val = stack.head.asInstanceOf[Val]
          val firstval: Val = stack(1).asInstanceOf[Val]
          val result: Val = op.asInstanceOf[BinOp].eval(firstval, secondval)
          Try(RpnCalculator(result :: stack.drop(2)))
        }
      }

  }

  /**
    * Pushes val's on the stack.
    *
    * If op is not a val, pop two numbers from the stack and apply the operation.
    *
    * @param op
    * @return
    */
  def push(op: Seq[Op]): Try[RpnCalculator] = {
    var newCalc2 = RpnCalculator()
    for (x <- op) {
      newCalc2 = newCalc2.push(x).get
    }
    Try(newCalc2)
  }



  /**
    * Returns an tuple of Op and a RevPolCal instance with the remainder of the stack.
    *
    * @return
    */
  def pop(): (Op, RpnCalculator) = {
    if (stack.isEmpty == false) {
      val RevPolCal = RpnCalculator(stack.tail)
      (stack.head, RevPolCal)
    }
    else {
      throw new NoSuchElementException
    }
  }
  /**
    * If stack is nonempty, returns the top of the stack. If it is empty, this function throws a NoSuchElementException.
    *
    * @return
    */
  def peek(): Op = {
    if (stack.isEmpty == false) {
      stack.head
    }
    else {
      throw new NoSuchElementException
    }

  }

  /**
    * returns the size of the stack.
    *
    * @return
    */
  def size: Int = stack.size
}