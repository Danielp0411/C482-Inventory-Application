/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Daniel
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    
    
    public static Part lookupPart(int partId) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        
        for(int i = 0; i < allParts.size(); i++) {
            Part p = allParts.get(i);
            
            if (p.getId() == partId) {
                return p;
            }
        }
        return null;
    }
    
    public static Product lookupProduct(int productId) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        
        for(int i = 0; i < allProducts.size(); i++) {
            Product p = allProducts.get(i);
            
            if (p.getId() == productId) {
                return p;
            }
        } 
        return null;
    }
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> partNames = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part p : allParts) {
            if(p.getName().toLowerCase().contains(partName)) {
                partNames.add(p);
            }
        }

        return partNames;
    }
   
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> productNames = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (Product p : allProducts) {
            if(p.getName().toLowerCase().contains(productName)) {
                productNames.add(p);
            }
        }

        return productNames;
    }
   
    public static void updatePart(int id, Part selectedPart) {
        int index = -1;
        for (Part part : getAllParts()) {
            index++;
            if (part.getId() == id) {
                getAllParts().set(index, selectedPart);
            }
        }
    }

    public static void updateProduct(int id, Product newProduct) {
        int index = -1;
        for (Product product : getAllProducts()) {
            index++;
            if (product.getId() == id) {
                getAllProducts().set(index, newProduct);
            }
        }
    }

    public static boolean deletePart(Part selectedPart){
        allParts.remove(selectedPart);
        return true;
    }
    public static boolean deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
        return true;
    }
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    
    //PartID Counter
    public static int partIdCounter() {
        int id = 1;
        for (Part a : getAllParts()) {
            if (a.getId() >= id) {
                id = a.getId() + 1;
            }
        }
        return id;
    }
    
    public static int productIdCounter() {
        int id = 100;
        for (Product p : getAllProducts()) {
            if (p.getId() >= id) {
                id = p.getId() + 1;
            }
        }
        return id;
    }
}
