package parsers

import commands.{Administrator, Command, User}
import exceptions.ParserException

import scala.util.{Failure, Try}


object CommandParser {
  def parse(msg : String, privileges: String, userID : Int) : Try[Command] = {
    Try {
      val charSet = msg.replaceAll("\\(\\(", "").replaceAll("\\)\\)", "").split("")
      val open_br = charSet.filter(c => c.equals("(")).toList
      val closed_br = charSet.filter(c => c.equals(")")).toList
      if (open_br.lengthCompare(closed_br.length) != 0) Failure(new ParserException)
      val pattern1 = "(/[^ ]+)".r
      val pattern2 = "\\((.*?(\\))+)*[^()]*\\)".r
      val pattern3 = "\n(\t| )*(.*)\n??".r

      val name = Try((pattern1 findAllIn msg).group(1))

      val params = Try((pattern2 findAllIn msg)
        .mkString("#")
        .split("#")
        .map(s => {
          s.drop(1)
            .dropRight(1)
            .replace("((", "(")
            .replace("))", ")")
        }).toList)

      val answers = Try((pattern3 findAllIn msg)
        .group(2)
        .mkString("#")
        .split("#")
        .map(s => {
          s.replace("((", "(")
            .replace("))", ")")
        }).toList)
      if (privileges.eq("Administrator"))
        Administrator(name, params, answers, userID)
      else User(name, params, answers, userID)
    }
  }
}