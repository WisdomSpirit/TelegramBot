package poll
import com.softwaremill.quicklens._


case class Poll(id: Int, name: String,
                isAnonymous: Boolean,
                viewType: String,
                startTime: String,
                stopTime: String,
                isRun: Boolean = false,
                questions : Vector[(String, String)] = Vector(),
                answers : Vector[Vector[String]] = Vector())

object Poll{
  def delete_question(question: (String, String), number: Int) : Poll => Poll =
    cp => cp.modify(_.questions)
    .using(_.filter(_ != question))
    .modify(_.answers)
    .using(a => a.filter(_ != a(number)))

  def set_question(question: String, qType: String, answers: Vector[String]) : Poll => Poll =
    cp => cp.modify(_.questions)
    .using(_ :+ (question, qType))
    .modify(_.answers)
    .using(_ :+ answers)
}