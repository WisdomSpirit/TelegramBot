package Repository

import poll.Poll

object CurrentPoll {
  private var P : Option[Poll] = Option.empty

  def get : Option[Poll] = P

  def set(poll : Poll) : Unit = P = Option(poll)

  def setNone() : Unit = P = Option.empty
}


//package Repository
//
//import poll.Poll
//
//object CurrentPoll {
//  private var P : Map[Int, Poll] = Map()
//
//  def get(id : Int): Option[Poll] = P.get(id)
//
//  def set(id : Int, poll : Poll) : Unit = P = P updated (id, poll)
//
//  def setNone(id : Int) : Unit = P = P - id
//}
