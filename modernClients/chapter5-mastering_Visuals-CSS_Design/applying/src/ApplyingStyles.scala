package chapter5.applying

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import javafx.stage.Stage

/**
 *
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i modernClients.chapter5-mastering_Visuals-CSS_Design.applying.run
 * ./mill -i modernClients.chapter5-mastering_Visuals-CSS_Design.applying.runMain chapter5.applying.ApplyingStyles
 * ./mill -i --watch modernClients.chapter5-mastering_Visuals-CSS_Design.applying.runMain chapter5.applying.ApplyingStyles
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
class ApplyingStyles extends Application {
    private val label = new Label("Stylized label")
    // Simplistic implementation of numeric field
    private val widthField = new TextField("200") {
        
        override def replaceText(start: Int, end: Int, text: String) = {
            if (text.matches("[0-9]*")) {
                super.replaceText(start, end, text)
            }
        }
        
        override def replaceSelection(replacement: String)= {
            if (replacement.matches("[0-9]*")) {
                super.replaceSelection(replacement)
            }
        }
    }
    private def updateLabelStyle() = {
        label.setStyle(
                "-fx-background-color: black;" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 10;" +
                        "-fx-pref-width: " + widthField.getText() + "px;"
        )
    }

    override def start(primaryStage: Stage) = {
        updateLabelStyle()
        widthField.setOnAction( e => updateLabelStyle())
        val root = new VBox(10, label, widthField)
        root.setStyle(
                "-fx-background-color: lightblue;" +
                "-fx-padding: 20px;")
        val scene = new Scene( root, 250, 100 )
        primaryStage.setTitle("My first CSS application")
        primaryStage.setScene(scene)
        primaryStage.show()
    }
}

// Does not seem to be required
object ApplyingStyles {

    def main(args: Array[String]): Unit = {
    Application.launch(classOf[ApplyingStyles], args*)
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
  
  
  
 