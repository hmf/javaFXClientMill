package org.modernclient

// cSpell:ignore javafx

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.stage.Stage

import javafx.scene.layout.VBox

/**
 *
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i modernClients.ch02-javafx_fundamentals.myshapesfxml.run
 * ./mill -i modernClients.ch02-javafx_fundamentals.myshapesfxml.runMain org.modernclient.MyShapesFXML
 * ./mill -i --watch modernClients.ch02-javafx_fundamentals.myshapesfxml.runMain org.modernclient.MyShapesFXML
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
class MyShapesFXML extends Application {

    override def start(stage: Stage) = {
        //val root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"))
        val root: VBox = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"))

        val scene = new Scene(root, Color.LIGHTYELLOW)
        // scene.getStylesheets().add(getClass()
        //         .getResource("/styles/Styles.css").toExternalForm())

        stage.setTitle("MyShapesApp with JavaFX")
        stage.setScene(scene)
        stage.show()
    }

    def launchIt():Unit = {
        Application.launch()
    }
    
}


object MyShapesFXML {
    /**
    * The main() method is ignored in correctly deployed JavaFX application.
    * main() serves only as fallback in case the application can not be
    * launched through deployment artifacts, e.g., in IDEs with limited FX
    * support. NetBeans ignores main().
    *
    * @param args the command line arguments
    */
    def main(args: Array[String]) =
        val app = new MyShapesFXML
        app.launchIt()

}