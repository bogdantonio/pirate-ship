package pirate;

public class PirateStatSet {
    private int statSetId;
    private int strength;
    private int agility;
    private int endurance;
    private int intelligence;
    private int charisma;
    private int willpower;

    public PirateStatSet(int statSetId, int strength, int agility, int endurance, int intelligence, int charisma, int willpower) {
        this.statSetId = statSetId;
        this.strength = strength;
        this.agility = agility;
        this.endurance = endurance;
        this.intelligence = intelligence;
        this.charisma = charisma;
        this.willpower = willpower;
    }

    public int getStatSetId() {
        return this.statSetId;
    }

    public void setStatSetId(int statSetId) {
        this.statSetId = statSetId;
    }

    public int getWillpower() {
        return this.willpower;
    }

    public void setWillpower(int willpower) {
        this.willpower = willpower;
    }

    public int getCharisma() {
        return this.charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = this.charisma;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getEndurance() {
        return this.endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getAgility() {
        return this.agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getStrength() {
        return this.strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void validateStatData() throws Exception{
        if(this.statSetId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }

        if(this.strength < 10 || this.strength > 100){
            throw new InvalidDataException("Invalid data for strength: the value must be between 10 and 100!");
        }
        if(this.agility < 10 || this.agility > 100){
            throw new InvalidDataException("Invalid data for agility: the value must be between 10 and 100!");
        }
        if(this.endurance < 10 || this.endurance > 100){
            throw new InvalidDataException("Invalid data for endurance: the value must be between 10 and 100!");
        }
        if(this.intelligence < 10 || this.intelligence > 100){
            throw new InvalidDataException("Invalid data for intelligence: the value must be between 10 and 100!");
        }
        if(this.charisma < 10 || this.charisma > 100) {
            throw new InvalidDataException("Invalid data for charisma: the value must be between 10 and 100!");
        }
        if(this.willpower < 10 || this.willpower > 100){
            throw new InvalidDataException("Invalid data for willpower: the value must be between 10 and 100!");
        }
    }

}
