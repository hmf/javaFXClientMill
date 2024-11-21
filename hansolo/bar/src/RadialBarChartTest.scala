// cSpell:ignore javafx, hansolo

package hansolo.charts


import eu.hansolo.fx.charts.SectorChart
import eu.hansolo.fx.charts.SectorChartBuilder
import eu.hansolo.fx.charts.data.ChartItem
import eu.hansolo.fx.charts.data.ChartItemBuilder
import eu.hansolo.fx.charts.series.ChartItemSeries
import eu.hansolo.fx.charts.series.ChartItemSeriesBuilder
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.stage.Stage

import java.util.List
import java.util.Random

import scala.compiletime.uninitialized


/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * X ./mill -i hansolo.bar.run
 * ./mill -i hansolo.bar.runMain hansolo.charts.RadialBarChartTest
 * ./mill -i --watch hansolo.bar.runMain hansolo.charts.RadialBarChartTest
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
class RadialBarChartTest extends Application {
    private val RND: Random = new Random()
    private var chart:         SectorChart    = uninitialized
    private var lastTimerCall: Long           = uninitialized
    private var timer:         AnimationTimer = uninitialized


    override def init() = {
        val porsche911: ChartItem      = ChartItemBuilder.create().name("911").textFill(Color.WHITE).value(120).fill(Color.rgb(19, 126, 140)).build()
        val porscheTaycan: ChartItem   = ChartItemBuilder.create().name("Taycan").textFill(Color.WHITE).value(45).fill(Color.rgb(19, 126, 140)).build()
        val porschePanamera: ChartItem = ChartItemBuilder.create().name("Panamera").textFill(Color.WHITE).value(80).fill(Color.rgb(19, 126, 140)).build()
        val porscheMacan    = ChartItemBuilder.create().name("Macan").textFill(Color.WHITE).value(20).fill(Color.rgb(19, 126, 140)).build()
        val porscheCayenne  = ChartItemBuilder.create().name("Cayenne").textFill(Color.WHITE).value(10).fill(Color.rgb(19, 126, 140)).build()
        val porscheMacan2   = ChartItemBuilder.create().name("Macan").textFill(Color.WHITE).value(20).fill(Color.rgb(19, 126, 140)).build()
        val porscheCayenne2 = ChartItemBuilder.create().name("Cayenne").textFill(Color.WHITE).value(10).fill(Color.rgb(19, 126, 140)).build()
        val porsche: ChartItemSeries[ChartItem] = ChartItemSeriesBuilder.create().name("Porsche").textFill(Color.WHITE).fill(Color.rgb(79, 186, 200)).items(porsche911, porscheTaycan, porschePanamera, porscheMacan, porscheCayenne, porscheMacan2, porscheCayenne2).build().asInstanceOf[ChartItemSeries[ChartItem]]

        val lamboAventador  = ChartItemBuilder.create().name("Aventador").textFill(Color.WHITE).value(31).fill(Color.rgb(236, 237, 150)).build()
        val lamboHuracan    = ChartItemBuilder.create().name("Huracan").textFill(Color.WHITE).value(40).fill(Color.rgb(236, 237, 150)).build()
        val lamboUrus       = ChartItemBuilder.create().name("Urus").textFill(Color.WHITE).value(25).fill(Color.rgb(236, 237, 150)).build()
        val lamboSian       = ChartItemBuilder.create().name("Sian").textFill(Color.WHITE).value(10).fill(Color.rgb(236, 237, 150)).build()
        val lamboUrus2      = ChartItemBuilder.create().name("Urus").textFill(Color.WHITE).value(25).fill(Color.rgb(236, 237, 150)).build()
        val lamboSian2      = ChartItemBuilder.create().name("Sian").textFill(Color.WHITE).value(10).fill(Color.rgb(236, 237, 150)).build()
        val lamborghini: ChartItemSeries[ChartItem] = ChartItemSeriesBuilder.create().name("Lamborghini").textFill(Color.WHITE).fill(Color.rgb(253, 223, 177)).items(lamboAventador, lamboHuracan, lamboUrus, lamboSian, lamboUrus2, lamboSian2).build().asInstanceOf[ChartItemSeries[ChartItem]]

        val ferrari812        = ChartItemBuilder.create().name("812").textFill(Color.WHITE).description("Ferrari 812").value(13).fill(Color.rgb(247, 73, 74)).build()
        val ferrari296        = ChartItemBuilder.create().name("296").textFill(Color.WHITE).value(21).fill(Color.rgb(247, 73, 74)).build()
        val ferrariSf90       = ChartItemBuilder.create().name("SF 90").textFill(Color.WHITE).value(32).fill(Color.rgb(247, 73, 74)).build()
        val ferrariF8         = ChartItemBuilder.create().name("F8").textFill(Color.WHITE).value(11).fill(Color.rgb(247, 73, 74)).build()
        val ferrariRoma       = ChartItemBuilder.create().name("Roma").textFill(Color.WHITE).value(29).fill(Color.rgb(247, 73, 74)).build()
        val ferrariPortofino  = ChartItemBuilder.create().name("Portofino").textFill(Color.WHITE).value(38).fill(Color.rgb(247, 73, 74)).build()
        val ferrariRoma2      = ChartItemBuilder.create().name("Roma").textFill(Color.WHITE).value(29).fill(Color.rgb(247, 73, 74)).build()
        val ferrariPortofino2 = ChartItemBuilder.create().name("Portofino").textFill(Color.WHITE).value(38).fill(Color.rgb(247, 73, 74)).build()
        val ferrari: ChartItemSeries[ChartItem] = ChartItemSeriesBuilder.create().name("Ferrari").textFill(Color.WHITE).fill(Color.rgb(244, 194, 184)).items(ferrari812, ferrari296, ferrariSf90, ferrariF8, ferrariRoma, ferrariPortofino, ferrariRoma2, ferrariPortofino2).build().asInstanceOf[ChartItemSeries[ChartItem]]

        val golf1               = ChartItemBuilder.create().name("812").textFill(Color.WHITE).description("Ferrari 812").value(13).fill(Color.rgb(42, 144, 89)).build()
        val passat1             = ChartItemBuilder.create().name("296").textFill(Color.WHITE).value(21).fill(Color.rgb(42, 144, 89)).build()
        val polo1               = ChartItemBuilder.create().name("SF 90").textFill(Color.WHITE).value(32).fill(Color.rgb(42, 144, 89)).build()
        val bulli1              = ChartItemBuilder.create().name("F8").textFill(Color.WHITE).value(11).fill(Color.rgb(42, 144, 89)).build()
        val phaeton             = ChartItemBuilder.create().name("Roma").textFill(Color.WHITE).value(29).fill(Color.rgb(42, 144, 89)).build()
        val tuareg              = ChartItemBuilder.create().name("Portofino").textFill(Color.WHITE).value(38).fill(Color.rgb(42, 144, 89)).build()
        val tuareg2             = ChartItemBuilder.create().name("Roma").textFill(Color.WHITE).value(29).fill(Color.rgb(42, 144, 89)).build()
        val beetle              = ChartItemBuilder.create().name("Portofino").textFill(Color.WHITE).value(38).fill(Color.rgb(42, 144, 89)).build()
        val vw: ChartItemSeries[ChartItem] = ChartItemSeriesBuilder.create().name("VW").textFill(Color.WHITE).fill(Color.rgb(102, 204, 159)).items(golf1, passat1, polo1, bulli1, phaeton, tuareg, tuareg2, beetle).build().asInstanceOf[ChartItemSeries[ChartItem]]

        val allSeries: List[ChartItemSeries[ChartItem]] = List.of(porsche, lamborghini, ferrari, vw)

        chart = SectorChartBuilder.create()
                                  .prefSize(600, 600)
                                  .radialBarChartMode(true)
                                  .itemTextVisible(true)
                                  .seriesTextVisible(true)
                                  .seriesSumTextVisible(false)
                                  .gridColor(Color.DARKGRAY)
                                  .threshold(50)
                                  .thresholdColor(Color.LIME)
                                  .thresholdVisible(true)
                                  .seriesBackgroundVisible(false)
                                  .allSeries(allSeries)
                                  .build()

        lastTimerCall = System.nanoTime()
        timer         = new AnimationTimer() {
            override def handle(now: Long) = {
                if (now > lastTimerCall + 200_000_000l) {
                    porsche.getItems().forEach(item => item.setValue(RND.nextDouble() * 100))
                    ferrari.getItems().forEach(item => item.setValue(RND.nextDouble() * 100))
                    lamborghini.getItems().forEach(item => item.setValue(RND.nextDouble() * 100))
                    vw.getItems().forEach(item => item.setValue(RND.nextDouble() * 100))
                    lastTimerCall = now
                }
            }
        }

        registerListener()
    }

    private def registerListener() = {

    }

    override def start(stage: Stage) = {
        val pane  = new StackPane(chart)
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(7, 36, 56), CornerRadii.EMPTY, Insets.EMPTY)))
        val scene = new Scene(pane)

        stage.setTitle("RadialBarChart")
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
// object RadialBarChartTest {
//   def main(args: Array[String]) =
//     val app = new RadialBarChartTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object RadialBarChartTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[RadialBarChartTest], args*)
//     }
// }
