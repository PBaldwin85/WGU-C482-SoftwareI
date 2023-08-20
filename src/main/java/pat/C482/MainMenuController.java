package pat.C482;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;



/**
 * Main Controller class used for the main menu GUI.
 */
public class MainMenuController implements Initializable {

    /** Stores the Part ID value. */
    public TableColumn<Parts, Integer> partID;
    /** Stores the Part Name value. */
    public TableColumn<Parts, String> partName;
    /** Stores the Part Inventory value. */
    public TableColumn<Parts, Integer> inventoryLevel;
    /** Stores the Part Cost value. */
    public TableColumn<Parts, Double> partCost;
    /** Part search text field value which is used to search for a part by name or number. */
    public TextField partSearchField;
    /** Stores the Product ID value. */
    public TableColumn<Products, Integer> productID;
    /** Stores the Product Name value. */
    public TableColumn<Products, String> productName;
    /** Stores the Product Inventory value. */
    public TableColumn<Products, Integer> productLevel;
    /** Stores the Product Cost valu. */
    public TableColumn<Products, Double> productCost;
    /** Stores the view for the parts table. */
    public TableView<Parts> partsTableView;
    /** The main window where the tables are stored onto. */
    public AnchorPane mainWindow;
    /** Stores the view for the products table. */
    public TableView<Products> productsTableView;
    /** Product search text field value which is used to search for a part by name or number. */
    public TextField productSearchField;
    Stage stage;
    Parent scene;
    boolean modify = false;

    /** Main Menu Controller constructor.
     */
    public MainMenuController(){
    }

    /** Adds a part.
     This is for the modify button which closes the Main scene (main FXML) and opens the Add Part Controller FXML.
     @param actionEvent Event triggered by the button click
     */
    public void AddPartClick(ActionEvent actionEvent) throws IOException {
        AddPartController.PartId(MainApplication.generatePartId());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addPart.fxml"));
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        stage.close();
        Parent addPartParent = loader.load();
        AddPartController AddPartController = loader.getController();
        AddPartController.setDataInHouse(MainApplication.getPartId());
        Scene scene = new Scene(addPartParent);
        stage.setScene(scene);
        stage.show();
    }

    /** Modifies the selected part.
     This is for the modify button which closes the Main scene (main FXML) and opens the Add Part Controller FXML.
     */
    public void ModifyPartClick() {
        Parts selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addPart.fxml"));
            Stage stage = (Stage) mainWindow.getScene().getWindow();
            stage.close();
            try {
                Parent addPartParent = loader.load();
                AddPartController AddPartController = loader.getController();
                if (selectedPart.getMachineId() != null) {
                    AddPartController.setDataInHouse(
                            selectedPart.getId(),
                            selectedPart.getName(),
                            selectedPart.getInventory(),
                            selectedPart.getPartCost(),
                            selectedPart.getMax(),
                            selectedPart.getMin(),
                            selectedPart.getMachineId()
                    );
                }
                else if (selectedPart.getCompanyName() != null){
                    AddPartController.setDataOutsourced(
                            selectedPart.getId(),
                            selectedPart.getName(),
                            selectedPart.getInventory(),
                            selectedPart.getPartCost(),
                            selectedPart.getMax(),
                            selectedPart.getMin(),
                            selectedPart.getCompanyName()
                    );
                }
                pat.C482.AddPartController.PartId(selectedPart.getId());
                Inventory.deletePart(selectedPart);
                AddPartController.deletedRow = true;
                Scene scene = new Scene(addPartParent);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** Adds a product.
     This is for the add product button which closes the Main scene (main FXML) and then opens the add product FXML
     @param actionEvent Event to change scenes triggered by the button click
     */
    public void AddProductClick(ActionEvent actionEvent) throws IOException {
        AddProductController.ProductId(MainApplication.generateProductId());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addProduct.fxml"));
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        stage.close();
        Parent addPartParent = loader.load();
        AddProductController AddProductController = loader.getController();
        AddProductController.setDefaultData(MainApplication.getProductId());
        Scene scene = new Scene(addPartParent);
        stage.setScene(scene);
        stage.show();
    }

    /** Modifies the selected product.
     This is the modify product button which closes the Main scene (Main FXML) and opens the add product FXML
     */
    public void ModifyProductClick() {
        Products selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addProduct.fxml"));
            Stage stage = (Stage) mainWindow.getScene().getWindow();
            stage.close();
            try {;
                Parent addPartParent = loader.load();
                AddProductController AddProductController = loader.getController();
                AddProductController.setData(
                        selectedProduct.getProductID(),
                        selectedProduct.getName(),
                        selectedProduct.getInventory(),
                        selectedProduct.getProductCost(),
                        selectedProduct.getMax(),
                        selectedProduct.getMin()
                );
                selectedProduct.getAssociatedPartsList().addAll();
                Inventory.productPartList.addAll(selectedProduct.getAssociatedPartsList());
                modify = true;
                AddProductController.savedAssociatedPartsList = FXCollections.observableArrayList(Inventory.productPartList);
                pat.C482.AddPartController.PartId(selectedProduct.getProductID());
                Inventory.deleteProduct(selectedProduct);
                AddProductController.deletedRow = true;
                Scene scene = new Scene(addPartParent);
                stage.setScene(scene);
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** Exits the program.
     The exit button closes the program
     */
    public void ExitClick() {
        Platform.exit();
    }

    /** RUNTIME ERROR.
     * Populates the Main Menu tables.
     * The runtime error I experienced had to do with the values inside the quotes causing the cells to not populate.
     * With "ProductID" set as "ID" it gets the following error: WARNING: Can not retrieve property 'ID' in
     * PropertyValueFactory: javafx.scene.control.cell.PropertyValueFactory@5eb3e04d with provided class type: class pat.C482.Products
     * java.lang.IllegalStateException: Cannot read from unreadable property ID.
     * The error was solved by matching up the getters more closely to the quotation text.
     * getProductID with "ProductID" works, while getProductID with "ID" doesn't work.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Inventory.deleteAllProductParts();
        partsTableView.setItems(Inventory.getParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        inventoryLevel.setCellValueFactory(new PropertyValueFactory<>("Inventory"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("PartCost"));

        productsTableView.setItems(Inventory.getProduct());
        productID.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        productName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productLevel.setCellValueFactory(new PropertyValueFactory<>("Inventory"));
        productCost.setCellValueFactory(new PropertyValueFactory<>("ProductCost"));
    }

    /** Search part text field.
     Takes what the user enters into the text field and calls the searchByPart function.
     Then the parts are filtered.
     */
    public void SearchPart() throws IOException {
        String partToSearch = partSearchField.getText();
        ObservableList<Parts> parts = searchByPart(partToSearch);
        partsTableView.setItems(parts);
    }

    /** Search product text field.
     Takes what the user enters into the text field and calls the searchByProduct function.
     Then the products are filtered.
     */
    public void SearchProduct() throws IOException {
        String productToSearch = productSearchField.getText();
        ObservableList<Products> products = searchByProduct(productToSearch);
        productsTableView.setItems(products);
    }

    /** Part search observable list.
     Compares the part search string that the user enters to find any matches against the parts table.
     */
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

    /** Product search observable list.
     Compares the product search string that the user enters to find any matches against the product table.
     */
    private ObservableList<Products> searchByProduct(String partialName) throws IOException {
        boolean match = false;
        ObservableList<Products> products = FXCollections.observableArrayList();
        ObservableList<Products> ProductList = Inventory.getProduct();
        for (Products bp : ProductList) {
            if (bp.getName().contains(partialName) || String.valueOf(bp.getProductID()).contains(partialName)) {
                products.add(bp);
                match = true;
            }
        }
        if (!match) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Product not found!");
            alert.showAndWait();
            productSearchField.setText("");
            products = Inventory.getProduct();
        }
        return products;
    }

    /** Delete part button.
     When the button is pressed, it calls the deleteSelectedRow function to determine if a row should be deleted or not.
     */
    public void PartDelete() {
        deletePartRow();
    }

    /** Deletes the selected parts row.
     If the delete button is pressed, then it determines if a row can be deleted or not.
     If a row isn't selected, nothing will be deleted.
     */
    public void deletePartRow() {
        Parts selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Would you like to delete the selected part?");
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            alert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == yesButton) {
                Inventory.deletePart(selectedPart);
                Inventory.deleteAllParts();
                }
            }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part to delete!");
            alert.showAndWait();
        }
    }

    /** Deletes the selected parts row.
     If the delete button is pressed, then it determines if a row can be deleted or not.
     If a row isn't selected, nothing will be deleted.
     */
    public void deleteProductRow() {
        Products selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            if (!modify) {
                Inventory.productPartList.addAll(selectedProduct.getAssociatedPartsList());
                if (!Inventory.productPartList.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setContentText("The selected product has parts associated with it and can't be deleted.");
                    ButtonType okButton = new ButtonType("Ok");
                    alert.getButtonTypes().setAll(okButton);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == okButton) {
                        Inventory.deleteAllProductParts();
                        return;
                    }
                }
            }
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Would you like to delete the selected product?");
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            alert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == yesButton) {
                Inventory.deleteProduct(selectedProduct);
                Inventory.deleteAllProducts();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a product to delete!");
            alert.showAndWait();
        }
    }

    /** Delete product button.
     When the button is pressed, it calls the deleteProductRow function to determine if a row should be deleted or not.
     */
    public void ProductDelete() {
        deleteProductRow();
    }


}
