/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
public class MainScreenController implements Initializable {

    @FXML
    private Button mainExitButton;
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
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNmCol;
    @FXML
    private TableColumn<Product, Integer> productInvCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    @FXML
    private Button prodDelButton;
    @FXML
    private Button prodModButton;
    @FXML
    private Button prodAddButton;
    @FXML
    private Button partDelButton;
    @FXML
    private Button partModButton;
    @FXML
    private Button partAddButton;
    @FXML
    private Button productSearchButton;
    @FXML
    private Button partSearchButton;
    @FXML
    private TextField partSearchBox;
    @FXML
    private TextField productSearchBox; 
    
    private static Part selectedPart = null;
    private static Product selectedProduct = null;
    
    public static Part getSelectedPart() {
        return selectedPart;
    }
    public static Product getSelectedProduct() {
        return selectedProduct;
    }
    
    @FXML
    private void searchParts(ActionEvent event) {
        String s = partSearchBox.getText().toLowerCase();
        ObservableList<Part> partsList = Inventory.lookupPart(s);
  try {      
        if (partsList.isEmpty()) {     
            int i = Integer.parseInt(s);
            Part p = Inventory.lookupPart(i);
            partsList.add(p);
        }
        partTable.setItems(partsList);
    }
  catch (NumberFormatException e){ System.out.println("Please enter a valid value in search field!"); }
    }
    
    @FXML
    private void addPartScreen(ActionEvent event) throws IOException {
       
       Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/AddPart.fxml"));
       Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
       stage.setTitle("Add Part");
       stage.setScene(new Scene(root));
       stage.show();
    }
    
    @FXML
    private void modifyPartScreen(ActionEvent event) throws IOException {
        
       selectedPart = partTable.getSelectionModel().getSelectedItem();
  
       if(selectedPart == null) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText("Nothing Selected");
           alert.setContentText("Nothing was selected to modify");
           alert.showAndWait();
       }
       else {
       Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/ModifyPart.fxml"));
       Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
       stage.setTitle("Modify Part");
       stage.setScene(new Scene(root));
       stage.show();
       }
    }
    
    @FXML
    private void deletePart(ActionEvent event) {
        if (partTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing Selected");
            alert.setContentText("Nothing was selected to delete");
            alert.showAndWait();
        } else if (Inventory.getAllParts().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Part");
            alert.setHeaderText("Deleting Part");
            alert.setContentText("Are you sure you want to delete " + partTable.getSelectionModel().getSelectedItem().getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                
                Inventory.deletePart(partTable.getSelectionModel().getSelectedItem());
            }
        }
    }
    
    @FXML
    private void searchProducts(ActionEvent event) {
        String s = productSearchBox.getText().toLowerCase();
        ObservableList<Product> ProductsList = Inventory.lookupProduct(s);
  try {      
        if (ProductsList.isEmpty()) {     
            int i = Integer.parseInt(s);
            Product p = Inventory.lookupProduct(i);
            ProductsList.add(p);
        }
        productTable.setItems(ProductsList);
    }
  catch (NumberFormatException e){ System.out.println("Please enter a valid value in search field!"); }
    }
    
    @FXML
    private void addProductScreen(ActionEvent event) throws IOException {
        
       Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/AddProduct.fxml"));
       Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
       stage.setTitle("Add Product");
       stage.setScene(new Scene(root));
       stage.show();
    }
    
    @FXML
    private void modifyProductScreen(ActionEvent event) throws IOException {
       selectedProduct = productTable.getSelectionModel().getSelectedItem();
       if(selectedProduct == null) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText("Nothing Selected");
           alert.setContentText("Nothing was selected to modify");
           alert.showAndWait();
       }
       else {
       Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/ModifyProduct.fxml"));
       Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
       stage.setTitle("Modify Product");
       stage.setScene(new Scene(root));
       stage.show();
       }
    }
     
    @FXML
    private void deleteProduct(ActionEvent event) {
        if (productTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing Selected");
            alert.setContentText("Nothing was selected to delete");
            alert.showAndWait();
        } else if (Inventory.getAllParts().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Product");
            alert.setHeaderText("Deleting Product");
            alert.setContentText("Are you sure you want to delete " + productTable.getSelectionModel().getSelectedItem().getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                
                Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
            }
        }
    }
   
    
    
    
    
    @FXML
    private void onActionExit(ActionEvent event) {
        System.exit(0);
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
        
        productTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNmCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    

    
}
