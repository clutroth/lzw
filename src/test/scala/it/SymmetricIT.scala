package it

import java.io._

import org.scalatest.FlatSpec
import pl.edu.pw.ee.decker.lzw.LZW

import scala.io.Source

/**
  * Created by clutroth on 04.01.17.
  */
class SymmetricIT extends FlatSpec {
  val ALG = "/algorithm.txt"

  it should "work" in {
    def file = getClass.getResourceAsStream(ALG)

    val compressed = File.createTempFile("compressed-test", ".txt")
    val decompressed = File.createTempFile("decompressed-test", ".txt")
    compress(file, compressed)
    decompress(compressed, decompressed)
    val decompressedInput = new FileInputStream(decompressed)
    val plain = file
    InputStreamEquals.isEqual(plain, decompressedInput)
    decompressedInput close()
    plain close()
  }
  it should "be symmetric for long string" in {
    //    val content = Source.fromFile(getClass.getResource(ALG).toURI).mkString
    val content = "Algorithm"
    //orithm\n\nThe"
    val original = new ByteArrayInputStream(content.getBytes())
    val compressionInput = new ByteArrayOutputStream()

    LZW.compress(original, compressionInput)

    original close()
    val compressed = new String(compressionInput.toByteArray)
//    val expected = List(1) map (_ toByte) toArray

//    assert(compressed == new String(expected))
    compressionInput.close()

    val decomressionInput = new ByteArrayInputStream(compressed.getBytes())
    val decopressionOutput = new ByteArrayOutputStream()

    LZW.decompress(decomressionInput, decopressionOutput)

    val decompressed = new String(decopressionOutput.toByteArray)
    decomressionInput close()
    decopressionOutput close()

    print(decompressed)

  }

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
      print((buf1 head) toChar)
    }

  }
}
