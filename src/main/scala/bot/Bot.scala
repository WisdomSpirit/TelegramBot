package bot

import commands.Command
import interaction.Reader

import scala.collection.mutable

class Bot {
  val comDict = new mutable.HashMap[String,]()

  def main(args: Array[String]) {
    val commands = Reader.parse("")

    commands.foreach(command => command.execute())
  }
}
