package interaction

import commands.Command

import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.util.Try


object Reader {
  private def converter(name: String) : List[String] = {
    var list = ListBuffer[String]()
    Source.fromFile(name).getLines().toList
  }

  def createCommands(querry : String) : Command = {
    val pattern1 = "(/[a-zA-Z_]+)".r
    val pattern2 = "\\(.*?(\\))+[^( ]*\\)*".r
    val pattern3 = "\\n(.*)\\n??".r

    val name = Try((pattern1 findAllIn querry).group(1))

    val params = Try((pattern2 findAllIn querry)
      .mkString("#")
      .split("#")
      .map(s => {
        s.drop(1)
          .dropRight(1)
          .replace("((", "(")
          .replace("))", ")")
      }).toList)

    val answers = Try((pattern3 findAllIn querry)
      .group(1)
      .mkString("#")
      .split("#")
      .map(s => {
        s.replace("((","(")
        .replace("))",")")
      }).toList)

    new Command(name, params, answers)
  }

  def parse(name : String) : ListBuffer[Command] = {
    val list = converter(name)
    var result = ListBuffer[Command]()
    for (i <- list.indices){
      var str = list(i)
      if (list(i).startsWith("/")) {
        if (list.length > i + 1) {
          var j = i+1
          while (list.length > j && !list(j).startsWith("/")) {
            str = str + '\n' + list(j)
            j = j+1
          }
        }
        result.append(createCommands(str))
      }
    }
    result
  }
}