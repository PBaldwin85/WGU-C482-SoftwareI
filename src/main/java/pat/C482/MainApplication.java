package pat.C482;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/** Javadoc is in the provided zip file */


/** FUTURE ENHANCEMENTS.
 * Main application class which is used to start the program.
 * A Future enhancement that could extend the functionality of the application is for a way to add parts or products in bulk.
 * There would be an import parts or products button that the user would press to browse their computer for a file, such as
 * a csv file, to instantly add many items at once. The application would read through the file and add all the appropriate
 * information to the system.
 */
public class MainApplication extends Application {
    /** Main application constructor.
     */
    public MainApplication() {
    }

    /** Loads the main menu GUI.
     * The selected main menu to load is main.fxml.
     * @param stage Sets the title and the scene.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/pat/C482/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 875, 325);
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    /** Stores the part ID which starts at 0. */
    private static int part = 0;

    /** Generates a part ID when called by incrementing the last part number by 1. */
    public static int generatePartId() {
        part ++;
        return part;
    }
    /** Gets the part ID */
    public static int getPartId() {
        return part;
    }

    /** Stores the product ID which starts at 999. */
    private static int product = 999;

    /** Generates a product ID when called by incrementing the last product number by 1. */
    public static int generateProductId() {
        product ++;
        return product;
    }
    /** Gets the product ID */
    public static int getProductId() {
        return product;
    }




}