package at.fhj.swengb.apps.calculator

import java.util.NoSuchElementException

import scala.util.Try

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
    if (s.isEmpty == true) {
      Try(RpnCalculator())
    } else {
      val stack_content: List[Op] = s.split(' ').map(p => Op(p)).toList
      var calcul = RpnCalculator()
      calcul = calcul.push(stack_content).get
      Try(calcul)
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
      val first_content: Val = stack.head.asInstanceOf[Val]
      val second_content: Val = stack.tail.head.asInstanceOf[Val]
      val result: Val = op.asInstanceOf[BinOp].eval(second_content , first_content)
      Try(RpnCalculator(result :: stack.drop(2)))
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
    var calculat = RpnCalculator()
    for (i <- op) {
      calculat = calculat.push(i).get
    }
    Try(calculat)
  }

  /**
    * Returns an tuple of Op and a RevPolCal instance with the remainder of the stack.
    *
    * @return
    */
  def pop(): (Op, RpnCalculator) = (stack.head, RpnCalculator(stack.tail))

  /**
    * If stack is nonempty, returns the top of the stack. If it is empty, this function throws a NoSuchElementException.
    *
    * @return
    */
  def peek(): Op = if (stack.isEmpty) {
    throw new NoSuchElementException} else {stack.head}

  /**
    * returns the size of the stack.
    *
    * @return
    */
  def size: Int = stack.size
}