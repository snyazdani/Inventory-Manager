package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @author Saba Yazdani, syazda4@wgu.edu
 * @version 1.0.0
 * @since 12/2/2020
 */

public class ModifyProductController implements Initializable {

    Inventory inv;
    Product product;

    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField price;
    @FXML
    private TextField count;
    @FXML
    private TextField min;
    @FXML
    private TextField max;
    @FXML
    private TableView<Part> assocPartsTable;
    @FXML
    private TableView<Part> partSearchTable;
    @FXML
    private TextField search;
    private ObservableList<Part> partsInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();
    private ObservableList<Part> associatedPartList = FXCollections.observableArrayList();

    /**
     * @param inv
     * @param product
     * @return controller to modify product
     */
    public ModifyProductController(Inventory inv, Product product) {
        this.inv = inv;
        this.product = product;
    }

    /**
     * @param rb
     * @param url
     * @return initialize the controller
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateSearchTable();
        setData();
    }

    /**
     * sets search table data
     */
    private void populateSearchTable() {
        partsInventory.setAll(inv.getAllParts());
        TableColumn<Part, Double> costCol = formatPrice();
        partSearchTable.getColumns().addAll(costCol);
        partSearchTable.setItems(partsInventory);
        partSearchTable.refresh();
    }

    /**
     * @param event
     * @return clears text fields
     */
    @FXML
    void clearTextField(MouseEvent event) {
        Object source = event.getSource();
        TextField field = (TextField) source;
        field.setText("");
        if (field == search) {
            partSearchTable.setItems(partsInventory);
        }
    }

    /**
     * @param event
     * @return modifies the product search information
     */
    @FXML
    private void modifyProductSearch(MouseEvent event) {
        if (search.getText() != null && search.getText().trim().length() != 0) {
            partsInventorySearch.clear();
            for (Part p : inv.getAllParts()) {
                if (p.getName().contains(search.getText().trim())) {
                    partsInventorySearch.add(p);
                }
            }
            partSearchTable.setItems(partsInventorySearch);
        }
    }

    /**
     * @param event
     * @return removes part from table
     */
    @FXML
    private void deletePart(MouseEvent event) {
        Part removePart = assocPartsTable.getSelectionModel().getSelectedItem();
        boolean deleted = false;
        if (removePart != null) {
            boolean remove = confirmationWindow(removePart.getName());
            if (remove) {
                deleted = product.deleteAssociatedPart(removePart.getPartID());
                associatedPartList.remove(removePart);
                assocPartsTable.refresh();
            }
        } else {
            return;
        }
        if (deleted) {
            ErrorController.infoWindow(1, removePart.getName());
        } else {
            ErrorController.infoWindow(2, "");
        }

    }

    /**
     * @param event
     * @return add part to table
     */
    @FXML
    private void addPart(MouseEvent event) {
        Part addPart = partSearchTable.getSelectionModel().getSelectedItem();
        boolean repeatedItem = false;

        if (addPart == null) {
            return;
        } else {
            int id = addPart.getPartID();
            for (int i = 0; i < associatedPartList.size(); i++) {
                if (associatedPartList.get(i).getPartID() == id) {
                    ErrorController.errorProduct(2, null);
                    repeatedItem = true;
                }
            }

            if (!repeatedItem) {
                associatedPartList.add(addPart);
            }
            assocPartsTable.setItems(associatedPartList);
        }
    }

    /**
     * @param event
     * @return Cancel modification of product
     */
    @FXML
    private void cancelModify(MouseEvent event) {
        boolean cancel = ErrorController.cancel();
        if (cancel) {
            mainScreen(event);
        } else {
            return;
        }
    }

    /**
     * @param event
     * @return Confirms save for product
     */
    @FXML
    private void saveProduct(MouseEvent event) {
        resetFieldsStyle();
        boolean end = false;
        TextField[] fieldCount = {count, price, min, max};
        double minCost = 0;
        for (int i = 0; i < associatedPartList.size(); i++) {
            minCost += associatedPartList.get(i).getPrice();
        }
        if (name.getText().trim().isEmpty() || name.getText().trim().toLowerCase().equals("part name")) {
            ErrorController.errorProduct(4, name);
            return;
        }
        for (int i = 0; i < fieldCount.length; i++) {
            boolean valueError = checkValue(fieldCount[i]);
            if (valueError) {
                end = true;
                break;
            }
            boolean typeError = checkType(fieldCount[i]);
            if (typeError) {
                end = true;
                break;
            }
        }
        if (Integer.parseInt(min.getText().trim()) > Integer.parseInt(max.getText().trim())) {
            ErrorController.errorProduct(10, min);
            return;
        }
        if (Integer.parseInt(count.getText().trim()) < Integer.parseInt(min.getText().trim())) {
            ErrorController.errorProduct(8, count);
            return;
        }
        if (Integer.parseInt(count.getText().trim()) > Integer.parseInt(max.getText().trim())) {
            ErrorController.errorProduct(9, count);
            return;
        }
        if (Double.parseDouble(price.getText().trim()) < minCost) {
            ErrorController.errorProduct(6, price);
            return;
        }
        if (associatedPartList.size() == 0) {
            ErrorController.errorProduct(7, null);
            return;
        }

        saveProduct();
        mainScreen(event);

    }

    /**
     * updates product data
     */
    private void saveProduct() {
        Product product = new Product(Integer.parseInt(id.getText().trim()), name.getText().trim(), Double.parseDouble(price.getText().trim()),
                Integer.parseInt(count.getText().trim()), Integer.parseInt(min.getText().trim()), Integer.parseInt(max.getText().trim()));
        for (int i = 0; i < associatedPartList.size(); i++) {
            product.addAssociatedPart(associatedPartList.get(i));
        }

        inv.updateProduct(product);

    }

    /**
     * Set associated part list data
     */
    private void setData() {
        for (int i = 0; i < 1000; i++) {
            Part part = product.lookupAssociatedPart(i);
            if (part != null) {
                associatedPartList.add(part);
            }
        }

        TableColumn<Part, Double> costCol = formatPrice();
        assocPartsTable.getColumns().addAll(costCol);

        assocPartsTable.setItems(associatedPartList);

        this.name.setText(product.getName());
        this.id.setText((Integer.toString(product.getProductID())));
        this.count.setText((Integer.toString(product.getInStock())));
        this.price.setText((Double.toString(product.getPrice())));
        this.min.setText((Integer.toString(product.getMin())));
        this.max.setText((Integer.toString(product.getMax())));

    }

    /**
     * Clears all text fields
     */
    private void resetFieldsStyle() {
        name.setStyle("-fx-border-color: lightgray");
        count.setStyle("-fx-border-color: lightgray");
        price.setStyle("-fx-border-color: lightgray");
        min.setStyle("-fx-border-color: lightgray");
        max.setStyle("-fx-border-color: lightgray");

    }

    /**
     * with this piece of code there was a runtime error that
     * wouldn't allow the pop up window to display correctly.
     * It was discovered that the ok button was not assigned correctly.
     * Once the button were assigned correctly the error was fixed.
     * @param name
     * @return Window to confirm deletion of part
     */
    private boolean confirmationWindow(String name) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete part");
        alert.setHeaderText("Are you sure you want to delete: " + name);
        alert.setContentText("Press OK to continue");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param event
     *  Main screen load
     */
    private void mainScreen(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainScreen.fxml"));
            MainScreenController controller = new MainScreenController(inv);

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {

        }
    }

    /**
     * @param field
     * @return confirms value
     */
    private boolean checkValue(TextField field) {
        boolean error = false;
        try {
            if (field.getText().trim().isEmpty() || field.getText().trim() == null) {
                ErrorController.errorProduct(1, field);
                return true;
            }
            if (field == price && Double.parseDouble(field.getText().trim()) < 0) {
                ErrorController.errorProduct(5, field);
                error = true;
            }
        } catch (Exception e) {
            error = true;
            ErrorController.errorProduct(3, field);
            System.out.println(e);

        }
        return error;
    }

    /**
     * @param field
     * @return validates type
     */
    private boolean checkType(TextField field) {

        if (field == price & !field.getText().trim().matches("\\d+(\\.\\d+)?")) {
            ErrorController.errorProduct(3, field);
            return true;
        }
        if (field != price & !field.getText().trim().matches("[0-9]*")) {
            ErrorController.errorProduct(3, field);
            return true;
        }
        return false;

    }

    /**
     * @param <T>
     * @return Sets format for cost column
     */
    private <T> TableColumn<T, Double> formatPrice() {
        TableColumn<T, Double> costCol = new TableColumn("Price");
        costCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        // Format as currency
        costCol.setCellFactory((TableColumn<T, Double> column) -> {
            return new TableCell<T, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    if (!empty) {
                        setText("$" + String.format("%10.2f", item));
                    }
                    else{
                        setText("");
                    }
                }
            };
        });
        return costCol;
    }

}
