package commands

import java.util.Date

import interaction.Writer
import poll.Poll

import scala.collection.mutable

object General {
  var ALLPOLLS = new mutable.HashMap[String, Poll]()
  var RUNPOLLS = new mutable.HashMap[String, Poll]()

  private def getMonth(month : String) : String = {
    month match {
      case "Jan" => "01"
      case "Feb" => "02"
      case "Mar" => "03"
      case "Apr" => "04"
      case "May" => "05"
      case "Jun" => "06"
      case "Jul" => "07"
      case "Aug" => "08"
      case "Sep" => "09"
      case "Oct" => "10"
      case "Nov" => "11"
      case "Dec" => "12"
    }
  }

  private def getDateTime(input : String) : String ={
    val date = input.split(' ')
    val output : String = date(3) + ' ' + date(5).drop(2) + ':' + getMonth(date(1)) + ':' + date(2)
    output
  }

  def createPoll(params : List[String]) : String = {
    val name = params.head
    var anonymous = true
    var viewType = "afterstop"
    var startTime = getDateTime(new Date().toString)
    var stopTime : String = null

    for (a <- params.indices){
      val obj = params(a)
      println(a, params(a))
      if (a == 1) {
        if (obj != "yes" && obj != "no") {
          Writer.write("Is your poll anonymous or not? Yes or No? Which pill You will choose?")
          return "Your poll wasn't created! Please try again later!"
        }
        else {
          if (obj == "yes") anonymous = true else anonymous = false
        }
      }
      if (a == 2) {
        if (obj != "afterstop" && obj != "continuous") {
          Writer.write("Unrecognised parameter. How do you want to view result? (continuous/afterstop)")
          return "Your poll wasn't created! Please try again later!"
        }
        viewType = obj
      }
      if (a == 3){
        val pattern = "(\\d\\d:\\d\\d:\\d\\d \\d\\d:\\d\\d:\\d\\d)".r
        if((pattern findAllIn obj) != null)
          startTime = obj
        else {
          Writer.write("Time froze! Choose another one!")
          return "Your poll wasn't created! Please try again later!"
        }
      }
      if (a == 4){
        val pattern = "(\\d\\d:\\d\\d:\\d\\d \\d\\d:\\d\\d:\\d\\d)".r
        if((pattern findAllIn obj).group(1) != null)
          stopTime = obj
        else {
          Writer.write("Time froze! Choose another one!")
          return "Your poll wasn't created! Please try again later!"
        }
      }
    }
    createPollDaemon(name, anonymous, viewType, startTime, stopTime).toString
  }

  private def createPollDaemon(title:String, anonymous:Boolean, viewType:String,
                 startTime : String, stopTime: String ) : String = {
    val poll = new Poll(title, anonymous, viewType, startTime, stopTime)
    val id = poll.hashCode().toString
    ALLPOLLS.put(id, poll)
    id
  }

  def pollList() : String = {
    try {
      ALLPOLLS.toList
      "More is better than less, am I right?"
    }
    catch {
      case e : Exception => "Can You see the list of Your polls? I can't too. But they exists."
    }
  }

  def deletePoll(id : String) : String = {
    try {
      ALLPOLLS.remove(id)
      RUNPOLLS.remove(id)
      "Exterminate! Exterminate! Exterminate!"
    }
    catch {
      case e: Exception => "Can't exterminate Your poll, it was very strong!"
    }
  }

  def startPoll(id : String) : String = {
    try {
      RUNPOLLS.put(id, ALLPOLLS(id))
     "Your poll was jast started, look for feedback!"
    }
    catch {
      case e : Exception => "Some trouble was detected, please try again later!"
    }
  }

  def stopPoll(id : String) : String = {
    try {
      RUNPOLLS.remove(id)
      "Your poll was just finished, that was a great poll!"
    }
    catch {
      case e : Exception => "Some trouble was detected, please try again later!"
    }
  }

  def pollResult(id : String) : String = {
    val string = if (ALLPOLLS(id).viewType.eq("continuous")) "just when you wish" else "only when the poll will be over"
    "Name: " + ALLPOLLS(id).name + "\n" +
    "Anonymous? " + ALLPOLLS(id).isAnonymous + "\n" +
    "When can I view results? " + string + "\n" +
    "It's time to start! " + ALLPOLLS(id).startTime + "\n" +
    "Your time is up, Poll'y! " + ALLPOLLS(id).startTime
  }
}