package pl.edu.pw.ee.decker.lzw

import java.io._

import org.scalatest.FlatSpec

/**
  * Created by clutroth on 02.01.17.
  */
class LZW$Test extends FlatSpec {
  def str2byte(s: String) = s getBytes (LZW CHARSET)


  behavior of "LZW$Test"

  it should "compress" in {
    val decompressed: Array[Byte] = str2byte("abccd_ab")
    val compressed: Array[Int] = Array[Int](1, 2, 3, 3, 4, 5, 6)
      val dic = str2byte("abcd_") toList
    val out = new ByteArrayOutputStream()
    val in = new ByteArrayInputStream(decompressed)
    LZW.compress(in, out, dic)
    out close()
    assertArraysEquals(compressed, out toByteArray)
  }

  def assertArraysEquals[A, B](a: Array[A], b: Array[B]) =
    assert((a deep) == (b deep))

  it should "decompress" in {
    val decompressed: Array[Byte] = str2byte("TOBEORNOTTOBEORTOBEORNOT")
    val compressed: Array[Int] = Array[Int](20, 15, 2, 5, 15, 18, 14, 15, 20, 27, 29, 31, 36, 30, 32, 34)
    val dic = str2byte("ABSDEFGKIJKLMNOPQRSTUVWXYZ") toList
    val out = new ByteArrayOutputStream()
    val tempf = File.createTempFile("stream_file", ".dat")
    val dataOut = new DataOutputStream(new FileOutputStream(tempf))
    compressed foreach (dataOut writeInt)
    dataOut close
    val in = new FileInputStream(tempf)
    def toStr(a: Array[Byte]):String=
     new String( a map (_ toChar) )
    LZW.decompress(in, out, dic)
    assertArraysEquals(out toByteArray, decompressed)

  }

}
