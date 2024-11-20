// cSpell:ignore javafx, hansolo

package hansolo.charts


import eu.hansolo.fx.charts.PanelBarChart
import eu.hansolo.fx.charts.PanelBarChartBuilder
import eu.hansolo.fx.charts.data.Categories
import eu.hansolo.fx.charts.data.ChartItem
import eu.hansolo.fx.charts.data.ChartItemBuilder
import eu.hansolo.fx.charts.data.DayOfWeekCategory
import eu.hansolo.fx.charts.data.MonthCategory
import eu.hansolo.fx.charts.series.ChartItemSeries
import eu.hansolo.fx.charts.series.ChartItemSeriesBuilder
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage

import java.time.format.TextStyle
import java.util.ArrayList
import java.util.List
import java.util.Locale
import java.util.Random

import scala.compiletime.uninitialized


/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i hansolo.bar.run
 * ./mill -i hansolo.bar.runMain hansolo.charts.PanelBarChartTest
 * ./mill -i --watch hansolo.bar.runMain hansolo.charts.PanelBarChartTest
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
class PanelBarChartTest extends Application {
    private val RND: Random = new Random()
    private var chart1: PanelBarChart = uninitialized
    private var chart2: PanelBarChart = uninitialized


    override def init() = {
        val categories: List[DayOfWeekCategory] = List.of(Categories.MONDAY, Categories.TUESDAY, Categories.WEDNESDAY, Categories.THURSDAY, Categories.FRIDAY, Categories.SATURDAY, Categories.SUNDAY)
        val listOfSeries: List[ChartItemSeries[ChartItem]] = new ArrayList[ChartItemSeries[ChartItem]]()
        val ss = 0 until 3
        for (s <- ss) {
            val serverNo: Int = s
            val series: ChartItemSeries[? <: ChartItem] = ChartItemSeriesBuilder.create().name("This week " + serverNo).build()
            categories.forEach(category => {
                val item: ChartItem = ChartItemBuilder.create().name(series.getName() + " " + category.getName(TextStyle.SHORT, Locale.US)).category(category).value(RND.nextDouble() * 100).fill(Color.ORANGE).build()
                series.getItems().add(item)
            })
            listOfSeries.add(series)
        }
        chart1 = PanelBarChartBuilder.create(categories)
                                     .listOfSeries(listOfSeries)
                                     .name("This week")
                                     .colorByCategory(true)
                                     .build()

        // Chart with comparison
        val comparisonListOfSeries: List[ChartItemSeries[ChartItem]] = new ArrayList[ChartItemSeries[ChartItem]]()
        for (s <- ss) {
            val serverNo: Int = s
            val series: ChartItemSeries[?] = ChartItemSeriesBuilder.create().name("Last week " + serverNo).build()
            categories.forEach(category => {
                val item: ChartItem = ChartItemBuilder.create().name(series.getName() + " " + category.getName(TextStyle.SHORT, Locale.US)).category(category).value(RND.nextDouble() * 100).fill(Color.BLUE).build()
                series.getItems().add(item)
            })
            comparisonListOfSeries.add(series)
        }


        chart2 = PanelBarChartBuilder.create(categories)
                                     .name("This week")
                                     .nameColor(Color.ORANGE)
                                     .seriesSumColor(Color.ORANGE)
                                     .categorySumColor(Color.ORANGE)
                                     .listOfSeries(listOfSeries)
                                     .comparisonEnabled(true)
                                     .comparisonName("Last week")
                                     .comparisonNameColor(Color.BLUE)
                                     .comparisonSeriesSumColor(Color.BLUE)
                                     .comparisonCategorySumColor(Color.BLUE)
                                     .comparisonListOfSeries(comparisonListOfSeries)
                                     .build()
    }

    override def start(stage: Stage) = {
        val pane = new VBox(10, chart1, chart2)
        pane.setPadding(new Insets(10))

        val scene = new Scene(pane)

        stage.setTitle("Panel Bar Chart")
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
// object PanelBarChartTest {
//   def main(args: Array[String]) =
//     val app = new PanelBarChartTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object PanelBarChartTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[PanelBarChartTest], args*)
//     }
// }
