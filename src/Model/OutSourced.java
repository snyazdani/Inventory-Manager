package Model;

/**
 * @author Saba Yazdani, syazda4@wgu.edu
 * @version 1.0.0
 * @since 12/2/2020
 */
public class OutSourced extends Part {

    private String companyName;

    public OutSourced(int partID, String name, double price, int numInStock, int min, int max, String company) {
        setPartID(partID);
        setName(name);
        setPrice(price);
        setInStock(numInStock);
        setMin(min);
        setMax(max);
        setCompanyName(company);
    }

    /**
     * @param name
     * @return sets company name to new name
     */
    //Setter
    public void setCompanyName(String name) {
        this.companyName = name;
    }

    //Getter
    /**
     * @return returns company name
     */
    public String getCompanyName() {
        return companyName;
    }


}
