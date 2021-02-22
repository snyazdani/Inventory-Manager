package Model;

/**
 * @author Saba Yazdani, syazda4@wgu.edu
 * @version 1.0.0
 * @since 12/2/2020
 */

public class InHouse extends Part {

    private int machineID;

    public InHouse(int partID, String name, double price, int numInStock, int min, int max, int machID) {

        setPartID(partID);
        setName(name);
        setPrice(price);
        setInStock(numInStock);
        setMin(min);
        setMax(max);
        setMachineID(machID);
    }

    /**
     * @param id
     * @return sets machineID to new ID
     */

    //Setter
    public void setMachineID(int id) {
        this.machineID = id;
    }

    /**
     * @return return ID
     */
    //Getter
    public int getMachineID() {
        return machineID;
    }

}