import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("resources/scene.fxml"));
        root.getStylesheets().add(getClass().getResource("resources/stylesheet.css").toExternalForm());
        primaryStage.setTitle("Time Counter");
        primaryStage.getIcons().add(new Image("file:icon.png"));
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.setMinWidth(292);
        primaryStage.setMinHeight(270);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
