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
    ???
  }

  override def close(): Unit =
    in.close()
}
