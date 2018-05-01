package rest_javafxclient;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import entity.FishStick;

/**
 * FishStickSearchController.java
 * Created by: Jennifer Aube, Zachary Brule, Hiral Nilesh Bhatt, Evandro Ramos da Silva, John Smith
 * Date created: April 11, 2018
 * Purpose: assign value to the variables, connect client and create list
 */
public class FishStickSearchController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TextField textFieldSearch;
    @FXML
    private Button buttonSearch;
    @FXML
    private TableView<FishStick> tableView;
    @FXML
    private void handleSearchAction() {
        WebTarget clientTarget;
        ObservableList<FishStick> data = tableView.getItems();
        data.clear();
        Client client = ClientBuilder.newClient();
        client.register(FishStickMessageBodyReader.class);
        if (textFieldSearch.getText().length() > 0) {
            clientTarget = client.target(
            "http://localhost:8080/CST8277Assignment4-war/webresources/entity.fishstick/{beginBy}");
            clientTarget = clientTarget.resolveTemplate("beginBy", textFieldSearch.getText());
        } else {
            clientTarget = client.target(
            "http://localhost:8080/CST8277Assignment4-war/webresources/entity.fishstick/");
        }
        GenericType<List<FishStick>> listc = new GenericType<List<FishStick>>() {
        };
        List<FishStick> customers = clientTarget.request("application/json").get(listc);
        for (FishStick c : customers) {
            data.add(c);
            System.out.println(c.toString());
        }
        }
        @Override
        public void initialize(URL url, ResourceBundle rb) {
        handleSearchAction();
        }      
    
}
