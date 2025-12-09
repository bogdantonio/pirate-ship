import crew.Crew;
import enemy.Enemy;
import enemy.Faction;
import events.*;
import pirate.Pirate;
import pirate.PirateStatSet;
import pirate.Role;
import pirateSubclasses.*;

import javax.xml.transform.Source;
import java.util.*;

public class AdventureDemo {
    public static void main (String[] args) throws Exception {
        // set up the enemies
        Enemy enemy1 = new Enemy(1, "Borsalino", "Kizaru", 87, Faction.MARINES, "M");
        Enemy enemy2 = new Enemy(2, "Marshall D. Teach", "Blackbeard", 90, Faction.PIRATES, "M");
        Enemy enemy3 = new Enemy(3, "Boa Hancock", "Pirate Empress", 75, Faction.PIRATES, "F");
        Enemy enemy4 = new Enemy(4, "Sakazuki", "Akainu", 95, Faction.MARINES, "M");
        Enemy enemy5 = new Enemy(5, "Charlotte Linlin", "Big Mom", 94, Faction.PIRATES, "F");

        // make new events
        EnemyEvent enemyEvent1 = new EnemyEvent(1, "A marine warship appears at the Horizon!", enemy1);
        EnemyEvent enemyEvent2 = new EnemyEvent(2, "The sea darkens as a pirate flag appears at the horizon!", enemy2);
        EnemyEvent enemyEvent3 = new EnemyEvent(3, "A pirate ship is trailing your crew. Beware!", enemy3);
        EnemyEvent enemyEvent4 = new EnemyEvent(4, "The ocean begins to boil! The Fleet Admiral has arrived to dispense Absolute Justice!", enemy4);
        EnemyEvent enemyEvent5 = new EnemyEvent(5, "A massive singing ship emerges from the fog demanding a toll of sweets or your life!", enemy5);

        PirateSubclassEvent pirateSubclassEvent1 = new PirateSubclassEvent(1, Role.COOK,
                "The crew is hungry! You don't have that many ingredients at hand so you must do what you can!", 60, 65, 72 );
        PirateSubclassEvent pirateSubclassEvent2 = new PirateSubclassEvent(2, Role.HELMSMAN,
                "A storm is winding upon the sea. Big waves are heading your way! You must avoid them!", 78, 82, 82);
        PirateSubclassEvent pirateSubclassEvent3 = new PirateSubclassEvent(3, Role.DOCTOR,
                "A mysterious jungle fever is spreading through the ship! You must synthesize a cure before the captain collapses!", 75, 70, 80);
        PirateSubclassEvent pirateSubclassEvent4 = new PirateSubclassEvent(4, Role.SNIPER,
                "The enemy captain is mocking us from his balcony. Shoot the feather off his hat to ruin his authority!", 85, 88, 90);
        PirateSubclassEvent pirateSubclassEvent5 = new PirateSubclassEvent(5, Role.NAVIGATOR,
                "The Log Pose is spinning wildly! A Knock Up Stream is forming beneath the hull—guide the ship to ride the current!", 80, 85, 75);

        Event event1 = new Event(1, EventType.ENEMY, enemyEvent1, null);
        Event event2 = new Event(2, EventType.SUBCLASS, null, pirateSubclassEvent1);
        Event event3 = new Event(3, EventType.ENEMY, enemyEvent2, null);
        Event event4 = new Event(4, EventType.SUBCLASS, null, pirateSubclassEvent2);
        Event event5 = new Event(5, EventType.ENEMY, enemyEvent3, null);
        Event event6 = new Event(6, EventType.SUBCLASS, null, pirateSubclassEvent3);
        Event event7 = new Event(7, EventType.ENEMY, enemyEvent4, null);
        Event event8 = new Event(8, EventType.SUBCLASS, null, pirateSubclassEvent4);
        Event event9 = new Event(9, EventType.ENEMY, enemyEvent5, null);
        Event event10 = new Event(10, EventType.SUBCLASS, null, pirateSubclassEvent5);

        // make the event set
        ArrayList<Event> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);
        events.add(event6);
        events.add(event7);
        events.add(event8);
        events.add(event9);
        events.add(event10);

        EventSet eventSet = new EventSet(1, events);
        eventSet.validateEventSetData();

        // set up the pirates
        PirateStatSet pirateStatSet1 = new PirateStatSet(1, 88, 89, 88, 82, 78, 90);
        PirateStatSet pirateStatSet2 = new PirateStatSet(2, 69, 95, 49, 88, 89, 76);
        PirateStatSet pirateStatSet3 = new PirateStatSet(3, 67, 96, 57, 72, 85, 84);
        PirateStatSet pirateStatSet4 = new PirateStatSet(4, 94, 92, 88, 90, 82, 96);
        PirateStatSet pirateStatSet5 = new PirateStatSet(5, 95, 60, 98, 50, 40, 90);
        PirateStatSet pirateStatSet6 = new PirateStatSet(6, 45, 90, 40, 95, 92, 60);
        PirateStatSet pirateStatSet7 = new PirateStatSet(7, 75, 75, 75, 75, 75, 75);
        PirateStatSet pirateStatSet8 = new PirateStatSet(8, 99, 99, 99, 99, 99, 99);

        // SECONDS
        Second second1 = new Second(131, Role.SECOND, "Pirate Hunter", "Roronoa Zoro", "M", pirateStatSet4, 1, 89, 77, 94);
        second1.validatePirateData(); second1.validateSubclassData();
        Second second2 = new Second(132, Role.SECOND, "Dark King", "Silvers Rayleigh", "M", pirateStatSet8, 2, 99, 95, 98);
        second2.validatePirateData(); second2.validateSubclassData();
        Second second3 = new Second(133, Role.SECOND, "The Beckman", "Benn Beckman", "M", pirateStatSet4, 3, 95, 98, 90);
        second3.validatePirateData(); second3.validateSubclassData();
        Second second4 = new Second(134, Role.SECOND, "Massacre Soldier", "Killer", "M", pirateStatSet1, 4, 85, 80, 88);
        second4.validatePirateData(); second4.validateSubclassData();
        ArrayList<Second> seconds = new ArrayList<>(); seconds.add(second1); seconds.add(second2); seconds.add(second3); seconds.add(second4);
        Collections.shuffle(seconds);

        // NAVIGATORS
        Navigator navigator1 = new Navigator(231, Role.NAVIGATOR , "Burglar Cat", "Nami", "F", pirateStatSet2, 1, 92, 95, 93);
        navigator1.validatePirateData(); navigator1.validateSubclassData();
        Navigator navigator2 = new Navigator(232, Role.NAVIGATOR , "Demon Sheriff", "Lafitte", "M", pirateStatSet3, 2, 88, 90, 85);
        navigator2.validatePirateData(); navigator2.validateSubclassData();
        Navigator navigator3 = new Navigator(233, Role.NAVIGATOR , "Talking Bear", "Bepo", "M", pirateStatSet5, 3, 75, 80, 88);
        navigator3.validatePirateData(); navigator3.validateSubclassData();
        Navigator navigator4 = new Navigator(234, Role.NAVIGATOR , "Liar", "Mont Blanc Noland", "M", pirateStatSet1, 4, 95, 85, 90);
        navigator4.validatePirateData(); navigator4.validateSubclassData();
        ArrayList<Navigator> navigators = new ArrayList<>(); navigators.add(navigator1); navigators.add(navigator2); navigators.add(navigator3); navigators.add(navigator4);
        Collections.shuffle(navigators);

        // SNIPER
        Sniper sniper1 = new Sniper(331, Role.SNIPER, "Sogeking", "Usopp", "M", pirateStatSet3, 1, 86, 88, 31);
        sniper1.validatePirateData(); sniper1.validateSubclassData();
        Sniper sniper2 = new Sniper(332, Role.SNIPER, "Chaser", "Yasopp", "M", pirateStatSet4, 2, 99, 95, 70);
        sniper2.validatePirateData(); sniper2.validateSubclassData();
        Sniper sniper3 = new Sniper(333, Role.SNIPER, "Supersonic", "Van Augur", "M", pirateStatSet2, 3, 95, 92, 50);
        sniper3.validatePirateData(); sniper3.validateSubclassData();
        Sniper sniper4 = new Sniper(334, Role.SNIPER, "Daddy the Father", "Daddy Masterson", "M", pirateStatSet7, 4, 88, 85, 65);
        sniper4.validatePirateData(); sniper4.validateSubclassData();
        ArrayList<Sniper> snipers = new ArrayList<>(); snipers.add(sniper1); snipers.add(sniper2); snipers.add(sniper3); snipers.add(sniper4);
        Collections.shuffle(snipers);

        // COOKS
        Cook cook1 = new Cook(431, Role.COOK, "Curly Eyebrow", "Sanji", "M", pirateStatSet1, 98, 1, 99, 90);
        cook1.validatePirateData(); cook1.validateSubclassData();
        Cook cook2 = new Cook(432, Role.COOK, "Red Leg", "Zeff", "M", pirateStatSet4, 90, 2, 90, 95);
        cook2.validatePirateData(); cook2.validateSubclassData();
        Cook cook3 = new Cook(433, Role.COOK, "Gourmet Knight", "Streusen", "M", pirateStatSet6, 95, 3, 93, 40);
        cook3.validatePirateData(); cook3.validateSubclassData();
        Cook cook4 = new Cook(434, Role.COOK, "Six-Sword Style", "Hatchan", "M", pirateStatSet5, 80, 4, 75, 85);
        cook4.validatePirateData(); cook4.validateSubclassData();
        ArrayList<Cook> cooks = new ArrayList<>(); cooks.add(cook1); cooks.add(cook2); cooks.add(cook3); cooks.add(cook4);
        Collections.shuffle(cooks);

        // DOCTORS
        Doctor doctor1 = new Doctor(531, Role.DOCTOR, "Cotton Lover", "Chopper", "M", pirateStatSet3, 1, 94, 88, 90);
        doctor1.validatePirateData(); doctor1.validateSubclassData();
        Doctor doctor2 = new Doctor(532, Role.DOCTOR, "Surgeon of Death", "Trafalgar Law", "M", pirateStatSet4, 2, 99, 95, 92);
        doctor2.validatePirateData(); doctor2.validateSubclassData();
        Doctor doctor3 = new Doctor(533, Role.DOCTOR, "Crocus", "Crocus", "M", pirateStatSet7, 3, 90, 85, 88);
        doctor3.validatePirateData(); doctor3.validateSubclassData();
        Doctor doctor4 = new Doctor(534, Role.DOCTOR, "Witch", "Kureha", "F", pirateStatSet2, 4, 98, 80, 95);
        doctor4.validatePirateData(); doctor4.validateSubclassData();
        ArrayList<Doctor> doctors = new ArrayList<>(); doctors.add(doctor1); doctors.add(doctor2); doctors.add(doctor3); doctors.add(doctor4);
        Collections.shuffle(doctors);

        // ARCHEOLOGISTS
        Archeologist archeologist1 = new Archeologist(631, Role.ARCHEOLOGIST, "Demon Child", "Nico Robin", "F", pirateStatSet2, 1, 98, 84, 99);
        archeologist1.validatePirateData(); archeologist1.validateSubclassData();
        Archeologist archeologist2 = new Archeologist(632, Role.ARCHEOLOGIST, "Scholar", "Clover", "M", pirateStatSet6, 2, 99, 40, 95);
        archeologist2.validatePirateData(); archeologist2.validateSubclassData();
        Archeologist archeologist3 = new Archeologist(633, Role.ARCHEOLOGIST, "Kozuki", "Oden", "M", pirateStatSet8, 3, 70, 95, 50);
        archeologist3.validatePirateData(); archeologist3.validateSubclassData();
        Archeologist archeologist4 = new Archeologist(634, Role.ARCHEOLOGIST, "Mother", "Nico Olvia", "F", pirateStatSet6, 4, 90, 50, 90);
        archeologist4.validatePirateData(); archeologist4.validateSubclassData();
        ArrayList<Archeologist> archeologists = new ArrayList<>(); archeologists.add(archeologist1); archeologists.add(archeologist2); archeologists.add(archeologist3); archeologists.add(archeologist4);
        Collections.shuffle(archeologists);

        // SHIPWRIGHTS
        Shipwright shipwright1 = new Shipwright(731, Role.SHIPWRIGHT, "Cyborg", "Franky", "M", pirateStatSet4, 1, 89, 84, 89);
        shipwright1.validatePirateData(); shipwright1.validateSubclassData();
        Shipwright shipwright2 = new Shipwright(732, Role.SHIPWRIGHT, "Mayor", "Iceburg", "M", pirateStatSet7, 2, 95, 90, 80);
        shipwright2.validatePirateData(); shipwright2.validateSubclassData();
        Shipwright shipwright3 = new Shipwright(733, Role.SHIPWRIGHT, "Legendary Builder", "Tom", "M", pirateStatSet5, 3, 99, 99, 90);
        shipwright3.validatePirateData(); shipwright3.validateSubclassData();
        Shipwright shipwright4 = new Shipwright(734, Role.SHIPWRIGHT, "Rope Master", "Paulie", "M", pirateStatSet3, 4, 85, 80, 75);
        shipwright4.validatePirateData(); shipwright4.validateSubclassData();
        ArrayList<Shipwright> shipwrights = new ArrayList<>(); shipwrights.add(shipwright1); shipwrights.add(shipwright2); shipwrights.add(shipwright3); shipwrights.add(shipwright4);
        Collections.shuffle(shipwrights);

        // MUSICIANS
        Musician musician1 = new Musician(831, Role.MUSICIAN, "Soul King", "Brook", "M", pirateStatSet1, 1, 82, 97, 69);
        musician1.validatePirateData(); musician1.validateSubclassData();
        Musician musician2 = new Musician(832, Role.MUSICIAN, "Roar of the Sea", "Scratchmen Apoo", "M", pirateStatSet3, 2, 90, 80, 85);
        musician2.validatePirateData(); musician2.validateSubclassData();
        Musician musician3 = new Musician(833, Role.MUSICIAN, "Diva", "Uta", "F", pirateStatSet6, 3, 99, 99, 50);
        musician3.validatePirateData(); musician3.validateSubclassData();
        Musician musician4 = new Musician(834, Role.MUSICIAN, "Calico", "Yorki", "M", pirateStatSet7, 4, 80, 85, 75);
        musician4.validatePirateData(); musician4.validateSubclassData();
        ArrayList<Musician> musicians = new ArrayList<>(); musicians.add(musician1); musicians.add(musician2); musicians.add(musician3); musicians.add(musician4);
        Collections.shuffle(musicians);

        // HELMSMEN
        Helmsman helmsman1 = new Helmsman(931, Role.HELMSMAN, "First Son of the Sea", "Jinbe", "M", pirateStatSet1, 1, 88, 83, 94);
        helmsman1.validatePirateData(); helmsman1.validateSubclassData();
        Helmsman helmsman2 = new Helmsman(932, Role.HELMSMAN, "Champion", "Jesus Burgess", "M", pirateStatSet5, 2, 85, 75, 80);
        helmsman2.validatePirateData(); helmsman2.validateSubclassData();
        Helmsman helmsman3 = new Helmsman(933, Role.HELMSMAN, "Giant", "Wadatsumi", "M", pirateStatSet5, 3, 70, 60, 95);
        helmsman3.validatePirateData(); helmsman3.validateSubclassData();
        Helmsman helmsman4 = new Helmsman(934, Role.HELMSMAN, "Slave", "Jean Bart", "M", pirateStatSet5, 4, 80, 70, 85);
        helmsman4.validatePirateData(); helmsman4.validateSubclassData();
        ArrayList<Helmsman> helmsmen = new ArrayList<>(); helmsmen.add(helmsman1); helmsmen.add(helmsman2); helmsmen.add(helmsman3); helmsmen.add(helmsman4);
        Collections.shuffle(helmsmen);

        // from here on, to build the crew:
        // select a pirate for a role. 3 random pirates will be displayed and the player must choose 1
        // after the crew is built, run the adventure
        Scanner input = new Scanner(System.in);
        EnumMap<Role, Pirate> crewMembers = new EnumMap<>(Role.class);
        System.out.println("What is your name, captain?");
        String captain = input.nextLine();
        System.out.println("And what is your alias?");
        String alias = input.nextLine();
        System.out.println("How should we name our pirate crew?");
        String crewName = input.nextLine();
        Crew myCrew = new Crew(1, crewName, captain, crewMembers, alias);
        myCrew.validateCrewData();

        System.out.println("Time to choose the pirate crew!");
        // choose crew members
        System.out.println("Who will have the honor to be your second?");
        List<Second> secondList = seconds.subList(0, 3); // take the 3 random seconds
        int secondIterator = 1;
        for(Second second: secondList){
            // display the stats for each second
            System.out.println(secondIterator++);
            second.printSubclassStats();
        }
        System.out.println("Who will you pick? (1/2/3)");
        int secondPick = input.nextInt();
        myCrew.addCrewMember(secondList.get(secondPick-1));

        System.out.println("\nWho will chart your course?");
        List<Navigator> navigatorList = navigators.subList(0, 3);
        int navigatorIterator = 1;
        for (Navigator nav : navigatorList) {
            System.out.println(navigatorIterator++);
            nav.printSubclassStats();
        }
        System.out.println("Who will you pick? (1/2/3)");
        int navigatorPick = input.nextInt();
        myCrew.addCrewMember(navigatorList.get(navigatorPick - 1));

        System.out.println("\nWho will watch your back from a distance?");
        List<Sniper> sniperList = snipers.subList(0, 3);
        int sniperIterator = 1;
        for (Sniper snipe : sniperList) {
            System.out.println(sniperIterator++);
            snipe.printSubclassStats();
        }
        System.out.println("Who will you pick? (1/2/3)");
        int sniperPick = input.nextInt();
        myCrew.addCrewMember(sniperList.get(sniperPick - 1));

        System.out.println("\nWho will keep the crew fed and happy?");
        List<Cook> cookList = cooks.subList(0, 3);
        int cookIterator = 1;
        for (Cook c : cookList) {
            System.out.println(cookIterator++);
            c.printSubclassStats();
        }
        System.out.println("Who will you pick? (1/2/3)");
        int cookPick = input.nextInt();
        myCrew.addCrewMember(cookList.get(cookPick - 1));

        System.out.println("\nWho will patch up your wounds?");
        List<Doctor> doctorList = doctors.subList(0, 3);
        int doctorIterator = 1;
        for (Doctor doc : doctorList) {
            System.out.println(doctorIterator++);
            doc.printSubclassStats();
        }
        System.out.println("Who will you pick? (1/2/3)");
        int doctorPick = input.nextInt();
        myCrew.addCrewMember(doctorList.get(doctorPick - 1));

        System.out.println("\nWho will steer the ship through the storm?");
        List<Helmsman> helmsmanList = helmsmen.subList(0, 3);
        int helmsmanIterator = 1;
        for (Helmsman helm : helmsmanList) {
            System.out.println(helmsmanIterator++);
            helm.printSubclassStats();
        }
        System.out.println("Who will you pick? (1/2/3)");
        int helmsmanPick = input.nextInt();
        myCrew.addCrewMember(helmsmanList.get(helmsmanPick - 1));

        System.out.println("\nWho will keep the morale high?");
        List<Musician> musicianList = musicians.subList(0, 3);
        int musicianIterator = 1;
        for (Musician music : musicianList) {
            System.out.println(musicianIterator++);
            music.printSubclassStats();
        }
        System.out.println("Who will you pick? (1/2/3)");
        int musicianPick = input.nextInt();
        myCrew.addCrewMember(musicianList.get(musicianPick - 1));

        System.out.println("\nWho will keep the ship afloat?");
        List<Shipwright> shipwrightList = shipwrights.subList(0, 3);
        int shipwrightIterator = 1;
        for (Shipwright ship : shipwrightList) {
            System.out.println(shipwrightIterator++);
            ship.printSubclassStats();
        }
        System.out.println("Who will you pick? (1/2/3)");
        int shipwrightPick = input.nextInt();
        myCrew.addCrewMember(shipwrightList.get(shipwrightPick - 1));

        System.out.println("\nWho will uncover the secrets of the past?");
        List<Archeologist> archeologistList = archeologists.subList(0, 3);
        int archeologistIterator = 1;
        for (Archeologist arch : archeologistList) {
            System.out.println(archeologistIterator++);
            arch.printSubclassStats();
        }
        System.out.println("Who will you pick? (1/2/3)");
        int archeologistPick = input.nextInt();
        myCrew.addCrewMember(archeologistList.get(archeologistPick - 1));

        // print the whole crew after the members have been chosen
        myCrew.printCrew();
        System.out.println("\nCrew power: " + myCrew.getCrewPower() + '\n');

        // run the adventure
        Adventure adventure = new Adventure(1, eventSet, myCrew);
        adventure.runAdventure();
    }
}
