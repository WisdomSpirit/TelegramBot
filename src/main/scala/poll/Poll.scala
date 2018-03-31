package poll

case class Poll(name: String,
                isAnonymous: Boolean,
                viewType: String,
                startTime: String,
                stopTime: String,
                isRun: Boolean = false,
                isOver: Boolean = false) {
  val inner: Inner = new Inner()
}