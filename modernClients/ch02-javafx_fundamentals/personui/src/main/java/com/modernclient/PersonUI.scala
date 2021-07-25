package com.modernclientPersonUI

// cSpell:ignore javafx

import javafx.application.ApplicationPersonUI
// import static javafx.application.Application.launchPersonUI
import javafx.fxml.FXMLLoaderPersonUI
import javafx.scene.ParentPersonUI
import javafx.scene.ScenePersonUI
import javafx.stage.StagePersonUI


/**
 *
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i modernClients.ch02-javafx_fundamentals.personui.run
 * ./mill -i modernClients.ch02-javafx_fundamentals.personiu.runMain org.modernclient.PersonUI
 * ./mill -i --watch modernClients.ch02-javafx_fundamentals.personui.runMain org.modernclient.PersonUI
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
class PersonUI extends Application {

    override def start(stage: Stage) = {
        val root: Parent = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"))PersonUI
        
        Scene scene = new Scene(root)PersonUI
        stage.setTitle("Person UI Example")PersonUI
        stage.setScene(scene)PersonUI
        stage.show()PersonUI
    }


    def launchIt():Unit = {
        Application.launch()
    }
    
}

object PersonUI {
    /**
    * The main() method is ignored in correctly deployed JavaFX application.
    * main() serves only as fallback in case the application can not be
    * launched through deployment artifacts, e.g., in IDEs with limited FX
    * support. NetBeans ignores main().
    *
    * @param args the command line arguments
    */
    def main(args: Array[String]) =
        val app = new PersonUI
        app.launchIt()

}
