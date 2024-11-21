// cSpell:ignore javafx, hansolo

package hansolo.charts


import eu.hansolo.fx.charts.BarChart
import eu.hansolo.fx.charts.BarChartBuilder
import eu.hansolo.fx.charts.data.ChartItem
import eu.hansolo.fx.charts.data.ChartItemBuilder
import eu.hansolo.fx.charts.series.ChartItemSeries
import eu.hansolo.fx.charts.series.ChartItemSeriesBuilder
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
    private val RND:           Random = new Random()
    private var chart:         BarChart[? <: ChartItem] = uninitialized
    private var items:         List[ChartItem]          = uninitialized
    private var lastTimerCall: Long                     = uninitialized
    private var timer:         AnimationTimer           = uninitialized


    override def init() = {
        items = new ArrayList[ChartItem]()
        items.add(ChartItemBuilder.create().name("Item 1").value(0).fill(Color.rgb(221, 78, 77)).build())
        items.add(ChartItemBuilder.create().name("Item 2").value(0).fill(Color.rgb(215, 131, 79)).build())
        items.add(ChartItemBuilder.create().name("Item 3").value(0).fill(Color.rgb(236, 165, 57)).build())
        items.add(ChartItemBuilder.create().name("Item 4").value(0).fill(Color.rgb(135, 170, 102)).build())
        items.add(ChartItemBuilder.create().name("Item 5").value(0).fill(Color.rgb(136, 171, 173)).build())
        items.add(ChartItemBuilder.create().name("Item 6").value(0).fill(Color.rgb(76, 179, 210)).build())
        items.add(ChartItemBuilder.create().name("Item 7").value(0).fill(Color.rgb(106, 198, 255)).build())

        chart = BarChartBuilder.create()
                               .prefSize(600, 300)
                               .items(items)
                               .orientation(Orientation.HORIZONTAL)
                               .backgroundFill(Color.rgb(48, 48, 48))
                               .namesBackgroundFill(Color.rgb(48, 48, 48))
                               .barBackgroundFill(Color.rgb(62, 62, 62))
                               .barBackgroundVisible(true)
                               .seriesFill(new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.rgb(91, 105, 255)), new Stop(1, Color.rgb(76, 41, 217))))
                               .shadowsVisible(false)
                               .textFill(Color.WHITE)
                               .namesTextFill(Color.rgb(190, 190, 190))
                               .useNamesTextFill(true)
                               .useItemTextFill(false)
                               .useItemFill(false)
                               .shortenNumbers(false)
                               .sorted(true)
                               .order(Order.DESCENDING)
                               //.order(Order.ASCENDING)
                               //.numberFormat(NumberFormat.PERCENTAGE)
                               .numberFormat(NumberFormat.NUMBER)
                               //.animated(false)
                               .minNumberOfBars(10)
                               .useMinNumberOfBars(false)
                               .useGivenColors(true)
                               .colors(List.of(Color.RED, Color.BLUE, Color.GREEN))
                               .barCornerRadius(15)
                               .boldValueFont(true)
                               .build()

        AnchorPane.setTopAnchor(chart, 10d)
        AnchorPane.setRightAnchor(chart, 10d)
        AnchorPane.setBottomAnchor(chart, 10d)
        AnchorPane.setLeftAnchor(chart, 10d)

        lastTimerCall = System.nanoTime()
        timer = new AnimationTimer() {
            override def handle(now: Long): Unit = {
                if (now > lastTimerCall + 3_000_000_000l) {
                    items.forEach(item => item.setValue(RND.nextDouble() * 75 + 25))
                    lastTimerCall = now
                }
            }
        }
    }

    override def start(stage: Stage) = {
        val pane = new AnchorPane(chart)
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(48, 48, 48), CornerRadii.EMPTY, Insets.EMPTY)))

        val scene = new Scene(pane)

        stage.setTitle("BarChart")
        stage.setScene(scene)
        stage.show()

        timer.start()
    }

    override def stop() = {
        System.exit(0)
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
