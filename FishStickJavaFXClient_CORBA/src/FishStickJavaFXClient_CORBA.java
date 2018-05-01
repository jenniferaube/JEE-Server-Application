/**
 * @author Hiral Nilesh Bhatt
 * CORBA Client using Java FX
 * Created by: Hiral Nilesh Bhatt, Jennifer Aube, Zachary Brule, Evandro Ramos da Silva, John Smith
 * Purpose: Create window to add new fishstick record, also display fishstick records.
 */
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import entity.FishStick;
import business.FishStickFacadeRemote;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 * 
 * @author Hiral Nilesh Bhatt
 * CORBA Client class extends Application
 */
public class FishStickJavaFXClient_CORBA extends Application {
    //Initiate labels and textfileds.
    private Label fishStickLabel = new Label();
    private Label recNumLabel = new Label();
    private Label omegaLabel = new Label();
    private Label lambdaLabel = new Label();
    private TextField recnumTextField = new TextField();
    private TextField omegaTextField = new TextField();
    private TextField lambdaTextField = new TextField();
    private TextArea viewFishStickTextArea = new TextArea();
    private Button viewButton = new Button();
    private Button addButton = new Button();


    private static FishStickFacadeRemote remoteFishStick = null;
    
    /**
     * 
     * @author Hiral Nilesh Bhatt 
     * @param primaryStage 
     * Start method for the application.
     */
    @Override
    public void start(Stage primaryStage) {
        
        fishStickLabel.setText("FishStick");//Label for FishStick.
        recNumLabel.setText("RecordNumber:");//Label for Record Number.
        omegaLabel.setText("Omega:");//Label for Omega.
        lambdaLabel.setText("Lambda:");//Label for Lambda.
        viewButton.setText("Show FishStick");//Button to show fishstick records.
        viewButton.setOnAction(new EventHandler<ActionEvent>() {//On click of the button.
        @Override
        public void handle(ActionEvent event) {
            
             try {
                if (remoteFishStick != null) {//Check for connection.
                    viewFishStickTextArea.clear();//
                   for (FishStick fs : remoteFishStick.findAll()) {//For each record.
                    viewFishStickTextArea.appendText(
                    String.format(//Formatted output for each record.
                    "FishStick :: ID:%d - Record number:%d, Lambda:%s, Omega:%s %n",
                    fs.getId(), fs.getRecordNum(),fs.getLambda().toString(),fs.getOmega().toString()));
                    }
                } 
                else {
                    showAlert(AlertType.ERROR, "No remote object available");//Display error popup.
                }
             } catch (Exception ex) {
                showAlert(AlertType.ERROR, ex.getMessage());} //Display error popup.
            }
        });
        addButton.setText("Add FishStick");//Adding new record.
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int recnum = 0;
                if(recnumTextField.getText().isEmpty()|| omegaTextField.getText().isEmpty() || lambdaTextField.getText().isEmpty()){//All fileds must be filled.
                    showAlert(AlertType.ERROR, "Please fill all the fields");//Display error popup.
                }
                else{
                    try{
                        recnum = Integer.parseInt(recnumTextField.getText());//Parse the record number to integer.
                        try {
                        if (remoteFishStick != null) {//Check for connection.
                            FishStick fishStick = new FishStick();
                            fishStick.setRecordNum(recnum);
                            fishStick.setOmega(omegaTextField.getText());
                            fishStick.setLambda(lambdaTextField.getText());
                            remoteFishStick.create(fishStick);
                            recnumTextField.clear();
                            omegaTextField.clear();
                            lambdaTextField.clear();
                         } 
                        else {
                            showAlert(AlertType.ERROR, "No remote object available");//Display error popup.
                        }
                        } catch (Exception ex) {
                            showAlert(AlertType.ERROR, ex.getMessage());//Display error popup.
                        }
                     }
                     catch(Exception ex){
                        showAlert(AlertType.ERROR, "Enter a number for record number.");//Display error popup. 
                     }
                     
                    
                }
               
                
            }
        });
        //Horizontal Set up.
        HBox buttonsHBox = new HBox();
        buttonsHBox.setSpacing(10);
        buttonsHBox.setPadding(new Insets(10, 10, 10, 10));
        buttonsHBox.setAlignment(Pos.CENTER_RIGHT);
        buttonsHBox.getChildren().addAll(addButton, viewButton);
        //Grid Set up.
        GridPane root = new GridPane();
        root.add(fishStickLabel, 1, 1); // column 1, row 1
        
        root.add(recNumLabel, 1, 2); // column 2, row 1
        root.add(recnumTextField, 2, 2);// column 2, row 2
        root.add(omegaLabel, 1, 3); //And so on..
        root.add(omegaTextField, 2, 3);
        root.add(lambdaLabel, 1, 4); 
        root.add(lambdaTextField, 2, 4);
        
        root.add(buttonsHBox, 1, 5); 
        
        root.add(viewFishStickTextArea, 1, 6);
        root.setHgap(10);
        root.setVgap(10);
        GridPane.setMargin(viewFishStickTextArea, new Insets(0, 10, 10, 0)); // top, right, bottom, left
      
        Scene scene = new Scene(root);
        primaryStage.setTitle("JavaFX Client - CORBA");
        primaryStage.setScene(scene);
        primaryStage.show();
        showAlert(AlertType.INFORMATION, "Trying for a session...");
        remoteFishStick = getRemoteSession();
        if(remoteFishStick != null){
        showAlert(AlertType.INFORMATION, "Got a session :)");//Connection Established with remote facade.
        }
        else{
        showAlert(AlertType.ERROR, "No remote object available");
        }

    }
    /**
     * @author Hiral Nilesh Bhatt
     * @param alertType Type of error
     * @param message message string to display
     * Produces alert boxes with messages.
     */
    public void showAlert(AlertType alertType, String message) {//Customized Alert box.
        Alert alert = new Alert(alertType);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /**
     * @author Hiral Nilesh Bhatt
     * @return FishStickFacadeRemote session object
     * Establishing session.
     */
    private static FishStickFacadeRemote getRemoteSession() {
        FishStickFacadeRemote session = null;
        // CORBA properties and values and lookup taken after earlier work provided by
        // Todd Kelley (2016) Personal Communication
        System.setProperty("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
        System.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
        try {
            InitialContext ic = new InitialContext();
            session = (FishStickFacadeRemote)
            ic.lookup("java:global/CST8277Assignment4/CST8277Assignment4-ejb/FishStickFacade");
            } catch (NamingException e) {
            System.out.println("Problem Cause: \n" + e.getMessage());
            } catch (Exception e) {
        System.out.println("Problem Cause: \n" + e.getMessage());
        }
        return session;
    }

    /**
     * @author Hiral Nilesh Bhatt
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(FishStickJavaFXClient_CORBA.class);
    }//End of Main.
    
}//End of Class.