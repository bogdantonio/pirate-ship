package crew;

import pirate.InvalidDataException;
import pirate.Pirate;
import pirate.PirateStatSet;
import pirate.Role;

import java.util.EnumMap;

public class Crew {
    private int crewId;
    private String crewName;
    private String captain;
    private double crewPower; // updated each time a new crew member is introduced
    // map the pirate to the enum type since I want one of each pirate subclass
    private EnumMap<Role, Pirate> crewMembers;

    public Crew(int crewId, String crewName, String captain, EnumMap<Role, Pirate> crewMembers) {
        this.crewId = crewId;
        this.crewName = crewName;
        this.captain = captain;
        this.crewMembers = new EnumMap<>(Role.class);
    }

    public int getCrewId() {
        return crewId;
    }

    public void setCrewId(int crewId) {
        this.crewId = crewId;
    }

    public String getCrewName() {
        return crewName;
    }

    public void setCrewName(String crewName) {
        this.crewName = crewName;
    }

    public String getCaptain() {
        return captain;
    }

    public void setCaptain(String captain) {
        this.captain = captain;
    }

    public double getCrewPower() {
        return crewPower;
    }

    public double setCrewPower(){
        double cumulativePower = 0;
        for (Pirate p : crewMembers.values()) {
            // the average of the stats for a pirate
            PirateStatSet pss = p.getPirateStatSet();
            double avg = (pss.getStrength() + pss.getAgility() + pss.getEndurance() +
                    pss.getIntelligence() + pss.getCharisma() + pss.getWillpower()) / 6.0;
            cumulativePower += avg;
        }
        // make the average for the whole crew
        return cumulativePower/crewMembers.size();
    }

    public EnumMap<Role, Pirate> getCrewMembers() {
        return crewMembers;
    }

    public void setCrewMembers(EnumMap<Role, Pirate> crewMembers) {
        this.crewMembers = crewMembers;
    }

    public void addCrewMember(Pirate pirate) throws Exception{
        if(this.fullCrew()){
            throw new FullCrewException("Pirates can't be added no more. The pirate crew is full!");
        }

        Role role = pirate.getRole();
        // check if the pirate with the specified role already exists
        if(crewMembers.containsKey(role)){
            throw new ExistingRoleException("A pirate with the role " + " already exists in the crew!");
        }
        crewMembers.put(role, pirate);
        this.crewPower = setCrewPower();
    }

    public boolean fullCrew(){
        return crewMembers.size() == Role.values().length;
    }

    public Pirate getPirate(Role role){
        return crewMembers.get(role);
    }

    public void printCrew(){
        System.out.println(getCrewName());
        System.out.println("Captain: " + getCaptain());
        for(int i = 0; i < crewMembers.size(); i++){
            System.out.println(Role.values()[i] + ": " + getPirate(Role.values()[i]).getName());
        }
    }

    public void validateCrewData() throws Exception{
        if(this.crewId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }

        if(this.captain.length() < 3){
            throw new InvalidDataException("Invalid data for captain: too few characters! The captain must have at least 5 characters!");
        }
        if(this.crewName.length() < 5){
            throw new InvalidDataException("Invalid data for crewName: too few characters! The crewName must have at least 5 characters!");
        }
    }

}
