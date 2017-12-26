package at.fhj.swengb.apps.battleship.model

import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
;

/**
  * Represents one part of a vessel or one part of the ocean.
  */

case class BattleFxCell(pos: BattlePos
                        , width: Double
                        , height: Double
                        , log: String => Unit
                        , someVessel: Option[Vessel] = None
                        , fn: (Vessel, BattlePos) => Unit
                        ) extends Rectangle(width, height) {

  def init(): Unit = {
    if (someVessel.isDefined) {
      setFill(Color.DARKBLUE)
    } else {
      setFill(Color.DARKBLUE)
    }
  }

  setOnMouseClicked(e => {
    someVessel match {
      case None =>
        log(s"Missed. Try it again!")
        setFill(Color.DODGERBLUE)
      case Some(v) =>
        log(s"Hit an enemy vessel!!")
        fn(v, pos)
        setFill(Color.DARKRED)
    }
  })

}
