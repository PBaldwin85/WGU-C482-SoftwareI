package pat.C482;

/** Class used for in house parts. */
public class Outsourced extends Parts {
    /** Initializes the company name. */
    private String companyName;
    /** Sets the company name */
    public void setCompanyName(String companyName) {
    }
    /** Returns the company name */
    public String getCompanyName(){
        return companyName;
    }

    /** Outsourced constructor.
     Used for setting the company name for an outsourced part.
     */
    public Outsourced(Integer partID, String partName, Integer inventoryLevel, double partCost, Integer max, Integer min, String companyName) {
        super(partID, partName, inventoryLevel, partCost, max, min);
        this.companyName = companyName;
    }
}
