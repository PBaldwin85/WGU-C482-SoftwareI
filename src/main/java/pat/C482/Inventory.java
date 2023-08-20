package pat.C482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Holds and provides access to the data.
 *
 */
public class Inventory {

    /** Default inventory constructor. */
    public Inventory() {
    }

    /** Parts observable list. */
    private static final ObservableList<Parts> partList = FXCollections.observableArrayList();

    /** Stores an in house part into the part list. */
    public static void addInHousePart(InHouse partID) {
        partList.add(partID);
    }
    /** Stores an outsourced part into the part list. */
    public static void addOutsourcedPart(Outsourced partID) {
        partList.add(partID);
    }

    /** Returns the part list. */
    public static ObservableList<Parts> getParts() {
        return partList;
    }
    /** Deletes the part from the parts list */
    public static void deletePart(Parts selectedPart) {
        partList.remove(selectedPart);
    }
    /** Deletes all parts from the observable list */
    public static void deleteAllParts() {
        partList.removeAll();
    }




    /** Product observable list */
    public static final ObservableList<Products> productList = FXCollections.observableArrayList();

    /** Stores a product into a list. */
    public static void addProduct(Products productID) {
        productList.add(productID);
    }

    /** Returns the product list. */
    public static ObservableList<Products> getProduct() {
        return productList;
    }


    /** Deletes a product from the observable list */
    public static void deleteProduct(Products selectedProduct) {
        productList.remove(selectedProduct);
    }

    /** Deletes all products from the observable list */
    public static void deleteAllProducts() {
        productList.removeAll();
    }





    /** Product parts observable list used for the associated parts list. */
    public static ObservableList<Parts> productPartList = FXCollections.observableArrayList();
    /** Returns the product parts list. */
    public static ObservableList<Parts> getProductParts() {
        return productPartList;
    }
    /** Deletes a part from the associated parts list. */
    public static void deleteProductPart(Parts selectedPart) {
        productPartList.remove(selectedPart);
    }
    /** Deletes all associated parts. */
    public static void deleteAllProductParts() {
        productPartList.clear();
    }
    /** Stores a part in the associated parts list. */
    public static void addProductPart(Parts selectedItem) {
        productPartList.add(selectedItem);
    }









}

