package org.modernclient

// cSpell:ignore javafx, dropshadow

import javafx.animation.Animation
import javafx.animation.Interpolator
import javafx.animation.RotateTransition
import javafx.application.Application
import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.beans.binding.When
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableObjectValue
import javafx.beans.value.ObservableValue
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.effect.DropShadow
import javafx.scene.effect.Reflection
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.scene.shape.Ellipse
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.Stage
import javafx.util.Duration


/**
 *
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i modernClients.ch02-javafx_fundamentals.myshapesproperties.run
 * ./mill -i modernClients.ch02-javafx_fundamentals.myshapesproperties.runMain org.modernclient.MyShapesProperties
 * ./mill -i --watch modernClients.ch02-javafx_fundamentals.myshapesproperties.runMain org.modernclient.MyShapesProperties
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
class MyShapesProperties extends Application {

    override def start(stage: Stage) = {
        // Build scene graph
        // Define a LinearGradient
        val stops = List( new Stop(0, Color.DODGERBLUE),
                new Stop(0.5, Color.LIGHTBLUE),
                new Stop(1.0, Color.LIGHTGREEN))
        val gradient = new LinearGradient(1, 1, 1, 0, true,
                CycleMethod.NO_CYCLE, stops:_*)

        // Create an Ellipse, set dropshadow, fill with gradient
        val ellipse = new Ellipse(110, 70)
        ellipse.setEffect( DropShadow(30, 10, 10, Color.GRAY))
        //ellipse.setFill(Color.LIGHTBLUE)
        ellipse.setFill(gradient)

        // Create a Text shape
        val text = new Text("My Shapes")
        text.setFont(new Font("Arial Bold", 24))
        // Create a second Text shape
        val text2 = new Text("Animation Status: ")
        text2.setFont(new Font("Arial Bold", 18))

        val r = new Reflection()
        r.setFraction(0.8)
        r.setTopOffset(5.0)
        text.setEffect(r)

        val stackPane = new StackPane()
        stackPane.getChildren().addAll(ellipse, text)
        val vBox = new VBox(stackPane, text2)
        vBox.setAlignment(Pos.CENTER)
        vBox.setSpacing(50.0)

        // Define RotateTransition for stackPane
        val rotate = new RotateTransition(Duration.millis(2500), stackPane)
        rotate.setToAngle(360)
        rotate.setFromAngle(0)
        rotate.setInterpolator(Interpolator.LINEAR)

        /*
        // 3 Ok
        // Invalidation Listener using lambda expression
        rotate.statusProperty().addListener(observable => {
            val o = observable.asInstanceOf[ObservableObjectValue[Animation.Status]]
            text2.setText("Animation status: " +
                    o.getValue())
            text2.setText("Animation status: " + rotate.getStatus())
        })
        */

        /*
        // 2 Ok
        // Invalidation Listener using anonymous class
        rotate.statusProperty().addListener(new InvalidationListener() {
            override def invalidated(observable: Observable) = {
                val o = observable.asInstanceOf[ObservableObjectValue[Animation.Status]]
                text2.setText("Animation status: " +
                        o.getValue())
            }

        })
        */

        /* // 1 Ok
        // Change Listener using lambda expression
        rotate.statusProperty().addListener((observableValue, oldValue, newValue) => {
            text2.setText("Was " + oldValue + ", Now " + newValue)
        })
        */
        
        // Change Listener using lambda expression
        rotate.statusProperty().addListener(( observableValue: ObservableValue[_ <:Animation.Status], 
                                              oldValue: Animation.Status, 
                                              newValue: Animation.Status) => {
            text2.setText("Was " + oldValue + ", Now " + newValue)
        })
        

        /* // 5 Ok (extra test)
        val cl = new ChangeListener[Animation.Status]() {
            override def changed(observableValue: ObservableValue[ _ <: Animation.Status],
                                oldValue: Animation.Status, newValue: Animation.Status) = {
                text2.setText("Was:" + oldValue + ", Now: " + newValue)

            }        
        }
        // Change Listener using anonymous class
        rotate.statusProperty().addListener(cl)
        */

        /*
        // 4 ok 
        // Change Listener using anonymous class
        rotate.statusProperty().addListener(new ChangeListener[Animation.Status]() {
            override def changed(observableValue: ObservableValue[ _ <: Animation.Status],
                                oldValue: Animation.Status, newValue: Animation.Status) = {
                text2.setText("Was - " + oldValue + ", Now - " + newValue)

            }
        })
        */


        // Bind expression with When
        text2.strokeProperty().bind(new When(rotate.statusProperty()
                .isEqualTo(Animation.Status.RUNNING)).`then`(Color.GREEN).otherwise(Color.RED))

        // Bind expression
        //text2.rotateProperty().bind(stackPane.rotateProperty())

        // Bidirectional Bind
        //text2.textProperty().bindBidirectional(text.textProperty())

        // Bind with fluent API
        //text2.textProperty().bind(stackPane.rotateProperty().asString("%.1f"))

        // configure mouse click handler
        stackPane.setOnMouseClicked(mouseEvent => {
            if (rotate.getStatus().equals(Animation.Status.RUNNING)) {
                rotate.pause()
            } else {
                rotate.play()
            }
        })

        val scene = new Scene(vBox, 350, 350, Color.LIGHTYELLOW)
        //scene.getStylesheets().add("/styles/Styles.css")
        
        stage.setTitle("MyShapesProperties")
        stage.setScene(scene)
        stage.show()
    }

    def launchIt():Unit = {
        Application.launch()
    }

}


object MyShapesProperties {
  /**
   * The main() method is ignored in correctly deployed JavaFX application.
   * main() serves only as fallback in case the application can not be
   * launched through deployment artifacts, e.g., in IDEs with limited FX
   * support. NetBeans ignores main().
   *
   * @param args the command line arguments
   */
  def main(args: Array[String]) =
    val app = new MyShapesProperties
    app.launchIt()

}