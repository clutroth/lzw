package pl.edu.pw.ee.decker.lzw.internal

import com.typesafe.scalalogging.Logger
import pl.edu.pw.ee.decker.lzw.LZW

import scala.collection.mutable

/**
  * Created by clutroth on 31.12.16.
  */
class MutableCompression(val dictionary: mutable.TreeMap[List[Byte], Int]) {
  var lastString: List[Byte] = List()
//  Log.init(dictionary)

  object Log {
    val log = Logger[MutableCompression]

    def str(list: List[Byte]): String =
      new String(new String(list toArray))

    def init(dictionary: mutable.TreeMap[List[Byte], Int]) =
      log debug (dictionary toString())

    def logDic(msg: String)
              (lastString: List[Byte], byte: Byte, dictionary: mutable.TreeMap[List[Byte], Int]) =
      log debug s"$msg ${str(lastString)}(${byte toChar}) as code ${dictionary(lastString :+ byte)}"

    def newWord(lastString: List[Byte], byte: Byte, dictionary: mutable.TreeMap[List[Byte], Int])
               (returned: Int) = {
      logDic("new word")(lastString, byte, dictionary)
      log debug s"returned $returned"
    }

    def knownWord(lastString: List[Byte], byte: Byte, dictionary: mutable.TreeMap[List[Byte], Int]) =
      logDic("known word")(lastString, byte, dictionary)
  }

  def put(b: Byte): Option[Int] = {
    val currentString = lastString :+ b
    if (dictionary contains currentString) {
//      Log.knownWord(lastString, b, dictionary)
      lastString = currentString
      None
    } else {
      val lastCode = dictionary(lastString)
      dictionary += currentString -> ((dictionary size) + 1)
//      Log.newWord(lastString, b, dictionary)(lastCode)
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
