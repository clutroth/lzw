package pl.edu.pw.ee.decker.allocation.ints.internal

import java.io.OutputStream

import pl.edu.pw.ee.decker.allocation.ints.IntWriter


/**
  * Created by clutroth on 1/7/17.
  */
class IntWriterImpl private(val out: OutputStream,
                            var buf: WriterBuffer) extends IntWriter with AutoCloseable {
  def this(out: OutputStream, startFrom: Int) =
    this(out, new WriterBuffer(startFrom))

  override def write(n: Int): Unit = {
    buf = buf.write(n)
    out.write(buf.fetch)
  }

  override def close(): Unit = {
    buf = buf.close()
    out.write(buf.fetch)
  }
}
