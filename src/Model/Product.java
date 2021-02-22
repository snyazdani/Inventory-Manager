package Model;

import java.util.ArrayList;

/**
 * @author Saba Yazdani, syazda4@wgu.edu
 * @version 1.0.0
 * @since 12/2/2020
 */
public class Product {

    private ArrayList<Part> associatedParts = new ArrayList<Part>();
    private int productID;
    private String name;
    private double price = 0.0;
    private int inStock = 0;
    private int min;
    private int max;
    private double cost;

    public Product(int productID, String name, double price, int inStock, int min, int max) {
        setProductID(productID);
        setName(name);
        setPrice(price);
        setInStock(inStock);
        setMin(min);
        setMax(max);

    }

    //Setters
    public void setProductID(int id) {
        this.productID = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setInStock(int quantity) {
        this.inStock = quantity;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    //Getters
    public int getProductID() {
        return this.productID;
    }

    public String getName() {
        return this.name;
    }


    public double getPrice() {
        return this.price;
    }


    public int getInStock() {
        return this.inStock;
    }


    public int getMin() {
        return this.min;
    }


    public int getMax() {
        return this.max;
    }

    /**
     * @param partToAdd add to associated parts
     */
    public void addAssociatedPart(Part partToAdd) {
        associatedParts.add(partToAdd);
    }

    /**
     * @param partToRemove
     * @return deletes associated parts
     */
    public boolean deleteAssociatedPart(int partToRemove) {
        int i;
        for (i = 0; i < associatedParts.size(); i++) {
            if (associatedParts.get(i).getPartID() == partToRemove) {
                associatedParts.remove(i);
                return true;
            }
        }

        return false;
    }

    /**
     * @param partToSearch set to associated parts ID
     */
    public Part lookupAssociatedPart(int partToSearch) {
        for (int i = 0; i < associatedParts.size(); i++) {
            if (associatedParts.get(i).getPartID() == partToSearch) {
                return associatedParts.get(i);
            }
        }
        return null;
    }

    /**
     * @return returns size of associated parts list
     */
    public int getPartsListSize() {
        return associatedParts.size();
    }

}