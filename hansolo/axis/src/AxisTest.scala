// cSpell:ignore javafx, hansolo

package hansolo.charts


import eu.hansolo.fx.charts.Axis
import eu.hansolo.fx.charts.tools.Helper
import eu.hansolo.toolbox.unit.Converter
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage

import eu.hansolo.toolbox.unit.Category.TEMPERATURE
import eu.hansolo.toolbox.unit.UnitDefinition.CELSIUS
import eu.hansolo.toolbox.unit.UnitDefinition.FAHRENHEIT


import scala.compiletime.uninitialized
import java.util.List


/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i hansolo.axis.run
 * ./mill -i hansolo.axis.runMain hansolo.charts.AxisTest
 * ./mill -i --watch hansolo.axis.runMain hansolo.charts.AxisTest
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
class AxisTest extends Application {
    private val AXIS_WIDTH : Double  = 20
    private val AXIS_HEIGHT: Double = 20

    private var xAxisBottom: Axis = uninitialized
    private var xAxisTop: Axis    = uninitialized
    private var yAxisLeft: Axis   = uninitialized
    private var yAxisRight: Axis  = uninitialized


    override def init() = {
        xAxisBottom = Helper.createBottomAxis(-20, 20, AXIS_HEIGHT)
        xAxisTop    = Helper.createTopAxis(0, 100, AXIS_HEIGHT)
        yAxisLeft   = Helper.createLeftAxis(-20, 20, AXIS_WIDTH)

        val tempConverter     = new Converter(TEMPERATURE, CELSIUS) // Type Temperature with BaseUnit Celsius
        val tempFahrenheitMin = tempConverter.convert(-20, FAHRENHEIT)
        val tempFahrenheitMax = tempConverter.convert(20, FAHRENHEIT)
        yAxisRight = Helper.createRightAxis(tempFahrenheitMin, tempFahrenheitMax, false, AXIS_WIDTH)

        AnchorPane.setTopAnchor(yAxisLeft, AXIS_HEIGHT)
        AnchorPane.setTopAnchor(xAxisTop, 0d)
        AnchorPane.setTopAnchor(yAxisRight, AXIS_HEIGHT)
    }

    override def start(stage: Stage) = {
        val pane = new AnchorPane(xAxisBottom, xAxisTop, yAxisLeft, yAxisRight)
        pane.setPadding(new Insets(10))
        pane.setPrefSize(400, 400)

        val scene = new Scene(pane)

        stage.setTitle("Axis Test")
        stage.setScene(scene)
        stage.show()

        xAxisTop.setMinValue(50)
        xAxisTop.setMaxValue(150)
        //xAxisTop.setAutoFontSize(true)
        //xAxisTop.setTitleFontSize(20)
    }

    override def stop() = {
        System.exit(0)
    }

    def launchIt():Unit = {
        Application.launch()
    }
}

// Not required, not needed
// object AxisTest {
//   def main(args: Array[String]) =
//     val app = new AxisTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object AxisTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[AxisTest], args*)
//     }
// }
