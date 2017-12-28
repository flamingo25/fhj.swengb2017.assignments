package at.fhj.swengb.apps.battleship.jfx

import java.net.URL
import java.nio.file.Files
import java.util.ResourceBundle
import javafx.fxml.{FXML, Initializable}
import javafx.scene.control.TextArea
import javafx.scene.layout.GridPane
import javafx.stage.FileChooser

import at.fhj.swengb.apps.battleship
import at.fhj.swengb.apps.battleship.model._
import at.fhj.swengb.apps.battleship.{BattleShipProtobuf, BattleShipProtocol}


class BattleShipFxController extends Initializable {


  @FXML private var battleGroundGridPane: GridPane = _

  /**
    * A text area box to place the history of the game
    */
  @FXML private var log: TextArea = _

  @FXML
  def newGame(): Unit = initGame()

  var currentGame: BattleShipGame = _

  override def initialize(url: URL, rb: ResourceBundle): Unit = initGame()

  private def getCellHeight(y: Int): Double = battleGroundGridPane.getRowConstraints.get(y).getPrefHeight

  private def getCellWidth(x: Int): Double = battleGroundGridPane.getColumnConstraints.get(x).getPrefWidth

  def appendLog(message: String): Unit = log.appendText(message + "\n")

  /**
    * Create a new game.
    *
    * This means
    *
    * - resetting all cells to 'empty' state
    * - placing your ships at random on the battleground
    *
    */
  def init(game: BattleShipGame, clicks: List[BattlePos]) : Unit = {
    battleGroundGridPane.getChildren.clear()
    for (c <- game.getCells) {
      battleGroundGridPane.add(c, c.pos.x, c.pos.y)
    }
    game.getCells().foreach(c => c.init)
    game.loadOrder(clicks)
  }


  private def initGame(): Unit = {
    currentGame = createGame()
    init(currentGame, List())
    appendLog("New game started.")
  }

  private def createGame(): BattleShipGame = {
    val field = BattleField(10, 10, Fleet(FleetConfig.Standard))

    val battleField: BattleField = BattleField.placeRandomly(field)

    BattleShipGame(battleField, getCellWidth, getCellHeight, appendLog)
  }

  def saveGame(): Unit = {
    val fileChooser = new FileChooser
    fileChooser.setInitialFileName("Saving Game")
    fileChooser.setInitialFileName("BattleShip Game")
    val selectedFile = fileChooser.showSaveDialog(null)
    if (selectedFile != null) {
      val current = BattleShipProtocol.convert(currentGame)
      current.writeTo(Files.newOutputStream(selectedFile.toPath))
    }
  }

  def loadGame(): Unit = {
    val fileChooser = new FileChooser
    fileChooser.setInitialFileName("Loading Game")
    fileChooser.setInitialFileName("BattleShip Game")
    val selectedFile = fileChooser.showOpenDialog(null)
    if (selectedFile != null) {
      val loadingGame = BattleShipProtobuf.BattleShipGame.parseFrom(Files.newInputStream(selectedFile.toPath))
      val convertingLoadedGame = BattleShipProtocol.convert(loadingGame)
      currentGame = BattleShipGame(convertingLoadedGame.battleField, getCellHeight, getCellHeight, appendLog)
      init(currentGame, convertingLoadedGame.clicks)
    }

  }
}