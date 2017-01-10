package pl.edu.pw.ee.decker.allocation.ints.internal

import org.scalatest.FlatSpec

import scala.annotation.tailrec

/**
  * Created by clutroth on 1/7/17.
  */
class WriterBufferTest extends FlatSpec {
  behavior of "WriterBufferTest"

  def toBinStr(long: Long): String =
    String format("%32s", long toBinaryString) replace(" ", "0")

  def toBinStr(b: Byte): String =
    String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');


  @tailrec
  private def write(wb: WriterBuffer, ints: List[Int]): WriterBuffer =
    ints match {
      case Nil => wb
      case _ => write(WriterBuffer.write(wb, ints head), ints tail)
    }

  it should "write 1, 8 in 7 bits" in {
    val wb = write(new WriterBuffer(3), List(1, 8))
    assert((wb buf) == 24L) // 0011000
    assert((wb usedBits) == 7)

  }

  it should "write two ones in 6 bits" in {
    val wb = write(new WriterBuffer(3), List(1, 1))
    assert((wb buf) == 9L) // 001001
    assert((wb usedBits) == 6)
  }

  it should "move first byte to fetch when using more than 8 bits" in {
    val wb = write(new WriterBuffer(3), List(1, 8, 2)) //[00110000]010
    assert(((wb fetch) deep) == (Array[Byte]((0x30) toByte) deep))
    assert((wb usedBits) == 3)
    assert((wb buf) == 2L)
  }

  it should "load 0xABCDE to [AB][CD] and E in buf in]" in {
    val (buf, fetch, used) = WriterBuffer.buf2Array(0xABCDEL, Array(), 20)
    assert(used == 4)
    assert(buf == 0xEL)
    assert((fetch deep) == (Array[Byte](0xAB toByte, 0xCD toByte) deep))
  }
  it should "close and clean" in {
    val wb = write(new WriterBuffer(3), List(1))
    //001
    val closed = WriterBuffer.close(wb)
    assert(closed.buf == 0L)
    assert(closed.usedBits == 0)
    assert(closed.fetch.deep == Array[Byte](0x20).deep) //00100000
  }
  it should "increment used bits when range is fully used" in{
    val wb = write(new WriterBuffer(3), List(7))
    assert(wb.usedBits == 4)

  }
}
