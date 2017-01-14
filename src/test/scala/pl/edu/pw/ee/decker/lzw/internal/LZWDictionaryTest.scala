package pl.edu.pw.ee.decker.lzw.internal

import org.scalatest.FlatSpec

import scala.collection.mutable

/**
  * Created by clutroth on 06.01.17.
  */
class LZWDictionaryTest extends FlatSpec {

  behavior of "LZWDictionaryTest"

  it should "createCompression" in {
    val actual = LZWDictionary.createCompression(List(-2, 2, 3))
    val expected = mutable.TreeMap[List[Byte], Int](List[Byte](-2) -> 1, List[Byte](2) -> 2, List[Byte](3) -> 3)(ListOrdering)
    assert(expected == actual)
  }

  it should "createDecompression" in {
    val actual = LZWDictionary.createDecompression(List(-2, 2, 3))
    val expected = mutable.TreeMap[Int, List[Byte]](1 -> List(-2), 2 -> List(2), 3 -> List(3))
    assert(expected == actual)

  }
  it should "create Dictionary for bytes" in {
    val dic = LZWDictionary.createCompression(((Byte MinValue) to (Byte MaxValue)) map (_ toByte) toList)
    assert((List[Byte](Byte MinValue) -> 1) == (dic head))
    assert((List[Byte](Byte MaxValue) -> 256) == (dic last))
    assert(dic(List('A' toByte)) == 194)
    assert(dic(List('a' toByte)) == 226)
    assert(dic(List('0' toByte)) == 177)
    assert(dic(List('B' toByte)) == 195)
    assert(dic(List('b' toByte)) == 227)
    assert(dic(List('1' toByte)) == 178)
  }

}
