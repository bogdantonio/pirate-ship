package pirateSubclasses;
import pirate.Pirate;
import pirate.PirateStatSet;
import pirate.Role;
import pirate.InvalidDataException;

public class Shipwright extends Pirate{
    private int shipwrightId;
    private int repair;
    private int construction;
    private int materials;

    public Shipwright(int pirateId, Role role, String alias, String name, String sex, PirateStatSet pirateStatSet,
                      int shipwrightId, int repair, int construction, int materials) {
        super(pirateId, role, alias, name, sex, pirateStatSet);
        this.shipwrightId = shipwrightId;
        this.repair = repair;
        this.construction = construction;
        this.materials = materials;
    }

    public int getShipwrightId() {
        return shipwrightId;
    }

    public void setShipwrightId(int shipwrightId) {
        this.shipwrightId = shipwrightId;
    }

    public int getRepair() {
        return repair;
    }

    public void setRepair(int repair) {
        this.repair = repair;
    }

    public int getConstruction() {
        return construction;
    }

    public void setConstruction(int construction) {
        this.construction = construction;
    }

    public int getMaterials() {
        return materials;
    }

    public void setMaterials(int materials) {
        this.materials = materials;
    }

    @Override
    public void validateSubclassData() throws Exception {
        if(this.shipwrightId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }
        super.validatePirateData();

        if(this.repair < 10 || this.repair > 100){
            throw new InvalidDataException("Invalid data for repair: the value must be between 10 and 100!");
        }
        if(this.construction< 10 || this.construction > 100){
            throw new InvalidDataException("Invalid data for construction: the value must be between 10 and 100!");
        }
        if(this.materials < 10 || this.materials > 100){
            throw new InvalidDataException("Invalid data for materials: the value must be between 10 and 100!");
        }
    }
}
