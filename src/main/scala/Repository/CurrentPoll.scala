package Repository

import poll.Poll

object CurrentPoll {
  private var P : Option[Poll] = Option.empty

  def get : Option[Poll] = P

  def set(poll : Poll) : Unit = P = Option(poll)

  def setNone() : Unit = P = Option.empty
}
