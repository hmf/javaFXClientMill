// cSpell:ignore javafx, hansolo

package hansolo.charts



import eu.hansolo.fx.charts.Axis
import eu.hansolo.fx.charts.AxisType
import eu.hansolo.fx.charts.Position
import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage
import javafx.scene.Scene


import scala.compiletime.uninitialized


/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i hansolo.axis.run
 * ./mill -i hansolo.axis.runMain hansolo.charts.LogAxisTest
 * ./mill -i --watch hansolo.axis.runMain hansolo.charts.LogAxisTest
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
class LogAxisTest extends Application {
    private var xAxisBottom: Axis = uninitialized
    private var yAxisLeft:   Axis = uninitialized
    private var logAxisX:    Axis = uninitialized 


    override def init() = {
        xAxisBottom = new Axis(0, 1000, Orientation.HORIZONTAL, AxisType.LOGARITHMIC, Position.BOTTOM)
        xAxisBottom.setPrefHeight(20)
        AnchorPane.setLeftAnchor(xAxisBottom, 20d)
        AnchorPane.setRightAnchor(xAxisBottom, 20d)
        AnchorPane.setBottomAnchor(xAxisBottom, 0d)

        yAxisLeft = new Axis(0, 1000, Orientation.VERTICAL, AxisType.LOGARITHMIC, Position.LEFT)
        yAxisLeft.setPrefWidth(20)
        AnchorPane.setLeftAnchor(yAxisLeft, 0d)
        AnchorPane.setTopAnchor(yAxisLeft, 20d)
        AnchorPane.setBottomAnchor(yAxisLeft, 20d)
    }

    override def start(stage: Stage) = {
        val pane = new AnchorPane(xAxisBottom, yAxisLeft)
        pane.setPadding(new Insets(10))
        pane.setPrefSize(400, 400)

        val scene = new Scene(pane)

        stage.setTitle("Title")
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
// object LogAxisTest {
//   def main(args: Array[String]) =
//     val app = new LogAxisTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object LogAxisTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[LogAxisTest], args*)
//     }
// }
