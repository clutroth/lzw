package pl.edu.pw.ee.decker.allocation.ints

/**
  * Created by clutroth on 1/7/17.
  */
trait IntReader extends AutoCloseable{
  def read: Option[Int]
}
