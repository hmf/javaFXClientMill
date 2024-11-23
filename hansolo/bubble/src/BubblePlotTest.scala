// cSpell:ignore javafx, hansolo

package hansolo.charts


import eu.hansolo.fx.charts.BubbleChart
import eu.hansolo.fx.charts.data.ChartItem
import eu.hansolo.fx.charts.data.ChartItemBuilder
import eu.hansolo.fx.charts.tools.NumberFormat
import eu.hansolo.fx.charts.tools.Order
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
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
 * ./mill -i hansolo.bubble.run
 * ./mill -i hansolo.bubble.runMain hansolo.charts.BubblePlotTest
 * ./mill -i --watch hansolo.bubble.runMain hansolo.charts.BubblePlotTest
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
class BubblePlotTest extends Application {
    private val RND: Random = new Random()
    private var chart: BubbleChart[?] = uninitialized
    private var items: List[ChartItem] = uninitialized


    override def init() = {
        items = new ArrayList[ChartItem]()
        items.add(ChartItemBuilder.create().name("Item 1").value(2).fill(Color.rgb(221, 78, 77)).build())
        items.add(ChartItemBuilder.create().name("Item 2").value(7).fill(Color.rgb(215, 131, 79)).build())
        items.add(ChartItemBuilder.create().name("Item 3").value(5).fill(Color.rgb(236, 165, 57)).build())
        items.add(ChartItemBuilder.create().name("Item 4").value(8).fill(Color.rgb(135, 170, 102)).build())
        items.add(ChartItemBuilder.create().name("Item 5").value(10).fill(Color.rgb(136, 171, 173)).build())
        items.add(ChartItemBuilder.create().name("Item 6").value(6).fill(Color.rgb(76, 179, 210)).build())
        items.add(ChartItemBuilder.create().name("Item 7").value(3).fill(Color.rgb(106, 198, 255)).build())

        chart = new BubbleChart(items)

        AnchorPane.setTopAnchor(chart, 10d)
        AnchorPane.setRightAnchor(chart, 10d)
        AnchorPane.setBottomAnchor(chart, 10d)
        AnchorPane.setLeftAnchor(chart, 10d)
    }

    override def start(stage: Stage) = {
        val pane = new AnchorPane(chart)
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(48, 48, 48), CornerRadii.EMPTY, Insets.EMPTY)))

        val scene = new Scene(pane)

        stage.setTitle("Bubble Chart")
        stage.setScene(scene)
        stage.show()

        chart.start()
    }

    override def stop() = {
        System.exit(0)
    }

    def launchIt():Unit = {
        Application.launch()
    }
}


// Not required, not needed
// object BubblePlotTest {
//   def main(args: Array[String]) =
//     val app = new BubblePlotTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object BubblePlotTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[BubblePlotTest], args*)
//     }
// }
