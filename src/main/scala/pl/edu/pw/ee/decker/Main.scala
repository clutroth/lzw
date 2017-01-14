package pl.edu.pw.ee.decker

import java.io.FileInputStream

import pl.edu.pw.ee.decker.lzw.LZW

import scala.reflect.io.File

/**
  * Created by clutroth on 1/12/17.
  */
object Main {
  def main(args: Array[String]): Unit ={
    val in = new FileInputStream(args(1))
    val fileOut =  File(args(2))
    if(!fileOut.exists)
      fileOut.createFile(false)
    val out = fileOut.outputStream()

    args headOption match {
      case Some("c") => LZW.compress(in, out)
      case Some("d") => LZW.decompress(in, out)
      case None => printHelp
      case _ => printHelp

    }
    in.close()
    out.close()
    def printHelp = println("LZW compression and decompression Scala implementation \n" +
      "compression usage: \n" +
      "$ java -jar lzw.jar c < original > compressed \n" +
      "decompression usage: \n" +
      "$ java -jar d < compressed > decompressed ")
  }

}
