package pl.edu.pw.ee.decker.allocation.ints.internal

import scala.annotation.tailrec

/**
  * Created by clutroth on 1/7/17.
  */
class WriterBuffer(val buf: Long,
                   val fetch: Array[Byte],
                   val usedBits: Int,
                   val codeCounter: Int) {
  def this(codeCounter: Int) {
    this(buf = 0L,
      fetch = Array(),
      usedBits = 0,
      codeCounter = codeCounter)
  }

  def write(n: Int): WriterBuffer = {
    WriterBuffer.write(this, n)
  }

  def close(): WriterBuffer = {
    WriterBuffer.close(this)
  }
}

object WriterBuffer {
  def close(wb: WriterBuffer): WriterBuffer = {
    val fetch = wb.buf << (BufUtils.BYTE_SIZE_IN_BITS - wb.usedBits)
    new WriterBuffer(buf = 0L,
      usedBits = 0,
      fetch = Array(fetch toByte),
      codeCounter = wb.codeCounter)
  }

  def write(wb: WriterBuffer, n: Int): WriterBuffer = {
    val wordLength = BufUtils.requiredBits(wb.codeCounter)
    val shiftedBuf = wb.buf << wordLength
    val newBuf = shiftedBuf | n
    val (reducedBuf, fetch, usedBits) = buf2Array(newBuf, Array(), wb.usedBits + wordLength)
    new WriterBuffer(buf = reducedBuf,
      fetch = fetch,
      codeCounter = wb.codeCounter + 1,
      usedBits = usedBits
    )
  }

  @tailrec
  def buf2Array(buf: Long, fetch: Array[Byte], usedBits: Int): (Long, Array[Byte], Int) = {
    val base = BufUtils.BYTE_SIZE_IN_BITS
    if (usedBits < base)
      return (buf, fetch, usedBits);
    else {
      val shift = usedBits - base
      val number = buf >>> shift
      val newBuf = number << shift ^ buf
      return buf2Array(newBuf, fetch :+ (number toByte), shift)
    }
  }

}
