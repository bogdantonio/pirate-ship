import java.util.EnumMap;
import pirate.*;
import pirateSubclasses.*;

public class Crew {
    public int crewId;
    public String crewName;
    public String captain;
    // map the pirate to the enum type since I want one of each pirate subclass
    public EnumMap<Role, Pirate> crewMembers;

    public Crew(int crewId, String crewName, String captain, EnumMap<Role, Pirate> crewMembers){
        this.crewId = crewId;
        this.crewName = crewName;
        this.captain = captain;
        this.crewMembers = new EnumMap<>(Role.class);
    }

    public int getCrewId(){
        return crewId;
    }

    public void setCrewId(int crewId){
        this.crewId = crewId;
    }

    public String getCrewName(){
        return crewName;
    }

    public void setCrewName(String crewName){
        this.crewName = crewName;
    }

    public String getCaptain(){
        return captain;
    }

    public void setCaptain(String captain){
        this.captain = captain;
    }

    public void addCrewMember(Pirate pirate) throws Exception{
        Role role = pirate.getRole();
        // check if the pirate with the specified role already exists
        if(crewMembers.containsKey(role)){
            throw new ExistingRoleException("A pirate with the role " + " already exists in the crew!");
        }
        crewMembers.put(role, pirate);
    }

    public Pirate getPirate(Role role){
        return crewMembers.get(role);
    }

    public boolean fullCrew(){
        return crewMembers.size() == Role.values().length;
    }

    public void printCrew(){
        System.out.println(getCrewName());
        System.out.println("Captain: " + getCaptain());
        for(int i = 0; i < Role.values().length; i++){
            System.out.println(Role.values()[i].toString() + ": " + getPirate(Role.values()[i]).getName());
        }
    }

    public void validateCrewData() throws Exception{
        if(crewId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }

        if(captain.length() < 5){
            throw new InvalidDataException("Invalid data for captain: too few characters! The captain must have at least 5 characters!");
        }
    }

}
