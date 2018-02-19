package commands

import scala.collection.mutable

class Command(name : String, params : List[String]) {
  val this.name = name
  val this.params = params

  def execute() {
    this.name match {
      case "/create_poll" => General.createPoll(this.params)
      case "/list" => General.pollList()
      case "/delete_poll" => General.deletePoll(this.params.head)
      case "/start_poll" => General.startPoll(this.params.head)
      case "/stop_poll" => General.stopPoll(this.params.head)
      case "/result" => General.pollResult(this.params.head)
    }
  }

}