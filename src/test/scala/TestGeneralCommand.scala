import Repository._
import commands.General._
import org.scalatest.FlatSpec

import scala.util.Try

class TestGeneralCommand extends FlatSpec{
  assertResult("Can You see the list of Your polls? I can't too. But they exists."){pollList()}
  assertResult("can't create a poll"){createPoll(Try(List[String]()))}
  assertResult("0") {createPoll(Try(List[String]("newpoll", "yes")))}
  assertResult("1") {createPoll(Try(List[String]("newpoll", "no")))}
  assertResult("can't create a poll") {createPoll(Try(List[String]("newpoll", "ys")))}
  assertResult("2"){createPoll(Try(List[String]("newpoll", "yes", "afterstop")))}
  assertResult("3"){createPoll(Try(List[String]("newpoll", "yes", "continuous")))}
  assertResult("can't create a poll"){createPoll(Try(List[String]("newpoll", "yes", "aftersop")))}
  assertResult("4"){createPoll(Try(List[String]("newpoll", "yes", "continuous", "23:12:04 12:45:12")))}
  assertResult("can't create a poll"){createPoll(Try(List[String]("newpoll", "yes", "continuous", "23:12:04 12::10")))}
  assertResult("5")
    {createPoll(Try(List[String]("newpoll", "no", "continuous", "23:12:04 12:45:12", "23:12:54 12:45:32")))}
  assertResult("can't create a poll"){createPoll(Try(List[String]("newpoll", "yes", "continuous", "23:12:04 12:sd:10")))}
  assertResult("can't create a poll"){createPoll(Try(List[String]("newpoll", "yes", "continuous", "23:12:04:23 12::10")))}
  assertResult("can't create a poll")
    {createPoll(Try(List[String]("newpoll", "yes", "afterstop", "23:12:04 12:45:12", "23:2:54 12:45:32")))}

  assertResult(6){AllPolls.getAll.count(e => true)}

  print(pollList())

  assert(AllPolls.get("1").isSuccess)
  assertResult("Exterminate! Exterminate! Exterminate!"){deletePoll(Try(List[String]("1")))}
  assert(AllPolls.get("1").isFailure)
  assertResult("Can't delete Your Poll, cuz there's no such one!"){deletePoll(Try(List[String]("")))}

  assert(RunPolls.getAll.isEmpty)
  assertResult("Your poll was just started, look for feedback!"){startPoll(Try(List[String]("0")))}
  assert(RunPolls.getAll.nonEmpty)
  assertResult("Can't start your Poll, cuz there's no such one!"){startPoll(Try(List[String]("1")))}

  assert(RunPolls.get("0").isSuccess)
  assertResult("Your poll was just finished, that was a great poll!"){stopPoll(Try(List[String]("0")))}
  assert(RunPolls.get("0").isFailure)
  assertResult("Cant't stop Your Poll, cuz it's not run!"){stopPoll(Try(List[String]("0")))}

//  print(pollResult(Try(List[String]("3"))))

}