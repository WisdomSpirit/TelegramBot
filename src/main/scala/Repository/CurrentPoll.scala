package Repository

import poll.Poll

object CurrentPoll {
  private var P : Poll = _

  def get : Poll = P

  def set(poll : Poll) : Unit = P = poll

  def set(poll : Option[Poll]) : Unit = poll.map(p => P = p)
//  val P : mutable.HashMap[String, Poll] = new mutable.HashMap[String, Poll]()
//
//  def get(name : String) {
//    P.get(name)
//  }
//
//  def set(name : String, poll : Poll) {
//    P.update(name, poll)
//  }
}
