// cSpell:ignore javafx, hansolo

package hansolo.charts


import eu.hansolo.fx.charts.BubbleGridChart
import eu.hansolo.fx.charts.BubbleGridChartBuilder
import eu.hansolo.fx.charts.data.BubbleGridChartItem
import eu.hansolo.fx.charts.data.BubbleGridChartItemBuilder
import eu.hansolo.fx.charts.data.ChartItem
import eu.hansolo.fx.charts.data.ChartItemBuilder
import eu.hansolo.fx.charts.tools.Helper
import eu.hansolo.fx.charts.tools.Order
import eu.hansolo.fx.charts.tools.Topic
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.stage.Stage

import java.util.List
import java.util.Locale
import java.util.Random


import scala.compiletime.uninitialized


/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * X ./mill -i hansolo.bubble.run
 * ./mill -i hansolo.bubble.runMain hansolo.charts.BubbleGridChartTest
 * ./mill -i --watch hansolo.bubble.runMain hansolo.charts.BubbleGridChartTest
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
class BubbleGridChartTest extends Application {
    private val RND: Random = new Random()

    private var bubbleGridChart: BubbleGridChart = uninitialized
    private var peaches1: BubbleGridChartItem = uninitialized
    private var peaches2: BubbleGridChartItem = uninitialized
    private var peaches3: BubbleGridChartItem = uninitialized
    private var peaches4: BubbleGridChartItem = uninitialized
    private var peaches5: BubbleGridChartItem = uninitialized
    private var peaches6: BubbleGridChartItem = uninitialized
    private var peaches7: BubbleGridChartItem = uninitialized
    private var peaches8: BubbleGridChartItem = uninitialized
    private var lastTimerCall: Long = uninitialized
    private var timer: AnimationTimer = uninitialized



    override def init() = {
        // Setup Data

        // Y categories
        val ripe: ChartItem                = ChartItemBuilder.create().name("Ripe").index(0).fill(Color.BLUE).build()
        val unripe: ChartItem              = ChartItemBuilder.create().name("Unripe").index(1).fill(Color.BLUE).build()
        val eatenByBirds: ChartItem        = ChartItemBuilder.create().name("Eaten by birds").index(2).fill(Color.ORANGE).build()
        val eatenByCaterpillars: ChartItem = ChartItemBuilder.create().name("Eaten by caterpillars").index(3).fill(Color.LIGHTBLUE).build()
        val hailDamaged: ChartItem         = ChartItemBuilder.create().name("Hail damaged").index(4).fill(Color.LIGHTBLUE).build()
        val notEnoughWater: ChartItem      = ChartItemBuilder.create().name("Not enough water").index(5).fill(Color.LIGHTBLUE).build()
        val mouldy: ChartItem              = ChartItemBuilder.create().name("Mouldy").index(6).fill(Color.LIGHTBLUE).build()
        val rotten: ChartItem              = ChartItemBuilder.create().name("Rotten").index(7).fill(Color.LIGHTBLUE).build()

        // X categories
        val peaches: ChartItem  = ChartItemBuilder.create().name("Peaches").index(0).fill(Color.ORANGERED).build()
        val apples: ChartItem   = ChartItemBuilder.create().name("Apples").index(1).fill(Color.LIMEGREEN).build()
        val pears: ChartItem    = ChartItemBuilder.create().name("Pears").index(2).fill(Color.ORANGE).build()
        val plums: ChartItem    = ChartItemBuilder.create().name("Plums").index(3).fill(Color.PURPLE).build()
        val apricots: ChartItem = ChartItemBuilder.create().name("Apricots").index(4).fill(Color.DARKORANGE).build()

        // Dataset
        peaches1  = BubbleGridChartItemBuilder.create().categoryXItem(peaches).categoryYItem(ripe).value(60).fill(Color.BLUE).build()
        peaches2  = BubbleGridChartItemBuilder.create().categoryXItem(peaches).categoryYItem(unripe).value(5).fill(Color.BLUE).build()
        peaches3  = BubbleGridChartItemBuilder.create().categoryXItem(peaches).categoryYItem(eatenByBirds).value(10).fill(Color.BLUE).build()
        peaches4  = BubbleGridChartItemBuilder.create().categoryXItem(peaches).categoryYItem(eatenByCaterpillars).value(0).fill(Color.BLUE).build()
        peaches5  = BubbleGridChartItemBuilder.create().categoryXItem(peaches).categoryYItem(hailDamaged).value(10).fill(Color.BLUE).build()
        peaches6  = BubbleGridChartItemBuilder.create().categoryXItem(peaches).categoryYItem(notEnoughWater).value(0).fill(Color.BLUE).build()
        peaches7  = BubbleGridChartItemBuilder.create().categoryXItem(peaches).categoryYItem(mouldy).value(5).fill(Color.BLUE).build()
        peaches8  = BubbleGridChartItemBuilder.create().categoryXItem(peaches).categoryYItem(rotten).value(10).fill(Color.BLUE).build()

        val apples1: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(apples).categoryYItem(ripe).value(90).fill(Color.BLUE).build()
        val apples2: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(apples).categoryYItem(unripe).value(0).fill(Color.BLUE).build()
        val apples3: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(apples).categoryYItem(eatenByBirds).value(0).fill(Color.BLUE).build()
        val apples4: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(apples).categoryYItem(eatenByCaterpillars).value(3).fill(Color.BLUE).build()
        val apples5: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(apples).categoryYItem(hailDamaged).value(2).fill(Color.BLUE).build()
        val apples6: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(apples).categoryYItem(notEnoughWater).value(0).fill(Color.BLUE).build()
        val apples7: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(apples).categoryYItem(mouldy).value(0).fill(Color.BLUE).build()
        val apples8: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(apples).categoryYItem(rotten).value(5).fill(Color.BLUE).build()

        val pears1: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(pears).categoryYItem(ripe).value(30).fill(Color.BLUE).build()
        val pears2: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(pears).categoryYItem(unripe).value(40).fill(Color.BLUE).build()
        val pears3: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(pears).categoryYItem(eatenByBirds).value(5).fill(Color.BLUE).build()
        val pears4: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(pears).categoryYItem(eatenByCaterpillars).value(10).fill(Color.BLUE).build()
        val pears5: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(pears).categoryYItem(hailDamaged).value(5).fill(Color.BLUE).build()
        val pears6: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(pears).categoryYItem(notEnoughWater).value(0).fill(Color.BLUE).build()
        val pears7: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(pears).categoryYItem(mouldy).value(0).fill(Color.BLUE).build()
        val pears8: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(pears).categoryYItem(rotten).value(10).fill(Color.BLUE).build()

        val plums1: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(plums).categoryYItem(ripe).value(15).fill(Color.BLUE).build()
        val plums2: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(plums).categoryYItem(unripe).value(5).fill(Color.BLUE).build()
        val plums3: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(plums).categoryYItem(eatenByBirds).value(30).fill(Color.BLUE).build()
        val plums4: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(plums).categoryYItem(eatenByCaterpillars).value(0).fill(Color.BLUE).build()
        val plums5: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(plums).categoryYItem(hailDamaged).value(2).fill(Color.BLUE).build()
        val plums6: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(plums).categoryYItem(notEnoughWater).value(0).fill(Color.BLUE).build()
        val plums7: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(plums).categoryYItem(mouldy).value(5).fill(Color.BLUE).build()
        val plums8: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(plums).categoryYItem(rotten).value(43).fill(Color.BLUE).build()

        val apricots1: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(apricots).categoryYItem(ripe).value(20).fill(Color.BLUE).build()
        val apricots2: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(apricots).categoryYItem(unripe).value(40).fill(Color.BLUE).build()
        val apricots3: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(apricots).categoryYItem(eatenByBirds).value(5).fill(Color.BLUE).build()
        val apricots4: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(apricots).categoryYItem(eatenByCaterpillars).value(0).fill(Color.BLUE).build()
        val apricots5: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(apricots).categoryYItem(hailDamaged).value(15).fill(Color.BLUE).build()
        val apricots6: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(apricots).categoryYItem(notEnoughWater).value(1).fill(Color.BLUE).build()
        val apricots7: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(apricots).categoryYItem(mouldy).value(14).fill(Color.BLUE).build()
        val apricots8: BubbleGridChartItem  = BubbleGridChartItemBuilder.create().categoryXItem(apricots).categoryYItem(rotten).value(5).fill(Color.BLUE).build()
        
        val chartItems: List[BubbleGridChartItem] = List.of(peaches1, peaches2, peaches3, peaches4, peaches5, peaches6, peaches7, peaches8,
                                                       apples1, apples2, apples3, apples4, apples5, apples6, apples7, apples8,
                                                       pears1, pears2, pears3, pears4, pears5, pears6, pears7, pears8,
                                                       plums1, plums2, plums3, plums4, plums5, plums6, plums7, plums8,
                                                       apricots1, apricots2, apricots3, apricots4, apricots5, apricots6, apricots7, apricots8)

        // Setup Chart
        bubbleGridChart = BubbleGridChartBuilder.create()
                                                .chartBackground(Color.WHITE)
                                                .textColor(Color.BLACK)
                                                .gridColor(Color.rgb(0, 0, 0, 0.1))
                                                .showGrid(true)
                                                .showValues(true)
                                                .showPercentage(true)
                                                .items(chartItems)
                                                .shortenNumbers(true)
                                                .sortCategoryX(Topic.NAME, Order.ASCENDING)
                                                .sortCategoryY(Topic.VALUE, Order.DESCENDING)
                                                .useXCategoryFill()
                                                .autoBubbleTextColor(true)
                                                .useGradientFill(false)
                                                .gradient(new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                                                                             new Stop(0.00, Color.web("#2C67D5")),
                                                                             new Stop(0.25, Color.web("#00BF6C")),
                                                                             new Stop(0.50, Color.web("#FFD338")),
                                                                             new Stop(0.75, Color.web("#FF8235")),
                                                                             new Stop(1.00, Color.web("#F23C5A"))))
                                                .build()

        lastTimerCall = System.nanoTime()
        timer         = new AnimationTimer() {
            override def handle(now: Long) = {
                if (now > lastTimerCall + 2_000_000_000l) {
                    peaches1.setValue(RND.nextInt(60))
                    peaches2.setValue(RND.nextInt(60))
                    peaches3.setValue(RND.nextInt(60))
                    peaches4.setValue(RND.nextInt(60))
                    peaches5.setValue(RND.nextInt(60))
                    peaches6.setValue(RND.nextInt(60))
                    peaches7.setValue(RND.nextInt(60))
                    peaches8.setValue(RND.nextInt(60))
                    lastTimerCall = now
                }
            }
        }
    }

    override def start(stage: Stage) = {
        val pane = new StackPane(bubbleGridChart)
        pane.setPadding(new Insets(10))

        val scene = new Scene(pane, 1240, 1000)

        stage.setTitle("Bubble Grid Chart")
        stage.setScene(scene)
        stage.show()

        //timer.start()
    }

    override def stop() = {
        System.exit(0)
    }

    def launchIt():Unit = {
        Application.launch()
    }
}


// Not required, not needed
// object BubbleGridChartTest {
//   def main(args: Array[String]) =
//     val app = new BubbleGridChartTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object BubbleGridChartTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[BubbleGridChartTest], args*)
//     }
// }
