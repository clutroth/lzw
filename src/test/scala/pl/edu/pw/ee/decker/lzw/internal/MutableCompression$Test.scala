package pl.edu.pw.ee.decker.lzw.internal

import org.scalatest.Matchers
import pl.edu.pw.ee.decker.lzw.LZW

import scala.collection.immutable.Seq
import scala.collection.mutable

/**
  * Created by clutroth on 31.12.16.
  */
class MutableCompression$Test extends org.scalatest.FlatSpec with Matchers {
  "createDictionary" should "create Map from list" in {
    val list = List[Byte](1, 2, 6, 5)

    val dic = LZW createDictionary list
    assert(dic == Map[List[Byte], Int](
      List[Byte](1) -> 1,
      List[Byte](2) -> 2,
      List[Byte](6) -> 3,
      List[Byte](5) -> 4)
    )
  }
  "MutableCompression$Test" should "compress" in {
    val compression: MutableCompression = createCompression
    def assertCompression(b: Byte, option: Option[Int]) =
      assert(compression.put(b) == option)
    assertCompression(1, None)
    assertCompression(2, Option(1))
    assertCompression(1, Option(2))
    assertCompression(2, None)
    assertCompression(3, Option(4))
    assert((compression buffer) == 3)
  }

  "MutableDecompression$Test" should "decompress" in {
    val seq = List(1, 2, 3) map (a => (a -> List(a.toByte))) toSeq
    val decompression = new MutableDecompression(mutable TreeMap (seq: _*))
    def assertDecompression(code: Int, word: List[Byte]) =
      assert((decompression put code) == word)
    assertDecompression(1, List(1)) // first code
    assertDecompression(1, List(1)) // 4: 1, 1
    assertDecompression(2, List(2)) // 5: 1, 2
    assertDecompression(4, List(1, 1)) // 5: 1, 2, 1
  }

  def createCompression: MutableCompression = {
    val map: Map[List[Byte], Int] = List(1, 2, 3) map (a => List(a toByte) -> a) toMap

    def createMapDictionary: mutable.Map[List[Byte], Int] = {
      mutable Map (map toSeq: _*)
    }
    new MutableCompression(createMapDictionary)
  }

  it should "buffer gives last string dictionary value" in {
    val compression = createCompression
    compression put 1
    assert((compression buffer) == 1)
  }
}
