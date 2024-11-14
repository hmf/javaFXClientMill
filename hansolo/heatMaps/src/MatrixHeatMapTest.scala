// cSpell:ignore javafx, hansolo

package hansolo.charts

import eu.hansolo.fx.charts.ChartType
import eu.hansolo.fx.charts.MatrixPane
import eu.hansolo.fx.charts.data.MatrixChartItem
import eu.hansolo.fx.charts.series.MatrixItemSeries
import eu.hansolo.fx.charts.tools.Helper
import eu.hansolo.fx.heatmap.ColorMapping
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.stage.Stage

import java.util.ArrayList
import java.util.List
import java.util.Random

import scala.compiletime.uninitialized


/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * X ./mill -i hansolo.heatMaps.run
 * ./mill -i hansolo.heatMaps.runMain hansolo.charts.MatrixHeatmapTest
 * ./mill -i --watch hansolo.heatMaps.runMain hansolo.charts.MatrixHeatmapTest
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
class MatrixHeatmapTest extends Application {
    private val RND         = new Random()
    private val TWO_PI      = 2 * Math.PI
    private val NO_OF_CELLS = 100
    private val STEP        = TWO_PI / NO_OF_CELLS

    private var matrixItemSeries1 : MatrixItemSeries[MatrixChartItem] = uninitialized
    private var matrixHeatMap1    : MatrixPane[MatrixChartItem]       = uninitialized
    private var factor            : Double                            = uninitialized

    private var matrixItemSeries2 : MatrixItemSeries[MatrixChartItem] = uninitialized
    private var matrixHeatMap2    : MatrixPane[MatrixChartItem]       = uninitialized

    private var matrixItemSeries3 : MatrixItemSeries[MatrixChartItem] = uninitialized
    private var matrixHeatMap3    : MatrixPane[MatrixChartItem]       = uninitialized

    private var lastTimerCall     : Long                              = uninitialized
    private var timer             : AnimationTimer                    = uninitialized


    override def init() = {
        var cellX = 0
        var cellY = 0
        val matrixData1: List[MatrixChartItem] = new ArrayList[MatrixChartItem]()
        var y = 0.0
        while (y < TWO_PI)
        {
            cellX = 0
            var x = 0.0
            while (x < TWO_PI)
            {
                matrixData1.add(new MatrixChartItem(cellX, cellY, (Math.cos(y * TWO_PI * 0.125) * Math.sin(x * TWO_PI * 0.125) + 1) * 0.5))
                cellX = cellX + 1
                x += STEP
            }
            cellY = cellY + 1
            y += STEP
        }

        matrixItemSeries1 = new MatrixItemSeries(matrixData1, ChartType.MATRIX_HEATMAP)

        matrixHeatMap1 = new MatrixPane(matrixItemSeries1)
        matrixHeatMap1.setColorMapping(ColorMapping.INFRARED_1)
        matrixHeatMap1.getMatrix().setUseSpacer(false)
        matrixHeatMap1.getMatrix().setColsAndRows(NO_OF_CELLS, NO_OF_CELLS)
        matrixHeatMap1.setPrefSize(400, 400)


        val matrixGradient: LinearGradient = Helper.createColorVariationGradient(Color.BLUE, 5)

        val matrixData2: List[MatrixChartItem] = new ArrayList[MatrixChartItem]()

        {
            var y = 0
            while (y < 6) {
                var x = 0
                while (x < 8) {
                    matrixData2.add(new MatrixChartItem(x, y, RND.nextDouble()))
                    x += 1
                }
                y += 1
            }
        }

        matrixItemSeries2 = new MatrixItemSeries(matrixData2, ChartType.MATRIX_HEATMAP)

        matrixHeatMap2 = new MatrixPane(matrixItemSeries2)
        //matrixHeatMap2.setColorMapping(ColorMapping.BLUE_TRANSPARENT_RED)
        matrixHeatMap2.setMatrixGradient(matrixGradient)
        matrixHeatMap2.getMatrix().setUseSpacer(true)
        matrixHeatMap2.getMatrix().setColsAndRows(8, 6)
        matrixHeatMap2.setPrefSize(400, 300)



        val matrixData3: List[MatrixChartItem] = new ArrayList[MatrixChartItem]()
        val matrixItemSeries3 = new MatrixItemSeries(matrixData3, ChartType.MATRIX_HEATMAP)

        val matrixHeatMap3 = new MatrixPane(matrixItemSeries3)
        matrixHeatMap3.setMatrixGradient(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                                                            new Stop(0.0, Color.web("#0085D9")),
                                                            new Stop(0.125, Color.web("#52B53D")),
                                                            new Stop(0.25, Color.web("#93CE7D")),
                                                            new Stop(0.375, Color.web("#BBDFAC")),
                                                            new Stop(0.5, Color.web("#EADEAC")),
                                                            new Stop(0.625, Color.web("#FFD01F")),
                                                            new Stop(0.75, Color.web("#FC9200")),
                                                            new Stop(0.875, Color.web("#EC3A21")),
                                                            new Stop(1.0, Color.web("#C4311D"))))
        matrixHeatMap3.getMatrix().setUseSpacer(false)
        matrixHeatMap3.getMatrix().setSquarePixels(false)
        matrixHeatMap3.getMatrix().setColsAndRows(108,40)
        matrixHeatMap3.setPrefSize(900, 400)


        lastTimerCall = System.nanoTime()
        timer = new AnimationTimer() {
            override def handle(now: Long) = {
                if (now > lastTimerCall + 10_000_000l) {
                    var cellX = 0
                    var cellY = 0
                    var y = 0.0
                    while (y < TWO_PI) {
                        if (java.lang.Double.compare(factor, Math.PI * 2.55) >= 0) { factor = 0 }
                        cellX = 0
                        var x = factor
                        while (x < TWO_PI + factor) {
                            var variance = Math.abs(Math.cos(x/100.0) + (RND.nextDouble() - 0.5) / 10.0)
                            var value = ((Math.cos(y * TWO_PI * 0.125) * Math.sin(x * TWO_PI * 0.125) + 1) * 0.5) * variance
                            matrixHeatMap1.setValueAt(cellX, cellY, value)
                            cellX += 1
                            x += STEP
                        }
                        cellY += 1
                        y += STEP
                    }
                    matrixHeatMap1.getMatrix().drawMatrix()
                    factor += STEP
                    lastTimerCall = now
                }
            }
        }
    }

    override def start(stage: Stage) = {
        val pane = new VBox(10, matrixHeatMap1, matrixHeatMap2)//, matrixHeatMap3)
        pane.setPadding(new Insets(10))

        val scene = new Scene(pane)

        stage.setTitle("MatrixHeatMap")
        stage.setScene(scene)
        stage.show()

        timer.start()

        /*
        for (int x = 0  x <108  x++) {
            for (int y = 0  y < 40  y++) {
                MatrixChartItem mdo = new MatrixChartItem(x, y, RND.nextDouble())
                matrixHeatMap3.getSeries().getItems().add(mdo)
            }
        }
        */
    }

    override def stop() = {
        System.exit(0)
    }

    def launchIt():Unit = {
        Application.launch()
    }
}
// Not required, not needed
// object MatrixHeatmapTest {
//   def main(args: Array[String]) =
//     val app = new MatrixHeatmapTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object MatrixHeatmapTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[MatrixHeatmapTest], args*)
//     }
// }
