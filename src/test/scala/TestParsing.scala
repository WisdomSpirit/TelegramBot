//import Repository._
//import commands.Commands._
//import parsers.CommandParser._
//import org.scalatest._
//
//
//class TestParsing extends FlatSpec {
//
//  "/list" should "be empty on start" in {
//    assertResult("Can You see the list of Your polls? I can't too. But they exists.") {
//      parse("/list").get.execute()
//    }
//  }
//
//  "/create_poll" should "be parsed right" in {
//    assert(parse("/create_poll (fghjkhk((akjdj)))").isSuccess)
//    assertResult("Success: 0") {
//      parse("/create_poll (name0)").get.execute()
//    }
//    assertResult("Success: 1") {
//      parse("/create_poll (name ((1))) (yes)").get.execute()
//    }
//    assertResult("Success: 2") {
//      parse("/create_poll (n a m e 2) (no) (afterstop)").get.execute()
//    }
//    assertResult("Success: 3") {
//      parse("/create_poll (na((me)) 3) (yes) (continuous) (23:23:23 23:23:23)")
//        .get.execute()
//    }
//    assertResult("Success: 4") {
//      parse("/create_poll (name4) (yes) (continuous) (23:23:23 23:23:23)" +
//        " (25:25:25 25:25:25)").get.execute()
//    }
//    print(pollList())
//  }
//
//  "CreateCommand" should "doesn't know strange input" in {
//    assertResult("Unrecognised command! Say what!?")(parse("/creat_pol (qwerty)").get.execute())
//  }
//
//  "/delete_poll" should "be parsed right" in {
//    assertResult("Exterminate! Exterminate! Exterminate!") {
//      parse("/delete_poll (0)").get.execute()
//    }
//    assert(AllPolls.get("0").isFailure)
//    assertResult("Can't delete Your Poll, cuz there's no such one!") {
//      parse("/delete_poll ").get.execute()
//    }
//    assertResult("Can't delete Your Poll, cuz there's no such one!") {
//      parse("/delete_poll ()").get.execute()
//    }
//    assertResult("Can't delete Your Poll, cuz there's no such one!") {
//      parse("/delete_poll (5)").get.execute()
//    }
//    assertResult("Can't delete Your Poll, cuz there's no such one!") {
//      parse("/delete_poll (d)").get.execute()
//    }
//  }
//
//  "/start_poll" should "be parsed right" in {
//    assertResult("Your poll was just started, look for feedback!") {
//      parse("/start_poll (1)").get.execute()
//    }
//    assert(AllPolls.getRun("1").isSuccess)
//    assertResult("Can't start your Poll, cuz there's no such one!") {
//      parse("/start_poll ()").get.execute()
//    }
//    assertResult("Can't start your Poll, cuz there's no such one!") {
//      parse("/start_poll (d)").get.execute()
//    }
//    assertResult("Can't start your Poll, cuz there's no such one!") {
//      parse("/start_poll (12)").get.execute()
//    }
//  }
//
//  "/stop_poll" should "be parsed right" in {
//    assertResult("Your poll was just finished, that was a great poll!") {
//      parse("/stop_poll (1)").get.execute()
//    }
//    assert(AllPolls.getRun("1").isFailure)
//    assertResult("Cant't stop Your Poll, cuz it's not run!") {
//      parse("/stop_poll (12)").get.execute()
//    }
//    assertResult("Cant't stop Your Poll, cuz it's not run!") {
//      parse("/stop_poll (f)").get.execute()
//    }
//  }
//}