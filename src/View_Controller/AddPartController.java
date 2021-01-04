/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class AddPartController implements Initializable {

    @FXML
    private AnchorPane InHouseRadio;
    @FXML
    private Label aLabel;
    @FXML
    private TextField aLabelTxt;
    @FXML
    private TextField partIdTxt;
    @FXML
    private TextField partNmTxt;
    @FXML
    private TextField partInvTxt;
    @FXML
    private TextField partPriceTxt;
    @FXML
    private TextField partMaxTxt;
    @FXML
    private TextField partMinTxt;
    @FXML
    private RadioButton aInHouseRadio;
    @FXML
    private ToggleGroup SourceToggle;
    @FXML
    private RadioButton aOutsourcedRadio;
    @FXML
    private Button aSaveButton;
    @FXML
    private Button aCancelButton;

    @FXML
    private void sourceToggle(ActionEvent event) {
        if(aInHouseRadio.isSelected()) {
            aLabelTxt.setVisible(true);
            aLabel.setText("Machine ID");
            aLabelTxt.setPromptText("Mach ID");
        }
        else if (aOutsourcedRadio.isSelected()) {
            aLabel.setText("Company Name");
            aLabelTxt.setPromptText("Comp Nm");
        }
    }
    
    @FXML
    private void onActionSave(ActionEvent event) throws IOException {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            
            if (Integer.parseInt(partMaxTxt.getText()) < Integer.parseInt(partMinTxt.getText())) {
                alert.setContentText("Max value cannot be less than Min value!");
                alert.showAndWait();
                return;
            }
            if (Integer.parseInt(partInvTxt.getText()) > Integer.parseInt(partMaxTxt.getText())) {
                alert.setContentText("Inventory value cannot be greater than Max value!");
                alert.showAndWait();
                return;
            }
            if (Integer.parseInt(partInvTxt.getText()) < Integer.parseInt(partMinTxt.getText())) {
                alert.setContentText("Inventory value cannot be less than Min value!");
                alert.showAndWait();
                return;
            }
            if (!aInHouseRadio.isSelected() && !aOutsourcedRadio.isSelected()) {
                alert.setContentText("Whoops! You forgot to check In-House or Outsourced.");
                alert.showAndWait();
                return;
            }
            else {
                if (aInHouseRadio.isSelected()) {
                Part p = new InHouse(Inventory.partIdCounter(),partNmTxt.getText(), Double.parseDouble(partPriceTxt.getText()), Integer.parseInt(partInvTxt.getText()), 
                        Integer.parseInt(partMinTxt.getText()), Integer.parseInt(partMaxTxt.getText()),Integer.parseInt(aLabelTxt.getText()));
                Inventory.addPart(p);
                }
                else if (aOutsourcedRadio.isSelected()) {
                    Part p = new Outsourced(Inventory.partIdCounter(),partNmTxt.getText(), Double.parseDouble(partPriceTxt.getText()), Integer.parseInt(partInvTxt.getText()), 
                            Integer.parseInt(partMinTxt.getText()), Integer.parseInt(partMaxTxt.getText()),aLabelTxt.getText());
                    Inventory.addPart(p);
                }
                goMainScreen(event);
            }
        }
        catch (NumberFormatException e){ System.out.println("Please enter valid values in text fields!"); }
    }
    
    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Add Part");
        alert.setHeaderText("Return to Main Screen");
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            goMainScreen(event);
        }
    }
   
    
    
    
    
    public void goMainScreen(ActionEvent event) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
       Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
       stage.setTitle("");
       stage.setScene(new Scene(root));
       stage.show();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
