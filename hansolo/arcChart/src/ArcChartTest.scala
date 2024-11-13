// cSpell:ignore javafx, hansolo

package hansolo.charts

import eu.hansolo.fx.charts.ArcChart
import eu.hansolo.fx.charts.ArcChartBuilder
import eu.hansolo.fx.charts.Cluster
import eu.hansolo.fx.charts.data.ChartItem
import eu.hansolo.fx.charts.data.Connection
import eu.hansolo.fx.charts.data.PlotItem
import eu.hansolo.fx.charts.event.ChartEvt
import eu.hansolo.toolbox.evt.Evt
import eu.hansolo.toolbox.evt.EvtObserver
import eu.hansolo.toolbox.evt.EvtType
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.stage.Stage


import scala.compiletime.uninitialized
import java.util.List


/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i hansolo.arcChart.run
 * ./mill -i hansolo.arcChart.runMain hansolo.charts.ArcChartTest
 * ./mill -i --watch hansolo.arcChart.runMain hansolo.charts.ArcChartTest
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
class ArcChartTest extends Application {
    
    private var arcChart: ArcChart = uninitialized

    override def init() = {
        // Setup Data
        // Wahlberechtigte 61_500_000
        val germany   = new PlotItem("GERMANY", 1_250_000, Color.rgb(255, 51, 51))
        val france    = new PlotItem("FRANCE", 1_000_000, Color.rgb(0, 0, 180))
        val spain     = new PlotItem("SPAIN", 300_000, Color.rgb(180, 0, 0))
        val italy     = new PlotItem("ITALY", 350_000, Color.rgb(0, 180, 0))
        val india     = new PlotItem("INDIA", 750_000, Color.rgb(255, 153, 51))
        val china     = new PlotItem("CHINA", 920_000, Color.rgb(255, 255, 51))
        val japan     = new PlotItem("JAPAN", 1_060_000, Color.rgb(153, 255, 51))
        val thailand  = new PlotItem("THAILAND", 720_000, Color.rgb(51, 255, 51))
        val singapore = new PlotItem("SINGAPORE", 800_000, Color.rgb(51, 255, 153))

        val asia   = new Cluster("asia", Color.rgb(220, 50, 50), china, japan, india, thailand, singapore)
        val europe = new Cluster("europe", Color.rgb(50, 50, 220), germany, france, italy, spain)

        // Connections
        germany.addToOutgoing(india, 150_000)
        germany.addToOutgoing(china, 90_000)
        germany.addToOutgoing(japan, 180_000)
        germany.addToOutgoing(thailand, 15_000)
        germany.addToOutgoing(singapore, 10_000)

        spain.addToOutgoing(italy, 100_000)
        spain.addToOutgoing(japan, 20_000)
        spain.addToOutgoing(thailand, 80_000)
        System.out.println("Spain sum of outgoing -> " + spain.getSumOfOutgoing())

        italy.addToOutgoing(germany, 20_000)
        italy.addToOutgoing(spain, 10_000)
        italy.addToOutgoing(singapore, 5_000)
        System.out.println("Italy sum of outgoing -> " + italy.getSumOfOutgoing())

        france.addToOutgoing(germany, 40_000)
        france.addToOutgoing(china, 20_000)
        france.addToOutgoing(singapore, 10_000)
        france.addToOutgoing(japan, 5_000)
        System.out.println("France sum of outgoing -> " + france.getSumOfOutgoing())

        japan.addToOutgoing(germany, 70_000)

        //india.addToOutgoing(australia, 35_000)
        //india.addToOutgoing(china, 10_000)
        india.addToOutgoing(japan, 40_000)
        india.addToOutgoing(thailand, 25_000)
        india.addToOutgoing(singapore, 8_000)

        //china.addToOutgoing(australia, 10_000)
        //china.addToOutgoing(india, 7_000)
        //china.addToOutgoing(japan, 40_000)
        //china.addToOutgoing(thailand, 5_000)
        china.addToOutgoing(singapore, 4_000)

        //japan.addToOutgoing(australia, 7_000)
        //japan.addToOutgoing(india, 8_000)
        //japan.addToOutgoing(china, 175_000)
        japan.addToOutgoing(thailand, 11_000)
        japan.addToOutgoing(singapore, 18_000)

        thailand.addToOutgoing(germany, 70_000)
        thailand.addToOutgoing(india, 30_000)
        thailand.addToOutgoing(china, 22_000)
        thailand.addToOutgoing(japan, 120_000)
        thailand.addToOutgoing(singapore, 40_000)

        singapore.addToOutgoing(germany, 60_000)
        singapore.addToOutgoing(india, 90_000)
        singapore.addToOutgoing(china, 110_000)
        singapore.addToOutgoing(japan, 14_000)
        singapore.addToOutgoing(thailand, 30_000)


        val items: List[PlotItem] = List.of(germany, france, italy, spain, india, china, japan, thailand, singapore)

        // Register listeners to click on connections and items
        items.forEach(item => {
            item.addChartEvtObserver(ChartEvt.ITEM_SELECTED, e => {
                val i = e.getSource().asInstanceOf[PlotItem]
                println("Selected: " + i.getName())
            })
        })

        // Setup Chart
        arcChart = ArcChartBuilder.create()
                                  .prefSize(600, 600)
                                  .items(items)
                                  .connectionOpacity(0.75)
                                  .decimals(0)
                                  .coloredConnections(false)
                                  .sortByCluster(true)
                                  .useFullCircle(true)
                                  .weightDots(true)
                                  .weightConnections(true)
                                  .build()

        val connectionObserver: EvtObserver[ChartEvt] = e => {
            // EvtType<? extends Evt> type = e.getEvtType()
            val type_ = e.getEvtType()
            if (type_.equals(ChartEvt.CONNECTION_SELECTED_TO) || 
                type_.equals(ChartEvt.CONNECTION_SELECTED_FROM) || 
                type_.equals(ChartEvt.CONNECTION_SELECTED)) {
                if (e.getSource().isInstanceOf[Connection]) {
                    val connection = e.getSource().asInstanceOf[Connection]
                    System.out.println("From: " + connection.getOutgoingItem().getName() + " -> to: " + connection.getIncomingItem().getName() + " -> Value: " + connection.getValue())
                }
            }
        }
        arcChart.getConnections().forEach(connection => connection.addChartEvtObserver(ChartEvt.ANY, connectionObserver))

        /* Custom connection colors
        if (null != arcChart.getConnection(australia, japan)) {
            arcChart.getConnection(australia, japan).setFill(Color.BLUE)
        }
        if (null != arcChart.getConnection(australia, india)) {
            arcChart.getConnection(australia, india).setFill(Color.CHOCOLATE)
        }
        if (null != arcChart.getConnection(japan, australia)) {
            arcChart.getConnection(japan, australia).setFill(Color.POWDERBLUE)
        }
        */

        /*
        arcChart.getConnections().forEach(connection -> {
            //connection.setFill(Color.CRIMSON)
            System.out.println(connection.getOutgoingItem().getName() + " -> " + connection.getIncomingItem().getName() + " -> Value: " + connection.getValue() + " -> Color: " + connection.getFill())
        })
        */

        //Connection connection = arcChart.getConnection(thailand, china)
        //System.out.println(null == connection ? "Connection is null!!!" : "Connection from Thailand -> China: " + connection.getValue())
    }

    override def start(stage: Stage) = {
        val pane = new StackPane(arcChart)
        pane.setPadding(new Insets(10))

        val scene = new Scene(pane)

        stage.setTitle("Arc Chart")
        stage.setScene(scene)
        stage.show()
    }

    override def stop() = {
        System.exit(0)
    }

    // public static void main(String[] args) {
    //     launch(args)
    // }
}

// Not required, not needed
// object ArcChartTest {
//   def main(args: Array[String]) =
//     val app = new ArcChartTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object ArcChartTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[ArcChartTest], args*)
//     }
// }
