/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class ModifyProductController implements Initializable {

    @FXML
    private TextField prodIdTxt;
    @FXML
    private TextField prodNmTxt;
    @FXML
    private TextField prodInvTxt;
    @FXML
    private TextField prodPriceTxt;
    @FXML
    private TextField prodMaxTxt;
    @FXML
    private TextField prodMinTxt;
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNmCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableView<Part> assocPartTable;
    @FXML
    private TableColumn<Part, Integer> assocPartIdCol;
    @FXML
    private TableColumn<Part, String> assocPartNmCol;
    @FXML
    private TableColumn<Part, Integer> assocPartInvCol;
    @FXML
    private TableColumn<Part, Double> assocPartPriceCol;
    @FXML
    private Button mAddButton;
    @FXML
    private Button mDelButton;
    @FXML
    private Button mCancelButton;
    @FXML
    private Button mSaveButton;
    @FXML
    private Button partSearchButton;
    @FXML
    private TextField partSearchBox;
    
    private Product selectedProduct = MainScreenController.getSelectedProduct();
    ObservableList<Part> assocPartsList = selectedProduct.getAllAssociatedParts();
    
    @FXML
    private void searchParts(ActionEvent event) {
        String s = partSearchBox.getText().toLowerCase();
        ObservableList<Part> partsList = Inventory.lookupPart(s);
  try {      
        if (partsList.size() == 0) {     
            int i = Integer.parseInt(s);
            Part p = Inventory.lookupPart(i);
            partsList.add(p);
        }
        partTable.setItems(partsList);
    }
  catch (NumberFormatException e){ System.out.println("Please enter a valid value in search field!"); }
    }
       
    @FXML
    private void onActionAdd(ActionEvent event) {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        boolean repeated = false;
        if (selectedPart == null) {
            return;
        }
        else {
            int id = selectedPart.getId();
            for(int i = 0; i < assocPartsList.size(); i++) {
                if (assocPartsList.get(i).getId() == id) {
                    repeated = true;
                }
            }
        }if(!repeated) {
            selectedProduct.addAssociatedParts(selectedPart);
        }
    }
    
    @FXML
    private void onActionDelete(ActionEvent event) {
        if (assocPartTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing Selected");
            alert.setContentText("Nothing was selected to delete");
            alert.showAndWait();
        } else if (Inventory.getAllParts().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Part");
            alert.setHeaderText("Deleting Part");
            alert.setContentText("Are you sure you want to delete " + assocPartTable.getSelectionModel().getSelectedItem().getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                
                selectedProduct.deleteAssociatedPart(assocPartTable.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Modify Product");
        alert.setHeaderText("Return to Main Screen");
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            goMainScreen(event);
        }
    }
    
    @FXML
    private void onActionSave(ActionEvent event) throws IOException {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            
            if (Integer.parseInt(prodMaxTxt.getText()) < Integer.parseInt(prodMinTxt.getText())) {
                alert.setContentText("Max value cannot be less than Min value!");
                alert.showAndWait();
                return;
            }
            if (Integer.parseInt(prodInvTxt.getText()) > Integer.parseInt(prodMaxTxt.getText())) {
                alert.setContentText("Inventory value cannot be greater than Max value!");
                alert.showAndWait();
                return;
            }
            if (Integer.parseInt(prodInvTxt.getText()) < Integer.parseInt(prodMinTxt.getText())) {
                alert.setContentText("Inventory value cannot be less than Min value!");
                alert.showAndWait();
                return;
            }
            else {            
                selectedProduct.setId(Integer.parseInt(prodIdTxt.getText()));
                selectedProduct.setName(prodNmTxt.getText());
                selectedProduct.setPrice(Double.parseDouble(prodPriceTxt.getText()));
                selectedProduct.setStock(Integer.parseInt(prodInvTxt.getText()));
                selectedProduct.setMin(Integer.parseInt(prodMinTxt.getText()));
                selectedProduct.setMax(Integer.parseInt(prodMaxTxt.getText()));
                
                Product modifiedProduct = new Product(selectedProduct.getId(), selectedProduct.getName(), selectedProduct.getPrice(), selectedProduct.getStock(), 
                        selectedProduct.getMin(), selectedProduct.getMax());
                Inventory.updateProduct(selectedProduct.getId(), modifiedProduct);
                
                for (int i = 0; i < assocPartsList.size(); i++) {
                    modifiedProduct.addAssociatedParts(assocPartsList.get(i));
                }
                goMainScreen(event);
            }
        }
        catch (NumberFormatException e){ System.out.println("Please enter valid values in text fields!"); }
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
        
        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNmCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        Product selectedProduct = MainScreenController.getSelectedProduct();
        
       
        assocPartTable.setItems(selectedProduct.getAllAssociatedParts());
        assocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNmCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        prodIdTxt.setText(Integer.toString(selectedProduct.getId()));
        prodNmTxt.setText(selectedProduct.getName());
        prodInvTxt.setText(Integer.toString(selectedProduct.getStock()));
        prodPriceTxt.setText(Double.toString(selectedProduct.getPrice()));
        prodMaxTxt.setText(Integer.toString(selectedProduct.getMax()));
        prodMinTxt.setText(Integer.toString(selectedProduct.getMin()));
        
    }       
}
