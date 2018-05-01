package rest_javafxclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * REST_JavaFXClient.java
 * Created by: Jennifer Aube, Zachary Brule, Hiral Nilesh Bhatt, Evandro Ramos da Silva, John Smith
 * Date created: April 11, 2018
 * Purpose: Create the window to display FishStick values
 */
public class REST_JavaFXClient extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FishStickSearch.fxml"));
        stage.setTitle("FishStick JavaFX REST Client");
        stage.setWidth(650);
        stage.setHeight(650);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
