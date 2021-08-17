package org.modernclients.others

// cSpell:ignore javafx, verdana

import collection.JavaConverters._

import javafx.application.Application
import javafx.stage.Stage

import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import javafx.scene.web.HTMLEditor
import javafx.scene.control.Pagination
import javafx.scene.control.Hyperlink
import javafx.scene.shape.Rectangle
import javafx.scene.paint.Color
import javafx.scene.paint.Stop
import javafx.scene.paint.LinearGradient

import javafx.event.EventHandler
import javafx.event.ActionEvent

import javafx.geometry.Pos
import javafx.scene.paint.CycleMethod
import javafx.scene.layout.BorderPane
import javafx.scene.control.Label
import javafx.scene.text.Font
import javafx.geometry.Insets
import javafx.scene.control.ScrollBar
import javafx.geometry.Orientation
import javafx.scene.Group
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.scene.layout.HBox
import javafx.scene.control.Spinner
import javafx.scene.control.SpinnerValueFactory
import javafx.scene.control.Tooltip

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

      val root = VBox(5)
      val scene = Scene(root, 650, 500)

      // Button 
      // val btn = Button("Say 'Hello Others'")
      // btn.setOnAction( (event: ActionEvent) => {
      //         println("Hello basic")
      //     }
      // )
      // // See VBox.setFillWidth(true)
      // btn.setMaxWidth(99999D) //or Double.MAX_VALUE
      // btn.setText("Say 'Hello Others")
      // btn.setOnAction(new EventHandler[ActionEvent]() {
      //      override def handle(event: ActionEvent) = {
      //         println("Hello Others")
      //     }
      // })

      // HTMLEditor
      /* 
        dependent on the JavaFX WebView component for rendering the user 
        input, this control does not ship in the javafx.controls module, 
        but rather the javafx.web module
      */
      val htmlEditor = HTMLEditor()
      val htmlText = "<b>Bold text</b>"
      htmlEditor.setHtmlText(htmlText)
      htmlEditor.setPrefHeight(200)
      
      // Pagination
      /*
        Pagination is an abstract way of representing multiple pages, where only
        the currently showing page actually exists in the scene graph and all 
        other pages are only generated upon request.

        The callback pageFactory allows for on-demand generation of pages, as 
        requested by the user
      */
      val pagination = Pagination(10, 0)
      pagination.setPageFactory(
        pageIndex => {
          val box = VBox(5)
          val len = 5
          for (i <- 0 until 5) {
              val linkNumber = pageIndex * len + i
              val link = Hyperlink("Hyperlink #" + linkNumber)
              link.setOnAction(e => println("Hyperlink #" + linkNumber + " clicked!"))
              box.getChildren().add(link)
          }
          box
      })
      pagination.prefHeight(100)

      // ScrollBar, ScrollBarMark
      // TODO not working
      val sc = ScrollBar()
      //sc.setLayoutX(scene.getWidth()-sc.getWidth())
      //sc.setLayoutY(scene.getHeight()-sc.getHeight())
      sc.setMin(0)
      sc.setMax(100)
      sc.setValue(0)
      sc.setOrientation(Orientation.HORIZONTAL)
      // Should be dynamically bound
      sc.setPrefWidth(scene.getWidth())

      // https://docs.oracle.com/javafx/2/ui_controls/scrollbar.htm
      // is typically used as part of a more complex UI control. For example, it
      // is used in the ScrollPane control to support vertical and horizontal scrolling
      val color = Color.BLUE
      val stops = Array[Stop](Stop(0, Color.BLACK), Stop(1,color))
      val gradient = LinearGradient(0.0, 0.0, 1500.0, 1000.0, false, CycleMethod.NO_CYCLE, stops:_*)

      // we place the linear gradient inside a big rectangle
      val RECT_X = 100  // If it exceeds the `hb`, then no scroll appears
      val RECT_Y = 50
      val rectangle = Rectangle(RECT_X, RECT_Y, gradient)
      val rectangle1 = Rectangle(RECT_X, RECT_Y, gradient)
      val rectangle2 = Rectangle(RECT_X, RECT_Y, gradient)
      val rectangle3 = Rectangle(RECT_X, RECT_Y, gradient)
      val rectangle4 = Rectangle(RECT_X, RECT_Y, gradient)

      val colorLabel = Label(s"Color: ${color}")
      colorLabel.setFont(Font("Verdana", 18))
      // Holds scroll container + scrollbar at the same level
      val vb = Group()   // VBox() Fail, Group(), ok wrong location, BorderPane() // fail
      val hb = HBox()
      //hb.setLayoutY(5)
      hb.setSpacing(10)

      hb.getChildren().addAll( colorLabel, rectangle, rectangle1, rectangle2, rectangle3, rectangle4)
      vb.getChildren().addAll( hb, sc)

      // Use of setLayoutY/setLayoutX flawed. Does not work
      // Should be set dynamically
      sc.setLayoutY(rectangle.getHeight())


      sc.valueProperty().addListener(new ChangeListener[Number]() {
            def changed(ov: ObservableValue[_ <: Number], old_val: Number, new_val: Number) = {
                  val delta = (new_val.doubleValue()/100.0)*RECT_X
                  println(s"ScrollBar old = ${old_val.doubleValue()}  new = ${new_val.doubleValue()} ; delta = $delta")
                  hb.setLayoutX(-delta)
                  //hb.setLayoutX(-new_val.doubleValue())
                  //rectangle.setLayoutX(-new_val.doubleValue())
            }
        })

      // Separator (see Container)

      // Spinner
      /*
        Because a Spinner can be used to step through various types of value
        (integer, float, double, or even a List of some type), the Spinner defers to a
        SpinnerValueFactory to handle the actual process of stepping through the range
        of values (and precisely how to step).
      */
      val spinner = Spinner[Integer]()
      spinner.setValueFactory(SpinnerValueFactory.IntegerSpinnerValueFactory(5, 10)) 
      spinner.valueProperty()
              .addListener(
                (o, oldValue, newValue) => {
                  println("value changed: '" + oldValue + "' -> '" + newValue + "'")
        })

      // Tooltip
      val rect = Rectangle(0, 0, 100, 100)
      val t = Tooltip("A Square")
      Tooltip.install(rect, t)

      val toolTipButton = Button("Hover Over Me");
      toolTipButton.setTooltip(Tooltip("Tooltip for Button"))
      

      // http://tutorials.jenkov.com/javafx/vbox.html
      //root.setAlignment(Pos.BASELINE_CENTER)
      root.setAlignment(Pos.TOP_CENTER)
      root.setFillWidth(true)
      //root.getChildren().add(btn)
      root.getChildren().add(htmlEditor)
      root.getChildren().add(pagination)
      root.getChildren().add(spinner)
      root.getChildren().add(toolTipButton)
      root.getChildren().add(vb)

      primaryStage.setScene(scene)
      primaryStage.show()
    }
}

object Others {

    def main(args: Array[String]): Unit = {
      Application.launch(classOf[Others], args: _*)
    }
}



