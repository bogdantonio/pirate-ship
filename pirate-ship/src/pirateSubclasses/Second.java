package pirateSubclasses;
import pirate.Pirate;
import pirate.Role;
import pirate.InvalidDataException;

public class Second extends Pirate{
    public int secondId;
    public int leadership;
    public int tactics;
    public int moraleBoost;

    public Second(int pirateId, String name, String alias, Role role,
                  int strength, int agility, int endurance,int intelligence, int charisma, int willpower,
                  int secondId, int leadership, int tactics, int moraleBoost){

        super(pirateId, name, alias, role, strength, agility, endurance, intelligence, charisma, willpower);
        this.secondId = secondId;
        this.leadership = leadership;
        this.tactics = tactics;
        this.moraleBoost = moraleBoost;
    }

    public int getSecondId(){
        return secondId;
    }

    public void setSecondId(int secondId){
        this.secondId = secondId;
    }

    public int getLeadership(){
        return leadership;
    }

    public void setLeadership(int leadership){
        this.leadership = leadership;
    }

    public int getTactics(){
        return tactics;
    }

    public void setTactics(int tactics){
        this.tactics = tactics;
    }

    public int getMoraleBoost(){
        return moraleBoost;
    }

    public void setMoraleBoost(int moraleBoost){
        this.moraleBoost = moraleBoost;
    }

    @Override
    public void validateSubclassData() throws Exception {
        if(secondId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }

        if(leadership < 10 || leadership > 100){
            throw new InvalidDataException("Invalid data for leadership: the value must be between 10 and 100!");
        }
        if(tactics< 10 || tactics > 100){
            throw new InvalidDataException("Invalid data for tactics: the value must be between 10 and 100!");
        }
        if(moraleBoost < 10 || moraleBoost > 100){
            throw new InvalidDataException("Invalid data for moraleBoost: the value must be between 10 and 100!");
        }
    }

}
