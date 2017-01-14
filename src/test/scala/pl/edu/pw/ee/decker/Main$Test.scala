package pl.edu.pw.ee.decker

import java.io.File

import org.apache.commons.io.FileUtils
import org.scalatest.FlatSpec


/**
  * Created by clutroth on 1/14/17.
  */
class Main$IT extends FlatSpec {
  it should "compress ad decompress symetric" in {
    testLZW(ALGORITHM)

  }

  private def testLZW (file:String) = {
    Main.main(Array("c", file, COMPRESSED))
    Main.main(Array("d", COMPRESSED, DECOMPRESSED))
    assert(FileUtils.contentEquals(new File(file), new File(DECOMPRESSED)))
  }

  val TEMP_DIR = System.getProperty("java.io.tmpdir");
  val COMPRESSED = s"$TEMP_DIR/compressed.dat"
  val DECOMPRESSED = s"$TEMP_DIR/decompressed.dat"
  val ALGORITHM = resource("/algorithm.txt")
  val LENA = resource("/lena8.png")

  def resource(name: String) =
    this.getClass.getResource(name).getPath

}
