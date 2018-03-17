package poll

import scala.collection.mutable.ListBuffer
import scala.util.Try

class Inner {
  private val questions = ListBuffer[ListBuffer[ListBuffer[String]]]()
                          //вопросы     ответы      результаты
  def get_question(number : Int) : Try[ListBuffer[ListBuffer[String]]] = Try(questions(number))
  def set_question(name: Try[String], qtype: Try[String], answers : List[String]) {

  }
  def get_answer(){}
  def set_answer(){}
  def get_results(){}
  def set_results(){}
}