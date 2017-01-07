package pl.edu.pw.ee.decker.lzw.internal

import scala.annotation.tailrec
import scala.math.Ordered.orderingToOrdered

/**
  * Created by clutroth on 02.01.17.
  */
object ListOrdering extends Ordering[List[Byte]] {

  @tailrec
  override def compare(a: List[Byte], b: List[Byte]) = {
    (a, b) match {
      case Pair(Nil, Nil) => 0
      case Pair(_, Nil) => 1
      case Pair(Nil, _) => -1
      case Pair(_, _) => {
        val a1 = a head
        val b1 = b head

        if (a1 == b1)
          compare(a tail, b tail)
        else
          a1 compare b1
      }
    }
  }
}
