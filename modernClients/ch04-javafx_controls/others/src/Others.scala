package org.modernclients.others

// cSpell:ignore javafx

import collection.JavaConverters._

import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.event.EventHandler
import javafx.event.ActionEvent
import javafx.scene.layout.VBox
import javafx.geometry.Pos

/**
 *
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i modernClients.ch04-javafx_controls.others.run
 * ./mill -i modernClients.ch04-javafx_controls.others.runMain org.modernclients.others.Others
 * ./mill -i --watch modernClients.ch04-javafx_controls.others.runMain org.modernclients.others.Others
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
class Others extends Application {


  override def start(primaryStage: Stage) = {
      primaryStage.setTitle("Others controls")

      // Button 
      val btn = new Button("Say 'Hello Others'")
      btn.setOnAction( (event: ActionEvent) => {
              println("Hello basic")
          }
      )
      // See VBox.setFillWidth(true)
      btn.setMaxWidth(99999D) //or Double.MAX_VALUE
      btn.setText("Say 'Hello Others")
      btn.setOnAction(new EventHandler[ActionEvent]() {
           override def handle(event: ActionEvent) = {
              println("Hello Others")
          }
      })
      
      // http://tutorials.jenkov.com/javafx/vbox.html
      //val root = new StackPane()
      val root = VBox(5)
      //root.setAlignment(Pos.BASELINE_CENTER)
      root.setAlignment(Pos.TOP_CENTER)
      root.setFillWidth(true)
      root.getChildren().add(btn)

      primaryStage.setScene(Scene(root, 500, 400))
      primaryStage.show()
    }
}

object Others {

    def main(args: Array[String]): Unit = {
      Application.launch(classOf[Others], args: _*)
    }
}



