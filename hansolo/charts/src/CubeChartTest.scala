package hansolo.charts

// cSpell:ignore javafx, hansolo


import eu.hansolo.fx.charts.CubeChart
import eu.hansolo.fx.charts.CubeChartBuilder
import javafx.animation.AnimationTimer
import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.util.Duration

import java.util.Random

import scala.util
import scala.compiletime.uninitialized
import scala.util.boundary, boundary.break
// import scala.collection.convert.ImplicitConversionsToScala.*


/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * X ./mill -i hansolo.charts.run
 * ./mill -i hansolo.charts.runMain hansolo.charts.CubeChartTest
 * ./mill -i --watch hansolo.charts.runMain hansolo.charts.CubeChartTest
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
 * @see https://github.com/HanSolo/charts/blob/master/src/test/java/eu/hansolo/fx/charts/CubeChartTest.java
 */
class CubeChartTest extends Application {
    private val RND:Random = new Random()
    private var chart1: CubeChart = uninitialized
    private var chart2: CubeChart = uninitialized
    private var chart3: CubeChart = uninitialized
    private var chart4: CubeChart = uninitialized
    private var lastTimerCall: Long = uninitialized
    private var timer: AnimationTimer = uninitialized
    private var timeline: Timeline = uninitialized


    override def init() = {
        chart1 = CubeChartBuilder.create()
                                 .leftFill(CubeChart.RED_ORANGE_LEFT_FILL)
                                 .rightFill(CubeChart.RED_ORANGE_RIGHT_FILL)
                                 .leftText("OF CODEBASES CONTAINED OPEN SOURCE")
                                 .rightText("OF CODE IN CODEBASES WAS OPEN SOURCE")
                                 .build()
        chart2 = CubeChartBuilder.create()
                                 .leftFill(CubeChart.ORANGE_GREEN_LEFT_FILL)
                                 .rightFill(CubeChart.ORANGE_GREEN_RIGHT_FILL)
                                 .build()
        chart3 = CubeChartBuilder.create()
                                 .leftFill(CubeChart.GREEN_BLUE_LEFT_FILL)
                                 .rightFill(CubeChart.GREEN_BLUE_RIGHT_FILL)
                                 .build()
        chart4 = CubeChartBuilder.create()
                                 .leftFill(CubeChart.BLUE_PURPLE_LEFT_FILL)
                                 .rightFill(CubeChart.BLUE_PURPLE_RIGHT_FILL)
                                 .build()
        lastTimerCall = System.nanoTime()
        timer = new AnimationTimer() {
            override def handle(now: Long) = {
                if (now > lastTimerCall + 1_500_000_000) {
                    timeline.stop()
                    val kv1_1 = new KeyValue(chart1.leftValueProperty(), chart1.getLeftValue(), Interpolator.EASE_BOTH)
                    val kv2_1 = new KeyValue(chart1.leftValueProperty(), RND.nextDouble(), Interpolator.EASE_BOTH)
                    val kv3_1 = new KeyValue(chart1.rightValueProperty(), chart1.getRightValue(), Interpolator.EASE_BOTH)
                    val kv4_1 = new KeyValue(chart1.rightValueProperty(), RND.nextDouble(), Interpolator.EASE_BOTH)

                    val kv1_2 = new KeyValue(chart2.leftValueProperty(), chart2.getLeftValue(), Interpolator.EASE_BOTH)
                    val kv2_2 = new KeyValue(chart2.leftValueProperty(), RND.nextDouble(), Interpolator.EASE_BOTH)
                    val kv3_2 = new KeyValue(chart2.rightValueProperty(), chart2.getRightValue(), Interpolator.EASE_BOTH)
                    val kv4_2 = new KeyValue(chart2.rightValueProperty(), RND.nextDouble(), Interpolator.EASE_BOTH)

                    val kv1_3 = new KeyValue(chart3.leftValueProperty(), chart3.getLeftValue(), Interpolator.EASE_BOTH)
                    val kv2_3 = new KeyValue(chart3.leftValueProperty(), RND.nextDouble(), Interpolator.EASE_BOTH)
                    val kv3_3 = new KeyValue(chart3.rightValueProperty(), chart3.getRightValue(), Interpolator.EASE_BOTH)
                    val kv4_3 = new KeyValue(chart3.rightValueProperty(), RND.nextDouble(), Interpolator.EASE_BOTH)

                    val kv1_4 = new KeyValue(chart4.leftValueProperty(), chart4.getLeftValue(), Interpolator.EASE_BOTH)
                    val kv2_4 = new KeyValue(chart4.leftValueProperty(), RND.nextDouble(), Interpolator.EASE_BOTH)
                    val kv3_4 = new KeyValue(chart4.rightValueProperty(), chart4.getRightValue(), Interpolator.EASE_BOTH)
                    val kv4_4 = new KeyValue(chart4.rightValueProperty(), RND.nextDouble(), Interpolator.EASE_BOTH)

                    val kf1 = new KeyFrame(Duration.ZERO, kv1_1, kv3_1, kv1_2, kv3_2, kv1_3, kv3_3, kv1_4, kv3_4)
                    val kf2 = new KeyFrame(Duration.millis(800), kv2_1, kv4_1, kv2_2, kv4_2, kv2_3, kv4_3, kv2_4, kv4_4)
                    timeline.getKeyFrames().setAll(kf1, kf2)
                    timeline.play()
                    lastTimerCall = now
                }
            }
        }
        timeline = new Timeline()
    }

    override def start(stage: Stage) = {
        val pane = new HBox(0, chart1, chart2, chart3, chart4)
        pane.setPadding(new Insets(10))
        //pane.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), CornerRadii.EMPTY, Insets.EMPTY)))

        val scene = new Scene(pane)

        stage.setTitle("Cube Chart")
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
// object CubeChartTest {
//   def main(args: Array[String]) =
//     val app = new CubeChartTest
//     app.launchIt()
// 
// }

// Not required, not needed
object CubeChartTest {

    def main(args: Array[String]): Unit = {
    Application.launch(classOf[CubeChartTest], args*)
    }
}
