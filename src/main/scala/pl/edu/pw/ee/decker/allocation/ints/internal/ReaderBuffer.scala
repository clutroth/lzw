package pl.edu.pw.ee.decker.allocation.ints.internal

import pl.edu.pw.ee.decker.allocation.ints.internal.ReaderBuffer._

import scala.annotation.tailrec

/**
  * Created by clutroth on 1/7/17.
  */
class ReaderBuffer(val buf: Long,
                   val codeCount: Int,
                   val fetch: List[Int],
                   val usedBits: Int,
                   val nextBuf: Array[Byte]) {
  def this(codeCount: Int) =
    this(buf = 0,
      codeCount = codeCount,
      fetch = List(),
      usedBits = 0,
      nextBuf = Array())

  def read(data: Array[Byte]): (Option[Int], ReaderBuffer) = {
    ReaderBuffer.readInt(this)
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[ReaderBuffer]

  override def equals(other: Any): Boolean = other match {
    case that: ReaderBuffer =>
      (that canEqual this) &&
        buf == that.buf &&
        codeCount == that.codeCount &&
        fetch == that.fetch &&
        usedBits == that.usedBits
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(buf, codeCount, fetch, usedBits)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString =
    s"ReaderBuffer($buf, $codeCount, ${seqToHexStr(fetch)}, $usedBits, ${seqToHexStr(nextBuf toList)})"
}

object ReaderBuffer {
  def seqToHexStr[A >: Number](list: List[A]): String = {
    val sb = new StringBuilder("Hex")
    sb append '('
    list map (s => f"$s%h") foreach (sb.append(_).append(", "))
    sb append (')') toString()
  }

  @tailrec
  def read(rb: ReaderBuffer, data: Array[Byte]): ReaderBuffer = {
    val wordLength: Int = BufUtils.requiredBits(rb.codeCount)
    if ((data isEmpty) && (rb.usedBits < wordLength))
      return rb

    @tailrec
    def loadBuf(usedBits: Int, data: Array[Byte], buf: Long): (Long, Array[Byte], Int) = {
      if (usedBits >= wordLength)
        return (buf, data, usedBits)
      val loadedBuf = (buf << BASE) | ((data head) & 0xFF)
      loadBuf(usedBits + BASE, data tail, loadedBuf)
    }

    val (loadedBuf, reducedData, usedLoadedBits) = loadBuf(rb.usedBits, data, rb.buf)
    val shift = usedLoadedBits - wordLength
    val code = loadedBuf >>> (shift) toInt
    val reducedBuf = loadedBuf ^ (code << shift)

    val newRb = new ReaderBuffer(buf = reducedBuf,
      codeCount = rb.codeCount + 1,
      fetch = rb.fetch :+ code,
      usedBits = shift,
      nextBuf = new Array(newBufSize(wordLength)))
    read(newRb, reducedData)
  }

  def newBufSize(wordLength: Int): Int = {
    Math.max(Math.ceil((wordLength toDouble) / BASE) toInt, 1)
  }

  def readInt(rb: ReaderBuffer): (Option[Int], ReaderBuffer) = {
    return if (rb.fetch isEmpty) {
      (None, rb)
    } else {
      (rb.fetch headOption, new ReaderBuffer(buf = rb.buf,
        codeCount = rb.codeCount,
        fetch = rb.fetch tail,
        usedBits = rb.usedBits,
        nextBuf = rb.nextBuf))
    }
  }

  val BASE = BufUtils.BYTE_SIZE_IN_BITS
}
