package pl.edu.pw.ee.decker.lzw.internal

import com.typesafe.scalalogging.Logger
import pl.edu.pw.ee.decker.lzw.LZW

import scala.collection.mutable

/**
  * Created by clutroth on 31.12.16.
  */
class MutableCompression(val dictionary: mutable.Map[List[Byte], Int]) {
  var lastString: List[Byte] = List()

  val log = Logger[MutableCompression]
  log debug (dictionary toString())

  def put(b: Byte): Option[Int] = {
    val currentString = lastString :+ b
    if (dictionary contains currentString) {
      lastString = currentString
      None
    } else {
      val lastCode = dictionary(lastString)
      dictionary += currentString -> ((dictionary size) + 1)
      lastString = List(b)
      Some(lastCode)
    }
  }

  def buffer: Int =
    if (lastString isEmpty)
      throw new IllegalStateException("Buffer is empty")
    else
      dictionary(lastString)

}
