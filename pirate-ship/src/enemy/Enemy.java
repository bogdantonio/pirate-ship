package enemy;

import pirate.InvalidDataException;

public class Enemy {
   public int enemyId;
   public String name;
   public String alias;
   public Faction faction;
   public double power;

    public Enemy(int enemyId, double power, Faction faction, String alias, String name) {
        this.enemyId = enemyId;
        this.power = power;
        this.faction = faction;
        this.alias = alias;
        this.name = name;
    }

    public int getEnemyId() {
        return enemyId;
    }

    public void setEnemyId(int enemyId) {
        this.enemyId = enemyId;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    void validateEnemyData() throws Exception {
        if (enemyId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }
        if(name.length() < 3){
            throw new InvalidDataException("Invalid data for name: too few characters! The name must have at least 5 characters!");
        }

        if(power < 0 || power > 100){
            throw new InvalidDataException("Invalid data for power: out of bounds! The interval is [0, 100]");
        }
    }
}
