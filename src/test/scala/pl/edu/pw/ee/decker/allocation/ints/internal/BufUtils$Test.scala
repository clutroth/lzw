package pl.edu.pw.ee.decker.allocation.ints.internal

import org.scalatest.FlatSpec

/**
  * Created by clutroth on 1/10/17.
  */
class BufUtils$Test extends FlatSpec {
  it should "know required bits" in {
    def assertBits(number: Int, bits: Int) =
      assert(BufUtils.requiredBits(number) == bits)

    assertBits(0, 0)
    assertBits(1, 1)
    assertBits(2, 2)
    assertBits(3, 2)
    assertBits(4, 3)
    assertBits(8, 4)
    assertBits(16, 5)
    assertBits(32, 6)
    assertBits(Int.MaxValue, 31)
  }
  it should "convert 0xAB byte to 0xAB Int" in {
    assert(BufUtils.binByteToBinInt(0xAB toByte) == 0xAB)
  }
}
