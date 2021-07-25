package hansolo.charts

// cSpell:ignore javafx, hansolo

import eu.hansolo.fx.charts.data.XYChartItem
import eu.hansolo.fx.charts.series.XYSeries
import eu.hansolo.fx.charts.series.XYSeriesBuilder

import eu.hansolo.fx.charts.ChartType
import eu.hansolo.fx.charts.AxisBuilder
import eu.hansolo.fx.charts.Position
import eu.hansolo.fx.charts.AxisType
import eu.hansolo.fx.charts.GridBuilder
import eu.hansolo.fx.charts.XYPane
import eu.hansolo.fx.charts.XYChart
import eu.hansolo.fx.charts.Axis

import eu.hansolo.fx.charts.data.XYItem

import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.scene.layout.StackPane
import javafx.scene.Scene


/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i hanSoloCharts.run
 * ./mill -i hanSoloCharts.runMain hansolo.charts.LineChartTest
 * ./mill -i --watch hanSoloCharts.runMain hansolo.charts.LineChartTest
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
class LineChartTest extends Application {
    val AXIS_WIDTH = 25d

    // NOTE: we have to add `.asInstanceOf[XYSeries[XYChartItem]]` because the 
    // builder uses the equivalent Java F-bounded type, but does not use a higher
    // kinded  type that holds the `T <: XYItem` contained in the `XYSeries`. This
    // means Scala does not know the type and the call to 
    // `new XYPane[XYChartItem](xySeries1, xySeries2)` will fail (the contained
    // type is not a `XYItem`)

    val xySeries1 = XYSeriesBuilder.create()
                                   .items(new XYChartItem(1, 600, "Jan"),
                                          new XYChartItem(2, 760, "Feb"),
                                          new XYChartItem(3, 585, "Mar"),
                                          new XYChartItem(4, 410, "Apr"),
                                          new XYChartItem(5, 605, "May"),
                                          new XYChartItem(6, 825, "Jun"),
                                          new XYChartItem(7, 595, "Jul"),
                                          new XYChartItem(8, 300, "Aug"),
                                          new XYChartItem(9, 515, "Sep"),
                                          new XYChartItem(10, 780, "Oct"),
                                          new XYChartItem(11, 570, "Nov"),
                                          new XYChartItem(12, 620, "Dec"))
                                   .chartType(ChartType.SMOOTH_AREA)
                                   .fill(Color.web("#00AEF520"))
                                   .stroke(Color.web("#00AEF5"))
                                   .symbolFill(Color.web("#00AEF5"))
                                   .symbolStroke(Color.web("#293C47"))
                                   .symbolSize(10)
                                   .strokeWidth(3)
                                   .symbolsVisible(true)
                                   .build()
                                   .asInstanceOf[XYSeries[XYChartItem]]

    val xySeries2 = XYSeriesBuilder.create()
                                   .items(new XYChartItem(1, 280, "Jan"),
                                          new XYChartItem(2, 190, "Feb"),
                                          new XYChartItem(3, 280, "Mar"),
                                          new XYChartItem(4, 300, "Apr"),
                                          new XYChartItem(5, 205, "May"),
                                          new XYChartItem(6, 430, "Jun"),
                                          new XYChartItem(7, 380, "Jul"),
                                          new XYChartItem(8, 180, "Aug"),
                                          new XYChartItem(9, 300, "Sep"),
                                          new XYChartItem(10, 440, "Oct"),
                                          new XYChartItem(11, 300, "Nov"),
                                          new XYChartItem(12, 390, "Dec"))
                                   .chartType(ChartType.SMOOTH_AREA)
                                   .fill(Color.web("#4EE29B20"))
                                   .stroke(Color.web("#4EE29B"))
                                   .symbolFill(Color.web("#4EE29B"))
                                   .symbolStroke(Color.web("#293C47"))
                                   .symbolSize(10)
                                   .strokeWidth(3)
                                   .symbolsVisible(true)
                                   .build()
                                   .asInstanceOf[XYSeries[XYChartItem]]

    val xAxisBottom = AxisBuilder.create(Orientation.HORIZONTAL, Position.BOTTOM)
                                 .`type`(AxisType.TEXT)
                                 .prefHeight(AXIS_WIDTH)
                                 .categories("", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
                                 .minValue(1)
                                 .maxValue(13)
                                 .autoScale(true)
                                 .axisColor(Color.web("#85949B"))
                                 .tickLabelColor(Color.web("#85949B"))
                                 .tickMarkColor(Color.web("#85949B"))
                                 //.tickMarksVisible(false)
                                 .build()

        AnchorPane.setBottomAnchor(xAxisBottom, 0d)
        AnchorPane.setLeftAnchor(xAxisBottom, AXIS_WIDTH)
        AnchorPane.setRightAnchor(xAxisBottom, AXIS_WIDTH)

    val yAxisLeft = AxisBuilder.create(Orientation.VERTICAL, Position.LEFT)
                               .`type`(AxisType.LINEAR)
                               .prefWidth(AXIS_WIDTH)
                               .minValue(0)
                               .maxValue(1000)
                               .autoScale(true)
                               .axisColor(Color.web("#85949B"))
                               .tickLabelColor(Color.web("#85949B"))
                               .tickMarkColor(Color.web("#85949B"))
                               //.tickMarksVisible(false)
                               .build()
        AnchorPane.setTopAnchor(yAxisLeft, 0d)
        AnchorPane.setBottomAnchor(yAxisLeft, AXIS_WIDTH)
        AnchorPane.setLeftAnchor(yAxisLeft, 0d)

    val grid = GridBuilder.create(xAxisBottom, yAxisLeft)
                               .gridLinePaint(Color.web("#384C57"))
                               .minorHGridLinesVisible(false)
                               .mediumHGridLinesVisible(false)
                               .minorVGridLinesVisible(false)
                               .mediumVGridLinesVisible(false)
                               .gridLineDashes(4, 4)
                               .build()

    val lineChartPane = new XYPane[XYChartItem](xySeries1, xySeries2)
    val lineChart: XYChart[XYChartItem] = new XYChart[XYChartItem](lineChartPane, grid, yAxisLeft, xAxisBottom)

    override def start(stage: Stage) = {
        val pane = new StackPane(lineChart)
        pane.setPadding(new Insets(10))
        pane.setBackground(new Background(new BackgroundFill(Color.web("#293C47"), CornerRadii.EMPTY, Insets.EMPTY)))

        val scene = new Scene(pane)

        stage.setTitle("Line Chart")
        stage.setScene(scene)
        stage.show()
    }

    override def stop() = {
        System.exit(0)
    }

    private def createLeftYAxis(MIN: Double, MAX: Double, AUTO_SCALE: Boolean, AXIS_WIDTH: Double): Axis = {
        val axis = new Axis(Orientation.VERTICAL, Position.LEFT)
        axis.setMinValue(MIN)
        axis.setMaxValue(MAX)
        axis.setPrefWidth(AXIS_WIDTH)
        axis.setAutoScale(AUTO_SCALE)

        AnchorPane.setTopAnchor(axis, 0d)
        AnchorPane.setBottomAnchor(axis, AXIS_WIDTH)
        AnchorPane.setLeftAnchor(axis, 0d)

        axis
    }

    private def createCenterYAxis(MIN: Double, MAX: Double, AUTO_SCALE: Boolean, AXIS_WIDTH: Double): Axis = {
        val axis = new Axis(Orientation.VERTICAL, Position.CENTER)
        axis.setMinValue(MIN)
        axis.setMaxValue(MAX)
        axis.setPrefWidth(AXIS_WIDTH)
        axis.setAutoScale(AUTO_SCALE)

        AnchorPane.setTopAnchor(axis, 0d)
        AnchorPane.setBottomAnchor(axis, AXIS_WIDTH)
        AnchorPane.setLeftAnchor(axis, axis.getZeroPosition())

        axis
    }

    private def createRightYAxis(MIN: Double, MAX: Double, AUTO_SCALE: Boolean, AXIS_WIDTH: Double): Axis = {
        val axis = new Axis(Orientation.VERTICAL, Position.RIGHT)
        axis.setMinValue(MIN)
        axis.setMaxValue(MAX)
        axis.setPrefWidth(AXIS_WIDTH)
        axis.setAutoScale(AUTO_SCALE)

        AnchorPane.setRightAnchor(axis, 0d)
        AnchorPane.setTopAnchor(axis, 0d)
        AnchorPane.setBottomAnchor(axis, AXIS_WIDTH)

        axis
    }

    private def createBottomXAxis(MIN: Double, MAX: Double, AUTO_SCALE: Boolean, AXIS_WIDTH: Double): Axis = {
        val axis = new Axis(Orientation.HORIZONTAL, Position.BOTTOM)
        axis.setMinValue(MIN)
        axis.setMaxValue(MAX)
        axis.setPrefHeight(AXIS_WIDTH)
        axis.setAutoScale(AUTO_SCALE)

        AnchorPane.setBottomAnchor(axis, 0d)
        AnchorPane.setLeftAnchor(axis, AXIS_WIDTH)
        AnchorPane.setRightAnchor(axis, AXIS_WIDTH)

        axis
    }

    private def createCenterXAxis(MIN: Double, MAX: Double, AUTO_SCALE: Boolean, AXIS_WIDTH: Double): Axis = {
        val axis = new Axis(Orientation.HORIZONTAL, Position.CENTER)
        axis.setMinValue(MIN)
        axis.setMaxValue(MAX)
        axis.setPrefHeight(AXIS_WIDTH)
        axis.setAutoScale(AUTO_SCALE)

        AnchorPane.setBottomAnchor(axis, axis.getZeroPosition())
        AnchorPane.setLeftAnchor(axis, AXIS_WIDTH)
        AnchorPane.setRightAnchor(axis, AXIS_WIDTH)

        axis
    }

    private def createTopXAxis(MIN: Double, MAX: Double, AUTO_SCALE: Boolean, AXIS_WIDTH: Double): Axis = {
        val axis = new Axis(Orientation.HORIZONTAL, Position.TOP)
        axis.setMinValue(MIN)
        axis.setMaxValue(MAX)
        axis.setPrefHeight(AXIS_WIDTH)
        axis.setAutoScale(AUTO_SCALE)

        AnchorPane.setTopAnchor(axis, AXIS_WIDTH)
        AnchorPane.setLeftAnchor(axis, AXIS_WIDTH)
        AnchorPane.setRightAnchor(axis, AXIS_WIDTH)

        return axis
    }

    def launchIt():Unit = {
        Application.launch()
    }
}

object LineChartTest {
  def main(args: Array[String]) =
    val app = new LineChartTest
    app.launchIt()

}