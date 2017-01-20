package pl.edu.pw.ee.decker.allocation.ints.internal

import java.io.{ByteArrayInputStream, DataInputStream, EOFException, InputStream}
import java.nio.ByteBuffer

import pl.edu.pw.ee.decker.allocation.ints.IntReader
import DummyIntReader._
import com.typesafe.scalalogging.Logger

import scala.util.Try

/**
  * Created by clutroth on 1/15/17.
  */
class DummyIntReader(val in: DataInputStream, var i: Int) extends IntReader {
  def this(is: InputStream, n: Int) {
    this(new DataInputStream(is), START)
  }

  val log = Logger[DummyIntReader]

  override def read: Option[Int] = {
    i = i + 1;
    val bufSize = bytesIn(i)
    if (in.available() < bufSize)
      return None
    val buf = new Array[Byte](bufSize)
    in.read(buf)
    val result = ByteBuffer.wrap(new Array[Byte](4-buf.length)++buf).getInt
    if (result == 0)
      return None
    //log.debug(s"size:${buf.length} buf${buf.toList}result:$result")
    Some(result)
  }

  override def close(): Unit = in.close()
}

object DummyIntReader {
  val START = 256

  def bytesIn(n: Int): Int =
    Math.ceil((Math.log(n) / Math.log(2)) / 8).toInt
}
