package commands

import interaction.Writer
import poll.Poll

import scala.collection.mutable

object General {
  var ALLPOLLS = new mutable.HashMap[Int, Poll]()
  var RUNPOLLS = new mutable.HashMap[Int, Poll]()

  def createPoll(title:String, anonymous:Boolean, view:Boolean, start:String, finish:String){
    val poll = new Poll(title, anonymous, view, start, finish)
    val id = poll.hashCode()
    ALLPOLLS.put(id, poll)
    Writer.write(id)
  }

  def pollList(){
    Writer.write(ALLPOLLS.toList)
  }

  def deletePoll(id : Int) {
    ALLPOLLS.remove(id)
    RUNPOLLS.remove(id)
  }

  def startPoll(id : Int) {
    try {
      RUNPOLLS.put(id, ALLPOLLS(id))
      Writer.write("Your poll was jast started, look for feedback!")
    }
    catch {
      case e : Exception => Writer.write("Some trouble was detected, please try again later!")
    }
  }

  def stopPoll(id : Int) {
    try {
      RUNPOLLS.remove(id)
      Writer.write("Your poll was just finished, that was a great poll!")
    }
    catch {
      case e : Exception => Writer.write("Some trouble was detected, please try again later!")
    }
  }

  def pollResult(id : Int){
    // make a beauty :)
    Writer.write(ALLPOLLS(id))
  }

}