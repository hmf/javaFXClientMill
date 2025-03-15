// cSpell:ignore javafx, hansolo

package hansolo.charts

import eu.hansolo.fx.charts.data.TYChartItem
import eu.hansolo.fx.charts.data.TYChartItem
import eu.hansolo.fx.charts.data.XYChartItem
import eu.hansolo.fx.charts.series.XYSeries
import eu.hansolo.fx.charts.series.XYSeriesBuilder
import eu.hansolo.fx.charts.tools.Helper
import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.stage.Stage

import eu.hansolo.fx.charts.Position // TODO
import eu.hansolo.fx.charts.AxisBuilder // TODO
import eu.hansolo.fx.charts.Prediction // TODO
import eu.hansolo.fx.charts.XYPane // TODO
import eu.hansolo.fx.charts.Axis // TODO
import eu.hansolo.fx.charts.AxisType // TODO
import eu.hansolo.fx.charts.XYChart // TODO
import eu.hansolo.fx.charts.Grid // TODO
import eu.hansolo.fx.charts.data.XYItem // TODO
import eu.hansolo.fx.charts.ChartType // TODO


import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.ArrayList
import java.util.Arrays
import java.util.List

import scala.compiletime.uninitialized
import scala.jdk.CollectionConverters.*
//import scala.collection.JavaConverters.*
import java.{util => ju}

/**
 * 
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i hansolo.timeSeries.run
 * ./mill -i hansolo.timeSeries.runMain hansolo.charts.TimeSeriesPredictionTest
 * ./mill -i --watch hansolo.timeSeries.runMain hansolo.charts.TimeSeriesPredictionTest
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
class TimeSeriesPredictionTest extends Application {
    private var timeSeriesPredictionChart: XYChart[TYChartItem] = uninitialized

    override def init() = {
        val timeStep: ChronoUnit = ChronoUnit.MONTHS

        // Target data
        val targetStart: LocalDateTime           = LocalDateTime.of(1949, 1, 1, 0, 0, 0, 0)
        val targetDataList: List[Double]         = getDataFromTextfile(getClass().getResource("/target_timeseries.txt").toExternalForm().replaceAll("file:", ""))

        // Prediction data
        val predictionStart: LocalDateTime       = LocalDateTime.of(1960, 1, 1, 0, 0, 0, 0)
        val predictionDataList: List[Double]     = getDataFromTextfile(getClass().getResource("/prediction_timeseries.txt").toExternalForm().replaceAll("file:", ""))

        // Quantile 50 data
        val quantile50DataList: List[Double]     = getDataFromTextfile(getClass().getResource("/quantile_50_timeseries.txt").toExternalForm().replaceAll("file:", ""))

        // Quantile 90 data
        val quantile90DataList: List[Double]     = getDataFromTextfile(getClass().getResource("/quantile_90_timeseries.txt").toExternalForm().replaceAll("file:", ""))

        // Target series
        val targetSeries: XYSeries[TYChartItem]  = createSeries(ChartType.LINE, targetStart, timeStep, Color.TRANSPARENT, Color.BLACK, targetDataList)

        // Create prediction
        val prediction: Prediction[TYChartItem]  = createPredication(predictionStart, timeStep, Color.RED, predictionDataList, quantile50DataList, quantile90DataList)

        timeSeriesPredictionChart = createChart(targetStart, timeStep, targetSeries, List.of(prediction))

        // Render chart to image and save it to given filename
        //timeSeriesPredictionChart.renderToImage("predictionChart.png", 900, 400)
    }

    override def start(stage: Stage) = {
        val pane: StackPane = new StackPane(timeSeriesPredictionChart)
        pane.setPadding(new Insets(10))

        val scene: Scene = new Scene(new StackPane(pane), 900, 400)

        stage.setTitle("TimeSeries with prediction")
        stage.setScene(scene)
        stage.show()
    }


    private def createChart(targetStart: LocalDateTime, timeStep: ChronoUnit, targetSeries: XYSeries[TYChartItem], predictions:List[Prediction[TYChartItem]]): XYChart[TYChartItem] = {
        // PredictionTimeSeriesChart
        val listOfSeries: List[XYSeries[TYChartItem]] = new ArrayList[XYSeries[TYChartItem]]()
        listOfSeries.add(targetSeries)
        predictions.forEach(prediction => {
            listOfSeries.add(prediction.getPredictionSeries())
            listOfSeries.add(prediction.getQuantile50Series())
            listOfSeries.add(prediction.getQuantile90Series())
        })

        // Calculate min,max values for y-axis
        var yAxisMinValue: Double = java.lang.Double.MAX_VALUE
        var yAxisMaxValue: Double = java.lang.Double.MIN_VALUE
        for 
            series: XYSeries[TYChartItem] <- listOfSeries.asScala
            i <- 0 until series.getItems().size()
        do
            val item: XYChartItem = series.getItems().get(i)
            yAxisMinValue = Math.min(yAxisMinValue, item.getY())
            yAxisMaxValue = Math.max(yAxisMaxValue, item.getY())

        val axisWidth: Double = 25d
        val xAxis:Axis = AxisBuilder.create(Orientation.HORIZONTAL, Position.BOTTOM)
                                .`type`(AxisType.TIME)
                                .start(targetStart)
                                .`end`(targetStart.plus(targetSeries.getNoOfItems(), timeStep))
                                .dateTimeFormatPattern("YYYY")
                                .autoScale(false)
                                .majorTickMarksVisible(false)
                                .mediumTickMarksVisible(false)
                                .minorTickMarksVisible(false)
                                .sameTickMarkLength(true)
                                .mediumTimeAxisTickLabelsVisible(true)
                                .rightAnchor(axisWidth)
                                .bottomAnchor(0d)
                                .leftAnchor(axisWidth)
                                .build()

        val yAxis: Axis = Helper.createLeftAxis(yAxisMinValue, yAxisMaxValue, "Value", true, axisWidth)
        yAxis.setDecimals(2)

        val xyPane: XYPane[TYChartItem] = new XYPane(listOfSeries)
        xyPane.getOverlays().addAll(predictions)

        XYChart(List.of(xyPane), yAxis, xAxis)
    }

    private def createPredication(predictionStart: LocalDateTime, timeStep: ChronoUnit, color: Color, predictionData: List[Double], quantile50Data:List[Double], quantile90Data: List[Double]): Prediction[TYChartItem] = {
        val predictionSeries: XYSeries[TYChartItem] = createSeries(ChartType.PREDICTION_TIMESERIES, predictionStart, timeStep, Color.TRANSPARENT, color, predictionData)
        val quantile50Series: XYSeries[TYChartItem] = createSeries(ChartType.PREDICTION_TIMESERIES, predictionStart, timeStep, Color.color(color.getRed(), color.getGreen(), color.getBlue(), 0.35), Color.TRANSPARENT, quantile50Data)
        val quantile90Series: XYSeries[TYChartItem] = createSeries(ChartType.PREDICTION_TIMESERIES, predictionStart, timeStep, Color.color(color.getRed(), color.getGreen(), color.getBlue(), 0.15), Color.TRANSPARENT, quantile90Data)
        Prediction(predictionStart, timeStep, predictionSeries, quantile50Series, quantile90Series)
    }

    private def createSeries(chartType: ChartType, start:LocalDateTime, timeStep:ChronoUnit, fill:Color, stroke:Color, data:List[Double]): XYSeries[TYChartItem] = {
        val series: XYSeries[TYChartItem] = XYSeriesBuilder.create()
                                                            .chartType(chartType)
                                                            .fill(fill)
                                                            .stroke(stroke)
                                                            .symbolFill(Color.TRANSPARENT)
                                                            .symbolStroke(Color.TRANSPARENT)
                                                            .symbolsVisible(false)
                                                            .symbolSize(5)
                                                            .strokeWidth(0.5)
                                                            .build()
                                                            .asInstanceOf[XYSeries[TYChartItem]] // TODO
        val timeStepSec:Long = timeStep.getDuration().getSeconds()
        for 
            i <- 0  until data.size()
        do
            val t: LocalDateTime     = start.plusSeconds(timeStepSec * i)
            val value: Double        = data.get(i)
            series.getItems().add(new TYChartItem(t, value))
        
        series
    }

    private def getDataFromTextfile(filename: String): List[Double] = {
        val data: String = Helper.readTextFile(filename)
        Arrays.stream(data.split("\\s?,\\s?")).toList().stream().map(v => java.lang.Double.parseDouble(v)).toList()
    }


    override def stop() = {
        System.exit(0)
    }
    
    def launchIt():Unit = {
        Application.launch()
    }

}


// Not required, not needed
// object TimeSeriesPredictionTest {
//   def main(args: Array[String]) =
//     val app = new TimeSeriesPredictionTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object TimeSeriesPredictionTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[TimeSeriesPredictionTest], args*)
//     }
// }
