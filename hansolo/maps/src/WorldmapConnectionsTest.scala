// cSpell:ignore javafx, hansolo

package hansolo.charts

import eu.hansolo.fx.charts.data.MapConnection
import eu.hansolo.fx.charts.data.WeightedMapPoints
import eu.hansolo.fx.charts.tools.MapPoint
import eu.hansolo.fx.charts.world.World
import eu.hansolo.fx.charts.world.World.Resolution
import eu.hansolo.fx.charts.world.WorldBuilder
import javafx.application.Application
import javafx.scene.image.Image
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.scene.layout.StackPane
import javafx.scene.Scene

// import eu.hansolo.fx.charts.AxisBuilder // TODO
// import eu.hansolo.fx.charts.Prediction // TODO
// import eu.hansolo.fx.charts.XYPane // TODO
// import eu.hansolo.fx.charts.Axis // TODO
// import eu.hansolo.fx.charts.AxisType // TODO
// import eu.hansolo.fx.charts.XYChart // TODO
// import eu.hansolo.fx.charts.Grid // TODO
// import eu.hansolo.fx.charts.data.XYItem // TODO
// import eu.hansolo.fx.charts.ChartType // TODO


import java.util.List
import java.util.Random


import scala.compiletime.uninitialized
import scala.jdk.CollectionConverters.*
//import scala.collection.JavaConverters.*
import java.{util => ju}

/**
 * 
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i hansolo.maps.run X
 * ./mill -i hansolo.maps.runMain hansolo.charts.WorldmapConnectionsTest
 * ./mill -i --watch hansolo.maps.runMain hansolo.charts.WorldmapConnectionsTest
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
class WorldmapConnectionsTest extends Application {
    private val RND:                Random = new Random()
    private var worldMap:           World = uninitialized
    private var animatedConnection: MapConnection = uninitialized

    override def init() = {
        worldMap = WorldBuilder.create()
                               .resolution(Resolution.HI_RES)
                               .zoomEnabled(true)
                               .hoverEnabled(false)
                               .selectionEnabled(false)
                               .backgroundColor(Color.WHITE)
                               .fillColor(Color.LIGHTGRAY)
                               .connectionWidth(1)
                               .weightedMapPoints(WeightedMapPoints.NONE)
                               .weightedMapConnections(false)
                               .arrowsVisible(false)
                               .drawImagePath(true)
                               .mapPointTextVisible(true)
                               .textColor(Color.BLACK)
                               .build()

        val calgary: MapPoint           = new MapPoint("Calgary", Color.RED,51.08299176,-114.0799982)
        val san_francisco: MapPoint     = new MapPoint("San Francisco", Color.BLUE, 37.74000775,-122.4599777)
        val new_york: MapPoint          = new MapPoint("New York", Color.BLUE,40.74997906,-73.98001693)
        val chicago: MapPoint           = new MapPoint("Chicago", Color.BLUE,41.82999066,-87.75005497)
        val denver: MapPoint            = new MapPoint("Denver",Color.BLUE, 39.73918805,-104.984016)

        val mexico_city: MapPoint       = new MapPoint("Mexico City",Color.GREEN, 19.44244244,-99.1309882)
        val buenos_aires: MapPoint      = new MapPoint("Buenos Aires", Color.LIGHTBLUE, -34.60250161,-58.39753137)
        val santiago_de_chile: MapPoint = new MapPoint("Santiago de Chile", Color.BLUE,-33.45001382,-70.66704085)
        val sao_paulo: MapPoint         = new MapPoint("Sao Paulo", Color.GREEN, -23.55867959,-46.62501998)

        val berlin: MapPoint            = new MapPoint("Berlin", Color.DARKORANGE, 52.52181866, 13.40154862)
        val paris: MapPoint             = new MapPoint("Paris", Color.DARKBLUE, 48.86669293,2.333335326)
        val madrid: MapPoint            = new MapPoint("Madrid", Color.YELLOW,40.40002626,-3.683351686)

        val johannesburg: MapPoint      = new MapPoint("Johannesburg", Color.BROWN,-26.17004474,28.03000972)
        val casablanca: MapPoint        = new MapPoint("Casablanca", Color.SADDLEBROWN,33.59997622,-7.616367433)
        val tunis: MapPoint             = new MapPoint("Tunis", Color.DARKGREEN,36.80277814,10.1796781)
        val alexandria: MapPoint        = new MapPoint("Alexandria", Color.BLACK,31.20001935,29.94999589)
        val nairobi: MapPoint           = new MapPoint("Nairobi", Color.LIGHTBLUE,-1.283346742,36.81665686)
        val abidjan: MapPoint           = new MapPoint("Abidjan", Color.IVORY,5.319996967,-4.04004826)

        val moscow: MapPoint            = new MapPoint("Moscow", Color.RED,55.75216412,37.61552283)
        val novosibirsk: MapPoint       = new MapPoint("Novosibirsk", Color.RED,55.02996014,82.96004187)
        val magadan: MapPoint           = new MapPoint("Magadan", Color.RED,59.57497988,150.8100089)

        val abu_dabi: MapPoint          = new MapPoint("Abu Dhabi", Color.GOLD, 24.46668357,54.36659338)
        val mumbai: MapPoint            = new MapPoint("Mumbai", Color.GOLD, 19.01699038,72.8569893)
        val hyderabad: MapPoint         = new MapPoint("Hyderabad", Color.GOLD,17.39998313,78.47995357)

        val beijing: MapPoint           = new MapPoint("Beijing", Color.DARKRED,39.92889223,116.3882857)
        val chongqing: MapPoint         = new MapPoint("Chongqing", Color.DARKRED,29.56497703,106.5949816)
        val hong_kong: MapPoint         = new MapPoint("Hong Kong", Color.DARKRED,22.3049809,114.1850093)
        val singapore: MapPoint         = new MapPoint("Singapore", Color.CRIMSON, 1.293033466,103.8558207)
        val tokio: MapPoint             = new MapPoint("Tokio",Color.RED, 35.652832,139.839478)

        val sydney: MapPoint            = new MapPoint("Sydney", Color.BLUE, -33.865143, 151.209900)
        val perth: MapPoint             = new MapPoint("Perth", Color.BLUE, -31.95501463,115.8399987)
        val christchurch: MapPoint      = new MapPoint("Christchurch", Color.BLUE, -43.53503131,172.6300207)


        val northAmerica: List[MapPoint] = List.of(calgary, san_francisco, chicago, new_york, denver)
        val southAmerica: List[MapPoint] = List.of(mexico_city, buenos_aires, santiago_de_chile, sao_paulo)
        val europe: List[MapPoint]       = List.of(madrid, paris, berlin)
        val afrika: List[MapPoint]       = List.of(johannesburg, casablanca, tunis, alexandria, nairobi, abidjan)
        val russia: List[MapPoint]       = List.of(moscow, novosibirsk, magadan)
        val india: List[MapPoint]        = List.of(abu_dabi, mumbai, hyderabad)
        val asia: List[MapPoint]         = List.of(beijing, hong_kong, singapore, tokio, chongqing)
        val australia: List[MapPoint]    = List.of(sydney, perth, christchurch)


        worldMap.addMapPoints(berlin, paris, san_francisco, abu_dabi, new_york, chicago, denver, sao_paulo, madrid, calgary,
                              mexico_city, buenos_aires, santiago_de_chile, johannesburg, moscow, novosibirsk, magadan,
                              mumbai, beijing, hong_kong, sydney, christchurch, tokio, singapore, casablanca, tunis, alexandria, nairobi,
                              abidjan, hyderabad, chongqing, perth)

        /*
        northAmerica.forEach(mapPoint => {
            worldMap.addMapConnections(new MapConnection(berlin, mapPoint, RND.nextInt(130) + 10, berlin.getFill(), Color.ORANGERED, true))
        })
        asia.forEach(mapPoint => {
            worldMap.addMapConnections(new MapConnection(berlin, mapPoint, RND.nextInt(130) + 10, berlin.getFill(), Color.ORANGERED, true))
        })
        australia.forEach(mapPoint => {
            worldMap.addMapConnections(new MapConnection(beijing, mapPoint, RND.nextInt(130) + 10, beijing.getFill(), Color.PURPLE, true))
            worldMap.addMapConnections(new MapConnection(hong_kong, mapPoint, RND.nextInt(130) + 10, beijing.getFill(), Color.PURPLE, true))
        })
        europe.forEach(mapPoint => {
            worldMap.addMapConnections(new MapConnection(johannesburg, mapPoint, RND.nextInt(130) + 10, johannesburg.getFill(), Color.ORANGE, true))
        })
        southAmerica.forEach(mapPoint => {
            worldMap.addMapConnections(new MapConnection(johannesburg, mapPoint, RND.nextInt(130) + 10, johannesburg.getFill(), Color.ORANGE, true))
        })
        */

        val sanfrancisco_mumbai: MapConnection     = new MapConnection(san_francisco, mumbai, 90, Color.ORANGERED, Color.BLUE, true)
        val sanfrancisco_newyork: MapConnection    = new MapConnection(san_francisco, new_york, 100, Color.ORANGERED, Color.BLUE, true)
        val sanfrancisco_abudabi: MapConnection    = new MapConnection(san_francisco, abu_dabi, 60, Color.ORANGERED, Color.BLUE, true)
        val sanfrancisco_mexicocity: MapConnection = new MapConnection(san_francisco, mexico_city, 30, Color.ORANGERED, Color.BLUE, true)
        val sanfrancisco_santiago: MapConnection   = new MapConnection(san_francisco, santiago_de_chile, 70, Color.ORANGERED, Color.BLUE, true)


        animatedConnection = new MapConnection(berlin, christchurch, 1, Color.CRIMSON)
        animatedConnection.setLineWidth(5)

        //worldMap.addMapConnections(sanfrancisco_mumbai, sanfrancisco_abudabi, sanfrancisco_newyork, sanfrancisco_mexicocity, sanfrancisco_santiago)
    }

    override def start(stage: Stage) = {
        val pane: StackPane = new StackPane(worldMap)

        val scene: Scene = new Scene(pane)

        stage.setTitle("Worldmap Connections")
        stage.setScene(scene)
        stage.show()

        val plane: Image = new Image(getClass().getResourceAsStream("/plane.png"))
        pane.setOnMousePressed(e => worldMap.animateImageAlongConnection(plane, animatedConnection))
    }

    override def stop() = {
        System.exit(0)
    }

    def launchIt():Unit = {
        Application.launch()
    }
}


// Not required, not needed
// object WorldmapConnectionsTest {
//   def main(args: Array[String]) =
//     val app = new WorldmapConnectionsTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object WorldmapConnectionsTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[WorldmapConnectionsTest], args*)
//     }
// }
