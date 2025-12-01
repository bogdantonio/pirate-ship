package pirateSubclasses;
import pirate.Pirate;
import pirate.Role;
import pirate.InvalidDataException;

public class Helmsman extends Pirate{
    public int helmsmanId;
    public int maneuvering;
    public int precision;
    public int stormRiding;

    public Helmsman(int pirateId, String name, String alias, Role role,
                  int strength, int agility, int endurance,int intelligence, int charisma, int willpower,
                  int helmsmanId, int maneuvering, int precision, int stormRiding){

        super(pirateId, name, alias, role, strength, agility, endurance, intelligence, charisma, willpower);
        this.helmsmanId = helmsmanId;
        this.maneuvering = maneuvering;
        this.precision = precision;
        this.stormRiding = stormRiding;
    }

    public int getHelmsmanId(){
        return helmsmanId;
    }

    public void setHelmsmanId(int helmsmanId){
        this.helmsmanId = helmsmanId;
    }

    public int getManeuvering(){
        return maneuvering;
    }

    public void setManeuvering(int maneuvering){
        this.maneuvering = maneuvering;
    }

    public int getPrecision(){
        return precision;
    }

    public void setPrecision(int precision){
        this.precision = precision;
    }

    public int getStormRiding(){
        return stormRiding;
    }

    public void setStormRiding(int stormRiding){
        this.stormRiding = stormRiding;
    }

    @Override
    public void validateSubclassData() throws Exception {
        if(helmsmanId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }

        if(maneuvering < 10 || maneuvering > 100){
            throw new InvalidDataException("Invalid data for maneuvering: the value must be between 10 and 100!");
        }
        if(precision< 10 || precision > 100){
            throw new InvalidDataException("Invalid data for precision: the value must be between 10 and 100!");
        }
        if(stormRiding < 10 || stormRiding > 100){
            throw new InvalidDataException("Invalid data for stormRiding: the value must be between 10 and 100!");
        }
    }
}
