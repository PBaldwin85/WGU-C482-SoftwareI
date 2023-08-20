package pat.C482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


/** Add part class.
 * Used for adding products and the controlling interaction with the GUI.
 */
public class AddProductController implements Initializable {
    /** Top table part Id column. */
    public TableColumn<Parts, Integer> partID;
    /** Top table part name column. */
    public TableColumn<Parts, String> partName;
    /** Top table inventory column. */
    public TableColumn<Parts, Integer> inventoryLevel;
    /** Top table cost column. */
    public TableColumn<Parts, Double> partCost;
    /** Top table view. */
    public TableView<Parts> partsTableView;
    /** Top table search box field. */
    public TextField partSearchField;
    /** Bottom table part id column. */
    public TableColumn<Parts, Integer> partID1;
    /** Bottom table part name column. */
    public TableColumn<Parts, String> partName1;
    /** Bottom table inventory column. */
    public TableColumn<Parts, Integer> inventoryLevel1;
    /** Bottom table cost column. */
    public TableColumn<Parts, Double> partCost1;
    /** Product name text field. */
    public TextField nameField;
    /** Product inventory text field. */
    public TextField inventoryField;
    /** Product price text field. */
    public TextField priceField;
    /** Product max text field. */
    public TextField maxField;
    /** Product min text field. */
    public TextField minField;
    /** Bottom table view used to store parts specific to the product. */
    public TableView<Parts> bottomTableView;
    /** Product ID field. Disabled from editing and automatically assigned. */
    public TextField idField;
    /** Sets the stage. */
    Stage stage;
    /** Sets the scene. */
    Parent scene;

    /** Saves the product ID in case changes are cancelled. */
    private Integer savedId;
    /** Saves the product name in case changes are cancelled. */
    private String savedName;
    /** Saves the product inventory in case changes are cancelled. */
    private Integer savedInventory;
    /** Saves the product cost in case changes are cancelled. */
    private double savedCost;
    /** Saves the product max in case changes are cancelled. */
    private Integer savedMax;
    /** Saves the product min in case changes are cancelled. */
    private Integer savedMin;
    /** Used to determine if a product row was deleted from the main menu.
     * ModifyProduct deletes the row so there isn't a duplicate and then if changes are cancelled it will restore what was deleted. */
    public boolean deletedRow = false;

    /** Holds the product ID value. */
    public static int product;
    /** Sets the product ID. */
    public static void ProductId(int num) {
        product = num;
    }
    /** Returns the product ID. */
    public static int getProductId() {
        return product;
    }
    /** Holds the parts specific to each product. */

    /** Default add product controller constructor */
    public AddProductController () {}

    /** Saveds the associated part list into this temporary list.
     * If the modification is cancelled, the list restores the old parts list.
     */
    public ObservableList<Parts> savedAssociatedPartsList = FXCollections.observableArrayList();

    /** Adds a part, if selected, from the top table to the bottom table. */
    public void Add() {
        Parts selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            boolean existsInTable = false;
            for (Parts part : bottomTableView.getItems()) {
                if (part.equals(selectedPart)) {
                    existsInTable = true;
                    break;
                }
            }
            if (!existsInTable) {
                Inventory.addProductPart(partsTableView.getSelectionModel().getSelectedItem());
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part to add!");
            alert.showAndWait();
        }
    }

    /** Saves all changes if all requirements are met.
     * Throws and handles exceptions to alert user of any requirements that aren't met. */
    public void SaveClick(ActionEvent event) {
        try {
            int id = getProductId();
            String name = nameField.getText();
            int inv = Integer.parseInt(inventoryField.getText());
            double price = Double.parseDouble(priceField.getText());
            int max = Integer.parseInt(maxField.getText());
            int min = Integer.parseInt(minField.getText());
            if (nameField.getText().isEmpty()) {
                throw new NumberFormatException();
            }
            if (max < min) {
                throw new NumberFormatException();
            }
            if (inv < min) {
                throw new NumberFormatException();
            }
            if (inv > max) {
                throw new NumberFormatException();
            }
            Products product = new Products(id, name, inv, price, max, min);
            for (Parts part : Inventory.productPartList) {
                product.addAssociatedPart(part);
            }
            Inventory.addProduct(product);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/pat/C482/main.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();

        }
        catch (NumberFormatException | IOException e)  {
            String invalidFields = "The following fields have errors:\n";
            if (nameField.getText().isEmpty()) {
                invalidFields += "Name is empty\n";
            }
            boolean invEmpty = false;
            if (inventoryField.getText().isEmpty()) {
                invalidFields += "Inventory is empty\n";
                invEmpty = true;
            }
            if (!invEmpty) {
                try {
                    int inv = Integer.parseInt(inventoryField.getText());
                }
                catch (NumberFormatException n) {
                    invalidFields += "Inventory is not a number\n";
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(invalidFields);
                    alert.showAndWait();
                    return;
                }
            }
            boolean priceEmpty = false;
            if (priceField.getText().isEmpty()) {
                invalidFields += "Price/Cost is empty\n";
                priceEmpty = true;
            }
            if (!priceEmpty) {
                try {
                    Double price = Double.parseDouble(priceField.getText());
                }
                catch (NumberFormatException n) {
                    invalidFields += "Price is not a number\n";
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(invalidFields);
                    alert.showAndWait();
                    return;
                }
            }
            boolean maxEmpty = false;
            if (maxField.getText().isEmpty()) {
                invalidFields += "Max is empty\n";
                maxEmpty = true;
            }
            if (!maxEmpty) {
                try {
                    int max = Integer.parseInt(maxField.getText());
                }
                catch (NumberFormatException n) {
                    invalidFields += "Max is not a number\n";
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(invalidFields);
                    alert.showAndWait();
                    return;
                }
            }
            boolean minEmpty = false;
            if (minField.getText().isEmpty()) {
                invalidFields += "Min is empty\n";
                minEmpty = true;
            }
            if (!minEmpty) {
                try {
                    int min = Integer.parseInt(minField.getText());
                }
                catch (NumberFormatException n) {
                    invalidFields += "Min is not a number\n";
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(invalidFields);
                    alert.showAndWait();
                    return;
                }
            }
            if (!maxEmpty && !minEmpty && !invEmpty) {
                int inv = Integer.parseInt(inventoryField.getText());
                int max = Integer.parseInt(maxField.getText());
                int min = Integer.parseInt(minField.getText());
                if (max < min) {
                    invalidFields += "Max is less than min\n";
                }
                if (inv < min) {
                    invalidFields += "Inventory is less than min\n";
                }
                if (inv > max) {
                    invalidFields += "Inventory is greater than max\n";
                }
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(invalidFields);
            alert.showAndWait();
        }

    }

    /** Cancel button which is used to cancel any changes done.
     * If Ok is pressed, all changes are cancelled and then takes you back to the main menu.
     * If cancel is pressed, it allows you to make more changes.
     */
    public void CancelClick(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel all changes. Would you like to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            if (deletedRow) {
                Products product = new Products(savedId,savedName,savedInventory, Double.parseDouble(String.valueOf(savedCost)),savedMax,savedMin);

                for (Parts part : savedAssociatedPartsList) {
                    product.addAssociatedPart(part);
                }
                Inventory.addProduct(product);
            }
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/pat/C482/main.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Initializes the table views prior to loading the screen.
     * partsTableView is for the top table and buttonTableView is for the bottom table. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(Inventory.getParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevel.setCellValueFactory(new PropertyValueFactory<>("Inventory"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("PartCost"));

        bottomTableView.setItems(Inventory.productPartList);
        partID1.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevel1.setCellValueFactory(new PropertyValueFactory<>("Inventory"));
        partCost1.setCellValueFactory(new PropertyValueFactory<>("PartCost"));
    }

    /** Search part text field.
     * Sends the entered text to the searchByPart function, the searchByPart function then find any matches and returns
     * any matches. The table is then filtered. */
    public void SearchPart() throws IOException {
        String enteredText = partSearchField.getText();
        ObservableList<Parts> parts = searchByPart(enteredText);
        partsTableView.setItems(parts);
    }

    /** Used to compare what's typed in the search field to what's in the top table.
     * If anything is found, it returns it to the SearchPart function. */
    private ObservableList<Parts> searchByPart(String partialName) throws IOException {
        boolean match = false;
        ObservableList<Parts> parts = FXCollections.observableArrayList();
        ObservableList<Parts> list = Inventory.getParts();
        for (Parts bp : list) {
            if (bp.getName().contains(partialName) || String.valueOf(bp.getId()).contains(partialName)) {
                parts.add(bp);
                match = true;
            }
        }
        if (!match) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Part not found!");
            alert.showAndWait();
            partSearchField.setText("");
            parts = Inventory.getParts();
        }
        return parts;
    }

    /** Function that deletes the selected row from the bottom table.
     * If nothing is selected then nothing happens. */
    public void deleteSelectedRow() {
        Parts selectedPart = bottomTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Would you like to remove the selected part?");
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            alert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == yesButton) {
                Inventory.deleteProductPart(selectedPart);
            }
        }
    }

    /** Remove Associated Part button to remove parts associated with a product. */
    public void RemovePart() {
        deleteSelectedRow();
    }

    /** Sets the text fields in the products menu when a product is modified.
     * Saves all data for when the user cancels changes.
     */
    public void setData(Integer id, String name, Integer inventory, String productCost, Integer max, Integer min) {
        idField.setText(String.valueOf(id));
        nameField.setText(name);
        inventoryField.setText(String.valueOf(inventory));
        priceField.setText(String.valueOf(productCost));
        maxField.setText(String.valueOf(max));
        minField.setText(String.valueOf(min));

        ProductId(id);

        savedId = id;
        savedName = name;
        savedInventory = inventory;
        savedCost = Double.parseDouble(productCost);
        savedMax = max;
        savedMin = min;
    }

    /** Sets the default fields.
     * Used when adding a product so that the product Id shows.
     */
    public void setDefaultData(int productId) {
        idField.setText(String.valueOf(productId));
    }



}
