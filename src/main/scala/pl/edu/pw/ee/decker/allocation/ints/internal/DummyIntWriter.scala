package pl.edu.pw.ee.decker.allocation.ints.internal

import java.io.{DataOutputStream, OutputStream}
import java.nio.ByteBuffer
import java.util.Arrays._

import com.typesafe.scalalogging.Logger
import pl.edu.pw.ee.decker.allocation.ints.IntWriter
import pl.edu.pw.ee.decker.allocation.ints.internal.DummyIntReader.{START, bytesIn}

/**
  * Created by clutroth on 1/15/17.
  */
class DummyIntWriter(val out: DataOutputStream, var counter: Int) extends IntWriter {
  def this(os: OutputStream, n: Int) {
    this(new DataOutputStream(os), START)
  }
  val log = Logger[DummyIntWriter]
  override def write(n: Int): Unit = {
    counter = counter + 1
    val s = 4
    val buf = ByteBuffer.allocate(s).putInt(n).array
    val bytes = s - bytesIn(counter)
    val range = copyOfRange(buf, bytes, s)
    //log.debug(s"code:$n counter:$counter, bytes: $bytes, buf: ${buf.toList} range:${range.toList}")
    out.write(range)
  }

  override def close(): Unit = {
    out.close()
  }
}
