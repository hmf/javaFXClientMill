// cSpell:ignore javafx, hansolo

package hansolo.charts



import eu.hansolo.fx.charts.Axis
import eu.hansolo.fx.charts.AxisType
import eu.hansolo.fx.charts.Grid
import eu.hansolo.fx.charts.Position
import javafx.application.Application
import javafx.geometry.Orientation
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.scene.layout.StackPane
import javafx.scene.Scene

import java.util.ArrayList
import java.util.HashMap
import java.util.List


import scala.compiletime.uninitialized
import scala.jdk.CollectionConverters.*
// import java.{util => ju}

/**
 * 
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i hansolo.grid.run
 * ./mill -i hansolo.grid.runMain hansolo.charts.LogGridTest
 * ./mill -i --watch hansolo.grid.runMain hansolo.charts.LogGridTest
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
 * @see https://github.com/HanSolo/charts/blob/master/src/test/java/eu/hansolo/fx/charts/LineChartTest.java
 */
class LogGridTest extends Application {
    private val AXIS_WIDTH: Double = 25d

    private var xAxisLog: Axis = uninitialized
    private var yAxisLog: Axis = uninitialized
    private var gridLog: Grid = uninitialized


    override def init() = {
        xAxisLog = createBottomXAxis(0, 1000, false)
        xAxisLog.setMajorTickMarkColor(Color.RED)
        xAxisLog.setType(AxisType.LOGARITHMIC)

        yAxisLog = createLeftYAxis(0, 1000, false)
        yAxisLog.setMinorTickMarkColor(Color.MAGENTA)
        yAxisLog.setType(AxisType.LOGARITHMIC)


        gridLog  = new Grid(xAxisLog, yAxisLog)
    }

    override def start(stage: Stage) = {
        val pane = new AnchorPane(xAxisLog, yAxisLog, gridLog)

        AnchorPane.setTopAnchor(yAxisLog, 0d)
        AnchorPane.setBottomAnchor(yAxisLog, 25d)
        AnchorPane.setLeftAnchor(yAxisLog, 0d)

        AnchorPane.setLeftAnchor(xAxisLog, 25d)
        AnchorPane.setRightAnchor(xAxisLog, 0d)
        AnchorPane.setBottomAnchor(xAxisLog, 0d)

        AnchorPane.setTopAnchor(gridLog, 0d)
        AnchorPane.setRightAnchor(gridLog, 0d)
        AnchorPane.setBottomAnchor(gridLog, 25d)
        AnchorPane.setLeftAnchor(gridLog, 25d)

        val scene = new Scene(pane)

        stage.setTitle("LogGridTest")
        stage.setScene(scene)
        stage.show()
    }


    override def stop() = {
        System.exit(0)
    }
    
    def launchIt():Unit = {
        Application.launch()
    }

    private def createLeftYAxis(MIN: Double, MAX: Double, AUTO_SCALE: Boolean): Axis = {
        val axis = new Axis(Orientation.VERTICAL, Position.LEFT)
        axis.setMinValue(MIN)
        axis.setMaxValue(MAX)
        axis.setPrefWidth(AXIS_WIDTH)
        axis.setAutoScale(AUTO_SCALE)

        AnchorPane.setTopAnchor(axis, 0d)
        AnchorPane.setBottomAnchor(axis, 25d)
        AnchorPane.setLeftAnchor(axis, 0d)

        axis
    }
    private def createRightYAxis(MIN: Double, MAX: Double, AUTO_SCALE: Boolean): Axis = {
        val axis = new Axis(Orientation.VERTICAL, Position.RIGHT)
        axis.setMinValue(MIN)
        axis.setMaxValue(MAX)
        axis.setPrefWidth(AXIS_WIDTH)
        axis.setAutoScale(AUTO_SCALE)

        AnchorPane.setRightAnchor(axis, 0d)
        AnchorPane.setTopAnchor(axis, 0d)
        AnchorPane.setBottomAnchor(axis, 25d)

        axis
    }

    private def createBottomXAxis(MIN: Double, MAX: Double, AUTO_SCALE: Boolean): Axis = {
        val axis = new Axis(Orientation.HORIZONTAL, Position.BOTTOM)
        axis.setMinValue(MIN)
        axis.setMaxValue(MAX)
        axis.setPrefHeight(AXIS_WIDTH)
        axis.setAutoScale(AUTO_SCALE)

        AnchorPane.setBottomAnchor(axis, 0d)
        AnchorPane.setLeftAnchor(axis, 25d)
        AnchorPane.setRightAnchor(axis, 25d)

        axis
    }
    private def createTopXAxis(MIN: Double, MAX: Double, AUTO_SCALE: Boolean): Axis = {
        val axis = new Axis(Orientation.HORIZONTAL, Position.TOP)
        axis.setMinValue(MIN)
        axis.setMaxValue(MAX)
        axis.setPrefHeight(AXIS_WIDTH)
        axis.setAutoScale(AUTO_SCALE)

        AnchorPane.setTopAnchor(axis, 25d)
        AnchorPane.setLeftAnchor(axis, 25d)
        AnchorPane.setRightAnchor(axis, 25d)

        axis
    }
}

// Not required, not needed
// object LogGridTest {
//   def main(args: Array[String]) =
//     val app = new LogGridTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object LogGridTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[LogGridTest], args*)
//     }
// }
