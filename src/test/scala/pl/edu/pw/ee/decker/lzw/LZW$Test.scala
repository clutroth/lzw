package pl.edu.pw.ee.decker.lzw

import java.io._

import org.scalatest.FlatSpec
import pl.edu.pw.ee.decker.lzw.internal.LZWDictionary

/**
  * Created by clutroth on 02.01.17.
  */
class LZW$Test extends FlatSpec {
  def str2byte(s: String) = s getBytes (LZW CHARSET)


  behavior of "LZW$Test"

  it should "compress" in {
    val decompressed: Array[Byte] = str2byte("abccd_ab")
    // 1 2 3 3 4 5 6
    /*
    codes:    1 2 3 3 4 5 6
    newCodes  6 7 8 9 A B
    bits      3 3 3 4 4 4
    binary:   001 010 011 0011 0100 0101 0110
    bytes     00101001  10011010  00101011 0000000
    hex       0x29      0x9A      0x2B      0x0
     */
    val compressed: Array[Int] = Array[Int](0x29, 0xFFFFFF9A, 0x2B, 0x0)
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
    //    val compressed: Array[Int] = Array[Int](20, 15, 2, 5, 15, 18, 14, 15, 20, 27, 29, 31, 36, 30, 32, 34)
    //    numbers   20, 15, 2, 5, 15, 18, 14, 15, 20, 27, 29, 31, 36, 30, 32, 34
    //    binary
    // 10100 01111 00010 00101 01111 10010 001110 001111 010100 011011 011101 011111 100100 011110 011110 100010 000000
    //  10100011|11000100|01010111|11001000|11100011|11010100|01101101|11010111|11100100|01111010|00001000|10000000|
    //  hex 0xA3  0xC4    0x57     0xC8     0xE3      0xD4    0x6D      0xD7    0xE4      0x7A    0x08      0x80  0x0
    val compressed = Array[Int](0xA3C457C8, 0xE3D46DD7, 0xE47A0880, 0x0)
    val dic = str2byte("ABSDEFGKIJKLMNOPQRSTUVWXYZ") toList
    val out = new ByteArrayOutputStream()
    val tempf = File.createTempFile("stream_file", ".dat")
    val dataOut = new DataOutputStream(new FileOutputStream(tempf))
    compressed foreach (dataOut writeInt)
    dataOut close
    val in = new FileInputStream(tempf)

    def toStr(a: Array[Byte]): String =
      new String(a map (_ toChar))

    LZW.decompress(in, out, dic)
    assertArraysEquals(out.toByteArray, decompressed)

  }
  it should "print alphabet" in {
    LZWDictionary.createDecompression(LZW.byteList)
      .map(e => (e._1, e._2.head))
      .map(e => s"${e._2.toChar} - ${e._1}")
      .foreach(println)
  }
  it should "compress Algorytm" in {
    val word = "Algorytm"
    val bytes: Array[Byte] = word getBytes()
    val expectedBytes: Array[Byte] = List(194, 237, 232, 240, 243, 250, 245, 238) map (n => (n - 128).toByte) toArray()

    assert(expectedBytes.deep == bytes.deep)
  }

}
