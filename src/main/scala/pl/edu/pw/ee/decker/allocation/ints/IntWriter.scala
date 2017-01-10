package pl.edu.pw.ee.decker.allocation.ints

/**
  * Created by clutroth on 1/7/17.
  */
trait IntWriter extends AutoCloseable{
  def write(n: Int)

}
