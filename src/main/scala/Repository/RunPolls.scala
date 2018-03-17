package Repository

import poll.Poll

import scala.collection.mutable
import scala.util.Try


object RunPolls {
  private val P = Map[String, Poll]()

  def get(id : String) : Try[Poll] = Try{P(id)}

  def set(id : String, poll : Try[Poll]) : Unit = poll.map(p => P updated (id, p))

  def set(id : String, poll : Poll) : Unit= P updated (id, poll)

  def remove(id : String) : Unit = P - id

  def getAll : Map[String, Poll] = P
}
