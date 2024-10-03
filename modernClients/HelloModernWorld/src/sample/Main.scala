package sample

// cSpell:ignore javafx

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

/**
 * Example of the javaFX 17 book. It creates a window and shows an image that
 * is loaded via a Java resource file. In this case the resources is the 
 * `sample.fxml` which is created with an [interactive GUI builder](https://github.com/gluonhq/scenebuilder).
 * When loaded using the JavaFX loader the file is interpreted and the 
 * GUI is automatically generated.
 * 
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i modernClients.HelloModernWorld.runMain sample.Main
 * ./mill -i --watch modernClients.HelloModernWorld.runMain sample.Main
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
class Main extends Application {

    override def start(primaryStage: Stage) = {
      // Both loads will work relative to the module
      val root: Parent = FXMLLoader.load(getClass().getResource("/sample.fxml"))
      //val root: Parent = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"))
      primaryStage.setTitle("Hello Modern World")
      primaryStage.setScene(new Scene(root))
      primaryStage.show()
    }

    def launchIt():Unit={
        Application.launch()
      }
}

/**
 * Example of the javaFX 17 book. 
 * 
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i modernClients.HelloModernWorld.runMain sample.Main
 * ./mill -i --watch modernClients.HelloModernWorld.runMain sample.Main
 *
 * @see https://stackoverflow.com/questions/12124657/getting-started-on-scala-javafx-desktop-application-development
 */
object Main {
    def main(args: Array[String]) =
      val app = new Main
      app.launchIt()
  
  }