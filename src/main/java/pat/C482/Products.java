package pat.C482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;


/** Parts class for storing and accessing parts.
 *
 */
public class Products extends Parts {

    /** Product ID variable. */
    private final Integer productID;
    /** Product name variable. */
    private final String productName;
    /** Inventory level variable. */
    private final Integer inventoryLevel;
    /** Product cost variable. */
    private final double productCost;
    /** Product max variable. */
    private final Integer max;
    /** Product min variable. */
    private final Integer min;
    /** Array list to store the part list associated with the product. */
    public ObservableList<Parts> associatedParts;

    /** Stores the products associated parts */
    public ObservableList<Parts> getAssociatedPartsList() {
        return associatedParts;
    }

    /** Adds an associated part to the associated parts list. */
    public void addAssociatedPart(Parts part) {
        associatedParts.add(part);
    }
    /** Removes an associated part from the associated parts list. */
    public void deleteAssociatedPart(Parts part) {
        associatedParts.remove(part);
    }

    /** Saves the product list.
    */
    public Products(Integer productID, String partName, Integer inventoryLevel, double partCost, Integer max, Integer min) {
        super();
        this.productID = productID;
        this.productName = partName;
        this.inventoryLevel = inventoryLevel;
        this.productCost = partCost;
        this.max = max;
        this.min = min;
        this.associatedParts = FXCollections.observableArrayList();
    }

    /** ID setter. */
    private void setID() {
    }
    /** Name setter. */
    private void setName() {
    }
    /** Price setter. */
    private void setPrice() {
    }
    /** Stock setter. */
    private void setStock() {
    }
    /** Min setter. */
    private void setMin() {
    }
    /** Max setter. */
    private void setMax() {
    }

    /** Returns the product ID. */
    public Integer getProductID() {
        return productID;
    }
    /** Returns the product name. */
    public String getName() throws IOException {
        return productName;
    }
    /** Returns the product inventory level. */
    public Integer getInventory() {
        return inventoryLevel;
    }
    /** Returns the product cost. */
    public String getProductCost() {
        return String.format("%.2f", productCost);
    }
    /** Returns the product max value. */
    public Integer getMax() {
        return max;
    }
    /** Returns the product minimum value. */
    public Integer getMin() {
        return min;
    }



}
