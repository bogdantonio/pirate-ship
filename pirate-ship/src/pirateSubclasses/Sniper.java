package pirateSubclasses;
import pirate.Pirate;
import pirate.PirateStatSet;
import pirate.Role;
import pirate.InvalidDataException;

public class Sniper extends Pirate {
    private int sniperId;
    private int accuracy;
    private int weaponRange;
    private int criticalChance;

    public Sniper(int pirateId, Role role, String alias, String name, String sex, PirateStatSet pirateStatSet,
                  int sniperId, int accuracy, int weaponRange, int criticalChance) {
        super(pirateId, role, alias, name, sex, pirateStatSet);
        this.sniperId = sniperId;
        this.accuracy = accuracy;
        this.weaponRange = weaponRange;
        this.criticalChance = criticalChance;
    }

    public int getSniperId() {
        return sniperId;
    }

    public void setSniperId(int sniperId) {
        this.sniperId = sniperId;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getWeaponRange() {
        return weaponRange;
    }

    public void setWeaponRange(int weaponRange) {
        this.weaponRange = weaponRange;
    }

    public int getCriticalChance() {
        return criticalChance;
    }

    public void setCriticalChance(int criticalChance) {
        this.criticalChance = criticalChance;
    }

    @Override
    public void validateSubclassData() throws Exception {
        if(this.sniperId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }

        if(this.accuracy < 10 || this.accuracy > 100){
            throw new InvalidDataException("Invalid data for accuracy: the value must be between 10 and 100!");
        }
        if(this.weaponRange< 10 || this.weaponRange > 100){
            throw new InvalidDataException("Invalid data for weaponRange: the value must be between 10 and 100!");
        }
        if(this.criticalChance < 10 || this.criticalChance > 100){
            throw new InvalidDataException("Invalid data for criticalChance: the value must be between 10 and 100!");
        }
    }
}
