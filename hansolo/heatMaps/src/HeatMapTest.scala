// cSpell:ignore javafx, hansolo

package hansolo.charts

import eu.hansolo.fx.heatmap.ColorMapping
import eu.hansolo.fx.heatmap.HeatMap
import eu.hansolo.fx.heatmap.HeatMapBuilder
import eu.hansolo.fx.heatmap.OpacityDistribution
import javafx.application.Application
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import javafx.scene.layout.StackPane
import javafx.scene.Scene

import scala.compiletime.uninitialized

/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * X ./mill -i hansolo.heatMaps.run
 * ./mill -i hansolo.heatMaps.runMain hansolo.charts.HeatMapTest
 * ./mill -i --watch hansolo.heatMaps.runMain hansolo.charts.HeatMapTest
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
class HeatMapTest extends Application {
    private var heatMap: HeatMap = uninitialized

    override def init() = {
        heatMap = HeatMapBuilder.create()
                                .prefSize(400, 400)
                                .colorMapping(ColorMapping.INFRARED_4)
                                .spotRadius(20)
                                .opacityDistribution(OpacityDistribution.CUSTOM)
                                .fadeColors(true)
                                .build()

        heatMap.setOnMouseMoved(e => heatMap.addSpot(e.getX(), e.getY()))

    }

    override def start(stage: Stage) = {
        val pane = new StackPane(heatMap)

        // Setup a mouse event filter which adds spots to the heatmap as soon as the mouse will be moved across the pane
        pane.addEventFilter(MouseEvent.MOUSE_MOVED, event => {
            var x = event.getX()
            var y = event.getY()
            if (x < heatMap.getSpotRadius()) x = heatMap.getSpotRadius()
            if (x > pane.getWidth() - heatMap.getSpotRadius()) x = pane.getWidth() - heatMap.getSpotRadius()
            if (y < heatMap.getSpotRadius()) y = heatMap.getSpotRadius()
            if (y > pane.getHeight() - heatMap.getSpotRadius()) y = pane.getHeight() - heatMap.getSpotRadius()

            heatMap.addSpot(x, y)
        })
        pane.widthProperty().addListener((ov, oldWidth, newWidth) => heatMap.setSize(newWidth.doubleValue(), pane.getHeight()))
        pane.heightProperty().addListener((ov, oldHeight, newHeight) => heatMap.setSize(pane.getWidth(), newHeight.doubleValue()))

        val scene = new Scene(pane, 400, 400)

        stage.setTitle("HeatMap (move mouse over pane)")
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
// object HeatMapTest {
//   def main(args: Array[String]) =
//     val app = new HeatMapTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object HeatMapTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[HeatMapTest], args*)
//     }
// }
