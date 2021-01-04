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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class ModifyPartController implements Initializable {

    @FXML
    private Label mLabel;
    @FXML
    private TextField mLabelTxt;
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
    private RadioButton mInHouseRadio;
    @FXML
    private ToggleGroup SourceToggle;
    @FXML
    private RadioButton mOutsourcedRadio;
    @FXML
    private Button mSaveButton;
    @FXML
    private Button mCancelButton;
 
    private Part selectedPart = null;
    
    @FXML
    private void sourceToggle(ActionEvent event) {
        if(mInHouseRadio.isSelected()) {
            mLabel.setText("Machine ID");
            mLabelTxt.setPromptText("Mach ID");
        }
        else if (mOutsourcedRadio.isSelected()) {
            mLabel.setText("Company Name");
            mLabelTxt.setPromptText("Comp Nm");
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
            else {
                selectedPart.setId(Integer.parseInt(partIdTxt.getText()));
                selectedPart.setName(partNmTxt.getText());
                selectedPart.setPrice(Double.parseDouble(partPriceTxt.getText()));
                selectedPart.setStock(Integer.parseInt(partInvTxt.getText()));
                selectedPart.setMin(Integer.parseInt(partMinTxt.getText()));
                selectedPart.setMax(Integer.parseInt(partMaxTxt.getText()));
                
                if (mInHouseRadio.isSelected() & selectedPart instanceof InHouse) {
                updatePartInHouse();
                } else if (mInHouseRadio.isSelected() & selectedPart instanceof Outsourced) {
                    updatePartInHouse();
                } else if (mOutsourcedRadio.isSelected() & selectedPart instanceof Outsourced) {
                    updatePartOutsourced();
                } else if (mOutsourcedRadio.isSelected() & selectedPart instanceof InHouse) {
                    updatePartOutsourced();
                }
                goMainScreen(event);
            }
        }
        catch (NumberFormatException e){ System.out.println("Please enter valid values in text fields!"); }
    }
    
    private void updatePartInHouse(){
        Part inHousePart = new InHouse(selectedPart.getId(),selectedPart.getName(), selectedPart.getPrice(),selectedPart.getStock(),
                selectedPart.getMin(), selectedPart.getMax(),Integer.parseInt(mLabelTxt.getText()));
        
        Inventory.updatePart(selectedPart.getId(), inHousePart);
    }
    private void updatePartOutsourced() {
        Part outsourcedPart = new Outsourced(selectedPart.getId(),selectedPart.getName(), selectedPart.getPrice(),selectedPart.getStock(),
                selectedPart.getMin(), selectedPart.getMax(), mLabelTxt.getText());
        
        Inventory.updatePart(selectedPart.getId(), outsourcedPart);
    }
    
    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Modify Part");
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
        
        selectedPart = MainScreenController.getSelectedPart();
        if (selectedPart instanceof InHouse) {
            mInHouseRadio.setVisible(true);
            mInHouseRadio.setSelected(true);
            mLabel.setText("Machine ID");
            mLabelTxt.setText(String.valueOf(((InHouse)selectedPart).getMachineId()));
        }
        else if (selectedPart instanceof Outsourced) {
            mOutsourcedRadio.setVisible(true);
            mOutsourcedRadio.setSelected(true);
            mLabel.setText("Company Name");
            mLabelTxt.setText(((Outsourced)selectedPart).getCompanyName());
        }
        partIdTxt.setText(Integer.toString(selectedPart.getId()));
        partNmTxt.setText(selectedPart.getName());
        partInvTxt.setText(Integer.toString(selectedPart.getStock()));
        partPriceTxt.setText(Double.toString(selectedPart.getPrice()));
        partMaxTxt.setText(Integer.toString(selectedPart.getMax()));
        partMinTxt.setText(Integer.toString(selectedPart.getMin()));
        
    }    
    
}
