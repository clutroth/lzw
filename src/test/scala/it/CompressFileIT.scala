package it

import java.io._

import org.scalatest.FlatSpec
import pl.edu.pw.ee.decker.lzw.LZW

/**
  * Created by clutroth on 02.01.17.
  */
class CompressFileIT extends FlatSpec {
  val f = File createTempFile("plain", "txt")
  val compressed = File createTempFile("compressed", "txt")
  writeToTempFile(f)

  def writeToTempFile(f: File): Unit = {
    val fw = new FileWriter(f)
    fw.write("abcd")
    fw.close
  }

  val in = new FileInputStream(f)
  val out = new FileOutputStream(compressed)
  LZW.compress(in, out, List(97,98,99, 100))
//  System.out.println(f.getAbsolutePath)
//  System.out.println(compressed.getAbsolutePath)
  f deleteOnExit

  compressed deleteOnExit


}
