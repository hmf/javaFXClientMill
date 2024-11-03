package chapter5.introduction

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.Stage

/*
public class HelloCSS extends Application {

    public static void main(String[] args) {
        Application.launch(HelloCSS.class, args)
    }

    public void start(Stage primaryStage) {

        Label label = new Label("Stylized label")
        VBox root = new VBox(label)
        Scene scene = new Scene(root, 200, 100)
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm())

        primaryStage.setTitle("My first CSS application")
        primaryStage.setScene(scene)
        primaryStage.show()

    }
}
*/
class HelloCSS extends Application {

  override def start(primaryStage: Stage) = {

    val label = new Label("Stylized label")
    val root = new VBox(label)
    val scene = new Scene(root, 200, 100)
    scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm())
    // val root: VBox = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"))

    primaryStage.setTitle("My first CSS application")
    primaryStage.setScene(scene)
    primaryStage.show()
  }
}
  
object HelloCSS {

    def main(args: Array[String]): Unit = {
    Application.launch(classOf[HelloCSS], args*)
    }
}
  

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
  
  
  
 