package database;

import adventure.Adventure;
import crew.Crew;
import pirateSubclasses.*;
import pirateSubclasses.pirate.Pirate;
import pirateSubclasses.pirate.PirateStatSet;

import java.sql.*;

public class InsertQuery {
    DataBaseCredentials DBC = new DataBaseCredentials();

    public void insertCrewData(Crew crew) throws SQLException {
        String query = "INSERT into public.crews (captain, crew_power, crew_name, captain_alias, " +
                "second, navigator, sniper, cook, doctor, archeologist, shipwright, musician, helmsman) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = DriverManager.getConnection(DBC.url, DBC.user, DBC.password);
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, crew.getCaptain());
        preparedStatement.setInt(2, (int) crew.getCrewPower());
        preparedStatement.setString(3, crew.getCrewName());
        preparedStatement.setString(4, crew.getCaptainAlias());

        preparedStatement.setInt(5, (crew.getSecond()).getSecondId());
        preparedStatement.setInt(6, (crew.getNavigator()).getNavigatorId());
        preparedStatement.setInt(7, (crew.getSniper()).getSniperId());
        preparedStatement.setInt(8, (crew.getCook()).getCookId());
        preparedStatement.setInt(9, (crew.getDoctor().getDoctorId()));
        preparedStatement.setInt(10, (crew.getArcheologist()).getArcheologistId());
        preparedStatement.setInt(11, (crew.getShipwright().getShipwrightId()));
        preparedStatement.setInt(12, (crew.getMusician().getMusicianId()));
        preparedStatement.setInt(13, (crew.getHelmsman().getHelmsmanId()));

        preparedStatement.executeUpdate();
        connection.close();
    }

    public void insertAdventureData(Adventure adventure) throws SQLException {
        DataBaseCredentials DBC = new DataBaseCredentials();

        String query = "INSERT into public.adventures (crew_id, event_set, successful_events, failed_events) " +
                "VALUES (?, ?, ?, ?) ";

        Connection connection = DriverManager.getConnection(DBC.url, DBC.user, DBC.password);
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, adventure.getCrew().getCrewId());
        preparedStatement.setInt(2, adventure.getEventSet().getEventSetId());
        preparedStatement.setInt(3, adventure.getSuccessfulEvents());
        preparedStatement.setInt(4, adventure.getFailedEvents());

        preparedStatement.executeUpdate();
        connection.close();
    }


    /* ----------------------------------- USED FOR INTERFACE IMPLEMENTATION ---------------------------- */

    // The Main Entry Point for saving the Crew
    public int insertFullCrewTransaction(Crew crew) throws SQLException {
        Connection conn = null;
        PreparedStatement psCrew = null;
        int generatedCrewId = -1;

        try {
            conn = DriverManager.getConnection(DBC.url, DBC.user, DBC.password);
            conn.setAutoCommit(false);

            // ... (Insert Members Logic stays the same) ...
            insertSecond(conn, crew.getSecond());
            insertNavigator(conn, crew.getNavigator());
            insertSniper(conn, crew.getSniper());
            insertCook(conn, crew.getCook());
            insertDoctor(conn, crew.getDoctor());
            insertArcheologist(conn, crew.getArcheologist());
            insertShipwright(conn, crew.getShipwright());
            insertMusician(conn, crew.getMusician());
            insertHelmsman(conn, crew.getHelmsman());

            // 2. UPDATE: Add 'RETURNING crew_id'
            String query = "INSERT into public.crews (captain, crew_power, crew_name, captain_alias, " +
                    "second, navigator, sniper, cook, doctor, archeologist, shipwright, musician, helmsman) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING crew_id";

            psCrew = conn.prepareStatement(query);
            // ... (Set all parameters 1-13 as before) ...
            psCrew.setString(1, crew.getCaptain());
            psCrew.setInt(2, (int) crew.getCrewPower());
            psCrew.setString(3, crew.getCrewName());
            psCrew.setString(4, crew.getCaptainAlias());
            psCrew.setInt(5, crew.getSecond().getSecondId());
            psCrew.setInt(6, crew.getNavigator().getNavigatorId());
            psCrew.setInt(7, crew.getSniper().getSniperId());
            psCrew.setInt(8, crew.getCook().getCookId());
            psCrew.setInt(9, crew.getDoctor().getDoctorId());
            psCrew.setInt(10, crew.getArcheologist().getArcheologistId());
            psCrew.setInt(11, crew.getShipwright().getShipwrightId());
            psCrew.setInt(12, crew.getMusician().getMusicianId());
            psCrew.setInt(13, crew.getHelmsman().getHelmsmanId());

            // 3. UPDATE: Execute and Get ID
            try (ResultSet rs = psCrew.executeQuery()) {
                if (rs.next()) {
                    generatedCrewId = rs.getInt(1);
                }
            }

            conn.commit();
            return generatedCrewId; // Return the new ID!

        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (psCrew != null) psCrew.close();
            if (conn != null) conn.close();
        }
    }

    public void insertAdventure(int crewId, int eventSetId, int success, int fail) throws SQLException {
        String sql = "INSERT INTO public.adventures (crew_id, event_set, successful_events, failed_events) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DBC.url, DBC.user, DBC.password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, crewId);
            ps.setInt(2, eventSetId);
            ps.setInt(3, success);
            ps.setInt(4, fail);

            ps.executeUpdate();
        }
    }

    // --- GENERIC HELPERS ---
    private int insertStatSet(Connection conn, PirateStatSet stats) throws SQLException {
        String sql = "INSERT INTO public.stat_sets (strength, agility, endurance, intelligence, charisma, willpower) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING stat_set_id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, stats.getStrength());
            ps.setInt(2, stats.getAgility());
            ps.setInt(3, stats.getEndurance());
            ps.setInt(4, stats.getIntelligence());
            ps.setInt(5, stats.getCharisma());
            ps.setInt(6, stats.getWillpower());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        throw new SQLException("Failed to insert stat set.");
    }

    private int insertBasePirate(Connection conn, Pirate p, int statSetId) throws SQLException {
        String sql = "INSERT INTO public.pirates (name, alias, role, sex, stat_set) VALUES (?, ?, ?::pirate_type, ?::sex_type, ?) RETURNING pirate_id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getAlias());
            ps.setString(3, p.getRole().toString());
            ps.setString(4, p.getSex());
            ps.setInt(5, statSetId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        throw new SQLException("Failed to insert pirate base.");
    }

    private void insertSecond(Connection conn, Second p) throws SQLException {
        int statId = insertStatSet(conn, p.getPirateStatSet());
        int pirateId = insertBasePirate(conn, p, statId);

        String sql = "INSERT INTO public.seconds (pirate_id, leadership, tactics, morale_boost) VALUES (?, ?, ?, ?) RETURNING second_id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pirateId);
            ps.setInt(2, p.getLeadership());
            ps.setInt(3, p.getTactics());
            ps.setInt(4, p.getMoraleBoost());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) p.setSecondId(rs.getInt(1)); // Update Object ID
            }
        }
    }

    private void insertNavigator(Connection conn, Navigator p) throws SQLException {
        int statId = insertStatSet(conn, p.getPirateStatSet());
        int pirateId = insertBasePirate(conn, p, statId);

        String sql = "INSERT INTO public.navigators (pirate_id, navigation, weather_prediction, map_reading) VALUES (?, ?, ?, ?) RETURNING navigator_id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pirateId);
            ps.setInt(2, p.getNavigation());
            ps.setInt(3, p.getWeatherPrediction());
            ps.setInt(4, p.getMapReading());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) p.setNavigatorId(rs.getInt(1));
            }
        }
    }

    private void insertSniper(Connection conn, Sniper p) throws SQLException {
        int statId = insertStatSet(conn, p.getPirateStatSet());
        int pirateId = insertBasePirate(conn, p, statId);

        String sql = "INSERT INTO public.snipers (pirate_id, accuracy, weapon_range, critical_chance) VALUES (?, ?, ?, ?) RETURNING sniper_id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pirateId);
            ps.setInt(2, p.getAccuracy());
            ps.setInt(3, p.getWeaponRange());
            ps.setInt(4, p.getCriticalChance());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) p.setSniperId(rs.getInt(1));
            }
        }
    }

    private void insertCook(Connection conn, Cook p) throws SQLException {
        int statId = insertStatSet(conn, p.getPirateStatSet());
        int pirateId = insertBasePirate(conn, p, statId);

        String sql = "INSERT INTO public.cooks (pirate_id, cooking, meal_quality, morale_impact) VALUES (?, ?, ?, ?) RETURNING cook_id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pirateId);
            ps.setInt(2, p.getCooking());
            ps.setInt(3, p.getMealQuality());
            ps.setInt(4, p.getMoraleImpact());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) p.setCookId(rs.getInt(1));
            }
        }
    }

    private void insertDoctor(Connection conn, Doctor p) throws SQLException {
        int statId = insertStatSet(conn, p.getPirateStatSet());
        int pirateId = insertBasePirate(conn, p, statId);

        String sql = "INSERT INTO public.doctors (pirate_id, medical_ability, healing_speed, diagnosis) VALUES (?, ?, ?, ?) RETURNING doctor_id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pirateId);
            ps.setInt(2, p.getMedicalAbility());
            ps.setInt(3, p.getHealingSpeed());
            ps.setInt(4, p.getDiagnosis());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) p.setDoctorId(rs.getInt(1));
            }
        }
    }

    private void insertArcheologist(Connection conn, Archeologist p) throws SQLException {
        int statId = insertStatSet(conn, p.getPirateStatSet());
        int pirateId = insertBasePirate(conn, p, statId);

        String sql = "INSERT INTO public.archeologists (pirate_id, artifact_knowledge, digging, trap_detection) VALUES (?, ?, ?, ?) RETURNING archeologist_id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pirateId);
            ps.setInt(2, p.getArtifactKnowledge());
            ps.setInt(3, p.getDigging());
            ps.setInt(4, p.getTrapDetection());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) p.setArcheologistId(rs.getInt(1));
            }
        }
    }

    private void insertShipwright(Connection conn, Shipwright p) throws SQLException {
        int statId = insertStatSet(conn, p.getPirateStatSet());
        int pirateId = insertBasePirate(conn, p, statId);

        String sql = "INSERT INTO public.shipwrights (pirate_id, repair, construction, materials) VALUES (?, ?, ?, ?) RETURNING shipwright_id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pirateId);
            ps.setInt(2, p.getRepair());
            ps.setInt(3, p.getConstruction());
            ps.setInt(4, p.getMaterials());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) p.setShipwrightId(rs.getInt(1));
            }
        }
    }

    private void insertMusician(Connection conn, Musician p) throws SQLException {
        int statId = insertStatSet(conn, p.getPirateStatSet());
        int pirateId = insertBasePirate(conn, p, statId);

        String sql = "INSERT INTO public.musicians (pirate_id, music, inspiration, buff_strength) VALUES (?, ?, ?, ?) RETURNING musician_id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pirateId);
            ps.setInt(2, p.getMusic());
            ps.setInt(3, p.getInspiration());
            ps.setInt(4, p.getBuffStrength());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) p.setMusicianId(rs.getInt(1));
            }
        }
    }

    private void insertHelmsman(Connection conn, Helmsman p) throws SQLException {
        int statId = insertStatSet(conn, p.getPirateStatSet());
        int pirateId = insertBasePirate(conn, p, statId);

        String sql = "INSERT INTO public.helmsmen (pirate_id, maneuvering, precision, storm_riding) VALUES (?, ?, ?, ?) RETURNING helmsman_id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pirateId);
            ps.setInt(2, p.getManeuvering());
            ps.setInt(3, p.getPrecision());
            ps.setInt(4, p.getStormRiding());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) p.setHelmsmanId(rs.getInt(1));
            }
        }
    }

}
