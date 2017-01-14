package pl.edu.pw.ee.decker.allocation.ints.internal

import java.io.{ByteArrayInputStream, DataInputStream, EOFException, InputStream}

import pl.edu.pw.ee.decker.allocation.ints.IntReader

import scala.util.Try

/**
  * Created by clutroth on 1/15/17.
  */
class DummyIntReader(val in: DataInputStream) extends IntReader {
  def this(is: InputStream, n: Int) {
    this(new DataInputStream(is))
  }

  override def read: Option[Int] = {
    try
      Some(in.readInt())
    catch {
      case _ => None
    }
  }

  override def close(): Unit = in.close()
}
