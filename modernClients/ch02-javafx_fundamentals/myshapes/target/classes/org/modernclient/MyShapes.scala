package org.modernclient

// cSpell:ignore javafx, myshapes

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Ellipse
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.Stage


/**
 *
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i modernClients.ch02-javafx_fundamentals.myshapes.run
 * ./mill -i modernClients.ch02-javafx_fundamentals.myshapes.runMain org.modernclient.MyShapes
 * ./mill -i --watch modernClients.ch02-javafx_fundamentals.myshapes.runMain org.modernclient.MyShapes
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
class MyShapes extends Application {

    override def start(stage: Stage) = {

        // Create an Ellipse and set fill color
        val ellipse = new Ellipse(110, 70)
        ellipse.setFill(Color.LIGHTBLUE)

        // Alternate color notations
        //ellipse.setFill(Color.web("#ADD8E680"))
        //ellipse.setFill(Color.web("0xADD8E680"))
        //ellipse.setFill(Color.rgb(173, 216, 230, .5))

        // Create a Text shape with font and size
        val text = new Text("My Shapes")
        text.setFont(new Font("Arial Bold", 24))

        val stackPane = new StackPane()
        stackPane.getChildren().addAll(ellipse, text)

        val scene = new Scene(stackPane, 350, 230, Color.LIGHTYELLOW)
        //scene.getStylesheets().add("/styles/Styles.css")
        
        stage.setTitle("MyShapes with JavaFX")
        stage.setScene(scene)
        stage.show()
    }

    def launchIt():Unit = {
        Application.launch()
    }

}

object MyShapes {
  /**
   * The main() method is ignored in correctly deployed JavaFX application.
   * main() serves only as fallback in case the application can not be
   * launched through deployment artifacts, e.g., in IDEs with limited FX
   * support. NetBeans ignores main().
   *
   * @param args the command line arguments
   */
  def main(args: Array[String]) =
    val app = new MyShapes
    app.launchIt()

}