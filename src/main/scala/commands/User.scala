package commands

import scala.util.Try
import exceptions._


case class User(name : Try[String], params : Try[List[String]], answers : Try[List[String]], userID : Int)
  extends Command {
  def execute() : String = {
    val cmd = Commands(userID)
    this.name.getOrElse() match {
      case "/create_poll" | "/delete_poll" | "/start_poll"
           | "/stop_poll" | "/add_question" | "/delete_question" =>
        "You don't have such level of privileges"
      case "/list" => cmd.pollList()
      case "/result" => cmd.pollResult(this.params)
      case "/begin" => cmd.begin(this.params)
      case "/end" => cmd.end
      case "/view" => cmd.view
      case "/answer" => cmd.pollResult(this.params)
      case _ => "Unrecognised command! Say what!?"
    }
  }
}