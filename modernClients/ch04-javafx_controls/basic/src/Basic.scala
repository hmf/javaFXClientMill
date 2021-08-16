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
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.HBox
import javafx.scene.control.RadioButton
import javafx.scene.control.TextField
import javafx.scene.control.PasswordField
import javafx.scene.control.TextArea

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
      val courierNewBold11 = Font.font("Courier New", FontWeight.BOLD, 11)
      hyperlink.setOnAction(
        event => 
          println("Hyperlink was clicked")
          hyperlink.setFont(courierNewBold11) 
          hyperlink.setTextFill(Color.RED)
        )

      // ToggleButton, RadioButton, and RadioMenuItem
      // ToggleGroup
      // create a few toggle buttons
      val tb1 = ToggleButton("Toggle button 1")
      val tb2 = ToggleButton("Toggle button 2")
      val tb3 = ToggleButton("Toggle button 3")
      
      // create a toggle group and add all the toggle buttons to it
      // toggleButton1.setToggleGroup(toggleGroup);
      val toggleGroup = ToggleGroup()
      toggleGroup.getToggles().addAll(tb1, tb2, tb3)
      val toggleHBox = HBox(tb1, tb2, tb3)
      toggleHBox.setAlignment(Pos.BASELINE_CENTER)

      // it is possible to add an onAction listener for each button
      tb1.setOnAction(e => println("ToggleButton 1 was clicked on!"))
      // but it is better to add a listener to the toggle group  selectedToggle property
      toggleGroup.selectedToggleProperty()
           .addListener(i => println("Selected toggle is " + toggleGroup.getSelectedToggle()) )

      // ToggleButton, RadioButton, and RadioMenuItem
      // ToggleGroup
      // create a few toggle buttons
      val rb1 = RadioButton("Toggle button 1")
      val rb2 = RadioButton("Toggle button 2")
      val rb3 = RadioButton("Toggle button 3")
      
      // create a toggle group and add all the toggle buttons to it
      // toggleButton1.setToggleGroup(toggleGroup);
      val radioGroup = ToggleGroup()
      radioGroup.getToggles().addAll(tb1, tb2, tb3)
      val radioHBox = HBox(rb1, rb2, rb3)
      radioHBox.setAlignment(Pos.BASELINE_CENTER)

      // it is possible to add an onAction listener for each button
      rb1.setOnAction(e => println("RadioButton 1 was clicked on!"))
      // but it is better to add a listener to the toggle group  selectedToggle property
      radioGroup.selectedToggleProperty()
           .addListener(i => println("Selected radio is " + radioGroup.getSelectedToggle()) )

      // TextArea, TextField, and PasswordField
      // Extend from TextInputControl
      // See also TextFormatter
      val textField = TextField()
      textField.setPromptText("Enter name here")
      // this is fired when the user hits the Enter key
      textField.setOnAction(e => println("Entered text is: " + textField.getText()))
      // we can also observe input in real time
      textField.textProperty().addListener(
        (o, oldValue, newValue) => 
          println("current text input is " + newValue)
        )

      val passwordField = PasswordField()
      passwordField.setPromptText("Enter password here")
      // this is fired when the user hits the Enter key
      passwordField.setOnAction(e => println("Entered password is: " + passwordField.getText()))
      // we can also observe input in real time
      passwordField.textProperty().addListener(
        (o, oldValue, newValue) => 
          println("current password input is " + newValue)
        )

      val textArea = TextArea()
      textArea.setPromptText("Enter multi-line here")
      // this is fired when the user hits the Enter key
      //textArea.setOnAction(e => println("Entered multi-line is: " + textArea.getText()))
      // Need his to avoid large gap between components
      textArea.setPrefHeight(0)
      textArea.setPrefRowCount(5)
      // we can also observe input in real time
      textArea.textProperty().addListener(
        (o, oldValue, newValue) => 
          println("current multi-line input is " + newValue)
        )


      // http://tutorials.jenkov.com/javafx/vbox.html
      //val root = new StackPane()
      val root = VBox(5)
      root.setAlignment(Pos.BASELINE_CENTER)
      root.setFillWidth(true)
      root.getChildren().add(btn)
      root.getChildren().add(cb)
      root.getChildren().add(hyperlink)
      root.getChildren().add(toggleHBox)
      root.getChildren().add(radioHBox)
      root.getChildren().add(textField)
      root.getChildren().add(passwordField)
      root.getChildren().add(textArea)

      primaryStage.setScene(Scene(root, 500, 400))
      primaryStage.show()
    }
}

object Basic {

    def main(args: Array[String]): Unit = {
      Application.launch(classOf[HelloWorld], args: _*)
    }
}


