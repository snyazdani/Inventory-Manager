package Model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Saba Yazdani, syazda4@wgu.edu
 * @version 1.0.0
 * @since 12/2/2020
 */
public class Inventory {

    private static ArrayList<Part> allParts;
    private static ArrayList<Product> allProducts;

    public Inventory() {
        allParts = new ArrayList<>();
        allProducts = new ArrayList<>();
    }

    /**
     * @param newPart added to all parts
     */
    public static void addPart(Part newPart) {
        if (newPart != null) {
            allParts.add(newPart);
        }
    }

    /**
     * @param newProduct added to all products
     */
    public static void addProduct(Product newProduct) {
        if (newProduct != null) {
            allProducts.add(newProduct);
        }
    }

    /**
     * @param partToLookUp
     * @return returns part
     */
    public static Part lookUpPart(int partToLookUp) {
        if (!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getPartID() == partToLookUp) {
                    return allParts.get(i);
                }
            }

        }
        return null;
    }

    /**
     * @param productToSearch
     * @return returns list of products
     */
    public static Product lookUpProduct(int productToSearch) {
        if (!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getProductID() == productToSearch) {
                    return allProducts.get(i);
                }
            }
        }
        return null;
    }

    /**
     * @param partNameToLookUp
     * @return returns list of parts
     */
    public static ObservableList<Part> lookUpPart(String partNameToLookUp) {
        if (!allParts.isEmpty()) {
            ObservableList searchPartsList = FXCollections.observableArrayList();
            for (Part p : getAllParts()) {
                if (p.getName().contains(partNameToLookUp)) {
                    searchPartsList.add(p);
                }
            }
            return searchPartsList;
        }
        return null;
    }

    public static ObservableList<Part> lookUpProduct(String productNameToLookUp) {
        return null;
    }

    /**
     * @param partToUpdate
     * @return returns updated part
     */
    public static void updatePart(Part partToUpdate) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getPartID() == partToUpdate.partID) {
                allParts.set(i, partToUpdate);
                break;
            }
        }
        return;
    }

    /**
     * @param product
     * @return sets product to updated product
     */

    public static void updateProduct(Product product) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProductID() == product.getProductID()) {
                allProducts.set(i, product);
                break;
            }
        }
        return;
    }

    /**
     * @param partToDelete
     * @return deletes part from list
     */
    public static boolean deletePart(Part partToDelete) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getPartID() == partToDelete.getPartID()) {
                allParts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * @param productToRemove
     * @return deletes product from list
     */
    public static boolean deleteProduct(int productToRemove) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProductID() == productToRemove) {
                allProducts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * @return returns all the parts
     */
    public static ArrayList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return returns all the products
     */
    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * @return returns size of allParts
     */
    public static int partListSize() {
        return allParts.size();
    }

    /**
     * @return returns size of allProducts
     */
    public static int productListSize() {
        return allProducts.size();
    }

}