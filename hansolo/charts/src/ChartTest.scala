package hansolo.charts

// cSpell:ignore javafx, hansolo


import eu.hansolo.fx.charts.Axis
import eu.hansolo.fx.charts.ChartType
import eu.hansolo.fx.charts.Grid
import eu.hansolo.fx.charts.Position
import eu.hansolo.fx.charts.XYChart
import eu.hansolo.fx.charts.data.XYItem // TODO
import eu.hansolo.fx.charts.XYPane
import eu.hansolo.fx.charts.YPane
import eu.hansolo.fx.charts.data.XYChartItem
import eu.hansolo.fx.charts.data.XYZChartItem
import eu.hansolo.fx.charts.data.ValueChartItem
import eu.hansolo.fx.charts.series.XYSeries
import eu.hansolo.fx.charts.series.XYSeriesBuilder
import eu.hansolo.fx.charts.series.XYZSeries
import eu.hansolo.fx.charts.series.YSeries
import eu.hansolo.fx.charts.tools.Helper
import eu.hansolo.fx.charts.tools.LineStyle
import eu.hansolo.fx.charts.tools.Marker
import eu.hansolo.fx.charts.tools.MarkerBuilder
import eu.hansolo.toolbox.unit.Converter
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.collections.ObservableList
import javafx.geometry.Insets
import javafx.geometry.Orientation
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

import scala.compiletime.uninitialized
// import scala.collection.convert.ImplicitConversionsToScala.*


/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i hansolo.charts.run
 * ./mill -i hansolo.charts.runMain hansolo.charts.ChartTest
 * ./mill -i --watch hansolo.charts.runMain hansolo.charts.ChartTest
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
 * @see https://github.com/HanSolo/charts/blob/master/src/test/java/eu/hansolo/fx/charts/ChartTest.java
 */
class ChartTest extends Application {
    private val AXIS_WIDTH: Double     = 25d
    private val COLORS: Array[Color]   = Array( Color.rgb(200, 0, 0, 0.75), Color.rgb(0, 0, 200, 0.75), Color.rgb(0, 200, 200, 0.75), Color.rgb(0, 200, 0, 0.75) )
    private val RND: Random            = new Random()
    private val NO_OF_X_VALUES: Int    = 100
    private var xySeries1: XYSeries[XYChartItem] = uninitialized
    private var xySeries2: XYSeries[XYChartItem] = uninitialized
    private var xySeries3: XYSeries[XYChartItem] = uninitialized
    private var xySeries4: XYSeries[XYChartItem] = uninitialized
    private var xySeries5: XYSeries[XYChartItem] = uninitialized

    private var lineChart:            XYChart[XYChartItem] = uninitialized
    private var lineChartXAxisBottom: Axis                 = uninitialized
    private var lineChartYAxisLeft:   Axis                 = uninitialized
    private var lineChartYAxisRight:  Axis                 = uninitialized

    private var areaChart:            XYChart[XYChartItem] = uninitialized
    private var areaChartXAxisBottom: Axis                 = uninitialized
    private var areaChartYAxisLeft:   Axis                 = uninitialized

    private var smoothLineChart:             XYChart[XYChartItem] = uninitialized
    private var smoothLineChartXAxisBottom : Axis                 = uninitialized
    private var smoothLineChartYAxisLeft :   Axis                 = uninitialized

    private var smoothAreaChart:             XYChart[XYChartItem] = uninitialized
    private var smoothAreaChartXAxisBottom : Axis                 = uninitialized
    private var smoothAreaChartYAxisLeft :   Axis                 = uninitialized

    private var scatterChart:            XYChart[XYChartItem] = uninitialized
    private var scatterChartXAxisBottom: Axis                 = uninitialized
    private var scatterChartXAxisCenter: Axis                 = uninitialized
    private var scatterChartYAxisLeft:   Axis                 = uninitialized
    private var scatterChartYAxisCenter: Axis                 = uninitialized

    private var ySeries: YSeries[ValueChartItem]  = uninitialized
    private var donutChart: YPane[ValueChartItem] = uninitialized

    private var xyzSeries: XYZSeries[XYZChartItem] = uninitialized

    private var lastTimerCall: Long   = uninitialized
    private var timer: AnimationTimer = uninitialized


    override def init() = {
        val xyItems1: List[XYChartItem]     = new ArrayList[XYChartItem](20)
        val xyItems2: List[XYChartItem]     = new ArrayList[XYChartItem](20)
        val xyItems3: List[XYChartItem]     = new ArrayList[XYChartItem](20)
        val xyItems4: List[XYChartItem]     = new ArrayList[XYChartItem](40)
        val yItem:    List[ValueChartItem]  = new ArrayList[ValueChartItem](20)
        val xyzItem:  List[XYZChartItem]    = new ArrayList[XYZChartItem](20)
        for (i <- 0 until NO_OF_X_VALUES) {
            xyItems1.add(new XYChartItem(i, RND.nextDouble() * 15, "P" + i, COLORS(RND.nextInt(3)), "P" + i))
            xyItems2.add(new XYChartItem(i, RND.nextDouble() * 15, "P" + i, COLORS(RND.nextInt(3)), "P" + i))
            xyItems3.add(new XYChartItem(i, RND.nextDouble() * 15, "P" + i, COLORS(RND.nextInt(3)), "P" + i))
        }
        for (i <- 0  until 20) {
            yItem.add(new ValueChartItem(RND.nextDouble() * 10, "P" + i, COLORS(RND.nextInt(3))))
            xyzItem.add(new XYZChartItem(RND.nextDouble() * 10, RND.nextDouble() * 10, RND.nextDouble() * 25, "P" + i, COLORS(RND.nextInt(3))))
        }
        for (i <- -20 until 20) {
            xyItems4.add(new XYChartItem(i, RND.nextDouble() * 40 - 20, "P" + i, COLORS(RND.nextInt(3)), "P" + i))
        }

        xySeries1 = XYSeriesBuilder.create()
                                   .items(xyItems1.asInstanceOf[List[XYItem]]) // TODO
                                   .chartType(ChartType.LINE)
                                   .fill(Color.TRANSPARENT)
                                   .stroke(Color.MAGENTA)
                                   .symbolFill(Color.RED)
                                   .symbolStroke(Color.TRANSPARENT)
                                   .symbolsVisible(true)
                                   .build()
                                   .asInstanceOf[XYSeries[XYChartItem]] // TODO

        xySeries2 = XYSeriesBuilder.create()
                                   .items(xyItems2.asInstanceOf[List[XYItem]]) // TODO
                                   .chartType(ChartType.AREA)
                                   .fill(Color.TRANSPARENT)
                                   .stroke(Color.BLUE)
                                   .symbolFill(Color.BLUE)
                                   .symbolStroke(Color.TRANSPARENT)
                                   .symbolsVisible(true)
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


        ySeries    = new YSeries(yItem, ChartType.DONUT)
        donutChart = new YPane(ySeries)

        xyzSeries   = new XYZSeries(xyzItem, ChartType.BUBBLE)

        // LineChart
        val tempConverter:     Converter = new Converter(TEMPERATURE, CELSIUS) // Type Temperature with BaseUnit Celsius
        val tempFahrenheitMin: Double    = tempConverter.convert(-10, FAHRENHEIT)
        val tempFahrenheitMax: Double    = tempConverter.convert(20, FAHRENHEIT)

        lineChartXAxisBottom = Helper.createBottomAxis(-10, NO_OF_X_VALUES, true, AXIS_WIDTH)
        lineChartYAxisLeft   = Helper.createLeftAxis(-10, 20, true, AXIS_WIDTH)
        lineChartYAxisRight  = Helper.createRightAxis(tempFahrenheitMin, tempFahrenheitMax, false, AXIS_WIDTH)

        lineChartXAxisBottom.setZeroColor(Color.BLACK)
        lineChartYAxisLeft.setZeroColor(Color.BLACK)

        lineChart = new XYChart[XYChartItem](new XYPane(xySeries2, xySeries1), lineChartYAxisLeft, lineChartYAxisRight, lineChartXAxisBottom)
        lineChart.getXYPane().setCrossHairVisible(true)


        val grid1: Grid = new Grid(lineChartXAxisBottom, lineChartYAxisLeft)
        lineChart.setGrid(grid1)

        val marker1: Marker = new Marker(lineChartYAxisLeft, 5, Color.RED, 3, LineStyle.SOLID, "Marker 1", Color.RED, "%.0f")
        lineChart.addMarker(marker1)

        val marker2: Marker = MarkerBuilder.create(lineChartXAxisBottom, 20).stroke(Color.BLUE).lineWidth(3).lineStyle(LineStyle.DOTTED).text("Marker 2").textFill(Color.BLUE).fromatString("%.0f").build()
        lineChart.addMarker(marker2)

        // AreaChart
        areaChartXAxisBottom = Helper.createBottomAxis(0, NO_OF_X_VALUES, true, AXIS_WIDTH)
        areaChartYAxisLeft   = Helper.createLeftAxis(0, 20, true, AXIS_WIDTH)
        areaChart            = new XYChart[XYChartItem](new XYPane(xySeries2), areaChartXAxisBottom, areaChartYAxisLeft)
        areaChart.getXYPane().setCrossHairVisible(true)

        xySeries2.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop(0.0, Color.rgb(0, 0, 255, 0.75)), new Stop(1.0, Color.rgb(0, 255, 255, 0.25))))
        xySeries2.setStroke(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop(0.0, Color.rgb(0, 0, 255, 1.0)), new Stop(1.0, Color.rgb(0, 255, 255, 1.0))))
        areaChart.getXYPane().setChartBackground(Color.rgb(50, 50, 50, 0.5))

        // SmoothLineChart
        smoothLineChartXAxisBottom = Helper.createBottomAxis(0, NO_OF_X_VALUES, true, AXIS_WIDTH)
        smoothLineChartYAxisLeft   = Helper.createLeftAxis(0, 20, true, AXIS_WIDTH)
        smoothLineChart            = new XYChart[XYChartItem](new XYPane(xySeries3), smoothLineChartYAxisLeft, smoothLineChartXAxisBottom)

        val grid2: Grid = new Grid(smoothLineChartXAxisBottom, smoothLineChartYAxisLeft)
        smoothLineChart.setGrid(grid2)

        // SmoothAreaChart
        smoothAreaChartXAxisBottom = Helper.createBottomAxis(0, NO_OF_X_VALUES, true, AXIS_WIDTH)
        smoothAreaChartYAxisLeft   = Helper.createLeftAxis(0, 20, true, AXIS_WIDTH)
        smoothAreaChart            = new XYChart[XYChartItem](new XYPane(xySeries4), smoothAreaChartYAxisLeft, smoothAreaChartXAxisBottom)

        xySeries4.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop(0.0, Color.rgb(255, 255, 255, 0.6)), new Stop(1.0, Color.TRANSPARENT)))
        xySeries4.setStroke(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop(0.0, Color.rgb(255, 255, 255, 1.0)), new Stop(1.0, Color.TRANSPARENT)))
        smoothAreaChart.getXYPane().setChartBackground(Color.rgb(25, 25, 25, 0.8))
        smoothAreaChart.getXYPane().setCrossHairVisible(true)


        // ScatterChart
        scatterChartXAxisBottom = Helper.createAxis(-20, 20, true, AXIS_WIDTH, Orientation.HORIZONTAL, Position.BOTTOM)
        scatterChartXAxisCenter = Helper.createCenterXAxis(-20, 20, true, AXIS_WIDTH)
        scatterChartYAxisLeft   = Helper.createAxis(-20, 20, true, AXIS_WIDTH, Orientation.VERTICAL, Position.LEFT)
        scatterChartYAxisCenter = Helper.createCenterYAxis(-20, 20, true, AXIS_WIDTH)
        scatterChart            = new XYChart[XYChartItem](new XYPane(xySeries5), scatterChartYAxisCenter, scatterChartXAxisCenter)
        scatterChart.getXYPane().setCrossHairVisible(true)

        scatterChartXAxisCenter.setAxisColor(Color.CRIMSON)
        scatterChartYAxisCenter.setAxisColor(Color.CRIMSON)

        lastTimerCall = System.nanoTime()
        timer = new AnimationTimer() {
            override def handle(now: Long) = {
                if (now > lastTimerCall + 1_000_000_000l) {
                    var xyItems: ObservableList[XYChartItem] = xySeries1.getItems()
                    xyItems.forEach(item => item.setY(RND.nextDouble() * 20))

                    xyItems = xySeries2.getItems()
                    xyItems.forEach(item => item.setY(RND.nextDouble() * 15))

                    //xyItems = xySeries3.getItems()
                    //xyItems.forEach(item -> item.setY(RND.nextDouble() * 15))

                    xyItems = xySeries4.getItems()
                    xyItems.forEach(item => item.setY(RND.nextDouble() * 15))

                    val yItems: ObservableList[ValueChartItem] = ySeries.getItems()
                    yItems.forEach(item => item.setValue(RND.nextDouble() * 20))

                    val xyzItems: ObservableList[XYZChartItem] = xyzSeries.getItems()
                    xyzItems.forEach(item => item.setZ(RND.nextDouble() * 25))

                    xySeries1.refresh()
                    xySeries2.refresh()
                    //xySeries3.refresh()
                    xySeries4.refresh()
                    ySeries.refresh()
                    xyzSeries.refresh()

                    lastTimerCall = now
                }
            }
        }
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
        gridPane.add(scatterChart, 0, 2)
        gridPane.add(donutChart, 1, 2)

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
// object ChartTest {
//   def main(args: Array[String]) =
//     val app = new ChartTest
//     app.launchIt()
// 
// }

// Not required, not needed
object ChartTest {

    def main(args: Array[String]): Unit = {
    Application.launch(classOf[ChartTest], args*)
    }
}
