package at.fhj.swengb.assignments.functional

/**
  * In this assignment you have the chance to demonstrate basic understanding of
  * functions like map/filter/foldleft a.s.o.
  **/
object FunctionalAssignment {

  /**
    * A function which returns its parameters in a changed order. Look at the type signature.
    */
  def flip[A, B](t: (A, B)): (B, A) = { (t._2, t._1)}

  /**
    * given a Seq[A] and a function f : A => B, return a Seq[B]
    */
  def unknown[A, B](as: Seq[A], fn: A => B): Seq[B] = {

    for {
      x <- as
    } yield fn(x)
  }

  /**
    * Returns the absolute value of the parameter i.
    *
    * @param i a value, either with a positive or a negative sign.
    * @return
    */
  def abs(i: Int): Int = if (i < 0) i * -1 else i


  // Describe with your own words what this function does.
  // in the comment below, add a description what this function does - in your own words - and give
  // the parameters more descriptive names.
  //
  // Afterwards, compare your new naming scheme with the original one.
  // What did you gain with your new names? What did you loose?
  //
  /**
    *
    * @param list a value of the data type list.
    * @param acc accumulator carried and extended during executing the function.
    * @param func a function taking the accumulator and the content of the list as two valid parameters.
    * @tparam A variable of type A.
    * @tparam B variable of type B.
    * @return command used to get the result of the function.
    *
    *  Die Funktion "op" agiert wie eine normale FoldLeft-Funktion, die den Accumulator (b) als Startwert nimmt und die
    *  gegebene Funktion (fn) auf die restlichen Parameter anwendet.
    *  In der unteren Funktion "sum" ist die Funktionalität der op-Funktion besser erkennbar. In diesem Fall wird die
    *  Zahl 0 als Akkumulator verwendet, die Funktion ist (_+_), wobei der Akkumulator additiv immer um die restlichen
    *  Werte der Liste "numbers" erweitert wird.
    *
    *  Es ist sinnvoll die Hauptfunktion "op" und die sekundäre Funktion "func" zu benennen, da somit klar wird, dass es
    *  sich um zwei unterschiedliche Funktionen handelt.
    *  Auch die Benennung A, B ist nicht schlecht, da somit zum Vorschein kommt, dass Dies zwei Variablen eines
    *  unterschiedlichen Typs sind.
    *  Nur die Variable "b" wurde unpassend benannt, da nicht gleich klar wird, dass es ein Akkumulator ist. Eine
    *  bessere Bezeichnung wäre zum Beispiel "acc".
    */
  def op[A, B](list: Seq[A], acc: B)(func: (B, A) => B): B = list.foldLeft(acc)(func)

  /**
    * implement the summation of the given numbers parameter.
    * Use the function 'op' defined above.
    *
    * @param numbers a list of numbers.
    * @return command used to get the result of the function.
    */
  def sum(numbers: Seq[Int]): Int = op(numbers,0) (_+_)


  /**
    * calculate the factorial number of the given parameter.
    *
    * for example, if we pass '5' as parameter, the function should do the following:
    *
    * 5! = 5 * 4 * 3 * 2 * 1
    *
    * @param i parameter for which the factorial must be calculated
    * @return i!
    */
  def fact(i: Int): Int = {
   if (i > 0) {
     var m = i
     for (x <- 1 to (i - 1)) {
        m = m * x
     }
     m
   }
   else 0
  }

  /**
    * compute the n'th fibonacci number
    *
    * hint: use a internal loop function which should be written in a way that it is tail
    * recursive.
    *
    * https://en.wikipedia.org/wiki/Fibonacci_number
    */
  def fib(n: Int): Int = n match {
    case 0 => n
    case 1 => n
    case _ => fib(n-1) + fib(n-2)
  }

  /**
    * Implement a isSorted which checks whether an Array[A] is sorted according to a
    * given comparison function.
    *
    * Implementation hint: you always have to compare two consecutive elements of the array.
    * Elements which are equal are considered to be ordered.
    */
  def isSorted[A](as: Array[A], gt: (A, A) => Boolean): Boolean = {
    var v: Boolean = true

    for (i <- 0 until as.length-1) {
      v = v && gt(as(i),as(i+1))
    }
    v
  }

  /**
    * Takes both lists and combines them, element per element.
    *
    * If one sequence is shorter than the other one, the function stops at the last element
    * of the shorter sequence.
    */
  def genPairs[A, B](as: Seq[A], bs: Seq[B]): Seq[(A, B)] = {
    for {

      x <- as
      y <- bs if as.indexOf(x) == bs.indexOf(y)
    } yield (x,y)
  }

  // a simple definition of a linked list, we define our own list data structure
  sealed trait MyList[+A]

  case object MyNil extends MyList[Nothing]

  case class Cons[+A](head: A, tail: MyList[A]) extends MyList[A]

  // the companion object contains handy methods for our data structure.
  // it also provides a convenience constructor in order to instantiate a MyList without hassle
  object MyList {

    def sum(list: MyList[Int]): Int = list match {
      case MyNil => 0

      case Cons(head,tail) => head + sum(tail)
    }

    def product(list: MyList[Int]): Int = list match {
      case MyNil => 1

      case Cons(head,tail) => head * product(tail)
    }

    def apply[A](as: A*): MyList[A] = {
      if (as.isEmpty) MyNil
      else Cons(as.head, apply(as.tail: _*))
    }

  }

}

