// cSpell:ignore javafx, hansolo

package hansolo.charts


import eu.hansolo.fx.charts.Axis
import eu.hansolo.fx.charts.BoxPlots
import eu.hansolo.fx.charts.BoxPlotsBuilder
import eu.hansolo.fx.charts.data.ChartItem
import eu.hansolo.fx.charts.series.ChartItemSeries
import eu.hansolo.fx.charts.series.ChartItemSeriesBuilder
import eu.hansolo.fx.charts.tools.Helper
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import javafx.stage.Stage

import java.util.ArrayList
import java.util.List

import scala.compiletime.uninitialized


/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * X ./mill -i hansolo.box.run
 * ./mill -i hansolo.box.runMain hansolo.charts.BoxPlotsTest
 * ./mill -i --watch hansolo.box.runMain hansolo.charts.BoxPlotsTest
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
class BoxPlotsTest extends Application {
    private val INSET: Double = 20
    private var boxPlots: BoxPlots[?] = uninitialized
    private var yAxisLeft: Axis = uninitialized


    override def init() = {
        yAxisLeft = Helper.createLeftAxis(0, 100, true, INSET)
        yAxisLeft.setDecimals(0)
        yAxisLeft.setAxisColor(Color.WHITE)
        yAxisLeft.setTickMarkColor(Color.WHITE)
        yAxisLeft.setTickLabelColor(Color.WHITE)

        AnchorPane.setBottomAnchor(yAxisLeft, 0d)

        boxPlots = BoxPlotsBuilder.create()
                                  .seriesList(createSeriesList())
                                  .backgroundColor(Color.rgb(48, 48, 48))
                                  .iqrStrokeColor(Color.WHITE)
                                  .iqrFillColor(Color.PURPLE)
                                  .whiskerStrokeColor(Color.WHITE)
                                  .nameVisible(true)
                                  .textFillColor(Color.WHITE)
                                  .yAxis(yAxisLeft)
                                  .build()
                                  //TODO .asInstanceOf[BoxPlots[ChartItem]]

        AnchorPane.setTopAnchor(boxPlots, 0d)
        AnchorPane.setRightAnchor(boxPlots, 0d)
        AnchorPane.setBottomAnchor(boxPlots, 0d)
        AnchorPane.setLeftAnchor(boxPlots, INSET)
    }

    override def start(stage: Stage) = {
        val pane = new AnchorPane()
        pane.getChildren().addAll(boxPlots, yAxisLeft)


        pane.setBackground(new Background(new BackgroundFill(Color.rgb(48, 48, 48), CornerRadii.EMPTY, Insets.EMPTY)))
        pane.setPadding(new Insets(10))

        val scene = new Scene(pane)

        stage.setTitle("Box Plots")
        stage.setScene(scene)
        stage.show()
    }

    override def stop() = {
        System.exit(0)
    }

    private def createSeriesList(): List[ChartItemSeries[?]] = {
        val store1: List[Double] = List.of(350.0, 460.0, 20.0, 160.0, 580.0, 250.0, 210.0, 120.0, 200.0, 510.0, 290.0, 380.0)
        val store2: List[Double] = List.of(520.0, 180.0, 260.0, 380.0, 80.0, 500.0, 630.0, 420.0, 210.0, 70.0, 440.0, 140.0)
        val store3: List[Double] = List.of(500.0, 120.0, 250.0, 320.0, 50.0, 520.0, 600.0, 380.0, 200.0, 90.0, 440.0, 120.0)

        val items1: List[ChartItem] = new ArrayList[ChartItem]()
        val items2: List[ChartItem] = new ArrayList[ChartItem]()
        val items3: List[ChartItem] = new ArrayList[ChartItem]()

        store1.forEach(v => items1.add(new ChartItem(v)))
        store2.forEach(v => items2.add(new ChartItem(v)))
        store3.forEach(v => items3.add(new ChartItem(v)))

        val series1: ChartItemSeries[?] = ChartItemSeriesBuilder.create().items(items1).name("Store 1").build()
        val series2: ChartItemSeries[?] = ChartItemSeriesBuilder.create().items(items2).name("Store 2").build()
        val series3: ChartItemSeries[?] = ChartItemSeriesBuilder.create().items(items3).name("Store 3").build()

        List.of(series1, series2, series3)
    }


    def launchIt():Unit = {
        Application.launch()
    }
}


// Not required, not needed
// object BoxPlotsTest {
//   def main(args: Array[String]) =
//     val app = new BoxPlotsTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object BoxPlotsTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[BoxPlotsTest], args*)
//     }
// }
