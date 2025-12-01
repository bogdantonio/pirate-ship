package pirateSubclasses;
import pirate.Pirate;
import pirate.Role;
import pirate.InvalidDataException;

public class Cook extends Pirate{
    public int cookId;
    public int cooking;
    public int mealQuality;
    public int moraleImpact;

    public Cook(int pirateId, String name, String alias, Role role,
                      int strength, int agility, int endurance,int intelligence, int charisma, int willpower,
                      int cookId, int cooking, int mealQuality, int moraleImpact){

        super(pirateId, name, alias, role, strength, agility, endurance, intelligence, charisma, willpower);
        this.cookId = cookId;
        this.cooking = cooking;
        this.mealQuality = mealQuality;
        this.moraleImpact = moraleImpact;
    }

    public int getCookId(){
        return cookId;
    }

    public void setCookId(int cookId){
        this.cookId = cookId;
    }

    public int getCooking(){
        return cooking;
    }

    public void setCooking(int cooking){
        this.cooking = cooking;
    }

    public int getMealQuality(){
        return mealQuality;
    }

    public void setMealQuality(int mealQuality){
        this.mealQuality = mealQuality;
    }

    public int getMoraleImpact(){
        return moraleImpact;
    }

    public void setMoraleImpact(int moraleImpact){
        this.moraleImpact = moraleImpact;
    }

    @Override
    public void validateSubclassData() throws Exception {
        if(cookId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }

        if(cooking < 10 || cooking > 100){
            throw new InvalidDataException("Invalid data for cooking: the value must be between 10 and 100!");
        }
        if(mealQuality< 10 || mealQuality > 100){
            throw new InvalidDataException("Invalid data for mealQuality: the value must be between 10 and 100!");
        }
        if(moraleImpact < 10 || moraleImpact > 100){
            throw new InvalidDataException("Invalid data for moraleImpact: the value must be between 10 and 100!");
        }
    }
}
