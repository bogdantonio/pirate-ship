package database;

import events.enemy.Enemy;
import events.enemy.Faction;
import events.*;
import pirateSubclasses.pirate.PirateStatSet;
import pirateSubclasses.pirate.Role;
import pirateSubclasses.*;

import java.sql.*;
import java.util.ArrayList;

public class SelectQuery {
    DataBaseCredentials DBC = new DataBaseCredentials();

    public EventSet selectEventSet() throws Exception {
        ArrayList<Event> events = new ArrayList<>();
        ArrayList<Integer> eventIds = new ArrayList<>();
        int eventSetId = 0;

        String setsQuery = "SELECT * FROM public.event_sets order by random() limit 1";

        Connection connection = DriverManager.getConnection(DBC.url, DBC.user, DBC.password);
        PreparedStatement setsStatement = connection.prepareStatement(setsQuery);
        ResultSet rsSets = setsStatement.executeQuery();

        // extract the id of each event that makes up the event set
        if(rsSets.next()){
            eventSetId = rsSets.getInt("event_set_id");

            for(int i = 1; i <= 10; i++){
                eventIds.add(rsSets.getInt("event" + i + "_id"));
            }
        }

        if(eventIds.isEmpty()){
            return null;
        }

        // take the data of the events in the event set
        String eventsQuery = "SELECT e.event_id, e.event_type, " +
                "ee.enemy_event_id, ee.prompt AS enemy_prompt, " +
                "se.subclass_event_id, se.prompt AS subclass_prompt, " +
                "en.enemy_id, en.name AS enemy_name, en.alias AS enemy_alias, en.power, en.faction, en.sex, " +
                "se.subclass, se.required_stat1, se.required_stat2, se.required_stat3 " +
                "FROM public.events e " +
                "LEFT JOIN public.enemies_events ee ON e.enemy_event_id = ee.enemy_event_id " +
                "LEFT JOIN public.enemies en ON ee.enemy_id = en.enemy_id " +
                "LEFT JOIN public.subclass_events se ON e.subclass_event_id = se.subclass_event_id " +
                "WHERE e.event_id IN (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement eventsStatement = connection.prepareStatement(eventsQuery);
        for (int i = 0; i < eventIds.size(); i++) {
            eventsStatement.setInt(i + 1, eventIds.get(i));
        }
        ResultSet rsEvents = eventsStatement.executeQuery();

        while(rsEvents.next()){
            int eventId = rsEvents.getInt("event_id");
            // take the type of the event to later create the new event object appropriately
            String typeStr = rsEvents.getString("event_type");

            // event wrapper to take the new event of either type and add it to the events
            Event eventWrapper = null;

            if ("ENEMY".equalsIgnoreCase(typeStr)) {
                // here fix the faction text since it may be multiple words in some cases
                String rawFaction = rsEvents.getString("faction");
                String fixedFaction = rawFaction.trim().replace(" ", "_").toUpperCase();
                // build the events.enemy object
                Enemy enemy = new Enemy(
                        rsEvents.getInt("enemy_id"),
                        rsEvents.getString("enemy_name"),
                        rsEvents.getString("enemy_alias"),
                        rsEvents.getInt("power"),
                        Faction.valueOf(fixedFaction),
                        rsEvents.getString("sex")
                );

                // make the events.enemy event
                EnemyEvent enemyEvent = new EnemyEvent(
                        rsEvents.getInt("enemy_event_id"),
                        rsEvents.getString("enemy_prompt"),
                        enemy
                );

                // give the events.enemy event to the wrapper
                eventWrapper = new Event(eventId, EventType.ENEMY, enemyEvent, null);

            } else if ("SUBCLASS".equalsIgnoreCase(typeStr)) {
                // build the pirateSubclassEvent
                PirateSubclassEvent subclassEvent = new PirateSubclassEvent(
                        rsEvents.getInt("subclass_event_id"),
                        Role.valueOf(rsEvents.getString("subclass")),
                        rsEvents.getString("subclass_prompt"),
                        rsEvents.getInt("required_stat1"),
                        rsEvents.getInt("required_stat2"),
                        rsEvents.getInt("required_stat3")
                );

                // wrap it in the main event class
                eventWrapper = new Event(eventId, EventType.SUBCLASS, null, subclassEvent);
            }

            if (eventWrapper != null) {
                events.add(eventWrapper);
            }
        }

        return new EventSet(eventSetId, events);
    }

    public int selectLastCrewId() throws SQLException{
        String query = "SELECT crew_id FROM public.crews order by crew_id desc limit 1";

        Connection connection = DriverManager.getConnection(DBC.url, DBC.user, DBC.password);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()){
            return rs.getInt("crew_id");
        }

        return -1;
    }

    public int selectLastAdventureId() throws SQLException{
        String query = "SELECT adventure_id FROM public.adventures order by adventure_id desc limit 1";

        Connection connection = DriverManager.getConnection(DBC.url, DBC.user, DBC.password);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()){
            return rs.getInt("adventure_id");
        }

        return -1;
    }

    public Second selectSecond() throws SQLException {
        Second second = null;

        String query = "SELECT s.second_id, s.leadership, s.tactics, s.morale_boost, " +
                    "p.pirate_id, p.name, p.alias, p.sex, p.role, " +
                    "st.stat_set_id, st.strength, st.agility, st.endurance, " +
                    "st.intelligence, st.charisma, st.willpower " +
                    "FROM public.seconds s " +
                    "JOIN public.pirates p ON s.pirate_id = p.pirate_id " +
                    "JOIN public.stat_sets st ON p.stat_set = st.stat_set_id " +
                    "ORDER BY random() LIMIT 1";

        Connection connection = DriverManager.getConnection(DBC.url, DBC.user, DBC.password);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()){
            // get the stat set
            PirateStatSet statSet = new PirateStatSet(
                    rs.getInt("stat_set_id"),
                    rs.getInt("strength"),
                    rs.getInt("agility"),
                    rs.getInt("endurance"),
                    rs.getInt("intelligence"),
                    rs.getInt("charisma"),
                    rs.getInt("willpower")
            );

            // build the second object
            second = new Second(
                    rs.getInt("pirate_id"),
                    Role.SECOND,
                    rs.getString("alias"),
                    rs.getString("name"),
                    rs.getString("sex"),
                    statSet,
                    rs.getInt("second_id"),
                    rs.getInt("leadership"),
                    rs.getInt("tactics"),
                    rs.getInt("morale_boost")
            );
        }

        return second;
    }

    public Navigator selectNavigator() throws SQLException {
        Navigator navigator = null;

        String query = "SELECT s.navigator_id, s.navigation, s.weather_prediction, s.map_reading, " +
                "p.pirate_id, p.name, p.alias, p.sex, p.role, " +
                "st.stat_set_id, st.strength, st.agility, st.endurance, " +
                "st.intelligence, st.charisma, st.willpower " +
                "FROM public.navigators s " +
                "JOIN public.pirates p ON s.pirate_id = p.pirate_id " +
                "JOIN public.stat_sets st ON p.stat_set = st.stat_set_id " +
                "ORDER BY random() LIMIT 1";

        Connection connection = DriverManager.getConnection(DBC.url, DBC.user, DBC.password);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()){
            PirateStatSet statSet = new PirateStatSet(
                    rs.getInt("stat_set_id"),
                    rs.getInt("strength"),
                    rs.getInt("agility"),
                    rs.getInt("endurance"),
                    rs.getInt("intelligence"),
                    rs.getInt("charisma"),
                    rs.getInt("willpower")
            );

            navigator = new Navigator(
                    rs.getInt("pirate_id"),
                    Role.NAVIGATOR,
                    rs.getString("alias"),
                    rs.getString("name"),
                    rs.getString("sex"),
                    statSet,
                    rs.getInt("navigator_id"),
                    rs.getInt("navigation"),
                    rs.getInt("weather_prediction"),
                    rs.getInt("map_reading")
            );
        }
        return navigator;
    }

    public Sniper selectSniper() throws SQLException {
        Sniper sniper = null;

        String query = "SELECT s.sniper_id, s.accuracy, s.weapon_rage, s.critical_chance, " +
                "p.pirate_id, p.name, p.alias, p.sex, p.role, " +
                "st.stat_set_id, st.strength, st.agility, st.endurance, " +
                "st.intelligence, st.charisma, st.willpower " +
                "FROM public.snipers s " +
                "JOIN public.pirates p ON s.pirate_id = p.pirate_id " +
                "JOIN public.stat_sets st ON p.stat_set = st.stat_set_id " +
                "ORDER BY random() LIMIT 1";

        Connection connection = DriverManager.getConnection(DBC.url, DBC.user, DBC.password);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()){
            PirateStatSet statSet = new PirateStatSet(
                    rs.getInt("stat_set_id"),
                    rs.getInt("strength"),
                    rs.getInt("agility"),
                    rs.getInt("endurance"),
                    rs.getInt("intelligence"),
                    rs.getInt("charisma"),
                    rs.getInt("willpower")
            );

            sniper = new Sniper(
                    rs.getInt("pirate_id"),
                    Role.SNIPER,
                    rs.getString("alias"),
                    rs.getString("name"),
                    rs.getString("sex"),
                    statSet,
                    rs.getInt("sniper_id"),
                    rs.getInt("accuracy"),
                    rs.getInt("weapon_rage"),
                    rs.getInt("critical_chance")
            );
        }
        return sniper;
    }

    public Cook selectCook() throws SQLException {
        Cook cook = null;

        String query = "SELECT s.cook_id, s.cooking, s.meal_quality, s.morale_impact, " +
                "p.pirate_id, p.name, p.alias, p.sex, p.role, " +
                "st.stat_set_id, st.strength, st.agility, st.endurance, " +
                "st.intelligence, st.charisma, st.willpower " +
                "FROM public.cooks s " +
                "JOIN public.pirates p ON s.pirate_id = p.pirate_id " +
                "JOIN public.stat_sets st ON p.stat_set = st.stat_set_id " +
                "ORDER BY random() LIMIT 1";

        Connection connection = DriverManager.getConnection(DBC.url, DBC.user, DBC.password);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()){
            PirateStatSet statSet = new PirateStatSet(
                    rs.getInt("stat_set_id"),
                    rs.getInt("strength"),
                    rs.getInt("agility"),
                    rs.getInt("endurance"),
                    rs.getInt("intelligence"),
                    rs.getInt("charisma"),
                    rs.getInt("willpower")
            );

            cook = new Cook(
                    rs.getInt("pirate_id"),
                    Role.COOK,
                    rs.getString("alias"),
                    rs.getString("name"),
                    rs.getString("sex"),
                    statSet,
                    rs.getInt("morale_impact"),
                    rs.getInt("cook_id"),
                    rs.getInt("cooking"),
                    rs.getInt("meal_quality")
            );
        }
        return cook;
    }

    public Doctor selectDoctor() throws SQLException {
        Doctor doctor = null;

        String query = "SELECT s.doctor_id, s.medical_ability, s.healing_speed, s.diagnosis, " +
                "p.pirate_id, p.name, p.alias, p.sex, p.role, " +
                "st.stat_set_id, st.strength, st.agility, st.endurance, " +
                "st.intelligence, st.charisma, st.willpower " +
                "FROM public.doctors s " +
                "JOIN public.pirates p ON s.pirate_id = p.pirate_id " +
                "JOIN public.stat_sets st ON p.stat_set = st.stat_set_id " +
                "ORDER BY random() LIMIT 1";

        Connection connection = DriverManager.getConnection(DBC.url, DBC.user, DBC.password);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()){
            PirateStatSet statSet = new PirateStatSet(
                    rs.getInt("stat_set_id"),
                    rs.getInt("strength"),
                    rs.getInt("agility"),
                    rs.getInt("endurance"),
                    rs.getInt("intelligence"),
                    rs.getInt("charisma"),
                    rs.getInt("willpower")
            );

            doctor = new Doctor(
                    rs.getInt("pirate_id"),
                    Role.DOCTOR,
                    rs.getString("alias"),
                    rs.getString("name"),
                    rs.getString("sex"),
                    statSet,
                    rs.getInt("doctor_id"),
                    rs.getInt("medical_ability"),
                    rs.getInt("healing_speed"),
                    rs.getInt("diagnosis")
            );
        }
        return doctor;
    }

    public Helmsman selectHelmsman() throws SQLException {
        Helmsman helmsman = null;

        String query = "SELECT s.helmsman_id, s.maneuvering, s.precision, s.storm_riding, " +
                "p.pirate_id, p.name, p.alias, p.sex, p.role, " +
                "st.stat_set_id, st.strength, st.agility, st.endurance, " +
                "st.intelligence, st.charisma, st.willpower " +
                "FROM public.helmsmen s " +
                "JOIN public.pirates p ON s.pirate_id = p.pirate_id " +
                "JOIN public.stat_sets st ON p.stat_set = st.stat_set_id " +
                "ORDER BY random() LIMIT 1";

        Connection connection = DriverManager.getConnection(DBC.url, DBC.user, DBC.password);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()){
            PirateStatSet statSet = new PirateStatSet(
                    rs.getInt("stat_set_id"),
                    rs.getInt("strength"),
                    rs.getInt("agility"),
                    rs.getInt("endurance"),
                    rs.getInt("intelligence"),
                    rs.getInt("charisma"),
                    rs.getInt("willpower")
            );

            helmsman = new Helmsman(
                    rs.getInt("pirate_id"),
                    Role.HELMSMAN,
                    rs.getString("alias"),
                    rs.getString("name"),
                    rs.getString("sex"),
                    statSet,
                    rs.getInt("helmsman_id"),
                    rs.getInt("storm_riding"),
                    rs.getInt("precision"),
                    rs.getInt("maneuvering")
            );
        }
        return helmsman;
    }

    public Musician selectMusician() throws SQLException {
        Musician musician = null;

        String query = "SELECT s.musician_id, s.music, s.inspiration, s.buff_strength, " +
                "p.pirate_id, p.name, p.alias, p.sex, p.role, " +
                "st.stat_set_id, st.strength, st.agility, st.endurance, " +
                "st.intelligence, st.charisma, st.willpower " +
                "FROM public.musicians s " +
                "JOIN public.pirates p ON s.pirate_id = p.pirate_id " +
                "JOIN public.stat_sets st ON p.stat_set = st.stat_set_id " +
                "ORDER BY random() LIMIT 1";

        Connection connection = DriverManager.getConnection(DBC.url, DBC.user, DBC.password);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()){
            PirateStatSet statSet = new PirateStatSet(
                    rs.getInt("stat_set_id"),
                    rs.getInt("strength"),
                    rs.getInt("agility"),
                    rs.getInt("endurance"),
                    rs.getInt("intelligence"),
                    rs.getInt("charisma"),
                    rs.getInt("willpower")
            );

            musician = new Musician(
                    rs.getInt("pirate_id"),
                    Role.MUSICIAN,
                    rs.getString("alias"),
                    rs.getString("name"),
                    rs.getString("sex"),
                    statSet,
                    rs.getInt("musician_id"),
                    rs.getInt("music"),
                    rs.getInt("inspiration"),
                    rs.getInt("buff_strength")
            );
        }
        return musician;
    }

    public Archeologist selectArcheologist() throws SQLException {
        Archeologist archeologist = null;

        String query = "SELECT s.archeologist_id, s.artifact_knowledge, s.digging, s.trap_detection, " +
                "p.pirate_id, p.name, p.alias, p.sex, p.role, " +
                "st.stat_set_id, st.strength, st.agility, st.endurance, " +
                "st.intelligence, st.charisma, st.willpower " +
                "FROM public.archeologists s " +
                "JOIN public.pirates p ON s.pirate_id = p.pirate_id " +
                "JOIN public.stat_sets st ON p.stat_set = st.stat_set_id " +
                "ORDER BY random() LIMIT 1";

        Connection connection = DriverManager.getConnection(DBC.url, DBC.user, DBC.password);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()){
            PirateStatSet statSet = new PirateStatSet(
                    rs.getInt("stat_set_id"),
                    rs.getInt("strength"),
                    rs.getInt("agility"),
                    rs.getInt("endurance"),
                    rs.getInt("intelligence"),
                    rs.getInt("charisma"),
                    rs.getInt("willpower")
            );

            archeologist = new Archeologist(
                    rs.getInt("pirate_id"),
                    Role.ARCHEOLOGIST,
                    rs.getString("alias"),
                    rs.getString("name"),
                    rs.getString("sex"),
                    statSet,
                    rs.getInt("archeologist_id"),
                    rs.getInt("trap_detection"),
                    rs.getInt("digging"),
                    rs.getInt("artifact_knowledge")
            );
        }
        return archeologist;
    }

    public Shipwright selectShipwright() throws SQLException {
        Shipwright shipwright = null;

        String query = "SELECT s.shipwright_id, s.repair, s.construction, s.materials, " +
                "p.pirate_id, p.name, p.alias, p.sex, p.role, " +
                "st.stat_set_id, st.strength, st.agility, st.endurance, " +
                "st.intelligence, st.charisma, st.willpower " +
                "FROM public.shipwrights s " +
                "JOIN public.pirates p ON s.pirate_id = p.pirate_id " +
                "JOIN public.stat_sets st ON p.stat_set = st.stat_set_id " +
                "ORDER BY random() LIMIT 1";

        Connection connection = DriverManager.getConnection(DBC.url, DBC.user, DBC.password);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()){
            PirateStatSet statSet = new PirateStatSet(
                    rs.getInt("stat_set_id"),
                    rs.getInt("strength"),
                    rs.getInt("agility"),
                    rs.getInt("endurance"),
                    rs.getInt("intelligence"),
                    rs.getInt("charisma"),
                    rs.getInt("willpower")
            );

            shipwright = new Shipwright(
                    rs.getInt("pirate_id"),
                    Role.SHIPWRIGHT,
                    rs.getString("alias"),
                    rs.getString("name"),
                    rs.getString("sex"),
                    statSet,
                    rs.getInt("shipwright_id"),
                    rs.getInt("repair"),
                    rs.getInt("construction"),
                    rs.getInt("materials")
            );
        }
        return shipwright;
    }

}
