// cSpell:ignore javafx, hansolo

package hansolo.charts


import eu.hansolo.fx.charts.world.World
import eu.hansolo.fx.charts.world.World.Resolution
import eu.hansolo.fx.charts.world.WorldBuilder
import eu.hansolo.toolboxfx.geom.Point
import eu.hansolo.fx.heatmap.ColorMapping
import eu.hansolo.fx.heatmap.Mapping
import eu.hansolo.fx.heatmap.OpacityDistribution
import javafx.application.Application
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.stage.Stage
import javafx.scene.layout.StackPane
import javafx.scene.Scene

import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.ArrayList
import java.util.List
import java.util.stream.Stream


import scala.compiletime.uninitialized
import javax.xml.transform.Source


/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * X ./mill -i hansolo.heatMaps.run
 * ./mill -i hansolo.heatMaps.runMain hansolo.charts.WorldHeatMapTest
 * ./mill -i --watch hansolo.heatMaps.runMain hansolo.charts.WorldHeatMapTest
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
 class WorldHeatMapTest extends Application {
    /*private*/ var pane     : StackPane             = uninitialized
    /*private*/ var worldMap : World                 = uninitialized
    /*private*/ var cities   : java.util.List[Point] = uninitialized

    override def init() = {
        try { 
            cities = readCitiesFromFile() 
        } catch {
            case e:IOException => cities = new ArrayList[Point]()
            case e:URISyntaxException => e.printStackTrace()
        }

        val customMapping = new Mapping() {
            private val stops: Array[Stop] = Array( new Stop(0.0, Color.LIME), new Stop(0.8, Color.YELLOW), new Stop(1.0, Color.CYAN) )
            private val gradient: LinearGradient = new LinearGradient(0.0, 0.0, 100.0, 0.0, false, CycleMethod.NO_CYCLE, stops*)

            override def getStops(): Array[Stop] = { stops }
            override def getGradient(): LinearGradient = { gradient }
        }

        worldMap = WorldBuilder.create()
                               .resolution(Resolution.HI_RES)
                               //.backgroundColor(Color.BLACK)
                               .fillColor(Color.BLACK)
                               .zoomEnabled(true)
                               .hoverEnabled(false)
                               .selectionEnabled(true)
                               .selectedColor(Color.LIGHTBLUE)
                               //.mousePressHandler(e -> {
                               //    //worldMap.setSelectedColor(worldMap.getSelectedCountry().getFill())
                               //    System.out.println(worldMap.getSelectedCountry())
                               //})
                               //.colorMapping(ColorMapping.BLUE_CYAN_GREEN_YELLOW_RED)
                               .colorMapping(ColorMapping.BLUE_GREEN_RED)
                               //.colorMapping(ChartsColorMapping.BLACK_WHITE)
                               //.colorMapping(customMapping)
                               .fadeColors(true)
                               .eventRadius(3)
                               .heatMapOpacity(0.75)
                               .opacityDistribution(OpacityDistribution.LINEAR)
                               .build()

        pane = new StackPane(worldMap)

        /* Add heatmap events by clicking on the map
        pane.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            double x = event.getX()
            double y = event.getY()
            HeatMap heatMap = worldMap.getHeatMap()
            if (x < heatMap.getEventRadius()) x = heatMap.getEventRadius()
            if (x > pane.getWidth() - heatMap.getEventRadius()) x = pane.getWidth() - heatMap.getEventRadius()
            if (y < worldMap.getHeatMap().getEventRadius()) y = worldMap.getHeatMap().getEventRadius()
            if (y > pane.getHeight() - heatMap.getEventRadius()) y = pane.getHeight() - heatMap.getEventRadius()

            worldMap.getHeatMap().addEvent(x, y)
        })
        */
    }

    override def start(stage: Stage) = {
        val scene = new Scene(pane)

        stage.setTitle("World Cities")
        stage.setScene(scene)
        stage.show()

        worldMap.getHeatMap().addSpots(cities)
    }

    @throws( classOf[IOException] )
    @throws( classOf[URISyntaxException] )
    private def readCitiesFromFile(): List[Point] = {
        val cities: List[Point] = new ArrayList[Point](8092)
        val citiesFile: URI = (getClass().getResource("/cities.txt")).toURI()
        val lines: Stream[String] = Files.lines(Paths.get(citiesFile))
        lines.forEach(line => {
            val city: Array[String] = line.split(",")
            val xy: Array[Double] = World.latLonToXY(java.lang.Double.parseDouble(city(1)), java.lang.Double.parseDouble(city(2)))
            cities.add(new Point(xy(0), xy(1)))
        })
        lines.close()
        return cities
    }

    override def stop() = {
        System.exit(0)
    }

    def launchIt():Unit = {
        Application.launch()
    }

}

// Not required, not needed
// object WorldHeatMapTest {
//   def main(args: Array[String]) =
//     val app = new WorldHeatMapTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object WorldHeatMapTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[WorldHeatMapTest], args*)
//     }
// }
