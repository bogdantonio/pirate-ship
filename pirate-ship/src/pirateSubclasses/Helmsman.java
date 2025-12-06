package pirateSubclasses;
import pirate.Pirate;
import pirate.PirateStatSet;
import pirate.Role;
import pirate.InvalidDataException;

public class Helmsman extends Pirate{
    private int helmsmanId;
    private int maneuvering;
    private int precision;
    private int stormRiding;

    public Helmsman(int pirateId, Role role, String alias, String name, String sex, PirateStatSet pirateStatSet,
                    int helmsmanId, int stormRiding, int precision, int maneuvering) {
        super(pirateId, role, alias, name, sex, pirateStatSet);
        this.helmsmanId = helmsmanId;
        this.stormRiding = stormRiding;
        this.precision = precision;
        this.maneuvering = maneuvering;
    }


    public int getHelmsmanId() {
        return helmsmanId;
    }

    public void setHelmsmanId(int helmsmanId) {
        this.helmsmanId = helmsmanId;
    }

    public int getManeuvering() {
        return maneuvering;
    }

    public void setManeuvering(int maneuvering) {
        this.maneuvering = maneuvering;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getStormRiding() {
        return stormRiding;
    }

    public void setStormRiding(int stormRiding) {
        this.stormRiding = stormRiding;
    }

    @Override
    public void validateSubclassData() throws Exception {
        if(this.helmsmanId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }
        super.validatePirateData();

        if(this.maneuvering < 10 || this.maneuvering > 100){
            throw new InvalidDataException("Invalid data for maneuvering: the value must be between 10 and 100!");
        }
        if(this.precision< 10 || this.precision > 100){
            throw new InvalidDataException("Invalid data for precision: the value must be between 10 and 100!");
        }
        if(this.stormRiding < 10 || this.stormRiding > 100){
            throw new InvalidDataException("Invalid data for stormRiding: the value must be between 10 and 100!");
        }
    }
}
