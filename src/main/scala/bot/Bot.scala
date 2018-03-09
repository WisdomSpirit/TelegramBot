package bot

import commands.Command
import interaction.{Reader, Writer}

import scala.collection.mutable

object Bot extends App {
  val comDict = new mutable.HashMap[String, Command]()

  override def main(args: Array[String]) {
      Reader.parse("in.txt").foreach(command => Writer.write(command.execute().toString))
  }
}
