package pl.edu.pw.ee.decker.allocation.ints.internal

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import org.scalatest.FlatSpec

/**
  * Created by clutroth on 1/11/17.
  */
class IntReaderImplTest extends FlatSpec {

  behavior of "IntReaderImplTest"

  it should "close" in {
    val arr = Array[Byte](0xDA toByte)
    val out = new ByteArrayInputStream(arr) // [111][010][10] = 0xDA
    val ir = new IntReaderImpl(out, 4)

    assert(ir.read == 7)
    assert(ir.read == 2)
    assert(ir.read == 4)
  }

  it should "read" in {
    val arr = Array[Byte](0xDA toByte)
    val out = new ByteArrayInputStream(arr) // [11][101][010] = 0xDA
    val ir = new IntReaderImpl(out, 3)
    assert(ir.read == 3)
    assert(ir.read == 5)
    assert(ir.read == 2)

  }

}
