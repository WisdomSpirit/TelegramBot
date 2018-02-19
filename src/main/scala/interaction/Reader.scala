package interaction

import commands.Command

import scala.collection.mutable.ListBuffer
import scala.io.Source

object Reader {
  private def converter(name: String) : ListBuffer[String] = {
    var list = ListBuffer[String]()
    Source.fromFile(name).getLines().foreach(line => list.append(line))
    list
  }

  def parse(name : String) : ListBuffer[Command] = {
    val list = converter(name)
    var result = ListBuffer[Command]()
    for (i <- list.indices){
      var tstr = ""
      if (list(i).startsWith("/")){
        while (!list(i+1).startsWith("/")){
          tstr = tstr + list(i)
        }
        result.append(createCommands(tstr))
      }
    }
    result
  }

  private def createCommands(querry : String) : Command = {
    val list = querry.split(" ").toList
    new Command(list(1), list.takeRight(list.length-1))
  }
}