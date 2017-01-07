package pl.edu.pw.ee.decker.lzw.internal

import org.scalatest.FlatSpec

/**
  * Created by clutroth on 02.01.17.
  */
class ListOrdering$Test extends FlatSpec {
  behavior of "ListOrdering$Test"

  def compare(a: List[Byte], b: List[Byte]) = ListOrdering.compare(a, b)

  def assertBigger(a: List[Byte], b: List[Byte]) = assert(compare(a, b) > 0)

  def assertLesser(a: List[Byte], b: List[Byte]) = assert(compare(a, b) < 0)

  it should "find greater" in {
    assertBigger(List(1, 1), List(1))
  }
  it should "find lesser" in {
    assert(compare(List(1), List(1)) == 0)
  }
  it should "find equal lists" in {
    assertLesser(List(0), List(1))
  }
  it should "pass negatives" in {
    assertBigger(List(1), List(-1))
  }
  it should "first position matters" in {
    assertLesser(List(-1, 10), List(10, 10))
  }

}
