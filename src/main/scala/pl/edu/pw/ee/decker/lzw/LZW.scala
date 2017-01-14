package pl.edu.pw.ee.decker.lzw

import java.io.{DataInputStream, DataOutputStream, InputStream, OutputStream}
import java.nio.charset.StandardCharsets

import pl.edu.pw.ee.decker.allocation.ints.internal.{DummyIntReader, DummyIntWriter, IntReaderImpl, IntWriterImpl}
import pl.edu.pw.ee.decker.lzw.internal.{LZWDictionary, MutableCompression, MutableDecompression}

import scala.annotation.tailrec
import scala.collection.immutable.Seq
import scala.collection.mutable

/**
  * Created by clutroth on 31.12.16.
  */
object LZW extends DictionaryCompression with Compression {
  override def compress(in: InputStream, os: OutputStream, dictionary: List[Byte]) = {
    val compression = new MutableCompression(LZWDictionary.createCompression(dictionary))
    val buf = Array[Byte](0)
    val out = new DummyIntWriter(os, dictionary.size)

    def read: Option[Int] = {
      in read buf
      compression put buf(0)
    }

    def write(res: Int): Unit = {
      out write res
    }

    while (notEmpty(in)) {
      val res: Option[Int] = read
      if (res isDefined)
        write(res get)
    }
    write(read get)
    out close
  }

  def notEmpty(in: InputStream) = (in available) > 0

  override def decompress(in: InputStream, os: OutputStream, dictionary: List[Byte]) = {
    val decompression = new MutableDecompression(LZWDictionary.createDecompression(dictionary))
    val dataIn = new DummyIntReader(in, dictionary.size);
    @tailrec
    def decode(code: Option[Int]): Unit = {
      if (code isEmpty)
        return
      else {
        val buf = decompression put (code get)
        os.write(buf toArray)
        decode(dataIn read)
      }
    }

    decode(dataIn read)
  }

  val CHARSET = StandardCharsets.UTF_8

  override def compress(in: InputStream, out: OutputStream): Unit =
    compress(in, out, byteList)

  override def decompress(in: InputStream, out: OutputStream): Unit =
    decompress(in, out, byteList)

  def byteList: List[Byte] = ((Byte MinValue) to (Byte MaxValue)) map (_ toByte) toList
}
