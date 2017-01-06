package pl.edu.pw.ee.decker.lzw.internal

import org.scalatest.FlatSpec

/**
  * Created by clutroth on 02.01.17.
  */
class ListOrdering$Test extends FlatSpec {
  behavior of "ListOrdering$Test"

  def compare(a: List[Byte], b: List[Byte]) = ListOrdering.compare(a, b)

  def isBigger(a: List[Byte], b: List[Byte]) = compare(a, b) == -1

  def isLesser(a: List[Byte], b: List[Byte]) = compare(a, b) == 1

  it should "find greater" in {
    assert(isBigger(List(1), List(1, 1)))
  }
  it should "find lesser" in {
    assert(compare(List(1), List(1)) == 0)
  }
  it should "find equal lists" in {
    assert(isLesser(List(1), List(0)))
  }

}
