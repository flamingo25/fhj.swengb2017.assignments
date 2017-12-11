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

  @FXML var first : TextField = _
  @FXML var second : TextField = _
  @FXML var last : TextField = _

  override def initialize(location: URL, resources: ResourceBundle) = {

  }
  def enter(): Unit = {
    if (first.getText.isEmpty == false) {
      getCalculator().push(Op(first.getText)) match {
        case Success(c) => setCalculator(c)
        case Failure(e) => // show warning / error
      }
      if (getCalculator().stack.size >= 2) {
        last.setText(getCalculator().stack(1).toString.drop(4).init)
      }
      else {
        last.setText("0")
      }
      second.setText(getCalculator().peek().toString.drop(4).init)
      first.setText("")
      getCalculator().stack foreach println
    }
  }

  def zero(): Unit = {
    first.setText(first.getText + "0")
  }

  def one(): Unit = {
    first.setText(first.getText + "1")
  }

  def two(): Unit = {
    first.setText(first.getText + "2")
  }

  def three(): Unit = {
    first.setText(first.getText + "3")
  }

  def four(): Unit = {
    first.setText(first.getText + "4")
  }

  def five(): Unit = {
    first.setText(first.getText + "5")
  }

  def six(): Unit = {
    first.setText(first.getText + "6")
  }

  def seven(): Unit = {
    first.setText(first.getText + "7")
  }

  def eight(): Unit = {
    first.setText(first.getText + "8")
  }

  def nine(): Unit = {
    first.setText(first.getText + "9")
  }

  def clear(): Unit = {
    if (first.getText.isEmpty) {
      setCalculator(RpnCalculator())
      second.setText("0")
      last.setText("0")
    }
    else {
      first.setText("")
    }
  }

  def change(): Unit = {
    if (first.getText.charAt(0) == '-') {
      first.setText(first.getText.drop(1))
    }
    else {
      first.setText("-" + first.getText)
    }
  }

  def percent(): Unit = {
    println("Not implemented")
  }

  def div(): Unit = {
    if (first.getText.isEmpty == false) {
      enter()
    }
    if (getCalculator().stack.size < 2) {
      last.setText("ERROR: NEED TWO VALUES")
    }
    else {
      first.setText("/")
      enter()
    }
  }

  def sum(): Unit = {
    if (first.getText.isEmpty == false) {
      enter()
    }
    if (getCalculator().stack.size < 2) {
      last.setText("ERROR: NEED TWO VALUES")
    }
    else {
      first.setText("+")
      enter()
    }
  }

  def mult(): Unit = {
    if (first.getText.isEmpty == false) {
      enter()
    }
    if (getCalculator().stack.size < 2) {
      last.setText("ERROR: NEED TWO VALUES")
    }
    else {
      first.setText("*")
      enter()
    }
  }

  def sub(): Unit = {
    if (first.getText.isEmpty == false) {
      enter()
    }
    if (getCalculator().stack.size < 2) {
      last.setText("ERROR: NEED TWO VALUES")
    }
    else {
      first.setText("-")
      enter()
    }
  }

  def comma(): Unit = {
    first.setText(first.getText + ".")
  }
}