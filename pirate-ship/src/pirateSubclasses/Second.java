package pirateSubclasses;
import pirate.Pirate;
import pirate.PirateStatSet;
import pirate.Role;
import pirate.InvalidDataException;

public class Second extends Pirate{
    private int secondId;
    private int leadership;
    private int tactics;
    private int moraleBoost;

    public Second(int pirateId, Role role, String alias, String name, String sex, PirateStatSet pirateStatSet,
                  int secondId, int leadership, int tactics, int moraleBoost) {
        super(pirateId, role, alias, name, sex, pirateStatSet);
        this.secondId = secondId;
        this.leadership = leadership;
        this.tactics = tactics;
        this.moraleBoost = moraleBoost;
    }

    public int getSecondId() {
        return secondId;
    }

    public void setSecondId(int secondId) {
        this.secondId = secondId;
    }

    public int getLeadership() {
        return leadership;
    }

    public void setLeadership(int leadership) {
        this.leadership = leadership;
    }

    public int getTactics() {
        return tactics;
    }

    public void setTactics(int tactics) {
        this.tactics = tactics;
    }

    public int getMoraleBoost() {
        return moraleBoost;
    }

    public void setMoraleBoost(int moraleBoost) {
        this.moraleBoost = moraleBoost;
    }

    @Override
    public void printSubclassStats() {
        super.printPirateStats();
        System.out.println("Leadership: " + leadership);
        System.out.println("Tactics: " + tactics);
        System.out.println("Morale Boost: " + moraleBoost);
    }

    @Override
    public void validateSubclassData() throws Exception {
        if(this.secondId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }
        super.validatePirateData();

        if(this.leadership < 10 || this.leadership > 100){
            throw new InvalidDataException("Invalid data for leadership: the value must be between 10 and 100!");
        }
        if(this.tactics< 10 || this.tactics > 100){
            throw new InvalidDataException("Invalid data for tactics: the value must be between 10 and 100!");
        }
        if(this.moraleBoost < 10 || this.moraleBoost > 100){
            throw new InvalidDataException("Invalid data for moraleBoost: the value must be between 10 and 100!");
        }
    }

}
