package pl.edu.pw.ee.decker.lzw.internal

import scala.collection.mutable

/**
  * Created by clutroth on 05.01.17.
  */
object LZWDictionary {
  def seqList(list: List[Byte]): List[(List[Byte], Int)] = list map (List(_)) zip (Stream from 1)

  def createCompression(list: List[Byte]): mutable.TreeMap[List[Byte], Int] = {

    mutable.TreeMap(seqList(list): _*)(ListOrdering)
  }

  def createDecompression(list: List[Byte]): mutable.TreeMap[Int, List[Byte]] = {
    val reversed = seqList(list) map (e => e._2 -> e._1)
    mutable.TreeMap(reversed: _*)
  }

}
