package pl.edu.pw.ee.decker.allocation.ints.internal

import org.scalatest.FlatSpec

/**
  * Created by clutroth on 1/7/17.
  */
class ReaderBuffer$Test extends FlatSpec {

  behavior of "ReaderBuffer$Test"

  //  it should "createBuffer" in {
  //    def assertCreateBuffer(usedBits: Int, wordLenght: Int)(bufSize: Int): Unit = {
  //      val actual = ReaderBuffer.createBuffer(wordLenght, usedBits, 4)
  //      assert((actual size) == bufSize)
  //    }
  //
  //    assertCreateBuffer(2, 4)(1)
  //    assertCreateBuffer(3, 8)(2)
  //    assertCreateBuffer(1, 11)(3)
  //  }

  it should "read 4 bit Int on clear buffer" in {
    val rb = new ReaderBuffer(codeCount = 8)
    val actual = ReaderBuffer.read(rb, Array(0xAB toByte))
    val expected = new ReaderBuffer(buf = 0,
      codeCount = 10,
      fetch = List(0xA, 0xB),
      usedBits = 0,
      nextBuf = new Array(1))
    assert(expected == actual)
  }
  it should "extend wordLength when necessary" in {
    val rb = new ReaderBuffer(codeCount = 0xF)
    val actual = ReaderBuffer.read(rb, Array(0xAD toByte, 0x80 toByte))//[1010]|[1101|1][000|00][00]
    val expected = new ReaderBuffer(buf = 0,
      codeCount = 18,
      fetch = List(0xA, 0x1B, 0),
      usedBits = 2,
      nextBuf = new Array(1))
    assert(expected == actual)
  }
  it should "extend nextBuf when oldone cant contain next code" in {
    val rb = new ReaderBuffer(codeCount = 0xFF)
    val actual = ReaderBuffer.read(rb, Array(0xAB toByte))
    val expected = new ReaderBuffer(buf = 0,
      codeCount = 0x100,
      fetch = List(0xAB),
      usedBits = 0,
      nextBuf = new Array(2))
    assert(expected == actual)
  }
  it should "for 2 and 3 as 5th and 6th word read two int codes" in {
    val rb = new ReaderBuffer(codeCount = 4)
    val actual = ReaderBuffer.read(rb, Array(0x4D toByte))
    //[010=2][011=3][01=1]
    val expected = new ReaderBuffer(buf = 1,
      codeCount = 6,
      fetch = List(0x2, 0x3),
      usedBits = 2,
      nextBuf = new Array(1))
    assert(expected == actual)
  }
  it should "for wordLength give expectedSize long buf" in {
    assertBufSize(1, 1)
    assertBufSize(2, 1)
    assertBufSize(3, 1)
    assertBufSize(4, 1)
    assertBufSize(7, 1)
    assertBufSize(8, 1)
    assertBufSize(9, 2)
    assertBufSize(16, 2)
    assertBufSize(17, 3)

    def assertBufSize(wordLength: Int, expectedSize: Int) =
      assert(expectedSize == ReaderBuffer.newBufSize(wordLength))
  }

}
