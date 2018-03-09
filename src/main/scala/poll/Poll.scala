package poll

class Poll(title:String, anonymous:Boolean, view:String, start:String, finish:String) {
  val name: String = title
  val isAnonymous : Boolean = anonymous
  val viewType : String = view
  val startTime : String = start
  val finishTime : String = finish
  val isOver : Boolean = false
  val inner : Inner = new Inner()
  var questions = List()
  var answers = List()
}
