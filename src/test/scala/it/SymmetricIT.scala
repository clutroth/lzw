package it

import java.io.{File, FileInputStream, FileOutputStream, InputStream}

import org.scalatest.FlatSpec
import pl.edu.pw.ee.decker.lzw.LZW

/**
  * Created by clutroth on 04.01.17.
  */
class SymmetricIT extends FlatSpec {
  def file = getClass.getResourceAsStream("/algorithm.txt")
  val compressed = File.createTempFile("compressed-algorithm", ".txt")
  val decompressed = File.createTempFile("decompressed-algorithm", ".txt")
  compress(file, compressed)
  decompress(compressed, decompressed)
  val decompressedInput = new FileInputStream(decompressed)
  val plain = file
//  InputStreamEquals.isEqual(plain, decompressedInput)
  decompressedInput close()
  plain close()


  def decompress(compressed: File, decompressed: File): Unit = {
    val decompressedOutput = new FileOutputStream(decompressed)
    val compressedInput = new FileInputStream(compressed)
    LZW.decompress(compressedInput, decompressedOutput)
    decompressedOutput close()
    compressedInput close()
  }


  def compress(plain: InputStream, compressed: File): Unit = {
    val compressedOutput = new FileOutputStream(compressed)
    LZW.compress(plain, compressedOutput)
    compressedOutput close()
    plain close
  }
}

object InputStreamEquals {
  def isEqual(in1: InputStream, in2: InputStream): Unit = {
    def notEmpty(in: InputStream) =
      (in available) > 0
    val buf1 = new Array[Byte](1)
    val buf2 = new Array[Byte](1)
    while (notEmpty(in1) && notEmpty(in2)) {
      in1 read buf1
      in2 read buf2
      assert((buf1 deep) == (buf2 deep))
      print ((buf1 head) toChar)
    }

  }
}
