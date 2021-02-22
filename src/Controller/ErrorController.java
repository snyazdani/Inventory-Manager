package Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.util.Optional;

/**
 * @author Saba Yazdani, syazda4@wgu.edu
 * @version 1.0.0
 * @since 12/2/2020
 */

public class ErrorController {

    /**
     * in the future this code could be expanded upon
     * to increase functionality of the software by
     * adding more exceptions that could occur during runtime.
     * @param code
     * @param field
     * @return alerts for part error
     */
    public static void errorPart(int code, TextField field) {
        fieldError(field);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("There was an error adding the part");
        alert.setHeaderText("Unable to add part");
        switch (code) {
            case 1: {
                alert.setContentText("Field must not be empty");
                break;
            }
            case 2: {
                alert.setContentText("Please select In House or Out Sourced");
                break;
            }
            case 3: {
                alert.setContentText("Format not valid");
                break;
            }
            case 4: {
                alert.setContentText("Please use a valid name");
                break;
            }
            case 5: {
                alert.setContentText("Value must be greater than zero");
                break;
            }
            case 6: {
                alert.setContentText("Inventory value must not be lower than minimum value");
                break;
            }
            case 7: {
                alert.setContentText("Inventory value must not be greater than maximum value");
                break;
            }
            case 8: {
                alert.setContentText("Minimum value must not be higher than maximum value");
                break;
            }
            case 9: {
                alert.setContentText("Please use a number for Machine ID");
                break;
            }
            default: {
                alert.setContentText("Program error has occurred");
                break;
            }
        }
        alert.showAndWait();
    }

    /**
     * @param code
     * @param field
     * @return alerts for product errors
     */
    public static void errorProduct(int code, TextField field) {
        fieldError(field);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("There was an error adding the product");
        alert.setHeaderText("Unable to add product");
        switch (code) {
            case 1: {
                alert.setContentText("Field must not be empty");
                break;
            }
            case 2: {
                alert.setContentText("A part is already associated with this product");
                break;
            }
            case 3: {
                alert.setContentText("Format not valid");
                break;
            }
            case 4: {
                alert.setContentText("Please use valid name");
                break;
            }
            case 5: {
                alert.setContentText("Value must be greater than zero");
                break;
            }
            case 6: {
                alert.setContentText("Cost must be higher than parts");
                break;
            }
            case 7: {
                alert.setContentText("Product must have a minimum of one part");
                break;
            }
            case 8: {
                alert.setContentText("Inventory value must not be lower than minimum value");
                break;
            }
            case 9: {
                alert.setContentText("Inventory value must not be greater than maximum value");
                break;
            }
            case 10: {
                alert.setContentText("Minimum value must not be greater than maximum value");
                break;
            }
            default: {
                alert.setContentText("Program error has occurred");
                break;
            }
        }
        alert.showAndWait();
    }

    /**
     * @param field
     * highlight missing field
     */
    private static void fieldError(TextField field) {
        if (field != null) {
            field.setStyle("-fx-border-color: red");
        }
    }

    /**
     * @param name
     * @return alert window for cancel confirmation
     */
    public static boolean confirmationWindow(String name) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete part");
        alert.setHeaderText("Are you sure you want to delete: " + name);
        alert.setContentText("Press OK to continue");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /**
     * @return cancel button
     */
    public static boolean cancel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Press OK to continue");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /**
     * @param code
     * @param name
     * @return alert window for part removal
     */
    public static void infoWindow(int code, String name) {
        if (code != 2) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmed");
            alert.setHeaderText(null);
            alert.setContentText(name + " has been removed");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred");
        }
    }
}
