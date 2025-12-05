package pirateSubclasses;
import pirate.Pirate;
import pirate.PirateStatSet;
import pirate.Role;
import pirate.InvalidDataException;

public class Cook extends Pirate{
    private int cookId;
    private int cooking;
    private int mealQuality;
    private int moraleImpact;

    public Cook(int pirateId, Role role, String alias, String name, String sex, PirateStatSet pirateStatSet,
                int moraleImpact, int cookId, int cooking, int mealQuality) {
        super(pirateId, role, alias, name, sex, pirateStatSet);
        this.moraleImpact = moraleImpact;
        this.cookId = cookId;
        this.cooking = cooking;
        this.mealQuality = mealQuality;
    }

    public int getCookId() {
        return cookId;
    }

    public void setCookId(int cookId) {
        this.cookId = cookId;
    }

    public int getCooking() {
        return cooking;
    }

    public void setCooking(int cooking) {
        this.cooking = cooking;
    }

    public int getMealQuality() {
        return mealQuality;
    }

    public void setMealQuality(int mealQuality) {
        this.mealQuality = mealQuality;
    }

    public int getMoraleImpact() {
        return moraleImpact;
    }

    public void setMoraleImpact(int moraleImpact) {
        this.moraleImpact = moraleImpact;
    }

    @Override
    public void validateSubclassData() throws Exception {
        if(this.cookId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }

        if(this.cooking < 10 || this.cooking > 100){
            throw new InvalidDataException("Invalid data for cooking: the value must be between 10 and 100!");
        }
        if(this.mealQuality< 10 || this.mealQuality > 100){
            throw new InvalidDataException("Invalid data for mealQuality: the value must be between 10 and 100!");
        }
        if(this.moraleImpact < 10 || this.moraleImpact > 100){
            throw new InvalidDataException("Invalid data for moraleImpact: the value must be between 10 and 100!");
        }
    }
}
