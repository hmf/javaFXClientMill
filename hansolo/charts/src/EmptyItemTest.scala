package hansolo.charts

// cSpell:ignore javafx, hansolo


import eu.hansolo.fx.charts.Axis
import eu.hansolo.fx.charts.ChartType
import eu.hansolo.fx.charts.Grid
import eu.hansolo.fx.charts.XYChart
import eu.hansolo.fx.charts.data.XYItem // TODO
import eu.hansolo.fx.charts.XYPane
import eu.hansolo.fx.charts.data.ValueChartItem
import eu.hansolo.fx.charts.data.XYChartItem
import eu.hansolo.fx.charts.data.XYZChartItem
import eu.hansolo.fx.charts.series.XYSeries
import eu.hansolo.fx.charts.series.XYSeriesBuilder
import eu.hansolo.fx.charts.tools.Helper
import eu.hansolo.toolbox.unit.Converter
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.layout.GridPane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.stage.Stage

import java.util.ArrayList
import java.util.List
import java.util.Random

import eu.hansolo.toolbox.unit.Category.TEMPERATURE
import eu.hansolo.toolbox.unit.UnitDefinition.CELSIUS
import eu.hansolo.toolbox.unit.UnitDefinition.FAHRENHEIT

// import scala.util
import scala.compiletime.uninitialized
import scala.util.boundary, boundary.break
// import scala.collection.convert.ImplicitConversionsToScala.*


/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * X ./mill -i hansolo.charts.run
 * ./mill -i hansolo.charts.runMain hansolo.charts.EmptyItemTest
 * ./mill -i --watch hansolo.charts.runMain hansolo.charts.EmptyItemTest
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
 * @see https://github.com/HanSolo/charts/blob/master/src/test/java/eu/hansolo/fx/charts/EmptyItemTest.java
 */
class EmptyItemTest extends Application {
    private val AXIS_WIDTH: Double     = 25d
    private val COLORS: Array[Color]   = Array( Color.rgb(200, 0, 0, 0.75), Color.rgb(0, 0, 200, 0.75), Color.rgb(0, 200, 200, 0.75), Color.rgb(0, 200, 0, 0.75) )
    private val RND: Random            = new Random()
    private val NO_OF_X_VALUES: Int    = 10
    private var xySeries1: XYSeries[XYChartItem] = uninitialized
    private var xySeries2: XYSeries[XYChartItem] = uninitialized
    private var xySeries3: XYSeries[XYChartItem] = uninitialized
    private var xySeries4: XYSeries[XYChartItem] = uninitialized
    private var xySeries5: XYSeries[XYChartItem] = uninitialized

    private var lineChart: XYChart[XYChartItem] = uninitialized
    private var lineChartXAxisBottom : Axis = uninitialized
    private var lineChartYAxisLeft   : Axis = uninitialized
    private var lineChartYAxisRight  : Axis = uninitialized

    private var areaChart: XYChart[XYChartItem] = uninitialized
    private var areaChartXAxisBottom: Axis = uninitialized
    private var areaChartYAxisLeft  : Axis = uninitialized

    private var smoothLineChart: XYChart[XYChartItem] = uninitialized
    private var smoothLineChartXAxisBottom: Axis = uninitialized
    private var smoothLineChartYAxisLeft  : Axis = uninitialized

    private var smoothAreaChart: XYChart[XYChartItem] = uninitialized
    private var smoothAreaChartXAxisBottom: Axis = uninitialized
    private var smoothAreaChartYAxisLeft  : Axis = uninitialized


    override def init() = {
        val xyItems1: List[XYChartItem] = new ArrayList[XYChartItem](20)
        val xyItems2: List[XYChartItem] = new ArrayList[XYChartItem](20)
        val xyItems3: List[XYChartItem] = new ArrayList[XYChartItem](20)
        val xyItems4: List[XYChartItem] = new ArrayList[XYChartItem](40)
        val yItem: List[ValueChartItem] = new ArrayList[ValueChartItem](20)
        val xyzItem: List[XYZChartItem] = new ArrayList[XYZChartItem](20)
        for (i <- 0 until NO_OF_X_VALUES) {
            if (i == 4) {
                xyItems1.add(new XYChartItem(i, RND.nextDouble() * 15, "P" + i, COLORS(RND.nextInt(3)), "P" + i, true))
                xyItems2.add(new XYChartItem(i, RND.nextDouble() * 15, "P" + i, COLORS(RND.nextInt(3)), "P" + i, true))
                xyItems3.add(new XYChartItem(i, RND.nextDouble() * 15, "P" + i, COLORS(RND.nextInt(3)), "P" + i, true))
            } else {
                xyItems1.add(new XYChartItem(i, RND.nextDouble() * 15, "P" + i, COLORS(RND.nextInt(3)), "P" + i))
                xyItems2.add(new XYChartItem(i, RND.nextDouble() * 15, "P" + i, COLORS(RND.nextInt(3)), "P" + i))
                xyItems3.add(new XYChartItem(i, RND.nextDouble() * 15, "P" + i, COLORS(RND.nextInt(3)), "P" + i))
            }
        }
        for (i <- 0 until 20) {
            yItem.add(new ValueChartItem(RND.nextDouble() * 10, "P" + i, COLORS(RND.nextInt(3))))
            xyzItem.add(new XYZChartItem(RND.nextDouble() * 10, RND.nextDouble() * 10, RND.nextDouble() * 25, "P" + i, COLORS(RND.nextInt(3))))
        }
        for (i <- -20  until 20) {
            xyItems4.add(new XYChartItem(i, RND.nextDouble() * 40 - 20, "P" + i, COLORS(RND.nextInt(3))))
        }

        xySeries1 = XYSeriesBuilder.create()
                                   .items(xyItems1.asInstanceOf[List[XYItem]]) // TODO
                                   .chartType(ChartType.LINE)
                                   .fill(Color.TRANSPARENT)
                                   .stroke(Color.MAGENTA)
                                   .symbolFill(Color.RED)
                                   .symbolStroke(Color.TRANSPARENT)
                                   .symbolsVisible(true)
                                   .symbolSize(10)
                                   .build()
                                   .asInstanceOf[XYSeries[XYChartItem]] // TODO

        xySeries2 = XYSeriesBuilder.create()
                                   .items(xyItems2.asInstanceOf[List[XYItem]]) // TODO)
                                   .chartType(ChartType.AREA)
                                   .fill(Color.TRANSPARENT)
                                   .stroke(Color.BLUE)
                                   .symbolFill(Color.BLUE)
                                   .symbolStroke(Color.TRANSPARENT)
                                   .symbolsVisible(true)
                                   .symbolSize(10)
                                   .build()
                                   .asInstanceOf[XYSeries[XYChartItem]] // TODO

        xySeries3 = new XYSeries[XYChartItem](xyItems3, ChartType.SMOOTH_LINE)
        xySeries4 = new XYSeries[XYChartItem](xyItems1, ChartType.SMOOTH_AREA)

        xySeries3.setSymbolFill(Color.LIME)
        xySeries4.setSymbolFill(Color.MAGENTA)

        xySeries3.setSymbolStroke(Color.TRANSPARENT)
        xySeries4.setSymbolStroke(Color.TRANSPARENT)

        xySeries5 = XYSeriesBuilder.create()
                                   .items(xyItems4.asInstanceOf[List[XYItem]]) // TODO)
                                   .chartType(ChartType.SCATTER)
                                   .fill(Color.TRANSPARENT)
                                   .stroke(Color.MAGENTA)
                                   .symbolFill(Color.RED)
                                   .symbolStroke(Color.TRANSPARENT)
                                   .symbolsVisible(true)
                                   .build()
                                   .asInstanceOf[XYSeries[XYChartItem]] // TODO

        // LineChart
        val tempConverter     = new Converter(TEMPERATURE, CELSIUS) // Type Temperature with BaseUnit Celsius
        val tempFahrenheitMin: Double = tempConverter.convert(-10, FAHRENHEIT)
        val tempFahrenheitMax: Double = tempConverter.convert(20, FAHRENHEIT)

        lineChartXAxisBottom = Helper.createBottomAxis(-10, NO_OF_X_VALUES, true, AXIS_WIDTH)
        lineChartYAxisLeft   = Helper.createLeftAxis(-10, 20, true, AXIS_WIDTH)
        lineChartYAxisRight  = Helper.createRightAxis(tempFahrenheitMin, tempFahrenheitMax, false, AXIS_WIDTH)

        lineChartXAxisBottom.setZeroColor(Color.BLACK)
        lineChartYAxisLeft.setZeroColor(Color.BLACK)

        lineChart = new XYChart[XYChartItem](new XYPane(xySeries2, xySeries1), lineChartYAxisLeft, lineChartYAxisRight, lineChartXAxisBottom)

        val grid1 = new Grid(lineChartXAxisBottom, lineChartYAxisLeft)
        lineChart.setGrid(grid1)


        // AreaChart
        areaChartXAxisBottom = Helper.createBottomAxis(0, NO_OF_X_VALUES, true, AXIS_WIDTH)
        areaChartYAxisLeft   = Helper.createLeftAxis(0, 20, true, AXIS_WIDTH)
        areaChart            = new XYChart[XYChartItem](new XYPane(xySeries2), areaChartXAxisBottom, areaChartYAxisLeft)

        xySeries2.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop(0.0, Color.rgb(0, 0, 255, 0.75)), new Stop(1.0, Color.rgb(0, 255, 255, 0.25))))
        xySeries2.setStroke(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop(0.0, Color.rgb(0, 0, 255, 1.0)), new Stop(1.0, Color.rgb(0, 255, 255, 1.0))))
        areaChart.getXYPane().setChartBackground(Color.rgb(50, 50, 50, 0.5))

        // SmoothLineChart
        smoothLineChartXAxisBottom = Helper.createBottomAxis(0, NO_OF_X_VALUES, true, AXIS_WIDTH)
        smoothLineChartYAxisLeft   = Helper.createLeftAxis(0, 20, true, AXIS_WIDTH)
        smoothLineChart            = new XYChart[XYChartItem](new XYPane(xySeries3), smoothLineChartYAxisLeft, smoothLineChartXAxisBottom)

        val grid2 = new Grid(smoothLineChartXAxisBottom, smoothLineChartYAxisLeft)
        smoothLineChart.setGrid(grid2)

        // SmoothAreaChart
        smoothAreaChartXAxisBottom = Helper.createBottomAxis(0, NO_OF_X_VALUES, true, AXIS_WIDTH)
        smoothAreaChartYAxisLeft   = Helper.createLeftAxis(0, 20, true, AXIS_WIDTH)
        smoothAreaChart            = new XYChart[XYChartItem](new XYPane(xySeries4), smoothAreaChartYAxisLeft, smoothAreaChartXAxisBottom)

        xySeries4.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop(0.0, Color.rgb(255, 255, 255, 0.6)), new Stop(1.0, Color.TRANSPARENT)))
        xySeries4.setStroke(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop(0.0, Color.rgb(255, 255, 255, 1.0)), new Stop(1.0, Color.TRANSPARENT)))
        smoothAreaChart.getXYPane().setChartBackground(Color.rgb(25, 25, 25, 0.8))

    }

    override def start(stage: Stage) = {
        val gridPane = new GridPane()
        gridPane.setPadding(new Insets(10))
        gridPane.setHgap(10)
        gridPane.setVgap(10)
        gridPane.add(lineChart, 0, 0)
        gridPane.add(areaChart, 1, 0)
        gridPane.add(smoothLineChart, 0, 1)
        gridPane.add(smoothAreaChart, 1, 1)

        val scene = new Scene(new StackPane(gridPane))

        stage.setTitle("Charts")
        stage.setScene(scene)
        stage.show()

        //timer.start()

        //modificationThread.start()
    }

    override def stop() = {
        System.exit(0)
    }
    
    def launchIt():Unit = {
        Application.launch()
    }

}

// Not required, not needed
// object EmptyItemTest {
//   def main(args: Array[String]) =
//     val app = new EmptyItemTest
//     app.launchIt()
// 
// }

// Not required, not needed
object EmptyItemTest {

    def main(args: Array[String]): Unit = {
    Application.launch(classOf[EmptyItemTest], args*)
    }
}
