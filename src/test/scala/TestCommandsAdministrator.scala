//import commands.Commands._
//import Repository._
//import org.scalatest.FlatSpec
//import scala.util.Try
//
//class TestCommandsAdministrator extends FlatSpec{
//
//  "/list" should "be empty on start" in {
//    assertResult("Can You see the list of Your polls? I can't too. But they exists.") {
//      pollList()
//    }
//  }
//
//  "/create_poll" should "run well" in {
//    assertResult("can't create a poll") {
//      createPoll(Try(List[String]()))
//    }
//    assertResult("Success: 0") {
//      createPoll(Try(List[String]("newpoll0", "yes")))
//    }
//    assertResult("Success: 1") {
//      createPoll(Try(List[String]("newpoll1", "no")))
//    }
//    assertResult("can't create a poll") {
//      createPoll(Try(List[String]("newpoll", "ys")))
//    }
//    assertResult("Success: 2") {
//      createPoll(Try(List[String]("newpoll2", "yes", "afterstop")))
//    }
//    assertResult("Success: 3") {
//      createPoll(Try(List[String]("newpoll3", "yes", "continuous")))
//    }
//    assertResult("can't create a poll") {
//      createPoll(Try(List[String]("newpoll", "yes", "aftersop")))
//    }
//    assertResult("Success: 4") {
//      createPoll(Try(List[String]("newpoll4", "yes", "continuous", "23:12:04 12:45:12")))
//    }
//    assertResult("can't create a poll") {
//      createPoll(Try(List[String]("newpoll", "yes", "continuous", "23:12:04 12::10")))
//    }
//    assertResult("Success: 5") {
//      createPoll(Try(List[String]("newpoll5", "no", "continuous", "23:12:04 12:45:12", "23:12:54 12:45:32")))
//    }
//    assertResult("can't create a poll") {
//      createPoll(Try(List[String]("newpoll", "yes", "continuous", "23:12:04 12:sd:10")))
//    }
//    assertResult("can't create a poll") {
//      createPoll(Try(List[String]("newpoll", "yes", "continuous", "23:12:04:23 12::10")))
//    }
//    assertResult("can't create a poll") {
//      createPoll(Try(List[String]("newpoll", "yes", "afterstop", "23:12:04 12:45:12", "23:2:54 12:45:32")))
//    }
//    assertResult(6) {
//      AllPolls.getAll.count(_ => true)
//    }
//  }
//
//  "/delete_poll" should "run well" in {
//    assert(AllPolls.get("1").isSuccess)
//    assertResult("Exterminate! Exterminate! Exterminate!") {
//      deletePoll(Try(List[String]("1")))
//    }
//    assert(AllPolls.get("1").isFailure)
//    assertResult("Can't delete Your Poll, cuz there's no such one!") {
//      deletePoll(Try(List[String]("1")))
//    }
//  }
//
//  "/start_poll" should "run well" in {
//    assert(AllPolls.getAllRun.isEmpty)
//    assertResult("Your poll was just started, look for feedback!") {
//      startPoll(Try(List[String]("0")))
//    }
//    assert(AllPolls.getAllRun.nonEmpty)
//    assertResult("Can't start your Poll, cuz there's no such one!") {
//      startPoll(Try(List[String]("1")))
//    }
//  }
//
//  "/stop_poll" should "run well" in {
//    assert(AllPolls.getRun("0").isSuccess)
//    assertResult("Your poll was just finished, that was a great poll!") {
//      stopPoll(Try(List[String]("0")))
//    }
//    assert(AllPolls.getRun("0").isFailure)
//    assertResult("Cant't stop Your Poll, cuz it's not run!") {
//      stopPoll(Try(List[String]("0")))
//    }
//  }
//
////    print(pollResult(Try(List[String]("3"))))
//}