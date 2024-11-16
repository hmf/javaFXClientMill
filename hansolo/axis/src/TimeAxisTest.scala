// cSpell:ignore javafx, hansolo

package hansolo.charts


import eu.hansolo.fx.charts.Axis
import eu.hansolo.fx.charts.ChartType
import eu.hansolo.fx.charts.Position
import eu.hansolo.fx.charts.XYChart
import eu.hansolo.fx.charts.XYPane
import eu.hansolo.fx.charts.data.TYChartItem
import eu.hansolo.fx.charts.series.XYSeries
import eu.hansolo.toolbox.unit.Converter
import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.scene.layout.StackPane
import javafx.scene.Scene

import java.time.LocalDateTime
import java.util.ArrayList
import java.util.List
import java.util.Random

import eu.hansolo.toolbox.unit.Category.TEMPERATURE
import eu.hansolo.toolbox.unit.UnitDefinition.CELSIUS
import eu.hansolo.toolbox.unit.UnitDefinition.FAHRENHEIT


import scala.compiletime.uninitialized


/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * X ./mill -i hansolo.axis.run
 * ./mill -i hansolo.axis.runMain hansolo.charts.TimeAxisTest
 * ./mill -i --watch hansolo.axis.runMain hansolo.charts.TimeAxisTest
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
class TimeAxisTest extends Application {
    private val AXIS_WIDTH:  Double                = 25d
    private val COLORS:      Array[Color]          = Array( Color.RED, Color.BLUE, Color.CYAN, Color.LIME )
    private val RND:         Random                = new Random()
    private var tySeries1:   XYSeries[TYChartItem] = uninitialized
    private var tyChart:     XYChart[TYChartItem]  = uninitialized
    private var xAxisBottom: Axis                  = uninitialized
    private var yAxisLeft:   Axis                  = uninitialized
    private var yAxisRight:  Axis                  = uninitialized


    override def init() = {
        val noOfValues                 = 24 * 60
        val start: LocalDateTime       = LocalDateTime.now()
        val end: LocalDateTime         = start.plusHours(24)
        val tyData1: List[TYChartItem] = new ArrayList[TYChartItem]()

        val is = 0 until noOfValues
        for (i <- is) {
            tyData1.add(new TYChartItem(start.plusMinutes(i), RND.nextDouble() * 12 + RND.nextDouble() * 6, "P" + i, COLORS(RND.nextInt(3))))
        }

        tySeries1 = new XYSeries(tyData1, ChartType.LINE, Color.RED, Color.rgb(255, 0, 0, 0.5))
        tySeries1.setSymbolsVisible(false)

        // XYChart
        val tempConverter     = new Converter(TEMPERATURE, CELSIUS) // Type Temperature with BaseUnit Celsius
        val tempFahrenheitMin: Double = tempConverter.convert(0, FAHRENHEIT)
        val tempFahrenheitMax: Double = tempConverter.convert(20, FAHRENHEIT)

        xAxisBottom = createBottomTimeAxis(start, end, "HH:mm", true)
        yAxisLeft   = createLeftYAxis(0, 20, true)
        yAxisRight  = createRightYAxis(tempFahrenheitMin, tempFahrenheitMax, false)
        tyChart     = new XYChart[TYChartItem](new XYPane(tySeries1), yAxisLeft, yAxisRight, xAxisBottom)
        tyChart.setPrefSize(400, 200)
    }

    override def start(stage: Stage) = {
        val pane = new StackPane(tyChart)
        pane.setPadding(new Insets(10))

        val scene = new Scene(new StackPane(pane))

        stage.setTitle("TimeAxis Test")
        stage.setScene(scene)
        stage.show()
    }

    override def stop() = {
        System.exit(0)
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

        return axis
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
    private def createBottomTimeAxis(START: LocalDateTime, END: LocalDateTime, PATTERN: String, AUTO_SCALE: Boolean): Axis = {
        val axis = new Axis(START, END, Orientation.HORIZONTAL, Position.BOTTOM)
        axis.setDateTimeFormatPattern(PATTERN)
        axis.setPrefHeight(AXIS_WIDTH)

        AnchorPane.setBottomAnchor(axis, 0d)
        AnchorPane.setLeftAnchor(axis, 25d)
        AnchorPane.setRightAnchor(axis, 25d)

        axis
    }

    def launchIt():Unit = {
        Application.launch()
    }
}

// Not required, not needed
// object TimeAxisTest {
//   def main(args: Array[String]) =
//     val app = new TimeAxisTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object TimeAxisTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[TimeAxisTest], args*)
//     }
// }
