package enemy;

import pirate.InvalidDataException;

public class Enemy {
    private int enemyId;
    private String name;
    private String alias;
    private double power;
    private Faction faction;
    private String sex;

    public Enemy(int enemyId, String name, String alias, double power, Faction faction, String sex) {
        this.enemyId = enemyId;
        this.name = name;
        this.alias = alias;
        this.power = power;
        this.faction = faction;
        this.sex = sex;
    }

    public int getEnemyId() {
        return enemyId;
    }

    public void setEnemyId(int enemyId) {
        this.enemyId = enemyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void printEnemy(){
        System.out.println("Enemy: " + this.name + " \"" + this.alias +"\"" + " | " + this.faction + " | Power: " + this.power);
    }

    public void validateEnemyData() throws Exception {
        if (this.enemyId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }

        if(this.name.length() < 3){
            throw new InvalidDataException("Invalid data for name: too few characters! The name must have at least 5 characters!");
        }
        if(this.sex.length() > 1){
            throw new InvalidDataException("Invalid data for sex: the length must have 1 character: M/F!");
        }

        if(this.power < 0 || this.power > 100){
            throw new InvalidDataException("Invalid data for power: out of bounds! The interval is [0, 100]");
        }
    }
}
