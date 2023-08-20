package pat.C482;

/** Class used for in house parts.*/
public class InHouse extends Parts {
    /** Initializes the machine ID. */
    private Integer machineId;
    /** Sets the machine ID. */
    public void setMachineID(Integer machineId) {
    }
    /** Returns the machine ID */
    public Integer getMachineId(){
        return machineId;
    }

    /** InHouse constructor.
        Used for setting the machine ID for an in house part.
     */
    public InHouse(Integer partID, String partName, Integer inventoryLevel, double partCost, Integer max, Integer min, Integer machineId) {
        super(partID, partName, inventoryLevel, partCost, max, min);
        this.machineId = machineId;
    }
}
