package pirateSubclasses;
import pirate.Pirate;
import pirate.Role;
import pirate.InvalidDataException;

public class Shipwright extends Pirate{
    public int shipwrightId;
    public int repair;
    public int construction;
    public int materials;

    public Shipwright(int pirateId, String name, String alias, Role role,
                  int strength, int agility, int endurance,int intelligence, int charisma, int willpower,
                  int shipwrightId, int repair, int construction, int materials){

        super(pirateId, name, alias, role, strength, agility, endurance, intelligence, charisma, willpower);
        this.shipwrightId = shipwrightId;
        this.repair = repair;
        this.construction = construction;
        this.materials = materials;
    }

    public int getShipwrightId(){
        return shipwrightId;
    }

    public void setShipwrightId(int shipwrightId){
        this.shipwrightId = shipwrightId;
    }

    public int getRepair(){
        return repair;
    }

    public void setRepair(int repair){
        this.repair = repair;
    }

    public int getConstruction(){
        return construction;
    }

    public void setConstruction(int construction){
        this.construction = construction;
    }

    public int getMaterials(){
        return materials;
    }

    public void setMaterials(int materials){
        this.materials = materials;
    }

    @Override
    public void validateSubclassData() throws Exception {
        if(shipwrightId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }

        if(repair < 10 || repair > 100){
            throw new InvalidDataException("Invalid data for repair: the value must be between 10 and 100!");
        }
        if(construction< 10 || construction > 100){
            throw new InvalidDataException("Invalid data for construction: the value must be between 10 and 100!");
        }
        if(materials < 10 || materials > 100){
            throw new InvalidDataException("Invalid data for materials: the value must be between 10 and 100!");
        }
    }
}
