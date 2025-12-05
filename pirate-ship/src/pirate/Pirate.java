package pirate;

public abstract class Pirate {
    private int pirateId;
    private String name;
    private String alias;
    private Role role;
    private String sex;
    private PirateStatSet pirateStatSet;

    public Pirate(int pirateId, Role role, String alias, String name, String sex, PirateStatSet pirateStatSet) {
        this.pirateId = pirateId;
        this.role = role;
        this.alias = alias;
        this.name = name;
        this.sex = sex;
        this.pirateStatSet = pirateStatSet;
    }

    public int getPirateId(){
        return this.pirateId;
    }

    public void setPirateId(int pirateId){
        this.pirateId = pirateId;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getAlias(){
        return this.alias;
    }

    public void setAlias(String alias){
        this.alias = alias;
    }

    public Role getRole(){
        return this.role;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public PirateStatSet getPirateStatSet() {
        return this.pirateStatSet;
    }

    public void setPirateStatSet(PirateStatSet pirateStatSet) {
        this.pirateStatSet = pirateStatSet;
    }

    public void validatePirateData() throws Exception{
        if(this.pirateId < 0){
            throw new InvalidDataException("Invalid data for pirateId: null values not supported!");
        }
        this.pirateStatSet.validateStatData();

        if(this.name.length() < 3){
            throw new InvalidDataException("Invalid data for name: the length must have more than 3 characters!");
        }
        if(this.sex.length() > 1){
            throw new InvalidDataException("Invalid data for sex: the length must have 1 character: M/F!");
        }

        // role is already of type Role so it is not point in checking for it;
        // also no point in checking for alias since it is an optional field: it might as well be NULL
    }

    public abstract void validateSubclassData() throws Exception;

}
