package commands

import scala.util.Try
import exceptions._


class Command(title : Try[String], parameters : Try[List[String]], rawAnswers : Try[List[String]]) {
  val name : String = if (title.isSuccess) title.get else ""
  val params : List[String] = if (parameters.isSuccess) parameters.get else throw ArgumentException
  val answers : List[String] = if (rawAnswers.isSuccess) rawAnswers.get else throw ArgumentException

  def execute() : String = {
    try {
      this.name match {
        case "/create_poll" => General.createPoll(this.params);
        case "/list" => General.pollList()
        case "/delete_poll" => General.deletePoll(this.params.head)
        case "/start_poll" => General.startPoll(this.params.head)
        case "/stop_poll" => General.stopPoll(this.params.head)
        case "/result" => General.pollResult(this.params.head)
        case "/add_question" => General.addQuestion(this.params, answers)
        case "/begin" => General.begin(this.params.head)
        case "/end" => General.end(this.params.head)
//        case "/view" => General.pollResult(this.params.head)
//        case "/delete_question" => General.pollResult(this.params.head)
//        case "/answer" => General.pollResult(this.params.head)
      }
    }
    catch {
      case e : Exception => throw CommandException
        "Unrecognised command! Say what!?"
    }
  }
}