// cSpell:ignore javafx, hansolo, voronoi

package hansolo.charts

import eu.hansolo.fx.charts.wafermap.DieMap
import eu.hansolo.fx.charts.wafermap.DieMapBuilder
import eu.hansolo.fx.charts.wafermap.KLA
import eu.hansolo.fx.charts.wafermap.KLAParser
import eu.hansolo.fx.charts.wafermap.WaferMap
import eu.hansolo.fx.charts.wafermap.WaferMapBuilder
import eu.hansolo.fx.heatmap.ColorMapping
import javafx.application.Application
import javafx.application.Platform
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.stage.Stage

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
// import java.util.ArrayList
// import java.util.Random
// import java.util.List
import java.util.Optional

import scala.compiletime.uninitialized
import scala.jdk.CollectionConverters.*
//import scala.collection.JavaConverters.*
import java.{util => ju}

/**
 * 
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i hansolo.maps.run
 * ./mill -i hansolo.maps.runMain hansolo.charts.WaferMapTest
 * ./mill -i --watch hansolo.maps.runMain hansolo.charts.WaferMapTest
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


class WaferMapTest extends Application {
    private var wafermap: WaferMap = uninitialized
    private var dieMap: DieMap     = uninitialized


    override def init() = {
        val filename: String        = getClass().getResource("/12.KLA").toString().replace("file:", "")
        val klaOpt: Optional[KLA]   = KLAParser.INSTANCE.parse(filename)

        //System.out.println(klaOpt.get())

        wafermap = WaferMapBuilder.create()
                                  .kla(klaOpt.get())
                                  .dieTextVisible(true)
                                  .densityColorsVisible(true)
                                  .defectsVisible(true)
                                  .heatmapVisible(true)
                                  .heatmapColorMapping(ColorMapping.BLUE_CYAN_GREEN_YELLOW_RED)
                                  .heatmapSpotRadius(7)
                                  .heatmapOpacity(0.75)
                                  .waferFill(Color.LIGHTGRAY)
                                  .waferStroke(Color.BLACK)
                                  .dieTextFill(Color.BLACK)
                                  .build()

        dieMap = DieMapBuilder.create()
                              .dieTextFill(Color.LIGHTGRAY)
                              .dieTextVisible(true)
                              .densityColorsVisible(false)
                              .build()

        wafermap.selectedDieProperty().addListener(o => Platform.runLater(() => dieMap.setDie(wafermap.getSelectedDie())))
    }

    override def start(stage: Stage) = {
        val pane: HBox  = new HBox(20, wafermap, dieMap)
        pane.setPadding(new Insets(10))
        val scene: Scene = new Scene(pane, Color.DARKGRAY)

        stage.setTitle("Wafermap")
        stage.setScene(scene)
        stage.setResizable(false)
        stage.show()
    }

    override def stop() = {
        wafermap.dispose()
        Platform.exit()
        System.exit(0)
    }

    def launchIt():Unit = {
        Application.launch()
    }

}

// Not required, not needed
// object WaferMapTest {
//   def main(args: Array[String]) =
//     val app = new WaferMapTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object WaferMapTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[WaferMapTest], args*)
//     }
// }
