package commands

import scala.util.Try


case class Administrator(name : Try[String],
                         params : Try[List[String]],
                         answers : Try[List[String]],
                         userID : Int)
  extends Command {
  def execute() : String = {
    val cmd = Commands(userID)
    this.name.getOrElse() match {
      case "/create_poll" => cmd.createPoll(this.params);
      case "/list" => cmd.pollList()
      case "/delete_poll" => cmd.deletePoll(this.params)
      case "/start_poll" => cmd.startPoll(this.params)
      case "/stop_poll" => cmd.stopPoll(this.params)
      case "/result" => cmd.pollResult(this.params)
      case "/add_question" => cmd.addQuestion(this.params, answers)
      case "/begin" => cmd.begin(this.params)
      case "/end" => cmd.end
      case "/view" => cmd.view
      case "/delete_question" => cmd.pollResult(this.params)
      case "/answer" => cmd.pollResult(this.params)
      case _ => "Unrecognised command! Say what!?"
    }
  }


}