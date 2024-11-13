// cSpell:ignore javafx, hansolo

package hansolo.charts

import eu.hansolo.fx.charts.areaheatmap.AreaHeatMap
import eu.hansolo.fx.charts.areaheatmap.AreaHeatMap.Quality
import eu.hansolo.fx.charts.areaheatmap.AreaHeatMapBuilder
import eu.hansolo.fx.charts.data.DataPoint
import eu.hansolo.fx.heatmap.ColorMapping
import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.layout.StackPane
import javafx.scene.Scene

import scala.compiletime.uninitialized
import java.util.ArrayList
import java.util.List
import java.util.Random



/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i hansolo.heatMaps.run
 * ./mill -i hansolo.arcChart.runMain hansolo.charts.AreaHeatMapTest
 * ./mill -i --watch hansolo.arcChart.runMain hansolo.charts.AreaHeatMapTest
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
class AreaHeatMapTest extends Application {
    private val RND = new Random()
    private var areaHeatMap: AreaHeatMap = uninitialized

    override def init() = {
        val randomPoints: List[DataPoint] = new ArrayList[DataPoint](29)
        //randomPoints.add(new DataPoint(0, 0, 0))
        //randomPoints.add(new DataPoint(400, 0, 0))
        //randomPoints.add(new DataPoint(400, 400, 0))
        //randomPoints.add(new DataPoint(0, 400, 0))
        val counter = 0 until 25
        for (_ <- counter) {
            val x = RND.nextDouble() * 400
            val y = RND.nextDouble() * 400
            val v = RND.nextDouble() * 100 - 50
            randomPoints.add(new DataPoint(x, y, v))
        }

        areaHeatMap = AreaHeatMapBuilder.create()
                                        .prefSize(400, 400)
                                        .colorMapping(ColorMapping.BLUE_CYAN_GREEN_YELLOW_RED)
                                        .quality(Quality.FINE)
                                        .heatMapOpacity(0.5)
                                        .useColorMapping(true)
                                        .dataPointsVisible(true)
                                        .noOfCloserInfluentialPoints(5)
                                        .dataPoints(randomPoints)
                                        .build()
    }

    override def start(stage: Stage) = {
        val pane = new StackPane(areaHeatMap)

        val scene = new Scene(pane)

        stage.setTitle("Area HeatMap")
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
// object AreaHeatMapTest {
//   def main(args: Array[String]) =
//     val app = new AreaHeatMapTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object AreaHeatMapTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[AreaHeatMapTest], args*)
//     }
// }
