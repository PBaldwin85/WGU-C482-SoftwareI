package pat.C482;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/** Add part class. This is used for adding parts and controlling the interaction with the GUI.*/
public class AddPartController {
    /** Id text box.*/
    public TextField idField;
    /** Name text box.*/
    public TextField nameField;
    /** Inventory text box.*/
    public TextField inventoryField;
    /** Price text box.*/
    public TextField priceField;
    /** Max text box.*/
    public TextField maxField;
    /** Min text box.*/
    public TextField minField;
    /** Used for both the Machine ID and Company name text box. */
    public TextField machineIdField;
    /** Used to set the text to either Machine ID or Company name in the parts GUI. */
    public Label machineID;
    /** In house radio button.*/
    public RadioButton inHouse;
    /** Outsourced radio button.*/
    public RadioButton outsourced;
    /** Used to swap the button between in house and outsourced part type.*/
    public ToggleGroup toggleGroup;
    /** Used to control the save button.*/
    public Button save;
    /** Used to determine if a part row was deleted from the main menu.
     * ModifyPart deletes the row so there isn't a duplicate and then modification is cancelled it will restore what was deleted. */
    public boolean deletedRow = false;
    /** Used to save and restore the ID value if the user cancels all changes.*/
    private Integer savedId;
    /** Used to save and restore the name value if the user cancels all changes.*/
    private String savedName;
    /** Used to save and restore the inventory value if the user cancels all changes.*/
    private Integer savedInventory;
    /** Used to save and restore the cost value if the user cancels all changes.*/
    private double savedCost;
    /** Used to save and restore the max value if the user cancels all changes.*/
    private Integer savedMax;
    /** Used to save and restore the min value if the user cancels all changes.*/
    private Integer savedMin;
    /** Used to save and restore the machine ID value if the user cancels all changes.*/
    private Integer savedMachine;
    /** Used to save and restore the company name value if the user cancels all changes.*/
    private String savedCompanyName;
    /** Sets the stage.*/
    Stage stage;
    /** Sets the scene.*/
    Parent scene;

    /** Holds the part ID value. */
    public static int part;
    /** Sets the part ID value. */
    public static void PartId(int num) {
        part = num;
    }
    /** Returns the part ID. */
    public static int getPartId() {
        return part;
    }

    /** Default add part controller constructor */
    public AddPartController() {}


    /**
     * Sets text to Company Name.
     * Changes the text to Company Name when the Outsourced RadioButton is clicked.
     */
    public void outsourced() {

        machineID.setText("Company Name");
        machineIdField.setText("");
    }

    /**
     * Sets text to Machine ID.
     * Changes the text to Company Name when the In-House RadioButton is clicked.
     *
     */
    public void inHouse() {

        machineID.setText("Machine ID");
        machineIdField.setText("");
    }

 

    /**
     * Cancels and goes back to the main menu.
     * When the Cancel button is clicked, goes back to the main menu if Ok is pressed. If cancel is pressed it will allow for more changes.
     *
     * @param actionEvent Goes back to the main menu
     */
    public void CancelClick(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel all changes. Would you like to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {

            if (deletedRow) {
                if (savedMachine != null) {
                    InHouse part = new InHouse(savedId, savedName, savedInventory, Double.parseDouble(String.valueOf(savedCost)), savedMax, savedMin, savedMachine);
                    Inventory.addInHousePart(part);
                }

                if (savedCompanyName != null) {
                    Outsourced part = new Outsourced(savedId, savedName, savedInventory, Double.parseDouble(String.valueOf(savedCost)), savedMax, savedMin, savedCompanyName);
                    Inventory.addOutsourcedPart(part);
                }
            }

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/pat/C482/main.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }
    /** Saves all changes and goes back to the main menu if all data is set correctly.
     * If any text fields aren't set or aren't the correct format, then a notification will be displayed.
     * */
    public void SaveClick(ActionEvent event) {
        try {
            if (inHouse.isSelected()) {
                int id = getPartId();
                String name = nameField.getText();
                int inv = Integer.parseInt(inventoryField.getText());
                double price = Double.parseDouble(priceField.getText());
                int max = Integer.parseInt(maxField.getText());
                int min = Integer.parseInt(minField.getText());
                Integer machineId = Integer.valueOf(machineIdField.getText());

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

                InHouse part = new InHouse(id, name, inv, price, max, min, machineId);
                Inventory.addInHousePart(part);
            }
            if (outsourced.isSelected()) {
                int id = getPartId();
                String name = nameField.getText();
                int inv = Integer.parseInt(inventoryField.getText());
                double price = Double.parseDouble(priceField.getText());
                int max = Integer.parseInt(maxField.getText());
                int min = Integer.parseInt(minField.getText());
                String companyName = machineIdField.getText();

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
                if (machineIdField.getText().isEmpty()) {
                    throw new NumberFormatException();
                }

                Outsourced part = new Outsourced(id, name, inv, price, max, min, companyName);
                Inventory.addOutsourcedPart(part);
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/pat/C482/main.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }

        catch (NumberFormatException | IOException e) {
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

            boolean machineEmpty = false;
            if (machineIdField.getText().isEmpty()) {
                machineEmpty = true;
                if (inHouse.isSelected()) {
                    invalidFields += "Machine ID is empty\n";
                }
                if (outsourced.isSelected()) {
                    invalidFields += "Company Name is empty\n";
                }
            }

            if (inHouse.isSelected() && !machineEmpty) {
                try {
                    int machineId = Integer.parseInt(machineIdField.getText());
                }
                catch (NumberFormatException n) {
                    invalidFields += "Machine ID is not a number\n";
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



    /** Sets the text fields for the selected in house part when a part is modified. */
    public void setDataInHouse(Integer id, String name, Integer inventory, String cost, Integer max, Integer min, Integer machineId) {
        idField.setText(String.valueOf(id));
        nameField.setText(name);
        inventoryField.setText(String.valueOf(inventory));
        priceField.setText(String.valueOf(cost));
        maxField.setText(String.valueOf(max));
        minField.setText(String.valueOf(min));
        machineIdField.setText(String.valueOf(machineId));


        savedId = id;
        savedName = name;
        savedInventory = inventory;
        savedCost = Double.parseDouble(cost);
        savedMax = max;
        savedMin = min;
        savedMachine = machineId;

        inHouse.setSelected(true);
        machineID.setText("Machine ID");


    }

    /** Sets the text fields for the selected outsourced part when a part is modified.*/
    public void setDataOutsourced(Integer id, String name, Integer inventory, String cost, Integer max, Integer min, String companyName) {

        idField.setText(String.valueOf(id));
        nameField.setText(name);
        inventoryField.setText(String.valueOf(inventory));
        priceField.setText(String.valueOf(cost));
        maxField.setText(String.valueOf(max));
        minField.setText(String.valueOf(min));
        machineIdField.setText(String.valueOf(companyName));


        savedId = id;
        savedName = name;
        savedInventory = inventory;
        savedCost = Double.parseDouble(cost);
        savedMax = max;
        savedMin = min;
        savedCompanyName = companyName;


        outsourced.setSelected(true);
        machineID.setText("Company Name");

    }

    /** Sets the default fields.
     * Used when adding a part so that the part Id shows.
     */
    public void setDataInHouse(int Id) {
        idField.setText(String.valueOf(Id));
    }
}

