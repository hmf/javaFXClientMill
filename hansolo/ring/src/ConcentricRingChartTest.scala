package hansolo.charts

// cSpell:ignore javafx, hansolo


import eu.hansolo.fx.charts.ConcentricRingChart
import eu.hansolo.fx.charts.ConcentricRingChartBuilder
import eu.hansolo.fx.charts.data.ChartItem
import eu.hansolo.fx.charts.data.ChartItemBuilder
import eu.hansolo.fx.charts.tools.NumberFormat
import eu.hansolo.fx.charts.tools.Order
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.stage.Stage

import java.util.Random

import scala.compiletime.uninitialized
// import scala.collection.convert.ImplicitConversionsToScala.*


/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i hansolo.ring.run
 * ./mill -i hansolo.ring.runMain hansolo.charts.ConcentricRingChartTest
 * ./mill -i --watch hansolo.ring.runMain hansolo.charts.ConcentricRingChartTest
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
 * @see https://github.com/HanSolo/charts/blob/master/src/test/java/eu/hansolo/fx/charts/ConcentricRingChartTest.java
 */
class ConcentricRingChartTest extends Application {
    private val RND: Random = new Random()
    private var chart1Data1: ChartItem = uninitialized
    private var chart1Data2: ChartItem = uninitialized
    private var chart1Data3: ChartItem = uninitialized
    private var chart1Data4: ChartItem = uninitialized
    private var chart1Data5: ChartItem = uninitialized
    private var chart1Data6: ChartItem = uninitialized
    private var chart1Data7: ChartItem = uninitialized
    private var chart1Data8: ChartItem = uninitialized
    private var chart: ConcentricRingChart = uninitialized
    private var lastTimerCall: Long        = uninitialized               
    private var timer: AnimationTimer      = uninitialized


    override def init() = {
        chart1Data1 = ChartItemBuilder.create().name("Item 1").fill(Color.web("#3552a0")).textFill(Color.WHITE).animated(true).build()
        chart1Data2 = ChartItemBuilder.create().name("Item 2").fill(Color.web("#45a1cf")).textFill(Color.WHITE).animated(true).build()
        chart1Data3 = ChartItemBuilder.create().name("Item 3").fill(Color.web("#45cf6d")).textFill(Color.WHITE).animated(true).build()
        chart1Data4 = ChartItemBuilder.create().name("Item 4").fill(Color.web("#e3eb4f")).textFill(Color.BLACK).animated(true).build()
        chart1Data5 = ChartItemBuilder.create().name("Item 5").fill(Color.web("#efb750")).textFill(Color.WHITE).animated(true).build()
        chart1Data6 = ChartItemBuilder.create().name("Item 6").fill(Color.web("#ef9850")).textFill(Color.WHITE).animated(true).build()
        chart1Data7 = ChartItemBuilder.create().name("Item 7").fill(Color.web("#ef6050")).textFill(Color.WHITE).animated(true).build()
        chart1Data8 = ChartItemBuilder.create().name("Item 8").fill(Color.web("#a54237")).textFill(Color.WHITE).animated(true).build()

        chart = ConcentricRingChartBuilder.create()
                                          .prefSize(400, 400)
                                          .items(chart1Data1, chart1Data2, chart1Data3, chart1Data4,
                                                 chart1Data5, chart1Data6, chart1Data7, chart1Data8)
                                          .sorted(false)
                                          .order(Order.DESCENDING)
                                          //.barBackgroundColor(Color.BLACK)
                                          .numberFormat(NumberFormat.PERCENTAGE_1_DECIMAL)
                                          .itemLabelFill(Color.BLACK)
                                          //.shortenNumbers(true)
                                          .build()

        lastTimerCall = System.nanoTime()
        timer = new AnimationTimer() {
            override def handle(now: Long) = {
                if (now > lastTimerCall + 1_000_000_000l) {
                    chart1Data1.setValue(RND.nextDouble() * 20)
                    chart1Data2.setValue(RND.nextDouble() * 20)
                    chart1Data3.setValue(RND.nextDouble() * 20)
                    chart1Data4.setValue(RND.nextDouble() * 20)
                    chart1Data5.setValue(RND.nextDouble() * 20)
                    chart1Data6.setValue(RND.nextDouble() * 20)
                    chart1Data7.setValue(RND.nextDouble() * 20)
                    chart1Data8.setValue(RND.nextDouble() * 20)

                    lastTimerCall = now
                }
            }
        }
    }

    override def start(stage: Stage) = {
        val pane = new StackPane(chart)
        pane.setPadding(new Insets(10))

        val scene = new Scene(pane)

        stage.setTitle("ConcentricRingChart")
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
// object ConcentricRingChartTest {
//   def main(args: Array[String]) =
//     val app = new ConcentricRingChartTest
//     app.launchIt()
// 
// }

// Not required, not needed
object ConcentricRingChartTest {

    def main(args: Array[String]): Unit = {
    Application.launch(classOf[ConcentricRingChartTest], args*)
    }
}
