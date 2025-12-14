import crew.Crew;
import database.InsertQuery;
import database.SelectQuery;
import pirate.Pirate;
import pirate.Role;
import pirateSubclasses.*;

import java.util.EnumMap;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) throws Exception {
        SelectQuery selectSubclassPirate = new SelectQuery();

        Second second = selectSubclassPirate.selectSecond();
        Navigator navigator = selectSubclassPirate.selectNavigator();
        Sniper sniper = selectSubclassPirate.selectSniper();
        Cook cook = selectSubclassPirate.selectCook();
        Archeologist archeologist = selectSubclassPirate.selectArcheologist();
        Doctor doctor = selectSubclassPirate.selectDoctor();
        Musician musician = selectSubclassPirate.selectMusician();
        Shipwright shipwright = selectSubclassPirate.selectShipwright();
        Helmsman helmsman = selectSubclassPirate.selectHelmsman();

        Scanner input = new Scanner(System.in);
        EnumMap<Role, Pirate> crewMembers = new EnumMap<>(Role.class);
        System.out.println("What is your name, captain?");
        String captain = input.nextLine();
        System.out.println("And what is your alias?");
        String alias = input.nextLine();
        System.out.println("How should we name our pirate crew?");
        String crewName = input.nextLine();

        Crew crew = new Crew(1, crewName, captain, crewMembers, alias);
        crew.validateCrewData();

        crew.addCrewMember(second);
        crew.addCrewMember(navigator);
        crew.addCrewMember(sniper);
        crew.addCrewMember(cook);
        crew.addCrewMember(archeologist);
        crew.addCrewMember(doctor);
        crew.addCrewMember(musician);
        crew.addCrewMember(shipwright);
        crew.addCrewMember(helmsman);

        InsertQuery insertQuery = new InsertQuery();
        insertQuery.insertCrewData(crew);

        crew.printCrew();

    }
}