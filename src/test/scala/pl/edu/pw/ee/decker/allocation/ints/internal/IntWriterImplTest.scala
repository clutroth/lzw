package pl.edu.pw.ee.decker.allocation.ints.internal

import java.io.{ByteArrayOutputStream, DataOutputStream}

import org.scalatest.FlatSpec

/**
  * Created by clutroth on 1/7/17.
  */
class IntWriterImplTest extends FlatSpec {

  behavior of "IntWriterImplTest"

  it should "write" in {
    val out = new ByteArrayOutputStream
    val writer = new IntWriterImpl(out, 0xF) // 4 bits
    writer.write(0xA) //1010
    writer.write(0x1A) // 11010
    writer.write(0x11) // 10001
    //[1010][1101][0100][0100]
    writer.close()
    assert(((Array[Byte](0xAD toByte, 0x44 toByte, 0 toByte)) deep) == ((out toByteArray) deep))
  }

}
