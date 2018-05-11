package commands
import Repository._
import poll.Poll

import scala.util.Try

case class Commands(userID : Int) {
  //  private def getMonth(month: String): String = {
  //    month match {
  //      case "Jan" => "01"
  //      case "Feb" => "02"
  //      case "Mar" => "03"
  //      case "Apr" => "04"
  //      case "May" => "05"
  //      case "Jun" => "06"
  //      case "Jul" => "07"
  //      case "Aug" => "08"
  //      case "Sep" => "09"
  //      case "Oct" => "10"
  //      case "Nov" => "11"
  //      case "Dec" => "12"
  //    }
  //  }

  //  private def getDateTime(input: String): String = {
  //    val date = input.split(' ')
  //    date(3) + ' ' + date(5).drop(2) + ':' + getMonth(date(1)) + ':' + date(2)
  //  }
  def createPoll(title: String, anonymous: Boolean,
                 viewType: String,
                 startTime: String,
                 stopTime: String): String = {
    val id = AllPolls.get_id()
    AllPolls.set(id, Poll(id, title, anonymous, viewType, startTime, stopTime))
    "Success: " + id
  }

  def pollList: String = {
    if (AllPolls.getAll.nonEmpty)
      AllPolls.getAll.toVector.sortBy(e => e._1.toInt).map(e =>
        s"${e._2.id} => ${e._2.name}\nis anonymous? ${e._2.isAnonymous}\nis it running? ${e._2.isRun}"
          + s"\nis over? ${!e._2.isRun}\n${e._2.viewType}\nstarts in: ${e._2.startTime}\nends in: ${e._2.stopTime}")
        .mkString("\n\n")
    else
      "Can You see the list of Your polls? I can't too. But they exists."
  }

  def deletePoll(id: Int): String = {
    if (AllPolls.getRun(id).isSuccess || AllPolls.get(id).isSuccess) {
      AllPolls.remove(id)
      "Exterminate! Exterminate! Exterminate!"
    } else "Can't delete Your Poll, cuz there's no such one!"
  }

  def startPoll(id: Int): String = {
    if (AllPolls.get(id).isSuccess && AllPolls.get(id).get.startTime == null) {
      AllPolls.setRun(id, AllPolls.get(id).get)
      "Your poll was just started, look for feedback!"
    } else if (AllPolls.get(id).isFailure)
      "Can't start your Poll, cuz there's no such one!"
    else "The Poll will start itself when the time come! Wait..."
  }

  def stopPoll(id: Int): String = {
    if (AllPolls.getRun(id).isSuccess && AllPolls.get(id).get.stopTime == null) {
      AllPolls.removeRun(id)
      "Your poll was just finished, that was a great poll!"
    } else if (AllPolls.getRun(id).isFailure)
        "Cant't stop Your Poll, cuz it's not run!"
    else "The Poll will stop itself when the time come! Wait..."
  }

  def pollResult(id: Int): String = {
    if (AllPolls.get(id).isSuccess) {
      AllPolls.get(id).map(p => {
        if ((p.viewType.eq("continuous") || p.isOver) && p.isAnonymous) {
          CurrentPoll.get(userID).map(cp =>
            s"Poll №${cp.id} ${cp.name}:\n${cp.questions.indices.map(q =>
              s"${q.toString}. ${cp.questions(q)._1} (${cp.questions(q)._2}):\n" + cp.answers(q).indices.map(a =>
                if (cp.questions(q)._2 == "open")
                s"\t$a) ${cp.answers(q)(a)}${(for (i <- Inner.get(p.id, q, a)) yield "\n\t\t" + i._2 + "\n").mkString("")}"
                else s"\t$a) ${cp.answers(q)(a)} - ${Inner.get(p.id, q, a).length} votes")
                  .mkString("\n")).mkString("\n")}"
          ).get
        }
        else if ((p.viewType.eq("continuous") || p.isOver) && !p.isAnonymous){
          CurrentPoll.get(userID).map(cp =>
            s"Poll №${cp.id} ${cp.name}:\n${cp.questions.indices.map(q =>
              s"${q.toString}. ${cp.questions(q)._1} (${cp.questions(q)._2}):\n" + cp.answers(q).indices.map(a =>
                if (cp.questions(q)._2 == "open")
                  s"\t$a) ${cp.answers(q)(a)}${(for (i <- Inner.get(p.id, q, a)) yield "\n\t\t" + i._2  + " -> "  + i._1).mkString("")}"
                else s"\t$a) ${cp.answers(q)(a)} :${(for (i <- Inner.get(p.id, q, a)) yield "\n" + i._1).mkString("")}")
                .mkString("\n")).mkString("\n")}"
          ).get
        }
        else "You can view the result only when the poll will be over"
        }).get
    }
    else "We don't have such a Poll"
  }

  def addQuestion(question: String, qType: String, answers: Vector[String]): String = {
    if (CurrentPoll.get(userID).isSuccess)
      CurrentPoll.get(userID).map(cp => {
        val poll = Poll.set_question(question, qType, answers)(cp)
        if (!AllPolls.containsRun(cp)) {
          CurrentPoll.set(userID, poll)
          AllPolls.set(cp.id, poll)
          val id = CurrentPoll.get(userID).get.questions.indexOf((question, qType))
          "Success: " + id.toString
        } else "It's already run, You should bear with it!"}).get
    else "There's no such current poll"
  }

  def begin(id: Int): String = {
    if (AllPolls.get(id).isSuccess && CurrentPoll.get(userID).isFailure) {
      CurrentPoll.set(userID, AllPolls.get(id).get)
      "Let's Rock!"
    }
    else if (AllPolls.get(id).isSuccess && CurrentPoll.get(userID).isSuccess)
      "You've already begun one Poll!"
    else "There is no such Poll!"
  }

  def end: String = {
    if (CurrentPoll.get(userID).isSuccess && Try(CurrentPoll.setNone(userID)).isSuccess)
      "Now, you're free!"
    else "You have no begun Poll!"
  }

  def view: String = {
    if (CurrentPoll.get(userID).isSuccess)
      CurrentPoll.get(userID).map(cp =>
        s"Poll №${cp.id} ${cp.name}:\n${cp.questions.indices.map(q =>
          s"${q.toString}. ${cp.questions(q)._1} (${cp.questions(q)._2}):\n" + cp.answers(q).indices.map(a =>
            s"\t$a) ${cp.answers(q)(a)}").mkString("\n")).mkString("\n")}"
      ).get
    else "There's no current Poll!"
  }

  def deleteQuestion(number : Int): String = {
    if (Try(CurrentPoll.get(userID).get.questions(number)).isSuccess)
      CurrentPoll.get(userID).map(cp => {
        val poll = Poll.delete_question(cp.questions(number), number)(cp)
        if (!AllPolls.containsRun(cp)) {
          CurrentPoll.set(userID, poll)
          AllPolls.set(cp.id, poll)
          "Success"
        } else "it's runned, you should bear with it!"
      }).get
    else "Can't delete this Question, set the right Poll or question!"
  }

  def answer(number: Int, answer : String): String = {
    if (Try(CurrentPoll.get(userID).get.questions(number)).isSuccess) {
      val currentPoll = CurrentPoll.get(userID).get
      val answers = Try(answer.split(" ").map(_.toInt))
      val answered: Boolean = if(currentPoll.questions(number)._2 == "open")
        Inner.get(currentPoll.id, number, 0).exists(e => e._1 == userID)
      else currentPoll.answers(number).indices.exists(a =>
        Inner.get(currentPoll.id, number, a).exists(e => e._1 == userID)
      )
      if (answered)
        "You've already answered it!"
      else {
        currentPoll.questions(number)._2 match {
          case "open" => if (!answer.isEmpty) {
            Inner.set(currentPoll.id, number, 0, userID, answer)
            "Success"
          } else "Type of the question - Open, your answer is incorrect"
          case "choice" => if (Try(currentPoll.answers(number)(answers.get.head)).isSuccess &&
            answers.get.length == 1){
              Inner.set(currentPoll.id, number, answers.get.head, userID, "")
              "Success"
          } else "Type of the question - Choice, your answer is incorrect"
          case "multi" => if (answers.isSuccess && !answers.get.isEmpty &&
            Try(answers.get.map(e => currentPoll.answers(number)(e))).isSuccess) {
              answers.get.foreach(a => Inner.set(currentPoll.id, number, a, userID, ""))
              "Success"
          } else "Type of the question - Multi, your answer is incorrect"
        }
      }
    } else "There's no such question or you've not chosen the Poll"
  }
}
