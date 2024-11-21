// cSpell:ignore javafx, hansolo

package hansolo.charts


import eu.hansolo.fx.charts.BoxPlot
import eu.hansolo.fx.charts.BoxPlotBuilder
import eu.hansolo.fx.charts.data.ChartItem
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
 * ./mill -i hansolo.box.run
 * ./mill -i hansolo.box.runMain hansolo.charts.BoxPlotTest
 * ./mill -i --watch hansolo.box.runMain hansolo.charts.BoxPlotTest
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
class BoxPlotTest extends Application {
    private var boxPlot: BoxPlot[ChartItem] = uninitialized
    private var items:   List[ChartItem]    = uninitialized


    override def init() = {
        items = new ArrayList[ChartItem]()

        // Prepare data for BoxPlot
        prepareData()

        boxPlot = BoxPlotBuilder.create()
                                .items(items)
                                .whiskerStrokeColor(Color.WHITE)
                                .iqrStrokeColor(Color.WHITE)
                                .outlierFillColor(Color.WHITE)
                                .textFillColor(Color.WHITE)
                                .name("Test")
                                .decimals(2)
                                .build()
                                .asInstanceOf[BoxPlot[ChartItem]]

        AnchorPane.setTopAnchor(boxPlot, 0d)
        AnchorPane.setRightAnchor(boxPlot, 0d)
        AnchorPane.setBottomAnchor(boxPlot, 0d)
        AnchorPane.setLeftAnchor(boxPlot, 0d)

    }

    override def start(stage: Stage) = {
        val pane = new AnchorPane()
        pane.getChildren().addAll(boxPlot)


        pane.setBackground(new Background(new BackgroundFill(Color.rgb(48, 48, 48), CornerRadii.EMPTY, Insets.EMPTY)))
        pane.setPadding(new Insets(10))

        val scene = new Scene(pane)

        stage.setTitle("Box Plot")
        stage.setScene(scene)
        stage.show()
    }

    override def stop() = {
        System.exit(0)
    }

    private def prepareData() = {
        //List<Double> data = List.of(52.0, 57.0, 57.0, 58.0, 63.0, 66.0, 66.0, 67.0, 67.0, 68.0, 69.0, 70.0, 70.0, 70.0, 70.0, 72.0, 73.0, 75.0, 75.0, 76.0, 76.0, 78.0, 79.0, 89.0)
        //List<Double> data = List.of(57.0, 57.0, 57.0, 58.0, 63.0, 66.0, 66.0, 67.0, 67.0, 68.0, 69.0, 70.0, 70.0, 70.0, 70.0, 72.0, 73.0, 75.0, 75.0, 76.0, 76.0, 78.0, 79.0, 81.0)
        val data: List[Double] = List.of(91.00,  95.00,  54.00,  69.00,  80.00,  85.00,  88.00,  73.00,  71.00,  70.00,  66.00,  90.00,  86.00,  84.00,  73.00)
        data.forEach(v => items.add(new ChartItem(v)))
    }


    def launchIt():Unit = {
        Application.launch()
    }
}


// Not required, not needed
// object BoxPlotTest {
//   def main(args: Array[String]) =
//     val app = new BoxPlotTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object BoxPlotTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[BoxPlotTest], args*)
//     }
// }
