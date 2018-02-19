package commands

import interaction.Writer
import poll.Poll

import scala.collection.mutable

object General {
  var ALLPOLLS = new mutable.HashMap[String, Poll]()
  var RUNPOLLS = new mutable.HashMap[String, Poll]()

  def createPoll(params : List[String]){
    val poll = new Poll(params(0), params(1).toBoolean, params(2).toBoolean, params(3), params(4))
    val id = poll.hashCode().toString
    ALLPOLLS.put(id, poll)
    Writer.write(id)
  }

  def pollList(){
    Writer.write(ALLPOLLS.toList)
  }

  def deletePoll(id : String) {
    ALLPOLLS.remove(id)
    RUNPOLLS.remove(id)
  }

  def startPoll(id : String) {
    try {
      RUNPOLLS.put(id, ALLPOLLS(id))
      Writer.write("Your poll was jast started, look for feedback!")
    }
    catch {
      case e : Exception => Writer.write("Some trouble was detected, please try again later!")
    }
  }

  def stopPoll(id : String) {
    try {
      RUNPOLLS.remove(id)
      Writer.write("Your poll was just finished, that was a great poll!")
    }
    catch {
      case e : Exception => Writer.write("Some trouble was detected, please try again later!")
    }
  }

  def pollResult(id : String) {
    Writer.write("Name: " + ALLPOLLS(id).name)
    Writer.write("Anonymous? " + ALLPOLLS(id).isAnonymous)
    val string = if (ALLPOLLS(id).viewType) "just when you wish" else "only when the poll will be over"
    Writer.write("When can I view results? " + string)
    Writer.write("It's time to start! " + ALLPOLLS(id).startTime)
    Writer.write("Your time is up, Poll'y! " + ALLPOLLS(id).startTime)
  }
}