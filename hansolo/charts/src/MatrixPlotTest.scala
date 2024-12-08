package hansolo.charts

// cSpell:ignore javafx, hansolo



import eu.hansolo.fx.charts.PixelMatrix
import eu.hansolo.fx.charts.PixelMatrix.PixelShape
import eu.hansolo.fx.charts.PixelMatrixBuilder
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.scene.layout.StackPane
import javafx.scene.Scene

import java.util.Random

import scala.compiletime.uninitialized
// import scala.collection.convert.ImplicitConversionsToScala.*


/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i hansolo.charts.run
 * ./mill -i hansolo.charts.runMain hansolo.charts.MatrixPlotTest
 * ./mill -i --watch hansolo.charts.runMain hansolo.charts.MatrixPlotTest
 * 
 * 
 * Note on resources (see StackOverflow link below): Mill's convention is to 
 * place a resources directory on the lowest level Mill module. To access 
 * these resources one must use the path relative to the application (Mill 
 * module) and not the class (because resources are not copied to the compiled 
 * class directory).
 * 
 * If you want to keep the resources next to the classes, these would require
 * you change Mill behavior to copy them, or do it yourself. 
 * 
 * 
 * @see https://stackoverflow.com/questions/22000423/javafx-and-maven-nullpointerexception-location-is-required
 * @see https://stackoverflow.com/questions/12124657/getting-started-on-scala-javafx-desktop-application-development
 * @see https://github.com/HanSolo/charts/blob/master/src/test/java/eu/hansolo/fx/charts/MatrixPlotTest.java
 */
class MatrixPlotTest extends Application {
    private val COLS: Int   = 108
    private val ROWS: Int   = 40
    private val RND: Random = new Random()
    private val OFF: Int         = PixelMatrix.convertToInt(Color.TRANSPARENT)
    private val DARK_RED: Int    = PixelMatrix.convertToInt(Color.web("#D04625"))
    private val RED: Int         = PixelMatrix.convertToInt(Color.web("#F3522C"))
    private val ORANGE: Int      = PixelMatrix.convertToInt(Color.web("#FCA300"))
    private val YELLOW: Int      = PixelMatrix.convertToInt(Color.web("#FFD824"))
    private val BEIGE: Int       = PixelMatrix.convertToInt(Color.web("#EFE3BC"))
    private val LIGHT_GREEN: Int = PixelMatrix.convertToInt(Color.web("#C5E3B9"))
    private val GREEN: Int       = PixelMatrix.convertToInt(Color.web("#A1D490"))
    private val DARK_GREEN: Int  = PixelMatrix.convertToInt(Color.web("#62BD4A"))
    private val BLUE: Int        = PixelMatrix.convertToInt(Color.web("#0197DE"))
    private var pixelMatrix: PixelMatrix = uninitialized
    private var values: Array[Int] = uninitialized
    private var counter: Int = uninitialized
    private var lastTimerCall: Long = uninitialized
    private var timer: AnimationTimer = uninitialized


    override def init() = {
        pixelMatrix = PixelMatrixBuilder.create()
                                        .prefSize(600, 300)
                                        .colsAndRows(COLS, ROWS)
                                        .pixelOnColor(Color.RED)
                                        .pixelOffColor(Color.TRANSPARENT)
                                        .pixelShape(PixelShape.ROUND)
                                        .squarePixels(true)
                                        .build()

        createRandomData()

        lastTimerCall = System.nanoTime()
        timer = new AnimationTimer() {
            override def handle(now: Long) = {
                if (now > lastTimerCall + 10_000_000) {
                    createRandomData()
                    lastTimerCall = now
                }
            }
        }
    }

    private def createRandomData() = {
        pixelMatrix.setAllPixelsOff()

        val MIN_VALUE = -20
        val MAX_VALUE = 20
        for (x <-0 until COLS) {
            val high = (RND.nextInt(MAX_VALUE) - MAX_VALUE) * -1
            val low  = (RND.nextInt(MIN_VALUE * -1)) + (MIN_VALUE * -1)
            for (y <- 0  until ROWS) {
                if (y >= high && y <= low) {
                    if (y >= 0 && y < 4) {
                        pixelMatrix.setPixel(x, y, if (high <= y) BLUE else OFF)
                    } else if (y >= 4 && y < 8) {
                        pixelMatrix.setPixel(x, y, if (high <= y) DARK_GREEN else OFF)
                    } else if (y >= 8 && y < 12) {
                        pixelMatrix.setPixel(x, y, if (high <= y) GREEN else OFF)
                    } else if (y >= 12 && y < 20) {
                        pixelMatrix.setPixel(x, y, if (high <= y) LIGHT_GREEN else OFF)
                    } else if (y >= 20 && y < 28) {
                        pixelMatrix.setPixel(x, y, if (high <= y) BEIGE else OFF)
                    } else if (y >= 28 && y < 32) {
                        pixelMatrix.setPixel(x, y, if (high <= y) YELLOW else OFF)
                    } else if (y >= 32 && y < 36) {
                        pixelMatrix.setPixel(x, y, if (high <= y) ORANGE else OFF)
                    } else if (y >= 36 && y < 40) {
                        pixelMatrix.setPixel(x, y, if (high <= y) RED else OFF)
                    } else {
                        pixelMatrix.setPixel(x, y, if (high <= y) DARK_RED else OFF)
                    }
                }
            }
        }
        pixelMatrix.drawMatrix()
    }

    override def start(stage: Stage) = {
        val pane = new StackPane(pixelMatrix)
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)))
        pane.setPadding(new Insets(10))

        val scene = new Scene(pane)

        stage.setTitle("MatrixPlot")
        stage.setScene(scene)
        stage.show()

        timer.start()
    }

    override def stop() = {
        System.exit(0)
    }
    
    def launchIt():Unit = {
        Application.launch()
    }
}

// Not required, not needed
// object MatrixPlotTest {
//   def main(args: Array[String]) =
//     val app = new MatrixPlotTest
//     app.launchIt()
// 
// }

// Not required, not needed
object MatrixPlotTest {

    def main(args: Array[String]): Unit = {
    Application.launch(classOf[MatrixPlotTest], args*)
    }
}
