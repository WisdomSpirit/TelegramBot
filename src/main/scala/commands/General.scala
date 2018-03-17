package commands

import java.util.Date

import Repository._
import exceptions.CreationException
import interaction.Writer
import poll.Poll

import scala.util.Try

object General {
  private def getMonth(month: String): String = {
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

  private def getDateTime(input: String): String = {
    val date = input.split(' ')
    date(3) + ' ' + date(5).drop(2) + ':' + getMonth(date(1)) + ':' + date(2)
  }

  def createPoll(params: Try[List[String]]): String = {
    val name = Try(params.get.head)
    val anonymous: Try[Boolean] = Try(
      if (Try(params.get(1)).isFailure) true
      else
        params.get(1) match {
          case "yes"   => true
          case "no" => false
          case _ =>
            Writer.write("no such argument")
            throw new CreationException()
        })
    val viewType: Try[String] = Try(
      if (Try(params.get(2)).isFailure) "afterstop"
      else
        params.get(2) match {
          case e: String if e == "afterstop" || e == "continuous" => e
          case _: String =>
            Writer.write("no such argument")
            throw new CreationException()
        })
    val startTime: Try[String] = Try(if (Try(params.get(3)).isFailure)
      getDateTime(new Date().toString)
    else
      params.get(3) match {
        case e: String
            if Try("\\d{2}:\\d{2}:\\d{2} \\d{2}:\\d{2}:\\d{2}".r.findAllIn(e)).isSuccess =>
          e
        case _: String =>
          Writer.write("no such argument")
          throw new CreationException()
      })
    val stopTime: Try[String] = Try(if (Try(params.get(4)).isFailure) null
    else
      params.get(4) match {
        case e: String
            if Try("\\d{2}:\\d{2}:\\d{2} \\d{2}:\\d{2}:\\d{2}".r.findAllIn(e)).isSuccess =>
          e
        case _: String =>
          Writer.write("no such argument")
          throw new CreationException()
      })
    if (name.isSuccess && anonymous.isSuccess && viewType.isSuccess &&
        startTime.isSuccess && startTime.isSuccess)
      createPollDaemon(name.get,
                       anonymous.get,
                       viewType.get,
                       startTime.get,
                       stopTime.get)
    else "can't create a poll"
  }

  private def createPollDaemon(title: String,
                               anonymous: Boolean,
                               viewType: String,
                               startTime: String,
                               stopTime: String): String = {
    val poll = new Poll(title, anonymous, viewType, startTime, stopTime)
    val id = poll.hashCode().toString
    AllPolls.set(id, poll)
    id
  }

  def pollList(): String = {
    if (Try(AllPolls.getAll).isSuccess)
      "More is better than less, am I right?"
    else
      "Can You see the list of Your polls? I can't too. But they exists."
  }

  def deletePoll(id: Try[List[String]]): String = {
    if (Try {
          val n = id.get.head
          AllPolls.remove(n)
          RunPolls.remove(n)
        }.isSuccess)
      "Exterminate! Exterminate! Exterminate!"
    else
      "Can't exterminate Your poll, it was very strong!"
  }

  def startPoll(id: Try[List[String]]): String = {
    if (Try {
      val n = id.get.head
      RunPolls.set(n, AllPolls.get(n))
    }.isSuccess) "Your poll was just started, look for feedback!"
    else
      "Can't start your Poll!Please try again later!"
  }

  def stopPoll(id: Try[List[String]]): String = {
    if (id.isSuccess) {
      RunPolls.remove(id.get.head)
      "Your poll was just finished, that was a great poll!"
    } else {
      "Some trouble was detected, please try again later!"
    }
  }

  def pollResult(id: Try[List[String]]): String = {
    if (id.isSuccess) {
      AllPolls
        .get(id.get.head)
        .map(p => {
          if (p.viewType.eq("continuous") || p.isOver) {
            if (p.isAnonymous) {} ///withoutNames else - with Names
            ""
          } else "You can view the result only when the poll will be over"
        })
        .get
    } else "Some trouble was detected, please try again later!"
  }

  def addQuestion(params: Try[List[String]],
                  answers: Try[List[String]]): String = {
    if (!params.isSuccess) "Wrong parameters"
    else if (!answers.isSuccess) "Wrong format of answers"
    else {
      val name = Try(params.get.head)
      val qtype = Try(params.get.last)
      CurrentPoll.get.inner.set_question(name, qtype, answers.get)
      "Success"
    }
  }

  def begin(id: Try[List[String]]): String = {
    if (id.isSuccess) {
      CurrentPoll.set(AllPolls.get(id.get.head))
      "Success"
    } else {
      "There is no such Poll"
    }
  }

  def end: String = {
    try {
      CurrentPoll.setNone()
      "Success"
    } catch {
      case _: Exception =>
        "It's impossible to catch a fail in this command! But you've done it!"
    }
  }

  def view: String = {
    Writer.write(CurrentPoll.get)
    // make a beauty
    ""
  }

//  def deleteQuestion(number : Int) {
//      CurrentPoll.get.questions.remove_question(number)
//  }

  def answer() {}
}
