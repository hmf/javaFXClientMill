// cSpell:ignore javafx, hansolo, voronoi

package hansolo.charts

import eu.hansolo.fx.charts.voronoi.VPoint
import eu.hansolo.fx.charts.voronoi.VoronoiChart
import eu.hansolo.fx.charts.voronoi.VoronoiChart.Type
import eu.hansolo.fx.charts.voronoi.VoronoiChartBuilder
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.stage.Stage

// import eu.hansolo.fx.charts.Position // TODO
// import eu.hansolo.fx.charts.AxisBuilder // TODO
// import eu.hansolo.fx.charts.Prediction // TODO
// import eu.hansolo.fx.charts.XYPane // TODO
// import eu.hansolo.fx.charts.Axis // TODO
// import eu.hansolo.fx.charts.AxisType // TODO
// import eu.hansolo.fx.charts.XYChart // TODO
// import eu.hansolo.fx.charts.Grid // TODO
// import eu.hansolo.fx.charts.data.XYItem // TODO
// import eu.hansolo.fx.charts.ChartType // TODO


// import java.time.LocalDateTime
// import java.time.temporal.ChronoUnit
// import java.util.Arrays
import java.util.ArrayList
import java.util.Random
import java.util.List

import scala.compiletime.uninitialized
import scala.jdk.CollectionConverters.*
//import scala.collection.JavaConverters.*
import java.{util => ju}

/**
 * 
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i hansolo.voronoi.run
 * ./mill -i hansolo.voronoi.runMain hansolo.charts.VoronoiChartTest
 * ./mill -i --watch hansolo.voronoi.runMain hansolo.charts.VoronoiChartTest
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
class VoronoiChartTest extends Application {
    private val RND : Random    = new Random()
    private val WIDTH : Double  = 600
    private val HEIGHT : Double = 600
    private var voronoiChart: VoronoiChart = uninitialized


    override def init() = {
        val vPoints: List[VPoint] = new ArrayList[VPoint]()
        for
            i <- 0 until 25
        do
            vPoints.add(new VPoint(RND.nextDouble() * WIDTH, RND.nextDouble() * HEIGHT))

        voronoiChart = VoronoiChartBuilder.create()
                                          .prefSize(WIDTH, HEIGHT)
                                          .`type`(Type.VORONOI)               // Type of diagram (VORONOI, DELAUNY)
                                          .borderColor(Color.BLACK)           // Color of line between regions
                                          .multiColor(true)                   // Randomly created fill colors for regions
                                          .pointsVisible(true)                // Points visible
                                          .pointColor(Color.BLACK)            // Color of points
                                          .fillRegions(true)                  // Fill regions, otherwise only the borders will be visible
                                          .interactive(true)                  // When true new points can be added by clicking in the diagram
                                          .voronoiColor(Color.ORANGERED)      // Fill color for voronoi regions if multicolor == false
                                          .delaunayColor(Color.YELLOWGREEN)   // Fill color for delauny regions if multicolor == false
                                          .points(vPoints)
                                          .build()

        registerListener()
    }

    private def registerListener() = {

    }

    override def start(stage: Stage) = {
        val pane: StackPane  = new StackPane(voronoiChart)
        val scene: Scene     = new Scene(pane)

        stage.setTitle("VoronoiChart")
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
// object VoronoiChartTest {
//   def main(args: Array[String]) =
//     val app = new VoronoiChartTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object VoronoiChartTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[VoronoiChartTest], args*)
//     }
// }
