package at.fhj.swengb.apps.calculator

import java.net.URL
import java.util.ResourceBundle
import javafx.application.Application
import javafx.beans.property.{ObjectProperty, SimpleObjectProperty}
import javafx.fxml.{FXML, FXMLLoader, Initializable}
import javafx.scene.control.TextField
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

import scala.util.{Failure, Success}
import scala.util.control.NonFatal

object CalculatorApp {

  def main(args: Array[String]): Unit = {
    Application.launch(classOf[CalculatorFX], args: _*)
  }
}

class CalculatorFX extends javafx.application.Application {

  val fxml = "/at/fhj/swengb/apps/calculator/calculator.fxml"
  val css = "/at/fhj/swengb/apps/calculator/calculator.css"

  def mkFxmlLoader(fxml: String): FXMLLoader = {
    new FXMLLoader(getClass.getResource(fxml))
  }

  override def start(stage: Stage): Unit =
    try {
      stage.setTitle("Calculator")
      setSkin(stage, fxml, css)
      stage.show()
      stage.setMinWidth(stage.getWidth)
      stage.setMinHeight(stage.getHeight)
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }

  def setSkin(stage: Stage, fxml: String, css: String): Boolean = {
    val scene = new Scene(mkFxmlLoader(fxml).load[Parent]())
    stage.setScene(scene)
    stage.getScene.getStylesheets.clear()
    stage.getScene.getStylesheets.add(css)
  }

}

class CalculatorFxController extends Initializable {

  val calculatorProperty: ObjectProperty[RpnCalculator] = new SimpleObjectProperty[RpnCalculator](RpnCalculator())

  def getCalculator() : RpnCalculator = calculatorProperty.get()

  def setCalculator(rpnCalculator : RpnCalculator) : Unit = calculatorProperty.set(rpnCalculator)

  @FXML var firstTextField : TextField = _
  @FXML var secondTextField : TextField = _
  @FXML var thirdTextField : TextField = _

  override def initialize(location: URL, resources: ResourceBundle) = {

  }

  def sgn(): Unit = {
    getCalculator().push(Op(firstTextField.getText)) match {
      case Success(c) => setCalculator(c)
      case Failure(e) => // show warning / error
    }
    getCalculator().stack foreach println
  }

  def zero() : Unit = {
    thirdTextField.appendText("0")

  }

  def one() : Unit = {
    thirdTextField.appendText("1")

  }

  def two() : Unit = {
    thirdTextField.appendText("2")

  }

  def three() : Unit = {
    thirdTextField.appendText("3")

  }

  def four() : Unit = {
    thirdTextField.appendText("4")

  }

  def five() : Unit = {
    thirdTextField.appendText("5")

  }

  def six() : Unit = {
    thirdTextField.appendText("6")

  }

  def seven() : Unit = {
    thirdTextField.appendText("7")

  }

  def eight() : Unit = {
    thirdTextField.appendText("8")

  }

  def nine() : Unit = {
    thirdTextField.appendText("9")

  }

  def add() : Unit = {
    thirdTextField.appendText("+")

  }

  def subst() : Unit = {
    thirdTextField.appendText("-")

  }

  def multi() : Unit = {
    thirdTextField.appendText("*")

  }

  def change() : Unit = {
    thirdTextField.appendText("+/-")

  }

  def div() : Unit = {
    thirdTextField.appendText("/")

  }

  def comma() : Unit = {
    thirdTextField.appendText(",")

  }


  def calc() : Unit = {
    if (secondTextField.getText == "") {
      secondTextField.setText(thirdTextField.getText)
      thirdTextField.setText("")
    }

    else if (firstTextField.getText == "" && secondTextField.getText != "") {
      firstTextField.setText (secondTextField.getText)
      secondTextField.setText (thirdTextField.getText)
      thirdTextField.setText("")
    }
  }

  def perc() : Unit = {
    thirdTextField.appendText("%")

  }


  def ac() : Unit = {
    firstTextField.setText("")
    secondTextField.setText("")
    thirdTextField.setText("")

  }


}

