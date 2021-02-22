package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InventoryManager extends Application {

    /**
     * @author Saba Yazdani, syazda4@wgu.edu
     * @version 1.0.0
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Inventory inv = new Inventory();
        inventoryData(inv);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainScreen.fxml"));
        Controller.MainScreenController controller = new Controller.MainScreenController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    //Preset data used by program
    void inventoryData(Inventory inv) {
        //Add InHouse Parts
        Part p1 = new InHouse(101, "part 101", 10.21, 15, 1, 250, 11);
        Part p2 = new InHouse(301, "part 301", 22.44, 10, 1, 250, 13);
        Part p3 = new InHouse(201, "part 201", 16.19, 6, 1, 250, 12);
        inv.addPart(p1);
        inv.addPart(p3);
        inv.addPart(p2);
        inv.addPart(new InHouse(401, "part 401", 7.22, 7, 1, 250, 14));
        inv.addPart(new InHouse(501, "part 501", 18.44, 22, 1, 250, 15));
        //Add OutSourced Parts
        Part p6 = new OutSourced(601, "part 601", 21.43, 25, 1, 250, "JDM Laboratories");
        Part p7 = new OutSourced(701, "part 701", 42.24, 14, 1, 250, "JDM Laboratories");
        Part p8 = new OutSourced(801, "part 801", 27.56, 30, 1, 250, "Clayton LLC");
        inv.addPart(p6);
        inv.addPart(p7);
        inv.addPart(p8);
        inv.addPart(new OutSourced(901, "part 901", 14.33, 13, 1, 250, "Clayton LLC"));
        inv.addPart(new OutSourced(1001, "part 1001", 27.25, 7, 1, 250, "Argent Corp."));
        //Add allProducts
        Product pro1 = new Product(1, "product 1", 172.15, 80, 1, 250);
        pro1.addAssociatedPart(p1);
        pro1.addAssociatedPart(p6);
        inv.addProduct(pro1);
        Product pro2 = new Product(2, "product 2", 161.19, 51, 1, 250);
        pro2.addAssociatedPart(p2);
        pro2.addAssociatedPart(p7);
        inv.addProduct(pro2);
        Product pro3 = new Product(3, "product 3", 236.24, 13, 1, 250);
        pro3.addAssociatedPart(p3);
        pro3.addAssociatedPart(p8);
        inv.addProduct(pro3);
        Product pro4 = new Product(4, "product 4", 252.94, 21, 1, 250);
        inv.addProduct(pro4);
        inv.addProduct(new Product(5, "product 5", 306.32, 24, 1, 250));

    }

}
