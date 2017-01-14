package pl.edu.pw.ee.decker.allocation.ints.internal

import java.io.{DataOutputStream, OutputStream}

import pl.edu.pw.ee.decker.allocation.ints.IntWriter

/**
  * Created by clutroth on 1/15/17.
  */
class DummyIntWriter(val out: DataOutputStream) extends IntWriter {
  def this(os: OutputStream, n: Int) {
    this(new DataOutputStream(os))
  }

  override def write(n: Int): Unit = {
    out.writeInt(n)
  }

  override def close(): Unit = {
    out.close()
  }
}
