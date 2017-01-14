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
    val out = new ByteArrayInputStream(arr)
    // [111][010][10] = 0xDA
    val ir = new IntReaderImpl(out, 4)

    assert(ir.read.get == 7)
    assert(ir.read.get == 2)
    assert(ir.read.get == 4)
    assert(ir.read.isEmpty)
  }

  it should "read" in {
    val arr = Array[Byte](0xDA toByte)
    val out = new ByteArrayInputStream(arr)
    // [11][101][010] = 0xDA
    val ir = new IntReaderImpl(out, 3)
    assert(ir.read.get == 3)
    assert(ir.read.get == 5)
    assert(ir.read.get == 2)
    assert(ir.read.isEmpty)

  }
  it should "be empty on zero code read" in {
    val arr = Array[Byte](0)
    val out = new ByteArrayInputStream(arr)
    // [11][101][010] = 0xDA
    val ir = new IntReaderImpl(out, 3)
    assert(ir.read.isEmpty)
  }

}
