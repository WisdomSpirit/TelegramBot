package poll

import java.text.SimpleDateFormat
import java.util.Date

class Poll(title:String, anonymous:Boolean, view:String, start:String, finish:String) {
  val name: String = title
  val isAnonymous : Boolean = anonymous
  val viewType : String = view
  val startTime : String = start
  val finishTime : String = finish
}
