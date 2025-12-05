package crew;

import java.util.EnumMap;

import pirate.Pirate;
import pirate.PirateStatSet;
import pirate.Role;
import pirateSubclasses.*;

public class CrewDemo {
    public static void main(String[] args) throws Exception {
        EnumMap<Role, Pirate> crewMembers = new EnumMap<>(Role.class);
        Crew myCrew = new Crew(1, "The Strawhats", "Luffy",  crewMembers);
        myCrew.validateCrewData();

        PirateStatSet pirateStatSet1 = new PirateStatSet(1, 88, 89, 88, 82, 78, 90);
        PirateStatSet pirateStatSet2 = new PirateStatSet(2, 69, 95, 49, 88, 89, 76);
        PirateStatSet pirateStatSet3 = new PirateStatSet(3, 67, 96, 57, 72, 85, 84);
        PirateStatSet pirateStatSet4 = new PirateStatSet(4, 94, 92, 88, 90, 82, 96);


        Second second = new Second(131, Role.SECOND, "Pirate Hunter", "Roronoa Zoro", "M", pirateStatSet4, 1, 89, 77, 94);
        second.validatePirateData(); second.validateSubclassData();
        Navigator navigator = new Navigator(231, Role.NAVIGATOR , "Burglar Cat", "Nami", "F", pirateStatSet2, 1, 92, 95,  93);
        navigator.validatePirateData(); navigator.validateSubclassData();
        Sniper sniper = new Sniper(331, Role.SNIPER, "Sogeking", "Usopp", "M", pirateStatSet3, 1, 86, 88, 31);
        sniper.validatePirateData(); sniper.validateSubclassData();
        Cook cook = new Cook(431, Role.COOK, "Curly Eyebrow", "Sanji", "M", pirateStatSet1, 90,1, 98, 99);
        cook.validatePirateData(); cook.validateSubclassData();
        Doctor doctor = new Doctor(531, Role.DOCTOR, "Cotton Lover", "Chopper", "M", pirateStatSet3, 1, 94, 88, 90);
        doctor.validatePirateData(); doctor.validateSubclassData();
        Archeologist archeologist = new Archeologist(631, Role.ARCHEOLOGIST, "Demon Child", "Nico Robin", "F", pirateStatSet2, 1, 98, 84, 99);
        archeologist.validatePirateData(); archeologist.validateSubclassData();
        Shipwright shipwright = new Shipwright(731, Role.SHIPWRIGHT, "Cyborg", "Franky", "M", pirateStatSet4, 1, 89, 84, 89);
        shipwright.validatePirateData(); shipwright.validateSubclassData();
        Musician musician = new Musician(831, Role.MUSICIAN, "Soul King", "Brook", "M", pirateStatSet1, 1, 82, 97, 69);
        musician.validatePirateData(); musician.validateSubclassData();
        Helmsman helmsman = new Helmsman(931, Role.HELMSMAN, "First Son of the Sea", "Jinbe", "M", pirateStatSet1, 1, 88, 83, 94);
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

        System.out.println(myCrew.getCrewName() + " crew power is: " + myCrew.getCrewPower());

    }
}
