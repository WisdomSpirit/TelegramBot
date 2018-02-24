package commands

import java.util.jar.Pack200.Unpacker

import scala.collection.mutable

class Command(title : String, parameters : List[String]) {
  val name : String = title
  val params : List[String] = parameters

  def execute() : String = {
    try {
      this.name match {
        case "/create_poll" => General.createPoll(this.params);
        case "/list" => General.pollList()
        case "/delete_poll" => General.deletePoll(this.params.head)
        case "/start_poll" => General.startPoll(this.params.head)
        case "/stop_poll" => General.stopPoll(this.params.head)
        case "/result" => General.pollResult(this.params.head)
      }
    }
    catch {
      case e : Exception => "Unrecognised command! Say what!?"
    }
  }
}