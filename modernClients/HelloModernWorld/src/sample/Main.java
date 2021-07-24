package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i modernClients.HelloModernWorld.runMain sample.Main
 * ./mill -i --watch modernClients.HelloModernWorld.runMain sample.Main
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
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //System.out.println(System.getProperty("user.dir"));
        // Place a resources directory at the same level as Mill module
        // Both loads will work relative to the module
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("resources/sample.fxml"));
        primaryStage.setTitle("Hello Modern World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
