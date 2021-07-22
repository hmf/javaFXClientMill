package helloworld;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i HelloWorldScala.run
 * ./mill -i HelloWorldScala.runMain helloworld.HelloWorld
 * ./mill -i --watch HelloWorldScala.run
 * 
 * @see https://stackoverflow.com/questions/12124657/getting-started-on-scala-javafx-desktop-application-development
 */
class HelloWorld extends Application {

    @Override
    def start(primaryStage: Stage) = {
        primaryStage.setTitle("Hello Scala World!")
        val btn = new Button()
        btn.setText("Say 'Hello Scala World'")
        btn.setOnAction(new EventHandler[ActionEvent]() {

            @Override
            def handle(event: ActionEvent) = {
                System.out.println("Hello Scala World!");
            }
        })

        val root = new StackPane()
        root.getChildren().add(btn)
        primaryStage.setScene(new Scene(root, 300, 250))
        primaryStage.show()
    }
}

object HelloWorld:
  def main(args: Array[String]) =
    Application.launch(classOf[HelloWorld], args: _*)
