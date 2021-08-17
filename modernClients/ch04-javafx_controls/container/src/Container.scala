package org.modernclients.container

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
import javafx.scene.control.TitledPane
import javafx.scene.control.Accordion

/**
 *
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i modernClients.ch04-javafx_controls.container.run
 * ./mill -i modernClients.ch04-javafx_controls.container.runMain org.modernclients.container.Container
 * ./mill -i --watch modernClients.ch04-javafx_controls.container.runMain org.modernclients.container.Container
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
class Container extends Application {


  override def start(primaryStage: Stage) = {
      primaryStage.setTitle("Container controls")

     /*
        Accordion and TitledPane
        ButtonBar
        ScrollPane
        SplitPane
        TabPane
        ToolBar
     */

      // Button 
      val btn = new Button("Say 'Hello container'")
      btn.setOnAction( (event: ActionEvent) => {
              println("Hello basic")
          }
      )
      // See VBox.setFillWidth(true)
      btn.setMaxWidth(99999D) //or Double.MAX_VALUE
      btn.setText("Say 'Hello container")
      btn.setOnAction(new EventHandler[ActionEvent]() {
           override def handle(event: ActionEvent) = {
              println("Hello container")
          }
      })
      
      val b1 = Button("Button 1")
      val b2 = Button("Button 2")
      val b3 = Button("Button 3")

      b1.setOnAction( (event: ActionEvent) => {
              println("Hello b1")
          }
      )

      b2.setOnAction( (event: ActionEvent) => {
              println("Hello b2")
          }
      )

      b3.setOnAction( (event: ActionEvent) => {
              println("Hello b3")
          }
      )

      val t1 = TitledPane("TitledPane 1", b1)
      val t2 = TitledPane("TitledPane 2", b2)
      val t3 = TitledPane("TitledPane 3", b3)
      val accordion = Accordion()
      accordion.getPanes().addAll(t1, t2, t3)

      // http://tutorials.jenkov.com/javafx/vbox.html
      //val root = new StackPane()
      val root = VBox(5)
      //root.setAlignment(Pos.BASELINE_CENTER)
      root.setAlignment(Pos.TOP_CENTER)
      root.setFillWidth(true)
      //root.getChildren().add(btn)
      root.getChildren().add(accordion)

      primaryStage.setScene(Scene(root, 500, 400))
      primaryStage.show()
    }
}

object Container {

    def main(args: Array[String]): Unit = {
      Application.launch(classOf[Container], args: _*)
    }
}



