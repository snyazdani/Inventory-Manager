package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.stage.Modality;
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

public class MainScreenController implements Initializable {

    Inventory inv;

    @FXML
    private TextField partSearchBox;
    @FXML
    private TextField productSearchBox;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableView<Product> productsTable;
    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Product> productInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();
    private ObservableList<Product> productInventorySearch = FXCollections.observableArrayList();

    public MainScreenController(Inventory inv) {
        this.inv = inv;
    }

    /**
     * @param url
     * @param rb
     * @return initialize controller
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generatePartsTable();
        generateProductsTable();
    }

    /**
     * display parts table
     */
    private void generatePartsTable() {
        partInventory.setAll(inv.getAllParts());
        TableColumn<Part, Double> costCol = formatPrice();
        partsTable.getColumns().addAll(costCol);
        partsTable.setItems(partInventory);
        partsTable.refresh();
    }

    /**
     * display products table
     */
    private void generateProductsTable() {
        productInventory.setAll(inv.getAllProducts());
        TableColumn<Product, Double> costCol = formatPrice();
        productsTable.getColumns().addAll(costCol);
        productsTable.setItems(productInventory);
        productsTable.refresh();
    }

    /**
     * @param event
     * @ return exit program
     */
    @FXML
    private void exitProgram(ActionEvent event
    ) {
        Platform.exit();
    }

    /**
     * @param event
     * @return exit button
     */
    @FXML
    private void exitProgramButton(MouseEvent event
    ) {
        Platform.exit();
    }


    /**
     * @param event
     * @return adds part ot list or alert window for thrown exceptions
     */
    @FXML
    private void searchForPart(MouseEvent event) {
        String searchPartString = partSearchBox.getText();
        if (searchPartString.isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Search Error");
            alert.setHeaderText("Empty Search");
            alert.setContentText("Search box is empty. Type the part name you are looking for, then press search.");
            alert.showAndWait();
        }
        else {
            try {
                Part searchPart = inv.lookUpPart(Integer.parseInt(searchPartString));
                if (searchPart != null) {
                    partSearchBox.getText().trim();
                    partsTable.setItems(inv.lookUpPart(partSearchBox.getText().trim()));
                    partsTable.refresh();
                } else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.initModality(Modality.NONE);
                    alert.setTitle("Search Error");
                    alert.setHeaderText("Part not found");
                    alert.setContentText("The part searched does not exist!");
                    alert.showAndWait();
                }

            } catch (NumberFormatException e) {
                if (!searchPartString.isEmpty()) {
                    partSearchBox.getText().trim();
                    partsTable.setItems(inv.lookUpPart(partSearchBox.getText().trim()));
                    partsTable.refresh();
                }
            }
        }
    }


    /**
     * @param event
     * @return adds product to list or alert window for thrown exceptions
     */
    @FXML
    private void searchForProduct(MouseEvent event) {
        String searchProductString = productSearchBox.getText();
        if (searchProductString.isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Search Error");
            alert.setHeaderText("Empty Search");
            alert.setContentText("Search box is empty. Type the product name you are looking for, then press search.");
            alert.showAndWait();
        }
        else {
            try {
                Product searchProduct = inv.lookUpProduct(Integer.parseInt(searchProductString));
                if (searchProduct != null) {
                    productInventorySearch.clear();
                    for (Product p : inv.getAllProducts()) {
                        if (p.getName().contains(productSearchBox.getText().trim())) {
                            productInventorySearch.add(p);
                        }
                    }
                    productsTable.setItems(productInventorySearch);
                    productsTable.refresh();
                }
                else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.initModality(Modality.NONE);
                    alert.setTitle("Search Error");
                    alert.setHeaderText("Product not found");
                    alert.setContentText("The product searched does not exist!");
                    alert.showAndWait();
                }

            } catch (NumberFormatException e) {
                if (!productSearchBox.getText().trim().isEmpty()) {
                    productInventorySearch.clear();
                    for (Product p : inv.getAllProducts()) {
                        if (p.getName().contains(productSearchBox.getText().trim())) {
                            productInventorySearch.add(p);
                        }
                    }
                    productsTable.setItems(productInventorySearch);
                    productsTable.refresh();
                }
            }
        }
    }

    /**
     * @param event
     * @return clear text fields
     */
    @FXML
    void clearText(MouseEvent event
    ) {
        Object source = event.getSource();
        TextField field = (TextField) source;
        field.setText("");
        if (partSearchBox == field) {
            if (partInventory.size() != 0) {
                partsTable.setItems(partInventory);
            }
        }
        if (productSearchBox == field) {
            if (productInventory.size() != 0) {
                productsTable.setItems(productInventory);
            }
        }
    }

    /**
     * @param event
     * @return add a part
     */
    @FXML
    private void addPart(MouseEvent event
    ) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddPart.fxml"));
            AddPartController controller = new AddPartController(inv);

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
     * @param event
     * @return modify a part
     */
    @FXML
    private void modifyPart(MouseEvent event
    ) {
        try {
            Part selected = partsTable.getSelectionModel().getSelectedItem();
            if (partInventory.isEmpty()) {
                errorWindow(1);
                return;
            }
            if (!partInventory.isEmpty() && selected == null) {
                errorWindow(2);
                return;
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyPart.fxml"));
                ModifyPartController controller = new ModifyPartController(inv, selected);

                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
//                ModifyPartController c  = (ModifyPartController)loader.getController();
//                c.setSelectPart(selected);
            }
        } catch (IOException e) {

        }
    }

    /**
     * @param event
     * @return delete a part
     */
    @FXML
    private void deletePart(MouseEvent event
    ) {
        Part removePart = partsTable.getSelectionModel().getSelectedItem();
        if (partInventory.isEmpty()) {
            errorWindow(1);
            return;
        }
        if (!partInventory.isEmpty() && removePart == null) {
            errorWindow(2);
            return;
        } else {
            boolean confirm = confirmationWindow(removePart.getName());
            if (!confirm) {
                return;
            }
            inv.deletePart(removePart);
            partInventory.remove(removePart);
            partsTable.refresh();

        }
    }

    /**
     * @param event
     * @return delete a product
     */
    @FXML
    private void deleteProduct(MouseEvent event
    ) {
        boolean deleted = false;
        Product removeProduct = productsTable.getSelectionModel().getSelectedItem();
        if (productInventory.isEmpty()) {
            errorWindow(1);
            return;
        }
        if (!productInventory.isEmpty() && removeProduct == null) {
            errorWindow(2);
            return;
        }
        if (removeProduct.getPartsListSize() > 0) {
            boolean confirm = confirmDelete(removeProduct.getName());
            if (!confirm) {
                return;
            }
        } else {
            if (removeProduct != null) {
                infoWindow(1, removeProduct.getName());
                deleted = true;
                if (deleted) {
                    return;

                } else {
                    infoWindow(2, "");
                }

            }
        }
        inv.deleteProduct(removeProduct.getProductID());
        productInventory.remove(removeProduct);
        productsTable.setItems(productInventory);
        productsTable.refresh();
    }

    /**
     * @param event
     * @return modify a product
     */
    @FXML
    private void modifyProduct(MouseEvent event
    ) {
        try {
            Product productSelected = productsTable.getSelectionModel().getSelectedItem();
            if (productInventory.isEmpty()) {
                errorWindow(1);
                return;
            }
            if (!productInventory.isEmpty() && productSelected == null) {
                errorWindow(2);
                return;

            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyProduct.fxml"));
                ModifyProductController controller = new ModifyProductController(inv, productSelected);

                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
        } catch (IOException e) {

        }
    }

    /**
     * @param event
     * @return adding a product
     */
    @FXML
    private void addProduct(MouseEvent event
    ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddProduct.fxml"));
            AddProductController controller = new AddProductController(inv);

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
     * @param code
     * @return Error window pops up when information missing
     */
    private void errorWindow(int code) {
        if (code == 1) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Inventory");
            alert.setContentText("There's nothing to select");
            alert.showAndWait();
        }
        if (code == 2) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Selection");
            alert.setContentText("You must select an item");
            alert.showAndWait();
        }

    }

    /**
     * @param name
     * @return alert window for confirmation of part deletion
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
     * @param name
     * @return alert window for product deletion
     */
    private boolean confirmDelete(String name) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete product");
        alert.setHeaderText("Are you sure you want to delete: " + name + " this product still has parts assigned to it");
        alert.setContentText("Press OK to continue");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param code
     * @param name
     * @return alert window for main
     */
    private void infoWindow(int code, String name) {
        if (code != 2) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Confirmed");
            alert.setHeaderText(null);
            alert.setContentText(name + " has been deleted!");

            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("There was an error");
        }
    }

    /**
     * @param <T>
     * @return sets format for cost column
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
