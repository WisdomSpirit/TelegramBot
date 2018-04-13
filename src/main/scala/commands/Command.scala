package commands

import scala.util.Try

trait Command {
  val name : Try[String]
  val params : Try[List[String]]
  val answers : Try[List[String]]
  val userID : Int

  def execute(): String
}
