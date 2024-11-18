// cSpell:ignore javafx, hansolo

package hansolo.charts

import eu.hansolo.fx.charts.ChartType
import eu.hansolo.fx.charts.NestedBarChart
import eu.hansolo.fx.charts.data.ChartItem
import eu.hansolo.fx.charts.event.ChartEvt
import eu.hansolo.fx.charts.series.ChartItemSeries
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.stage.Stage

import eu.hansolo.fx.charts.color.MaterialDesignColors.*

import scala.compiletime.uninitialized


/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * X ./mill -i hansolo.bar.run
 * ./mill -i hansolo.bar.runMain hansolo.charts.NestedBarChartTest
 * ./mill -i --watch hansolo.bar.runMain hansolo.charts.NestedBarChartTest
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
class NestedBarChartTest extends Application {
    private var chart: NestedBarChart = uninitialized

    override def init() = {
        val p1Q1 = new ChartItem("Product 1", 16, CYAN_700.get())
        val p2Q1 = new ChartItem("Product 2", 8, CYAN_500.get())
        val p3Q1 = new ChartItem("Product 3", 4, CYAN_300.get())
        val p4Q1 = new ChartItem("Product 4", 2, CYAN_100.get())

        val p1Q2 = new ChartItem("Product 1", 12, PURPLE_700.get())
        val p2Q2 = new ChartItem("Product 2", 5, PURPLE_500.get())
        val p3Q2 = new ChartItem("Product 3", 3, PURPLE_300.get())
        val p4Q2 = new ChartItem("Product 4", 1, PURPLE_100.get())

        val p1Q3 = new ChartItem("Product 1", 14, PINK_700.get())
        val p2Q3 = new ChartItem("Product 2", 7, PINK_500.get())
        val p3Q3 = new ChartItem("Product 3", 3.5, PINK_300.get())
        val p4Q3 = new ChartItem("Product 4", 1.75, PINK_100.get())

        val p1Q4 = new ChartItem("Product 1", 18, AMBER_700.get())
        val p2Q4 = new ChartItem("Product 2", 9, AMBER_500.get())
        val p3Q4 = new ChartItem("Product 3", 4.5, AMBER_300.get())
        val p4Q4 = new ChartItem("Product 4", 2.25, AMBER_100.get())

        val q1 = new ChartItemSeries[ChartItem](ChartType.NESTED_BAR, "1st Quarter", CYAN_900.get(), Color.TRANSPARENT, p1Q1, p2Q1, p3Q1, p4Q1)
        val q2 = new ChartItemSeries[ChartItem](ChartType.NESTED_BAR, "2nd Quarter", PURPLE_900.get(), Color.TRANSPARENT, p1Q2, p2Q2, p3Q2, p4Q2)
        val q3 = new ChartItemSeries[ChartItem](ChartType.NESTED_BAR, "3rd Quarter", PINK_900.get(), Color.TRANSPARENT, p1Q3, p2Q3, p3Q3, p4Q3)
        val q4 = new ChartItemSeries[ChartItem](ChartType.NESTED_BAR, "4th Quarter", AMBER_900.get(), Color.TRANSPARENT, p1Q4, p2Q4, p3Q4, p4Q4)


        chart = new NestedBarChart(q1, q2, q3, q4)

        chart.addChartEvtObserver(ChartEvt.ANY,  e => System.out.println(e))
    }

    override def start(stage: Stage) = {
        val pane = new StackPane(chart)
        pane.setPadding(new Insets(10))

        val scene = new Scene(pane)

        stage.setTitle("Nested Bar Chart")
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
// object NestedBarChartTest {
//   def main(args: Array[String]) =
//     val app = new NestedBarChartTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object NestedBarChartTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[NestedBarChartTest], args*)
//     }
// }
