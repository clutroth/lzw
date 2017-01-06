package pl.edu.pw.ee.decker.lzw

import java.io.{InputStream, OutputStream}

/**
  * Created by clutroth on 31.12.16.
  */
trait Compression {
  def compress(in: InputStream, out: OutputStream)

  def decompress(in: InputStream, out: OutputStream)
}
