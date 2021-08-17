package org.modernclients.container

// cSpell:ignore javafx

import collection.JavaConverters._

import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import javafx.scene.layout.HBox
import javafx.scene.layout.TilePane

import javafx.scene.control.TitledPane
import javafx.scene.control.Accordion
import javafx.scene.control.CheckBox
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonBar.ButtonData
import javafx.scene.control.ScrollPane

import javafx.geometry.Pos
import javafx.geometry.Orientation

import javafx.event.EventHandler
import javafx.event.ActionEvent

import javafx.scene.paint.Stop
import javafx.scene.paint.Color
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.CycleMethod

import javafx.scene.shape.Rectangle

import javafx.beans.value.ChangeListener
import javafx.scene.layout.StackPane
import javafx.scene.control.SplitPane
import javafx.scene.control.TabPane
import javafx.scene.control.Tab
import java.util.Random
import javafx.scene.control.ToolBar
import javafx.scene.control.Separator

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
      // btn.setMaxWidth(99999D) //or Double.MAX_VALUE
      // btn.setText("Say 'Hello container")
      // btn.setOnAction(new EventHandler[ActionEvent]() {
      //      override def handle(event: ActionEvent) = {
      //         println("Hello container")
      //     }
      // })
      
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


      val b21 = Button("Button 2.1")
      val b22 = Button("Button 2.")
      val b23 = Button("Button 2.3")

      b21.setRotate(90)
      b22.setRotate(90)
      b23.setRotate(90)

      b1.setOnAction( (event: ActionEvent) => {
              println("Hello b2.1")
          }
      )

      b2.setOnAction( (event: ActionEvent) => {
              println("Hello b2.2")
          }
      )

      b3.setOnAction( (event: ActionEvent) => {
              println("Hello b2.3")
          }
      )

      val t21 = TitledPane("TitledPane 1", b21)
      val t22 = TitledPane("TitledPane 2", b22)
      val t23 = TitledPane("TitledPane 3", b23)
      val accordion2 = Accordion()

      // t21.setRotate(90)
      // t22.setRotate(90)
      // t23.setRotate(90)

      val mainPane = new HBox(accordion2)
      accordion2.prefWidthProperty().bind(mainPane.heightProperty())
      accordion2.prefHeightProperty().bind(mainPane.widthProperty().divide(2))

      accordion2.setRotate(270)
      accordion2.getPanes().addAll(t21, t22, t23)

      println(s"accordion.getContentBias() = ${accordion.getContentBias()}")
      println(s"accordion2.getContentBias() = ${accordion2.getContentBias()}")

      val vertical = TilePane(Orientation.VERTICAL)
      vertical.setHgap(8)
      vertical.setPrefColumns(4)
      for (i <- 0 until 4) {
          vertical.getChildren().add(CheckBox(s"check ${i+1}"))
      }

      // ButtonBar functionality of placing the provided Buttons in the correct
      // order for the operating system on which the user interface is running

      // Create the ButtonBar instance
      val buttonBar = ButtonBar()

      // Create the buttons to go into the ButtonBar
      val yesButton = Button("Yes")
      val noButton = new Button("No")

      ButtonBar.setButtonData(yesButton, ButtonData.YES)
      ButtonBar.setButtonData(noButton, ButtonData.NO)

      // Add buttons to the ButtonBar
      buttonBar.getButtons().addAll(yesButton, noButton)

      // ScrollPane
      // in this sample we create a linear gradient to make the scrolling visible
      val stops = Array[Stop](Stop(0, Color.BLACK), Stop(1,Color.RED))
      val gradient = LinearGradient(0.0, 0.0, 1500.0, 1000.0, false, CycleMethod.NO_CYCLE, stops:_*)

      // we place the linear gradient inside a big rectangle
      val rect = Rectangle(2000, 2000, gradient)

      // which is placed inside a scroll pane that is quite small in comparison
      val scrollPane = ScrollPane()
      scrollPane.setPrefSize(120, 120)
      scrollPane.setContent(rect)

      // and we then listen (and log) when the user is scrolling vertically or horizontally
      val o: ChangeListener[_ >: Number] = 
        (obs, oldValue, newValue) => {
            println(s"x / y values are: ( ${scrollPane.getHvalue()} , ${scrollPane.getVvalue()} )")
      }
      scrollPane.hvalueProperty().addListener(o)
      scrollPane.vvalueProperty().addListener(o)

      // SplitPane
      /*
      The SplitPane control observes the minimum and maximum size properties of its
      children. It will never reduce the size of a node below its minimum size and will never give
      it more size than its maximum size. For this reason, it is recommended that all nodes added
      to a SplitPane be wrapped inside a separate layout container, such that the layout container
      may handle the sizing of the node, without impacting the SplitPane’s ability to function.
      */
      val sp1 = StackPane()
      val sp2 = StackPane()
      val sp3 = StackPane()

      sp1.getChildren().add(Button("Button One"))
      sp2.getChildren().add(Button("Button Two"))
      sp3.getChildren().add(Button("Button Three"))

      val splitPane = SplitPane()
      splitPane.getItems().addAll(sp1, sp2, sp3)
      splitPane.setDividerPositions(0.3f, 0.6f, 0.9f)

      val random = Random()
      
      // https://stackoverflow.com/questions/4246351/creating-random-colour-in-java
      def randomColor(): Color = {
        /*
        val hue = random.nextFloat()
        // Saturation between 0.1 and 0.3
        val saturation = (random.nextInt(2000) + 1000) / 10000f
        val luminance = 0.9f
        Color.hsb(hue, saturation, luminance)
        */
        //Color((Math.random() * 0x1000000).toInt)

        //to get rainbow, pastel colors
        // val hue = random.nextFloat()
        // val saturation = 0.9f//1.0 for brilliant, 0.0 for dull
        // val luminance = 1.0f //1.0 for brighter, 0.0 for black
        // println(s"hue = $hue")
        // Color.hsb(hue, saturation, luminance)

        val R = Math.random()*256
        val G = Math.random()*256
        val B = Math.random()*256
        Color.rgb(R.toInt, G.toInt, B.toInt); //random color, but can be bright or dull
      }

      // TabPane
      val tabPane = TabPane()
      tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE)
      for (i <- 1 to 5) {
        val tab = Tab(s"Tab $i", new Rectangle(200, 50, randomColor()))
        tabPane.getTabs().add(tab)
      }      

      // ToolBar
      /* 
         The most common elements to add to a ToolBar are other UI controls such
         as Button, ToggleButton, and Separator, but there is no restriction on 
         what can be placed within a ToolBar, as long as it is a Node.

         The ToolBar control does offer one useful piece of functionality – it supports the
         concept of overflow, so that if there are more elements to be displayed than there is
         space to display them all, it removes the “overflowing” elements from the ToolBar and
         instead shows an overflow button that when clicked pops up a menu containing all
         overflowing elements of the ToolBar.         

         ToolBar offers a vertical orientation, so that it may be placed on the left- or 
         right-hand side of an application user interface
      */
      val toolBar = ToolBar()
      toolBar.getItems().addAll(
                            Button("New"),
                            Button("Open"),
                            Button("Save"),
                            Separator(),
                            Button("Clean"),
                            Button("Compile"),
                            Button("Run"),
                            Separator(),
                            Button("Debug"),
                            Button("Profile"),
                            Separator(),
                            Button("Overflow-1"),
                            Button("Overflow-1")
      )

      // http://tutorials.jenkov.com/javafx/vbox.html
      //val root = new StackPane()
      val root = VBox(5)
      //root.setAlignment(Pos.BASELINE_CENTER)
      root.setAlignment(Pos.TOP_CENTER)
      root.setFillWidth(true)
      //root.getChildren().add(btn)
      root.getChildren().add(accordion)
      root.getChildren().add(mainPane)
      root.getChildren().add(vertical)
      root.getChildren().add(buttonBar)
      root.getChildren().add(scrollPane)
      root.getChildren().add(splitPane)
      root.getChildren().add(tabPane)
      root.getChildren().add(toolBar)

      primaryStage.setScene(Scene(root, 600, 800))
      primaryStage.show()
    }
}

object Container {

    def main(args: Array[String]): Unit = {
      Application.launch(classOf[Container], args: _*)
    }
}



