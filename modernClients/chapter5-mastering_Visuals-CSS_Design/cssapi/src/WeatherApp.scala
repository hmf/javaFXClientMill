package chapter5.cssapi

import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.stage.Stage

/**
 *
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i modernClients.chapter5-mastering_Visuals-CSS_Design.cssapi.run
 * ./mill -i modernClients.chapter5-mastering_Visuals-CSS_Design.cssapi.runMain chapter5.cssapi.WeatherApp
 * ./mill -i --watch modernClients.chapter5-mastering_Visuals-CSS_Design.cssapi.runMain chapter5.cssapi.WeatherApp
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
 * @see https://stackoverflow.com/questions/22000423/javafx-and-maven-nullpointerexception-location-is-required
 * @see https://stackoverflow.com/questions/12124657/getting-started-on-scala-javafx-desktop-application-development
 */
class WeatherApp extends Application {

    override def start(primaryStage: Stage) = {

        val rain = new WeatherIcon()
        rain.getStyleClass().add("rain")

        val thunderstorm = new WeatherIcon()
        thunderstorm.getStyleClass().add("thunderstorm")

        val clouds = new WeatherIcon( WeatherType.CLOUDY )

        val root = new VBox(10, rain, thunderstorm, clouds)
        root.setAlignment(Pos.CENTER)

        val scene = new Scene( root)
        scene.getStylesheets().add( getClass().getResource("styles.css").toExternalForm())

        primaryStage.setTitle("WeatherType Application")
        primaryStage.setScene(scene)
        primaryStage.show()

    }
}

//  
//  

/**
 *
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i modernClients.ch04-javafx_controls.popup.run
 * ./mill -i modernClients.ch04-javafx_controls.popup.runMain org.modernclients.popup.Popup
 * ./mill -i --watch modernClients.ch04-javafx_controls.popup.runMain org.modernclients.popup.Popup
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
 * @see https://stackoverflow.com/questions/22000423/javafx-and-maven-nullpointerexception-location-is-required
 * @see https://stackoverflow.com/questions/12124657/getting-started-on-scala-javafx-desktop-application-development
 */
/*
class AppX extends Application {

    override def start(primaryStage: Stage) = {
      }
  }
  
object Appx {

    def main(args: Array[String]): Unit = {
    Application.launch(classOf[Appx], args: _*)
    }
}
*/
  
  
  
 