package org.modernclients.controls

// cSpell:ignore javafx

import collection.JavaConverters._


import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import javafx.scene.layout.VBox
import javafx.geometry.Pos
import javafx.scene.control.Hyperlink
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.paint.Color

/**
 *
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i modernClients.ch04-javafx_controls.basic.run
 * ./mill -i modernClients.ch04-javafx_controls.basic.runMain org.modernclients.controls.Basic
 * ./mill -i --watch modernClients.ch04-javafx_controls.basic.runMain org.modernclients.controls.Basic
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
class HelloWorld extends Application {

  /*
  1. "Labeled" controls: Button, CheckBox, Hyperlink, Label, RadioButton, and ToggleButton
      Button, CheckBox, Hyperlink, Label, RadioButton, and ToggleButton
      MenuButton, TitledPane, and ToggleButton
  2. "Text input" controls: TextField, TextArea, and PasswordField
  3. "Other" simple controls: ProgressBar, ProgressIndicator, and Slider
*/


  override def start(primaryStage: Stage) = {
      primaryStage.setTitle("Basic controls")

      // Button 
      val btn = new Button("Say 'Hello basic")
      btn.setOnAction( (event: ActionEvent) => {
              println("Hello basic")
          }
      )
      // See VBox.setFillWidth(true)
      btn.setMaxWidth(99999D) //or Double.MAX_VALUE
      //btn.setText("Say 'Hello basic")
      // btn.setOnAction(new EventHandler[ActionEvent]() {
      //      override def handle(event: ActionEvent) = {
      //         println("Hello basic")
      //     }
      // })

      // Checkbox
      val cb = CheckBox("Enable Power Plant")
      cb.setIndeterminate(false)
      cb.setOnAction(e => println("Action event fired"))
      cb.selectedProperty().addListener(i => println("Selected state change to " + cb.isSelected()))

      // Hyperlink
      val hyperlink = new Hyperlink("Click Me!")
      val courierNewFontBold36 = Font.font("Courier New", FontWeight.BOLD, 11)
      hyperlink.setOnAction(
        event => 
          println("Hyperlink was clicked")
          hyperlink.setFont(courierNewFontBold36) 
          hyperlink.setTextFill(Color.RED)
        )

      // http://tutorials.jenkov.com/javafx/vbox.html
      //val root = new StackPane()
      val root = VBox(5)
      root.setAlignment(Pos.BASELINE_CENTER)
      root.setFillWidth(true)
      root.getChildren().add(btn)
      root.getChildren().add(cb)
      root.getChildren().add(hyperlink)

      primaryStage.setScene(new Scene(root, 300, 250))
      primaryStage.show()
    }
}

object Basic {

    def main(args: Array[String]): Unit = {
      Application.launch(classOf[HelloWorld], args: _*)
    }
}


