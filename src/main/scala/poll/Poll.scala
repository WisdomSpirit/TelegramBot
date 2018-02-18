package poll

class Poll(title:String, anonymous:Boolean, view:Boolean, start:String, finish:String) {
  val name: String = title
  val isAnonymous : Boolean = anonymous
  val viewType : Boolean = anonymous
  val startTime : String = start
  val finishTime : String = finish
}
