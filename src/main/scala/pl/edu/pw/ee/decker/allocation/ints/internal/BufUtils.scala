package pl.edu.pw.ee.decker.allocation.ints.internal

/**
  * Created by clutroth on 1/10/17.
  */
object BufUtils {
  def requiredBits(number: Int): Int =
    INT_SIZE_IN_BITS - Integer.numberOfLeadingZeros(number)
  def binByteToBinInt(b:Byte):Int ={
    val shift = INT_SIZE_IN_BITS - BYTE_SIZE_IN_BITS
    b << shift >>> shift
  }

  val BYTE_SIZE_IN_BITS = 8
  val INT_SIZE_IN_BITS = 4 * BYTE_SIZE_IN_BITS

}
