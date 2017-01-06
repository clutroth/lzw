package pl.edu.pw.ee.decker.lzw.internal

/**
  * Created by clutroth on 02.01.17.
  */
object ListOrdering extends Ordering[List[Byte]] {

  override def compare(a: List[Byte], b: List[Byte]) =
    new String(a.toArray) compareTo new String(b.toArray)
}
