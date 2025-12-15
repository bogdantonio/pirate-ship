import adventure.Adventure;
import crew.Crew;
import database.InsertQuery;
import database.SelectQuery;
import events.EventSet;
import pirateSubclasses.pirate.Pirate;
import pirateSubclasses.pirate.Role;
import pirateSubclasses.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) throws Exception {
        SelectQuery selectQuery = new SelectQuery();

        // EVENTS part
        EventSet eventSet = selectQuery.selectEventSet();
        eventSet.validateEventSetData();

        // CREW part
        ArrayList<Second> seconds = new ArrayList<>();
        seconds.add(selectQuery.selectSecond());
        seconds.add(selectQuery.selectSecond());
        seconds.add(selectQuery.selectSecond());

        ArrayList<Navigator> navigators = new ArrayList<>();
        navigators.add(selectQuery.selectNavigator());
        navigators.add(selectQuery.selectNavigator());
        navigators.add(selectQuery.selectNavigator());

        ArrayList<Sniper> snipers = new ArrayList<>();
        snipers.add(selectQuery.selectSniper());
        snipers.add(selectQuery.selectSniper());
        snipers.add(selectQuery.selectSniper());

        ArrayList<Cook> cooks = new ArrayList<>();
        cooks.add(selectQuery.selectCook());
        cooks.add(selectQuery.selectCook());
        cooks.add(selectQuery.selectCook());

        ArrayList<Doctor> doctors = new ArrayList<>();
        doctors.add(selectQuery.selectDoctor());
        doctors.add(selectQuery.selectDoctor());
        doctors.add(selectQuery.selectDoctor());

        ArrayList<Archeologist> archeologists = new ArrayList<>();
        archeologists.add(selectQuery.selectArcheologist());
        archeologists.add(selectQuery.selectArcheologist());
        archeologists.add(selectQuery.selectArcheologist());

        ArrayList<Shipwright> shipwrights = new ArrayList<>();
        shipwrights.add(selectQuery.selectShipwright());
        shipwrights.add(selectQuery.selectShipwright());
        shipwrights.add(selectQuery.selectShipwright());

        ArrayList<Musician> musicians = new ArrayList<>();
        musicians.add(selectQuery.selectMusician());
        musicians.add(selectQuery.selectMusician());
        musicians.add(selectQuery.selectMusician());

        ArrayList<Helmsman> helmsmen = new ArrayList<>();
        helmsmen.add(selectQuery.selectHelmsman());
        helmsmen.add(selectQuery.selectHelmsman());
        helmsmen.add(selectQuery.selectHelmsman());

        Scanner input = new Scanner(System.in);
        EnumMap<Role, Pirate> crewMembers = new EnumMap<>(Role.class);
        System.out.println("What is your name, captain?");
        String captain = input.nextLine();
        System.out.println("And what is your alias?");
        String alias = input.nextLine();
        System.out.println("How should we name our pirate crew?");
        String crewName = input.nextLine();

        int crewId = selectQuery.selectLastCrewId() + 1;
        Crew crew = new Crew(crewId, crewName, captain, crewMembers, alias);
        crew.validateCrewData();

        System.out.println("Time to choose the pirateSubclasses.pirate crew!");

        System.out.println("Who will have the honor to be your second?");
        int secondIterator = 1;
        for(Second second: seconds){
            // display the stats for each second
            System.out.println(secondIterator++);
            second.printSubclassStats();
        }
        System.out.println("Who will you pick? (1/2/3)");
        int secondPick = input.nextInt();
        crew.addCrewMember(seconds.get(secondPick-1));

        System.out.println("\nWho will chart your course?");
        int navigatorIterator = 1;
        for (Navigator nav : navigators) {
            System.out.println(navigatorIterator++);
            nav.printSubclassStats();
        }
        System.out.println("Who will you pick? (1/2/3)");
        int navigatorPick = input.nextInt();
        crew.addCrewMember(navigators.get(navigatorPick - 1));

        System.out.println("\nWho will watch your back from a distance?");
        int sniperIterator = 1;
        for (Sniper snipe : snipers) {
            System.out.println(sniperIterator++);
            snipe.printSubclassStats();
        }
        System.out.println("Who will you pick? (1/2/3)");
        int sniperPick = input.nextInt();
        crew.addCrewMember(snipers.get(sniperPick - 1));

        System.out.println("\nWho will keep the crew fed and happy?");
        int cookIterator = 1;
        for (Cook c : cooks) {
            System.out.println(cookIterator++);
            c.printSubclassStats();
        }
        System.out.println("Who will you pick? (1/2/3)");
        int cookPick = input.nextInt();
        crew.addCrewMember(cooks.get(cookPick - 1));

        System.out.println("\nWho will uncover the secrets of the past?");
        int archeologistIterator = 1;
        for (Archeologist arch : archeologists) {
            System.out.println(archeologistIterator++);
            arch.printSubclassStats();
        }
        System.out.println("Who will you pick? (1/2/3)");
        int archeologistPick = input.nextInt();
        crew.addCrewMember(archeologists.get(archeologistPick - 1));

        System.out.println("\nWho will patch up your wounds?");
        int doctorIterator = 1;
        for (Doctor doc : doctors) {
            System.out.println(doctorIterator++);
            doc.printSubclassStats();
        }
        System.out.println("Who will you pick? (1/2/3)");
        int doctorPick = input.nextInt();
        crew.addCrewMember(doctors.get(doctorPick - 1));

        System.out.println("\nWho will keep the ship afloat?");
        int shipwrightIterator = 1;
        for (Shipwright ship : shipwrights) {
            System.out.println(shipwrightIterator++);
            ship.printSubclassStats();
        }
        System.out.println("Who will you pick? (1/2/3)");
        int shipwrightPick = input.nextInt();
        crew.addCrewMember(shipwrights.get(shipwrightPick - 1));

        System.out.println("\nWho will keep the morale high?");
        List<Musician> musicianList = musicians.subList(0, 3);
        int musicianIterator = 1;
        for (Musician music : musicianList) {
            System.out.println(musicianIterator++);
            music.printSubclassStats();
        }
        System.out.println("Who will you pick? (1/2/3)");
        int musicianPick = input.nextInt();
        crew.addCrewMember(musicianList.get(musicianPick - 1));

        System.out.println("\nWho will steer the ship through the storm?");
        int helmsmanIterator = 1;
        for (Helmsman helm : helmsmen) {
            System.out.println(helmsmanIterator++);
            helm.printSubclassStats();
        }
        System.out.println("Who will you pick? (1/2/3)");
        int helmsmanPick = input.nextInt();
        crew.addCrewMember(helmsmen.get(helmsmanPick - 1));

        InsertQuery insertQuery = new InsertQuery();
        insertQuery.insertCrewData(crew);

        crew.printCrew();
        System.out.println("\nCrew power: " + crew.getCrewPower());

        int adventureId = selectQuery.selectLastAdventureId() + 1;
        Adventure adventure = new Adventure(adventureId, eventSet, crew);
        adventure.runAdventure();

        insertQuery.insertAdventureData(adventure);
    }
}