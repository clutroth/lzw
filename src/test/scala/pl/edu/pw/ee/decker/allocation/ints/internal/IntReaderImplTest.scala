package pl.edu.pw.ee.decker.allocation.ints.internal

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import org.scalatest.FlatSpec

/**
  * Created by clutroth on 1/11/17.
  */
class IntReaderImplTest extends FlatSpec {

  behavior of "IntReaderImplTest"

  it should "close" in {
    val arr = Array[Byte](0xEA toByte)
    val out = new ByteArrayInputStream(arr)
    // [111][010][10] = 0xEA
    val ir = new IntReaderImpl(out, 4)

    assert(ir.read.get == 7)
    assert(ir.read.get == 2)
    assert(ir.read.get == 4)
    assert(ir.read.isEmpty)
  }

  it should "read" in {
    val arr = Array[Byte](0xEA toByte)
    val out = new ByteArrayInputStream(arr)
    // [11][101][010] = 0xEA
    val ir = new IntReaderImpl(out, 3)
    assert(ir.read.get == 3)
    assert(ir.read.get == 5)
    assert(ir.read.get == 2)
    assert(ir.read.isEmpty)
  }
  it should "read long word" in {
    val arr = List(0xEA, 0x4A , 0x67, 0x4C, 0x0) map (_ toByte) toArray
    // 111010100|100101001|100111010|1100
    val out = new ByteArrayInputStream(arr)
    val ir = new IntReaderImpl(out, 256)
    assert(ir.read.get == 0x1D4)
    assert(ir.read.get == 0x129)
    assert(ir.read.get == 0x13A)
    assert(ir.read.get == 0xC0)
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
