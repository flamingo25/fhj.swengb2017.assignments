package at.fhj.swengb.apps.calculator

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths}
import java.util
import java.util.List
import scala.collection.JavaConverters._
import org.scalatest.WordSpecLike

class TimesheetSpec extends WordSpecLike {

  val p: Path = Paths.get("C:\\Users\\Eliska\\IdeaProjects\\fhj.swengb2017.assignments\\calculator\\timesheet-calculator.adoc")

  "A test"should {
  "work" in{
    //this is the skeleton of a test
    val result: String = Files.readAllLines(p).asScala.mkString("\n")

    assert(expected == result)
    println(result)
  }
}
  val expected =
    """== Time expenditure: Calculator assignment
      |
      |[cols="1,1,4", options="header"]
      |.Time expenditure
      ||===
      || Date
      || Hours
      || Description
      |
      || 29.11.17
      || 1
      || Review of this and that
      |
      || 30.11.17
      || 5
      || implemented css
      |
      || 11.07.17
      || 2
      || fixed bugs
      |
      ||===
      |""".stripMargin
}
