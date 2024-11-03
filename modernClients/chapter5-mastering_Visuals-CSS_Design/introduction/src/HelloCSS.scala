package chapter5.introduction

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.Stage

/**
 *
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i modernClients.chapter5-mastering_Visuals-CSS_Design.introduction.run
 * ./mill -i modernClients.chapter5-mastering_Visuals-CSS_Design.introduction.runMain chapter5.introduction.HelloCSS
 * ./mill -i --watch modernClients.chapter5-mastering_Visuals-CSS_Design.introduction.runMain chapter5.introduction.HelloCSS
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
  

  
  
 