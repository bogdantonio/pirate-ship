import java.util.EnumMap;
import pirate.*;
import pirateSubclasses.*;

public class CrewDemo {
    public static void main(String[] args) throws Exception {
        EnumMap<Role, Pirate> crewMembers = new EnumMap<>(Role.class);
        Crew myCrew = new Crew(1, "The Strawhats", "Luffy",  crewMembers);
        myCrew.validateCrewData();

        Second second = new Second(131, "Zoro", "Pirate Hunter", Role.Second, 88, 84, 89, 78, 72, 93, 31, 82, 88, 90);
        second.validatePirateData(); second.validateSubclassData();
        Navigator navigator = new Navigator(231, "Nami", "The thief cat", Role.Navigator, 54, 81, 46, 92, 95, 74,31, 86, 92, 93);
        navigator.validatePirateData(); navigator.validateSubclassData();
        Sniper sniper = new Sniper(331, "Usopp", "Sogeking", Role.Sniper, 41, 77, 52, 86, 68, 82, 31, 98, 96, 17);
        sniper.validatePirateData(); sniper.validateSubclassData();
        Cook cook = new Cook(431, "Sanji", "Curly Eyebrow", Role.Cook, 82, 92, 71,72, 88, 91, 31, 99, 99, 92);
        cook.validatePirateData(); cook.validateSubclassData();
        Doctor doctor = new Doctor(531, "Chopper", "Cotton Lover", Role.Doctor, 48, 74, 62, 94, 88, 90, 31, 97, 89, 96);
        doctor.validatePirateData(); doctor.validateSubclassData();
        Archeologist archeologist = new Archeologist(631, "Robin", "Demon Child", Role.Archeologist, 52, 78, 59, 98, 84, 87, 31, 99, 56, 74);
        archeologist.validatePirateData(); archeologist.validateSubclassData();
        Shipwright shipwright = new Shipwright(731, "Franky", "The Cyborg", Role.Shipwright, 94, 72, 96, 79, 74, 89, 31, 93, 97, 89);
        shipwright.validatePirateData(); shipwright.validateSubclassData();
        Musician musician = new Musician(831, "Brook", "Soul King", Role.Musician, 57, 93, 61, 82, 97, 85, 31, 94, 86, 72);
        musician.validatePirateData(); musician.validateSubclassData();
        Helmsman helmsman = new Helmsman(931, "Jinbe", "First Son of the Sea", Role.Helmsman, 90, 62, 95, 78, 83, 94, 31, 89, 89, 91);
        helmsman.validatePirateData(); helmsman.validateSubclassData();

        myCrew.addCrewMember(second);
        myCrew.addCrewMember(navigator);

        myCrew.printCrew();
        if (myCrew.fullCrew()) {
            System.out.println("The pirate crew is full!\n");
        } else {
            System.out.println("Members can still be added to the crew!\n");
        }

        myCrew.addCrewMember(sniper);
        myCrew.addCrewMember(cook);
        myCrew.addCrewMember(doctor);
        myCrew.addCrewMember(archeologist);
        myCrew.addCrewMember(shipwright);
        myCrew.addCrewMember(musician);
        myCrew.addCrewMember(helmsman);

        myCrew.printCrew();
        if (myCrew.fullCrew()) {
            System.out.println("The pirate crew is full!\n");
        } else {
            System.out.println("Members can still be added to the crew!\n");
        }

        System.out.println(myCrew.crewName + " crew power is: " + myCrew.getCrewPower());

    }
}
