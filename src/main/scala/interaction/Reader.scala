package interaction

import scala.collection.mutable.ListBuffer
import scala.io.Source

object Reader {
  private def converter(name: String) : ListBuffer[String] = {
    var list = ListBuffer[String]()
    Source.fromFile(name).getLines().foreach(line => list.append(line))
    list
  }

  def parse(name : String) : ListBuffer[String] = {
    val list = converter(name)
    var result = ListBuffer[String]()
    for (i <- list.indices){
      var tstr = ""
      if (list(i).startsWith("/")){
        while (!list(i+1).startsWith("/")){
          tstr = tstr + list(i)
        }
        result.append(tstr)
      }
    }
    result
  }
}