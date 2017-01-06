package pl.edu.pw.ee.decker.lzw

import java.io.{InputStream, OutputStream}

/**
  * Created by clutroth on 31.12.16.
  */
trait DictionaryCompression {
  def compress(in: InputStream, os: OutputStream, dictionary: List[Byte])

  def decompress(in: InputStream, os: OutputStream, dictionary: List[Byte])
}
