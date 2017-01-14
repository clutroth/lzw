package pl.edu.pw.ee.decker.allocation.ints.internal

import java.io.InputStream

import pl.edu.pw.ee.decker.allocation.ints.IntReader


/**
  * Created by clutroth on 1/7/17.
  */
class IntReaderImpl(val in: InputStream,
                    var readBuf: ReaderBuffer) extends IntReader {
  def this(in: InputStream, startFrom: Int) =
    this(in, new ReaderBuffer(startFrom))

  override def read: Int = {
    val res = readBuf.read(readBuf.nextBuf)
    readBuf=res._2
    res._1
  }

  override def close(): Unit =
    in.close()

  override def isEmpty: Boolean =
    (in available) == 0 && (readBuf.fetch.isEmpty)
}
