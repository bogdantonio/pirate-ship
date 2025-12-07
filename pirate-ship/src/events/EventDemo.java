package events;

import enemy.Enemy;
import enemy.Faction;
import pirate.Role;

import java.util.ArrayList;

public class EventDemo{
    public static void main (String[] args) throws Exception {
        Enemy enemy1 = new Enemy(1, "Borsalino", "Kizaru", 87, Faction.MARINES, "M");
        Enemy enemy2 = new Enemy(2, "Marshall D. Teach", "Blackbeard", 90, Faction.PIRATES, "M");
        Enemy enemy3 = new Enemy(3, "Boa Hancock", "Pirate Empress", 75, Faction.PIRATES, "F");

        EnemyEvent enemyEvent1 = new EnemyEvent(1, "A marine warship appears at the Horizon!", enemy1);
        EnemyEvent enemyEvent2 = new EnemyEvent(2, "The sea darkens as a pirate flag appears at the horizon!", enemy2);
        EnemyEvent enemyEvent3 = new EnemyEvent(3, "A pirate ship is trailing your crew. Beware!", enemy3);

        PirateSubclassEvent pirateSubclassEvent1 = new PirateSubclassEvent(1, Role.COOK,
                "The crew is hungry! You don't have that many ingredients at hand so you must do what you can!", 60, 65, 72 );
        PirateSubclassEvent pirateSubclassEvent2 = new PirateSubclassEvent(2, Role.HELMSMAN,
                "A storm is winding upon the sea. Big waves are heading your way! You must avoid them!", 78, 82, 82);

        Event event1 = new Event(1, EventType.ENEMY, enemyEvent1, null);
        Event event2 = new Event(2, EventType.SUBCLASS, null, pirateSubclassEvent1);
        Event event3 = new Event(3, EventType.ENEMY, enemyEvent2, null);
        Event event4 = new Event(4, EventType.SUBCLASS, null, pirateSubclassEvent2);
        Event event5 = new Event(5, EventType.ENEMY, enemyEvent3, null);

        ArrayList<Event> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);

        EventSet eventSet = new EventSet(1, events);
        eventSet.validateEventSetData();
        eventSet.printEvents();
    }
}

