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

  @FXML var textOne : TextField = _
  @FXML var textTwo : TextField = _
  @FXML var textThree : TextField = _

  override def initialize(location: URL, resources: ResourceBundle) = {

  }

    def calc(): Unit = {
      getCalculator().push(Op(textThree.getText)) match {
        case Success(c) => setCalculator(c)
        case Failure(e) => // show warning / error
      }


      /*  Wenn Stack size kleiner als 2 --> textOne nicht befüllen


      if (getCalculator().stack.size < 2)      {
        (textTwo.setText(getCalculator().stack(0).toString.drop(4).init))
        textThree.setText("")
      }
      else {

      }
         */
       

      getCalculator().stack foreach println


      (textTwo.setText(getCalculator().stack(0).toString.drop(4).init))
      textThree.setText("")
      (textOne.setText(getCalculator().stack(1).toString.drop(4).init))
      textThree.setText("")
    }



  def one() : Unit = {
    textThree.appendText("1")

  }

  def two() : Unit = {
    textThree.appendText("2")

  }

  def three() : Unit = {
    textThree.appendText("3")

  }

  def four() : Unit = {
    textThree.appendText("4")

  }

  def five() : Unit = {
    textThree.appendText("5")

  }

  def six() : Unit = {
    textThree.appendText("6")

  }

  def seven() : Unit = {
    textThree.appendText("7")

  }

  def eight() : Unit = {
    textThree.appendText("8")

  }

  def nine() : Unit = {
    textThree.appendText("9")

  }

  def zero() : Unit = {
    textThree.appendText("0")

  }


  def add() : Unit = {
    textThree.appendText("+")

  }

  def subst() : Unit = {
    textThree.appendText("-")

  }

  def multi() : Unit = {
    textThree.appendText("*")

  }

  def change() : Unit = {
    textTwo.setText("-" ++ textTwo.getText())

  }


  def div() : Unit = {
    textThree.appendText("/")

  }

  def comma() : Unit = {
    textThree.appendText(".")

  }


  def perc() : Unit = {
    textThree.appendText("%")
  }


  def ac() : Unit = {
    textOne.setText("")
    textTwo.setText("")
    textThree.setText("")

  }


}

