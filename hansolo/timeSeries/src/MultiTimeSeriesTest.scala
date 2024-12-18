// cSpell:ignore javafx, hansolo

package hansolo.charts

import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.stage.Stage
import eu.hansolo.fx.charts.data.XYChartItem
import eu.hansolo.fx.charts.series.XYSeries
import eu.hansolo.fx.charts.series.XYSeriesBuilder
import eu.hansolo.fx.charts.tools.Helper
import eu.hansolo.fx.charts.XYPane // TODO
import eu.hansolo.fx.charts.Axis // TODO
import eu.hansolo.fx.charts.AxisType // TODO
import eu.hansolo.fx.charts.XYChart // TODO
import eu.hansolo.fx.charts.Grid // TODO
import eu.hansolo.fx.charts.data.XYItem // TODO
import eu.hansolo.fx.charts.ChartType // TODO


import java.util.ArrayList
import java.util.Comparator
import java.util.HashMap
import java.util.LinkedList
import java.util.List
import java.util.Map

import scala.compiletime.uninitialized
import scala.jdk.CollectionConverters.*
import java.{util => ju}

/**
 * 
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i hansolo.timeSeries.run
 * ./mill -i hansolo.timeSeries.runMain hansolo.charts.MultiTimeSeriesTest
 * ./mill -i --watch hansolo.timeSeries.runMain hansolo.charts.MultiTimeSeriesTest
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
class MultiTimeSeriesTest extends Application {
    private val AXIS_WIDTH: Double = 25d

    private var multiTimeSeriesChart: XYChart[XYChartItem] = uninitialized
    private var xAxis: Axis = uninitialized
    private var yAxis: Axis = uninitialized


    override def init() = {
        // Data Series 1
        val listOfSeries1:  List[XYSeries[XYChartItem]] = new ArrayList[XYSeries[XYChartItem]]()
        // val filename1:      String                      = classOf[MultiTimeSeriesTest].getResource("/data1.csv").toExternalForm().replaceAll("file:", "")
        val filename1:      String                      = getClass().getResource("/data1.csv").toExternalForm().replaceAll("file:", "")
        val data1:          String                      = Helper.readTextFile(filename1)
        val lines1:         Array[String]               = data1.split(System.getProperty("line.separator"))
        val firstLine1:     String                      = lines1(0)
        val names1:         Array[String]               = firstLine1.split(",")
        val xAxisValues1:   List[Double]                = new ArrayList[Double]()
        var yAxisMinValue1                              = java.lang.Double.MAX_VALUE
        var yAxisMaxValue1                              = java.lang.Double.MIN_VALUE
        val seriesDataMap1: Map[String, List[XYChartItem]] = new HashMap[String, List[XYChartItem]]()
        for (i <- 1  until lines1.length) {
            val line: String = lines1(i)
            val xyItems: List[XYChartItem] = new ArrayList[XYChartItem]()
            val dataPoints: Array[String] = line.split(",")
            val timePoint  = java.lang.Double.parseDouble(dataPoints(0))
            xAxisValues1.add(timePoint)
            for (j <- 1  until dataPoints.length) {
                val value = java.lang.Double.parseDouble(dataPoints(j))
                yAxisMinValue1 = Math.min(yAxisMinValue1, value)
                yAxisMaxValue1 = Math.max(yAxisMaxValue1, value)

                if (seriesDataMap1.containsKey(names1(j))) {
                    seriesDataMap1.get(names1(j)).add(new XYChartItem(timePoint, value, names1(j), Color.MAGENTA))
                } else {
                    seriesDataMap1.put(names1(j), new LinkedList[XYChartItem]())
                    seriesDataMap1.get(names1(j)).add(new XYChartItem(timePoint, value, names1(j), Color.MAGENTA))
                }
            }
        }

        seriesDataMap1.entrySet().forEach(entry => {
            val xySeries: XYSeries[XYChartItem] = XYSeriesBuilder.create()
                                                            // .items(entry.getValue().toArray(new Array[XYChartItem](0)))
                                                            //.items(entry.getValue()) // TODO
                                                            .items(entry.getValue().asInstanceOf[List[XYItem]]) // TODO
                                                            .chartType(ChartType.MULTI_TIME_SERIES)
                                                            .fill(Color.TRANSPARENT)
                                                            .stroke(Color.MAGENTA)
                                                            .symbolFill(Color.RED)
                                                            .symbolStroke(Color.TRANSPARENT)
                                                            .symbolsVisible(false)
                                                            .symbolSize(5)
                                                            .strokeWidth(0.5)
                                                            .build()
                                                            .asInstanceOf[XYSeries[XYChartItem]] // TODO
            listOfSeries1.add(xySeries)
        })

        // Data Series 2
        val listOfSeries2: List[XYSeries[XYChartItem]]     = new ArrayList[XYSeries[XYChartItem]]()
        // val filename2: String                              = classOf[MultiTimeSeriesTest].getResource("data2.csv").toExternalForm().replaceAll("file:", "")
        val filename2: String                              = getClass().getResource("/data2.csv").toExternalForm().replaceAll("file:", "")
        val data2: String                                  = Helper.readTextFile(filename2)
        val lines2: Array[String]                          = data2.split(System.getProperty("line.separator"))
        val firstLine2: String                             = lines2(0)
        val names2: Array[String]                          = firstLine2.split(",")
        val xAxisValues2: List[Double]                     = new ArrayList[Double]()
        var yAxisMinValue2                                 = java.lang.Double.MAX_VALUE
        var yAxisMaxValue2                                 = java.lang.Double.MIN_VALUE
        val seriesDataMap2: Map[String, List[XYChartItem]] = new HashMap[String, List[XYChartItem]]()
        for (i <- 1 until lines2.length) {
            val line: String = lines2(i)
            val xyItems: List[XYChartItem] = new ArrayList[XYChartItem]()
            val dataPoints: Array[String] = line.split(",")
            val timePoint  = java.lang.Double.parseDouble(dataPoints(0))
            xAxisValues2.add(timePoint)
            for (j <- 1 until dataPoints.length) {
                val value: Double = java.lang.Double.parseDouble(dataPoints(j))
                yAxisMinValue2 = Math.min(yAxisMinValue2, value)
                yAxisMaxValue2 = Math.max(yAxisMaxValue2, value)

                if (seriesDataMap2.containsKey(names2(j))) {
                    seriesDataMap2.get(names2(j)).add(new XYChartItem(timePoint, value, names2(j), Color.MAGENTA))
                } else {
                    seriesDataMap2.put(names2(j), new LinkedList[XYChartItem]())
                    seriesDataMap2.get(names2(j)).add(new XYChartItem(timePoint, value, names2(j), Color.MAGENTA))
                }
            }
        }

        seriesDataMap2.entrySet().forEach(entry => {
            val xySeries: XYSeries[XYChartItem] = XYSeriesBuilder.create()
                                                            // .items(entry.getValue().toArray(new Array[XYChartItem](0)))
                                                            .items(entry.getValue().asInstanceOf[List[XYItem]]) // TODO
                                                            .chartType(ChartType.MULTI_TIME_SERIES)
                                                            .fill(Color.TRANSPARENT)
                                                            .stroke(Color.MAGENTA)
                                                            .symbolFill(Color.RED)
                                                            .symbolStroke(Color.TRANSPARENT)
                                                            .symbolsVisible(false)
                                                            .symbolSize(5)
                                                            .strokeWidth(0.5)
                                                            .build()
                                                            .asInstanceOf[XYSeries[XYChartItem]] // TODO
            listOfSeries2.add(xySeries)
        })


        // Data Series 3
        val listOfSeries3 : List[XYSeries[XYChartItem]]    = new ArrayList[XYSeries[XYChartItem]]()
        val filename3: String                              = classOf[MultiTimeSeriesTest].getResource("/data3.csv").toExternalForm().replaceAll("file:", "")
        //val filename3: String                              = getClass().getResource("/data3.csv").toExternalForm().replaceAll("file:", "")
        val data3: String                                  = Helper.readTextFile(filename3)
        val lines3: Array[String]                          = data3.split(System.getProperty("line.separator"))
        val firstLine3: String                             = lines3(0)
        val names3: Array[String]                          = firstLine3.split(",")
        val xAxisValues3: List[Double]                     = new ArrayList[Double]()
        var yAxisMinValue3                                 = java.lang.Double.MAX_VALUE
        var yAxisMaxValue3                                 = java.lang.Double.MIN_VALUE
        val seriesDataMap3: Map[String, List[XYChartItem]] = new HashMap[String, List[XYChartItem]]()
        for (i <- 1 until lines3.length) {
            val line: String = lines3(i)
            val xyItems: List[XYChartItem] = new ArrayList[XYChartItem]()
            val dataPoints: Array[String] = line.split(",")
            val timePoint  = java.lang.Double.parseDouble(dataPoints(0))
            xAxisValues3.add(timePoint)
            for (j <- 1 until dataPoints.length) {
                val value = java.lang.Double.parseDouble(dataPoints(j))
                yAxisMinValue3 = Math.min(yAxisMinValue3, value)
                yAxisMaxValue3 = Math.max(yAxisMaxValue3, value)

                if (seriesDataMap3.containsKey(names3(j))) {
                    seriesDataMap3.get(names3(j)).add(new XYChartItem(timePoint, value, names3(j), Color.MAGENTA))
                } else {
                    seriesDataMap3.put(names3(j), new LinkedList[XYChartItem]())
                    seriesDataMap3.get(names3(j)).add(new XYChartItem(timePoint, value, names3(j), Color.MAGENTA))
                }
            }
        }

        seriesDataMap3.entrySet().forEach(entry => {
            val xySeries: XYSeries[XYChartItem] = XYSeriesBuilder.create()
                                                            // .items(entry.getValue().toArray(new Array[XYChartItem](0)))
                                                            .items(entry.getValue().asInstanceOf[List[XYItem]]) // TODO
                                                            .chartType(ChartType.MULTI_TIME_SERIES)
                                                            .fill(Color.TRANSPARENT)
                                                            .stroke(Color.MAGENTA)
                                                            .symbolFill(Color.RED)
                                                            .symbolStroke(Color.TRANSPARENT)
                                                            .symbolsVisible(false)
                                                            .symbolSize(5)
                                                            .strokeWidth(0.5)
                                                            .build()
                                                            .asInstanceOf[XYSeries[XYChartItem]] // TODO
            listOfSeries3.add(xySeries)
        })


        val yAxisMinValue: Double = Math.min(Math.min(yAxisMinValue1, yAxisMinValue2), yAxisMinValue3)
        val yAxisMaxValue: Double = Math.max(Math.max(yAxisMaxValue1, yAxisMaxValue2), yAxisMaxValue3)


        // MultiTimeSeriesChart
        val start: Double = xAxisValues1.stream().min(Comparator.comparingDouble(_.doubleValue)).get()
        val end: Double   = xAxisValues1.stream().max(Comparator.comparingDouble(_.doubleValue)).get()
        xAxis = Helper.createBottomAxis(start, end, "Time [s]", true, AXIS_WIDTH)
        xAxis.setDecimals(1)

        yAxis = Helper.createLeftAxis(yAxisMinValue, yAxisMaxValue, "Ratio", true, AXIS_WIDTH)
        yAxis.setDecimals(2)

        xAxis.setZeroColor(Color.BLACK)
        yAxis.setZeroColor(Color.BLACK)

        val xyPane1 = new XYPane(listOfSeries1)
        xyPane1.setAverageStroke(Color.rgb(247, 118, 109))
        xyPane1.setAverageStrokeWidth(3)
        xyPane1.setStdDeviationFill(Color.rgb(247, 118, 109, 0.2))
        xyPane1.setStdDeviationStroke(Color.rgb(120, 120, 120))
        xyPane1.setEnvelopeVisible(true)
        xyPane1.setEnvelopeFill(Color.TRANSPARENT)
        xyPane1.setEnvelopeStroke(Color.rgb(247, 118, 109))

        val xyPane2 = new XYPane(listOfSeries2)
        xyPane2.setAverageStroke(Color.rgb(42, 186, 56))
        xyPane2.setAverageStrokeWidth(3)
        xyPane2.setStdDeviationFill(Color.rgb(42, 186, 56, 0.2))
        xyPane2.setStdDeviationStroke(Color.rgb(120, 120, 120))
        xyPane2.setEnvelopeVisible(true)
        xyPane2.setEnvelopeFill(Color.TRANSPARENT)
        xyPane2.setEnvelopeStroke(Color.rgb(42, 186, 56))

        val xyPane3 = new XYPane(listOfSeries3)
        xyPane3.setAverageStroke(Color.rgb(97, 155, 255))
        xyPane3.setAverageStrokeWidth(3)
        xyPane3.setStdDeviationFill(Color.rgb(97, 155, 255, 0.2))
        xyPane3.setStdDeviationStroke(Color.rgb(120, 120, 120))
        xyPane3.setEnvelopeVisible(true)
        xyPane3.setEnvelopeFill(Color.TRANSPARENT)
        xyPane3.setEnvelopeStroke(Color.rgb(97, 155, 255))

        val xyPanes: List[XYPane[XYChartItem]] = new ArrayList[XYPane[XYChartItem]]()
        xyPanes.add(xyPane1)
        xyPanes.add(xyPane2)
        xyPanes.add(xyPane3)

        multiTimeSeriesChart = new XYChart(xyPanes, yAxis, xAxis)

        val grid1 = new Grid(xAxis, yAxis)
        multiTimeSeriesChart.setGrid(grid1)
    }

    override def start(stage: Stage) = {
        val pane = new StackPane(multiTimeSeriesChart)
        pane.setPadding(new Insets(10))

        val scene = new Scene(new StackPane(pane), 800, 600)

        stage.setTitle("MultiTimeSeriesCharts")
        stage.setScene(scene)
        stage.show()
    }

    override def stop() = {
        System.exit(0)
    }
    
    def launchIt():Unit = {
        Application.launch()
    }

}


// Not required, not needed
// object MultiTimeSeriesTest {
//   def main(args: Array[String]) =
//     val app = new MultiTimeSeriesTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object MultiTimeSeriesTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[MultiTimeSeriesTest], args*)
//     }
// }
