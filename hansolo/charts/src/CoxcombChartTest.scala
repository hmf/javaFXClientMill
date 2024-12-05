package hansolo.charts

// cSpell:ignore javafx, hansolo



import eu.hansolo.fx.charts.CoxcombChart
import eu.hansolo.fx.charts.CoxcombChartBuilder
import eu.hansolo.fx.charts.data.ChartItem
import eu.hansolo.fx.charts.data.ChartItemBuilder
import eu.hansolo.fx.charts.data.Metadata
import eu.hansolo.fx.charts.tools.Order
import javafx.application.Application
import javafx.beans.property.StringProperty
import javafx.beans.property.StringPropertyBase
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.scene.layout.StackPane
import javafx.scene.Scene

import java.util.List
import java.util.Optional

import scala.util
import scala.compiletime.uninitialized
import scala.util.boundary, boundary.break
// import scala.collection.convert.ImplicitConversionsToScala.*


/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * X ./mill -i hansolo.charts.run
 * ./mill -i hansolo.charts.runMain hansolo.charts.CoxcombChartTest
 * ./mill -i --watch hansolo.charts.runMain hansolo.charts.CoxcombChartTest
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
 * @see https://github.com/HanSolo/charts/blob/master/src/test/java/eu/hansolo/fx/charts/CoxcombChartTest.java
 */
class CoxcombChartTest extends Application {
    private var  chart: CoxcombChart = uninitialized
    private var  textPane: VBox = uninitialized
    private var  metainfo1: Metainfo = uninitialized
    private var  metainfo2: Metainfo = uninitialized
    private var  metainfo3: Metainfo = uninitialized
    private var  metainfo4: Metainfo = uninitialized
    private var  metainfo5: Metainfo = uninitialized
    private var  metainfo6: Metainfo = uninitialized


    override def init() = {
        metainfo1 = new Metainfo("Text 1")
        metainfo2 = new Metainfo("Text 2")
        metainfo3 = new Metainfo("Text 3")
        metainfo4 = new Metainfo("Text 4")
        metainfo5 = new Metainfo("Text 5")
        metainfo6 = new Metainfo("Text 6")


        val items: List[ChartItem] = List.of(
            ChartItemBuilder.create().name("Item 1").value(27).fill(Color.web("#96AA3B")).metadata(metainfo1).build(),
            ChartItemBuilder.create().name("Item 2").value(24).fill(Color.web("#29A783")).metadata(metainfo2).build(),
            ChartItemBuilder.create().name("Item 3").value(16).fill(Color.web("#098AA9")).metadata(metainfo3).build(),
            ChartItemBuilder.create().name("Item 4").value(15).fill(Color.web("#62386F")).metadata(metainfo4).build(),
            ChartItemBuilder.create().name("Item 5").value(13).fill(Color.web("#89447B")).metadata(metainfo5).build(),
            ChartItemBuilder.create().name("Item 6").value(5).fill(Color.web("#EF5780")).metadata(metainfo6).build()
            //new ChartItem("Item 1", 27, Color.web("#96AA3B")),
            //new ChartItem("Item 2", 24, Color.web("#29A783")),
            //new ChartItem("Item 3", 16, Color.web("#098AA9")),
            //new ChartItem("Item 4", 15, Color.web("#62386F")),
            //new ChartItem("Item 5", 13, Color.web("#89447B")),
            //new ChartItem("Item 6", 5, Color.web("#EF5780"))
            )

        val onPressedHandler: EventHandler[MouseEvent] = e => {
            boundary:
                val opt: Optional[ChartItem] = chart.getSelectedItem(e)
                if (opt.isEmpty()) { break(0) }
                val selectedItem: ChartItem = opt.get()
                //System.out.println(selectedItem)
                if (selectedItem.isSelected()) {
                    selectedItem.setSelected(false)
                } else {
                    items.forEach(item => item.setSelected(false))
                    selectedItem.setSelected(true)
                }
        }

        val onMoveHandler: EventHandler[MouseEvent] = e => {
            boundary:
                val opt: Optional[ChartItem] = chart.getSelectedItem(e)
                if (opt.isEmpty()) { break(0) }
                System.out.println(opt.get())
        }

        chart = CoxcombChartBuilder.create()
                                   .items(items)
                                   .textColor(Color.WHITE)
                                   .autoTextColor(false)
                                   .useChartItemTextFill(false)
                                   .equalSegmentAngles(true)
                                   .order(Order.ASCENDING)
                                   .onMousePressed(onPressedHandler)
                                   .onMouseMoved(onMoveHandler)
                                   .showPopup(false)
                                   .showItemName(true)
                                   .formatString("%.2f")
                                   .selectedItemFill(Color.MAGENTA)
                                   .build()

        val row1: Label = new Label("Main title")
        val row2: Label = new Label("Sub title 1")
        val row3: Label = new Label("Sub title 2")
        textPane = new VBox(10, row1, row2, row3)
        textPane.setMouseTransparent(true)
    }

    override def start(stage: Stage) = {
        val pane = new StackPane(chart, textPane)
        pane.setPadding(new Insets(10))

        val scene = new Scene(pane)

        stage.setTitle("Coxcomb Chart")
        stage.setScene(scene)
        stage.show()

        chart.addItem(new ChartItem("New Item", 3, Color.RED))

        metainfo6.setText("Changed Text 6")
    }

    override def stop() = {
        System.exit(0)
    }
    
    def launchIt():Unit = {
        Application.launch()
    }


    // ******************** Inner Classes *************************************
    class Metainfo(text_ : String) extends Metadata {
        private val text: StringProperty = new StringPropertyBase(text_) {
            override def invalidated() = { super.invalidated() }
            override def getBean(): Object = { Metainfo.this }
            override def getName(): String = { "text" }
        }

        def getText(): String = { text.get() }
        def setText(text: String): Unit = { this.text.set(text) }
        def textProperty(): StringProperty = { text }

        override def toString(): String = { return text.get() }
    }

}

// Not required, not needed
// object CoxcombChartTest {
//   def main(args: Array[String]) =
//     val app = new CoxcombChartTest
//     app.launchIt()
// 
// }

// Not required, not needed
object CoxcombChartTest {

    def main(args: Array[String]): Unit = {
    Application.launch(classOf[CoxcombChartTest], args*)
    }
}
