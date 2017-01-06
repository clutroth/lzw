package pl.edu.pw.ee.decker.lzw.internal


import com.typesafe.scalalogging.Logger

import scala.collection.mutable

/**
  * Created by clutroth on 03.01.17.
  */
class MutableDecompression(val dictionary: mutable.TreeMap[Int, List[Byte]]) {
  var previousCode: Option[Int] = None
  Log.init(dictionary)

  object Log {
    val log = Logger[MutableDecompression]

    def str(list: List[Byte]): String =
      new String(list toArray)

    def init(dictionary: mutable.TreeMap[Int, List[Byte]]): Unit =
      log debug s"initialized wth dictionary: $dictionary"

    def seqToStr(s: (Int, List[Byte])) =
      Seq(s) map (s => (s._1, str(s._2))) head

    def msg(m: String)(code: Int, word: List[Byte], dictionary: mutable.TreeMap[Int, List[Byte]]) =
      log debug s"$m code ($code->$word) added ${seqToStr((dictionary toSeq) last)}"

    def newOne(code: Int, word: List[Byte], dictionary: mutable.TreeMap[Int, List[Byte]]) =
      msg("new one")(code, word, dictionary)

    def existing(code: Int, word: List[Byte], dictionary: mutable.TreeMap[Int, List[Byte]]) =
      msg("existing")(code, word, dictionary)

    def firstCode(code: Int, word: List[Byte]) =
      log debug s"first code $code for word $word(${str(word)})"


  }

  def put(code: Int): List[Byte] = {
    if (previousCode isEmpty) {
      previousCode = Some(code)
      val word = dictionary(code)
      Log.firstCode(code, word)
      word
    } else {
      val lastWord = dictionary(previousCode get)
      previousCode = Some(code)
      if (dictionary contains code) {
        val word = dictionary(code)
        addWord(dictionary, lastWord :+ (word head))
        Log.existing(code, word, dictionary)
        word
      } else {
        val word = lastWord :+ (lastWord head)
        addWord(dictionary, word)
        Log.newOne(code, word, dictionary)
        word
      }
    }
  }

  def addWord(dictionary: mutable.Map[Int, List[Byte]], word: List[Byte]): Unit = {
    dictionary += (((dictionary size) + 1) -> word)
  }
}
