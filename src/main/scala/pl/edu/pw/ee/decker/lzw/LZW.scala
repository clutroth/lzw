package pl.edu.pw.ee.decker.lzw

import java.io.{DataInputStream, DataOutputStream, InputStream, OutputStream}
import java.nio.charset.StandardCharsets

import pl.edu.pw.ee.decker.allocation.ints.IntReader
import pl.edu.pw.ee.decker.allocation.ints.internal.{IntReaderImpl, IntWriterImpl}
import pl.edu.pw.ee.decker.lzw.internal.{LZWDictionary, MutableCompression, MutableDecompression}

import scala.collection.immutable.Seq
import scala.collection.mutable

/**
  * Created by clutroth on 31.12.16.
  */
object LZW extends DictionaryCompression with Compression {
  override def compress(in: InputStream, os: OutputStream, dictionary: List[Byte]) = {
    val compression = new MutableCompression(LZWDictionary.createCompression(dictionary))
    val buf = Array[Byte](0)
    val out = new IntWriterImpl(os, dictionary.length)

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
    val res: Option[Int] = read
    if (res isDefined)
      write(res get)
    out close
  }

  def notEmpty(in: InputStream) = (in available) > 0

  override def decompress(in: InputStream, os: OutputStream, dictionary: List[Byte]) = {
    val decompression = new MutableDecompression(LZWDictionary.createDecompression(dictionary))
    val buf = Array[Byte](0)
    val dataIn = new IntReaderImpl(in, dictionary.length);
//    def read =
//      decompression put (dataIn read)
//
//    def write(data: List[Byte]) =
//      os write (data toArray)
//
    while (dataIn.isEmpty == false) {
      val code = dataIn read

      val byte = decompression put code

      os write (byte toArray)
    }
//    os close
  }

  val CHARSET = StandardCharsets.UTF_8

  override def compress(in: InputStream, out: OutputStream): Unit =
    compress(in, out, byteList)

  override def decompress(in: InputStream, out: OutputStream): Unit =
    decompress(in, out, byteList)

  def byteList: List[Byte] = ((Byte MinValue) to (Byte MaxValue)) map (_ toByte) toList
}
