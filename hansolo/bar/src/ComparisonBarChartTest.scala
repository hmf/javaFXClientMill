// cSpell:ignore javafx, hansolo

package hansolo.charts



import eu.hansolo.fx.charts.ComparisonBarChartBuilder
import eu.hansolo.fx.charts.ComparisonBarChart // TODO
import eu.hansolo.fx.charts.data.ChartItem
import eu.hansolo.fx.charts.data.ChartItemBuilder
import eu.hansolo.toolboxfx.font.Fonts
import eu.hansolo.fx.charts.series.ChartItemSeries
import eu.hansolo.fx.charts.series.ChartItemSeriesBuilder
import eu.hansolo.fx.charts.tools.NumberFormat
import eu.hansolo.fx.charts.tools.Order
import eu.hansolo.fx.charts.Category
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.effect.BlurType
import javafx.scene.effect.DropShadow
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.Region
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Paint
import javafx.scene.paint.Stop
import javafx.scene.text.TextAlignment
import javafx.stage.Stage

import java.util.ArrayList
import java.util.HashMap
import java.util.LinkedList
import java.util.List
import java.util.Map
import java.util.Random

import scala.compiletime.uninitialized


/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * X ./mill -i hansolo.bar.run
 * ./mill -i hansolo.bar.runMain hansolo.charts.ComparisonBarChartTest
 * ./mill -i --watch hansolo.bar.runMain hansolo.charts.ComparisonBarChartTest
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
class ComparisonBarChartTest extends Application {
    private val RND = new Random()
    private var categories: List[Category]                = uninitialized
    private var optionsProductA: Map[Category, ChartItem] = uninitialized
    private var optionsProductB: Map[Category, ChartItem] = uninitialized
    private var chart: ComparisonBarChart                 = uninitialized
    private var lastTimerCall: Long                       = uninitialized
    private var timer: AnimationTimer                     = uninitialized


    override def init() = {
        categories      = new LinkedList[Category]()
        optionsProductA = new HashMap[Category, ChartItem]()
        optionsProductB = new HashMap[Category, ChartItem]()

        val is = 0 until 5
        for (i <- is) {
            val category = new Category("Option " + i)
            categories.add(category)
            optionsProductA.put(category, ChartItemBuilder.create().name("Product A (Option " + i + ")").category(category).value(0).build())
            optionsProductB.put(category, ChartItemBuilder.create().name("Product B (Option " + i + ")").category(category).value(0).build())
        }

        optionsProductA.get(categories.get(0)).setValue(72)
        optionsProductA.get(categories.get(1)).setValue(60)
        optionsProductA.get(categories.get(2)).setValue(100)
        optionsProductA.get(categories.get(3)).setValue(38)
        optionsProductA.get(categories.get(4)).setValue(80)

        optionsProductB.get(categories.get(0)).setValue(95)
        optionsProductB.get(categories.get(1)).setValue(83)
        optionsProductB.get(categories.get(2)).setValue(50)
        optionsProductB.get(categories.get(3)).setValue(100)
        optionsProductB.get(categories.get(4)).setValue(75)

        val series1: ChartItemSeries[ChartItem] = ChartItemSeriesBuilder.create()
                                                                   .name("Product A")
                                                                   .items(new ArrayList[ChartItem](optionsProductA.values()))
                                                                   .fill(new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.rgb(255, 105, 91)), new Stop(1, Color.rgb(217, 41, 76))))
                                                                   .textFill(Color.WHITE)
                                                                   .animated(true)
                                                                   .animationDuration(1000)
                                                                   .build()
                                                                   .asInstanceOf[ChartItemSeries[ChartItem]] // TODO

        val series2: ChartItemSeries[ChartItem] = ChartItemSeriesBuilder.create()
                                                                   .name("Product B")
                                                                   .items(new ArrayList[ChartItem](optionsProductB.values()))
                                                                   .fill(new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.rgb(0, 150, 235)), new Stop(1, Color.rgb(0, 212, 244))))
                                                                   .textFill(Color.WHITE)
                                                                   .animated(true)
                                                                   .animationDuration(1000)
                                                                   .build()
                                                                   .asInstanceOf[ChartItemSeries[ChartItem]] // TODO

        chart = ComparisonBarChartBuilder.create(series1, series2)
                                         .prefSize(600, 300)
                                         .backgroundFill(Color.rgb(244, 250, 255))
                                         .categoryBackgroundFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                                                                                    new Stop(0.0, Color.rgb(244, 250, 255)),
                                                                                    new Stop(0.05, Color.WHITE),
                                                                                    new Stop(0.95, Color.WHITE),
                                                                                    new Stop(1.0, Color.rgb(244, 250, 255))))
                                         .barBackgroundFill(Color.rgb(232, 240, 252))
                                         .barBackgroundVisible(true)
                                         .shadowsVisible(true)
                                         .textFill(Color.WHITE)
                                         .categoryTextFill(Color.rgb(64, 66, 100))
                                         .shortenNumbers(false)
                                         .sorted(false)
                                         .order(Order.DESCENDING)
                                         .numberFormat(NumberFormat.PERCENTAGE)
                                         .doCompare(false)
                                         .categorySumVisible(false)
                                         .betterColor(Color.BLUE)
                                         .poorerColor(Color.RED)
                                         .build()

        AnchorPane.setTopAnchor(chart, 100d)
        AnchorPane.setRightAnchor(chart, 10d)
        AnchorPane.setBottomAnchor(chart, 10d)
        AnchorPane.setLeftAnchor(chart, 10d)

        lastTimerCall = System.nanoTime()
        timer = new AnimationTimer() {
            override def handle(now: Long) = {
                if (now > lastTimerCall + 3_000_000_000l) {
                    categories.forEach(category => {
                        optionsProductA.get(category).setValue(RND.nextDouble() * 75 + 25)
                        optionsProductB.get(category).setValue(RND.nextDouble() * 75 + 25)
                    })

                    lastTimerCall = now
                }
            }
        }
    }

    override def start(stage: Stage) = {
        val productABox = createProductBox("PRODUCT A", "Some text to describe product A", "A", new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.rgb(255, 105, 91)), new Stop(1, Color.rgb(217, 41, 76))), Color.rgb(217, 41, 76), true)
        productABox.setFillHeight(true)
        productABox.setAlignment(Pos.CENTER)
        HBox.setHgrow(productABox, Priority.ALWAYS)

        val spacer = new Region()
        HBox.setHgrow(spacer, Priority.SOMETIMES)

        val productBBox = createProductBox("PRODUCT B", "Some text to describe product B", "B", new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.rgb(0, 150, 235)), new Stop(1, Color.rgb(0, 212, 244))), Color.rgb(0, 150, 235), false)
        productBBox.setFillHeight(true)
        productBBox.setAlignment(Pos.CENTER)
        HBox.setHgrow(productBBox, Priority.ALWAYS)

        val header = new HBox(productABox, spacer, productBBox)
        AnchorPane.setTopAnchor(header, 10d)
        AnchorPane.setRightAnchor(header, 10d)
        AnchorPane.setLeftAnchor(header, 10d)

        val pane = new AnchorPane(header, chart)
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(244, 250, 255), CornerRadii.EMPTY, Insets.EMPTY)))

        pane.setPadding(new Insets(10))

        val scene = new Scene(pane)

        stage.setTitle("ComparisonBarChart")
        stage.setScene(scene)
        stage.show()

        timer.start()
    }

    override def stop() = {
        System.exit(0)
    }

    private def createProductBox(name: String, desc: String, shortForm: String, background: Paint, color: Color, left: Boolean): HBox = {
        val header = new Label(name)
        header.setAlignment(Pos.CENTER)
        header.setFont(Fonts.opensansSemibold(24))
        header.setTextFill(color)
        val description   = new Label(desc)
        description.setAlignment(if (left) Pos.CENTER_RIGHT else Pos.CENTER_LEFT)
        description.setTextAlignment(if (left) TextAlignment.RIGHT else TextAlignment.LEFT)
        description.setFont(Fonts.opensansLight(12))
        description.setTextFill(Color.rgb(64, 66, 100))
        val vbox = new VBox(10, header, description)
        vbox.setFillWidth(true)
        vbox.setAlignment(Pos.CENTER)
        val shortFormLabel = new Label(shortForm)
        shortFormLabel.setMinSize(64, 64)
        shortFormLabel.setMaxSize(64, 64)
        shortFormLabel.setPrefSize(64, 64)
        shortFormLabel.setTextFill(Color.WHITE)
        shortFormLabel.setFont(Fonts.opensansSemibold(36))
        shortFormLabel.setAlignment(Pos.CENTER)
        shortFormLabel.setPadding(new Insets(-3, 0, 0, 0))
        shortFormLabel.setBackground(new Background(new BackgroundFill(background, new CornerRadii(100), Insets.EMPTY)))
        shortFormLabel.setEffect(new DropShadow(BlurType.TWO_PASS_BOX, Color.rgb(0, 0, 0, 0.15), 5, 0.0, 0, 5))
        val hbox: HBox =
        if (left) {
            new HBox(20, vbox, shortFormLabel)
        } else {
            new HBox(20, shortFormLabel, vbox)
        }

        hbox.setFillHeight(true)
        hbox.setAlignment(Pos.CENTER)
        hbox
    }

    def launchIt():Unit = {
        Application.launch()
    }
}


// Not required, not needed
// object ComparisonBarChartTest {
//   def main(args: Array[String]) =
//     val app = new ComparisonBarChartTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object ComparisonBarChartTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[ComparisonBarChartTest], args*)
//     }
// }
