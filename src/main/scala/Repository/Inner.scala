package Repository

object Inner {
  private var inner : Map[(Int, Int, Int), List[Int]] = Map[(Int, Int, Int), List[Int]]()
  //                       pollId  numQ numA    people

  def get(pollId: Int, numQ: Int, numA: Int): List[Int]= {
    inner.getOrElse((pollId, numQ, numA), List())
  }

  def set(pollId: Int, numQ: Int, numA: Int, answer: Int): Unit ={
    val list : List[Int] = inner.getOrElse((pollId, numQ, numA), List()) :+ answer
    inner = inner updated ((pollId, numQ, numA), list)
  }
}