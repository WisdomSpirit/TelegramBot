import commands.General._
import org.scalatest.FlatSpec

class TestGeneralCommands extends FlatSpec{
  assertResult("Your poll wasn't created! Please try again later!"){createPoll(Try(List[String]()))}
}
