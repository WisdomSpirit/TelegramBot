package interaction

import commands.Command

import scala.io.Source
import scala.util.Try



object Reader {
  private def converter(name: String) : List[String] = {
    Source.fromFile(name).getLines().toList
  }

  def createCommands(querry : String) : Command = {
    val pattern0 = "(/.*? (\\(.*?\\))+)".r
    val pattern1 = "(/[a-zA-Z_]+)".r
    val pattern2 = "\\(.*?(\\))+[^( ]*\\)*".r
    val pattern3 = "\n(\t| )*(.*)\n??".r

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
      .group(2)
      .mkString("#")
      .split("#")
      .map(s => {
        s.replace("((", "(")
          .replace("))", ")")
      }).toList)

    new Command(name, params, answers)
  }

  def parse(name : String) : Command = {
    val list = converter(name)
    val result = list.mkString(util.Properties.lineSeparator)
    createCommands(result)
  }
}