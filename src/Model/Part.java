package Model;

/**
 * @author Saba Yazdani, syazda4@wgu.edu
 * @version 1.0.0
 * @since 12/2/2020
 */
public abstract class Part {

    protected int partID;
    protected String partName;
    protected double partPrice = 0.0;
    protected int partInStock;
    protected int min;
    protected int max;

    /**
     * @param partID
     * @return sets partID to new partID
     */

    //Setters
    public void setPartID(int partID) {
        this.partID = partID;
    }

    public void setName(String name) {
        this.partName = name;
    }

    public void setPrice(double price) {
        this.partPrice = price;
    }

    public void setInStock(int quantity) {
        this.partInStock = quantity;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    //Getters
    public int getPartID() {
        return this.partID;
    }

    public String getName() {
        return this.partName;
    }

    public double getPrice() {
        return partPrice;
    }

    public int getInStock() {
        return this.partInStock;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

}