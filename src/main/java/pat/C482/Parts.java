package pat.C482;


import java.io.IOException;

/** Parts class for accessing parts.
 */
public abstract class Parts {

    /** Stores the part ID integer. */
    private Integer partID;
    /** Stores the part name string. */
    private String partName;
    /** Stores the inventory level integer. */
    private Integer inventoryLevel;
    /** Stores the part cost. */
    private double partCost;
    /** Stores the max inventory value. */
    private Integer max;
    /** Stores the minimum inventory value. */
    private Integer min;
    /** Stores the machine id. */
    private Integer machineID;
    private String companyName;


    /** Parts constructor.
     * Used for saving a part list.

     * @param partID Used to store the part id.
     * @param partName Used to store the part name.
     * @param inventoryLevel Used to store the inventory level.
     * @param partCost Used to store the part cost.
     * @param max Used to store the max value.
     * @param min Used to store the minimum value.
     */
    public Parts(Integer partID, String partName, Integer inventoryLevel, double partCost, Integer max, Integer min) {
        this.partID = partID;
        this.partName = partName;
        this.inventoryLevel = inventoryLevel;
        this.partCost = partCost;
        this.max = max;
        this.min = min;
    }

    /** Default parts constructor */
    public Parts() {

    }

    /** ID setter. */
    private void setID() {
    }
    /** Name setter. */
    private void setName() {
    }
    /** Price setter. */
    private void setPrice() {
    }
    /** Stock setter. */
    private void setStock() {
    }
    /** Min setter. */
    private void setMin() {
    }
    /** Max setter. */
    private void setMax() {
    }

    /** Returns the selected part ID. */
    public Integer getId() {
        return partID;
    }

    /** Returns the selected part name. */
    public String getName() throws IOException {
        return partName;
    }

    /** Returns the selected part inventory level. */
    public Integer getInventory() {
        return inventoryLevel;
    }

    /** Returns the selected part cost. */
    public String getPartCost() {
        return String.format("%.2f", partCost);
    }

    /** Returns the selected max value. */
    public Integer getMax() {
        return max;
    }

    /** Returns the selected minimum value. */
    public Integer getMin() {
        return min;
    }

    /**
     * Returns the selected machine ID.
     */
    public Integer getMachineId() {
        return machineID;
    }
    /** Returns the selected machine ID. */
    public String getCompanyName() {
        return companyName;
    }


}
