package Pirate;

public abstract class Pirate {
    public int pirateId;
    public String name;
    public String alias;
    public Role role;
    public int strength;
    public int agility;
    public int endurance;
    public int intelligence;
    public int charisma;
    public int willpower;

    public Pirate(int pirateId, String name, String alias, Role role,
                  int strength, int agility, int endurance,int intelligence, int charisma, int willpower){
        this.pirateId = pirateId;
        this.name = name;
        this.alias = alias;
        this.role = role;
        this.strength = strength;
        this.agility = agility;
        this.endurance = endurance;
        this.intelligence = intelligence;
        this.charisma = charisma;
        this.willpower = willpower;
    }

    public int getPirateId(){
        return pirateId;
    }

    public void setPirateId(int pirateId){
        this.pirateId = pirateId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getAlias(){
        return alias;
    }

    public void setAlias(String alias){
        this.alias = alias;
    }

    public Role getRole(){
        return role;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public int getStrength(){
        return strength;
    }

    public void setStrength(int strength){
        this.strength = strength;
    }

    public int getAgility(){
        return agility;
    }

    public void setAgility(int agility){
        this.agility = agility;
    }

    public int getEndurance(){
        return endurance;
    }

    public void setEndurance(int endurance){
        this.endurance = endurance;
    }

    public int getIntelligence(){
        return intelligence;
    }

    public void setIntelligence(int intelligence){
        this.intelligence = intelligence;
    }

    public int getCharisma(){
        return charisma;
    }

    public void setCharisma(int charisma){
        this.charisma = charisma;
    }

    public int getWillpower(){
        return willpower;
    }

    public void setWillpower(int willpower){
        this.willpower = willpower;
    }

    public void validatePirateData(Pirate pirate) throws Exception{
        if(pirate.getPirateId() < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }
        if(pirate.getName().length() < 5){
            throw new InvalidDataException("Invalid data for name: too few characters! The name must have at least 5 characters!");
        }

        if(pirate.getStrength() < 10 || pirate.getStrength() > 100){
            throw new InvalidDataException("Invalid data for strength: the value must be between 10 and 100!");
        }
        if(pirate.getAgility() < 10 || pirate.getAgility() > 100){
            throw new InvalidDataException("Invalid data for agility: the value must be between 10 and 100!");
        }
        if(pirate.getEndurance() < 10 || pirate.getEndurance() > 100){
            throw new InvalidDataException("Invalid data for endurance: the value must be between 10 and 100!");
        }
        if(pirate.getIntelligence() < 10 || pirate.getIntelligence() > 100){
            throw new InvalidDataException("Invalid data for intelligence: the value must be between 10 and 100!");
        }
        if(pirate.getCharisma() < 10 || pirate.getCharisma() > 100) {
            throw new InvalidDataException("Invalid data for charisma: the value must be between 10 and 100!");
        }
        if(pirate.getWillpower() < 10 || pirate.getWillpower() > 100){
            throw new InvalidDataException("Invalid data for willpower: the value must be between 10 and 100!");
        }
    }

    public abstract void validateSubclassData() throws Exception;
}
