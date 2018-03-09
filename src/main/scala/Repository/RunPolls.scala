package Repository

import poll.Poll
import scala.collection.mutable


object RunPolls {
  private val P = new mutable.HashMap[String, Poll]()

  def get(id : String) : Option[Poll] = P.get(id)

  def set(id : String, poll : Option[Poll]) : Unit = poll.map(p => P.update(id, p))

  def set(id : String, poll : Poll) : Unit= P.update(id, poll)

  def remove(id : String) : Option[Poll] = P.remove(id)

  def getAll : mutable.HashMap[String, Poll] = P
}
