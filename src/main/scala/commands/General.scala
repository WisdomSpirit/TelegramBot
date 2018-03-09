package commands

import java.util.Date

import Repository._
import interaction.Writer
import poll.Poll

import scala.util.Try


object General {
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
    date(3) + ' ' + date(5).drop(2) + ':' + getMonth(date(1)) + ':' + date(2)
  }

  def createPoll(params : List[String]) : String = {
    val name = if (params.head != null) params.head else{
      Writer.write("How should I call your Poll?")
      return "Your poll wasn't created! Please try again later!"
    }

    var anonymous = true
    var viewType = "afterstop"
    var startTime = getDateTime(new Date().toString)
    var stopTime : String = null

    for (a <- params.indices){
      val obj = params(a)
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
    AllPolls.set(id, poll)
    id
  }

  def pollList() : String = {
    try {
      AllPolls.getAll
      "More is better than less, am I right?"
    }
    catch {
      case _ :Exception => "Can You see the list of Your polls? I can't too. But they exists."
    }
  }

  def deletePoll(id : String) : String = {
    try {
      AllPolls.remove(id)
      RunPolls.remove(id)
      "Exterminate! Exterminate! Exterminate!"
    }
    catch {
      case _ :Exception => "Can't exterminate Your poll, it was very strong!"
    }
  }

  def startPoll(id : String) : String = {
    try {
      RunPolls.set(id, AllPolls.get(id))
      "Your poll was just started, look for feedback!"
    }
    catch {
      case _ :Exception => "Can't start your Poll!Please try again later!"
    }
  }

  def stopPoll(id : String) : String = {
    try {
      RunPolls.remove(id)
      "Your poll was just finished, that was a great poll!"
    }
    catch {
      case _ :Exception => "Some trouble was detected, please try again later!"
    }
  }

  def pollResult(id : String) : String = {
      AllPolls.get(id).map(p => {
      if (p.viewType.eq("continuous") || p.isOver) {
        if (p.isAnonymous) {} ///withoutNames else - with Names
        ""
      }
      else "You can view the result only when the poll will be over"
    }).get
  }

  def addQuestion(params : List[String], answers : List[String]) : String = {
    try {
      val name = Try(params.head)
      val qtype = Try(params.last)
      CurrentPoll.get.inner.set_question(name, qtype, answers)
      "Success"
    }
    catch {
      case _ : Exception => "Something went wrong"
    }
  }

  def begin(id : String) : String = {
    try {
      CurrentPoll.set(AllPolls.get(id))
      "Success"
    }
    catch {
      case _ :Exception => "There is no such Poll"
    }
  }

  def end : String = {
    try {
      CurrentPoll.set(None)
      "Success"
    }
    catch {
      case _ :Exception => "It's impossible to catch a fail in this command! But you've done it!"
    }
  }

  def view() {
    Writer.write(CurrentPoll.get)
    // make a beauty
  }

//  def deleteQuestion(number : Int) {
//      CurrentPoll.get.questions.remove_question(number)
//  }

  def answer() {

  }
}