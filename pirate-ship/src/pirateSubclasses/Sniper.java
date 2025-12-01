package pirateSubclasses;
import pirate.Pirate;
import pirate.Role;
import pirate.InvalidDataException;

public class Sniper extends Pirate {
    public int sniperId;
    public int accuracy;
    public int weaponRange;
    public int criticalChance;

    public Sniper(int pirateId, String name, String alias, Role role,
                     int strength, int agility, int endurance,int intelligence, int charisma, int willpower,
                     int sniperId, int accuracy, int weaponRange, int criticalChance){

        super(pirateId, name, alias, role, strength, agility, endurance, intelligence, charisma, willpower);
        this.sniperId = sniperId;
        this.accuracy = accuracy;
        this.weaponRange = weaponRange;
        this.criticalChance = criticalChance;
    }

    public int getSniperId(){
        return sniperId;
    }

    public void setSniperId(int sniperId){
        this.sniperId = sniperId;
    }

    public int getAccuracy(){
        return accuracy;
    }

    public void setAccuracy(int accuracy){
        this.accuracy = accuracy;
    }

    public int getWeaponRange(){
        return weaponRange;
    }

    public void setWeaponRange(int weaponRange){
        this.weaponRange = weaponRange;
    }

    public int getCriticalChance(){
        return criticalChance;
    }

    public void setCriticalChance(int criticalChance){
        this.criticalChance = criticalChance;
    }

    @Override
    public void validateSubclassData() throws Exception {
        if(sniperId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }

        if(accuracy < 10 || accuracy > 100){
            throw new InvalidDataException("Invalid data for accuracy: the value must be between 10 and 100!");
        }
        if(weaponRange< 10 || weaponRange > 100){
            throw new InvalidDataException("Invalid data for weaponRange: the value must be between 10 and 100!");
        }
        if(criticalChance < 10 || criticalChance > 100){
            throw new InvalidDataException("Invalid data for criticalChance: the value must be between 10 and 100!");
        }
    }
}
