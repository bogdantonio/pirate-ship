import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import crew.Crew;
import database.DataBaseCredentials;
import database.InsertQuery;
import pirateSubclasses.*;
import pirateSubclasses.pirate.Pirate;
import pirateSubclasses.pirate.PirateStatSet;
import pirateSubclasses.pirate.Role;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;
import java.util.*;

public class JavaAPIServer {
    private static DataBaseCredentials DBC;

    public static void main(String[] args) throws IOException {
        DBC = new DataBaseCredentials();

        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // --- 1. Define API Endpoints ---
        server.createContext("/api/create-crew", new CreateCrewHandler());
        server.createContext("/api/candidates", new CandidatesHandler());
        server.createContext("/api/adventure/start", new AdventureHandler());
        server.createContext("/api/adventure/end", new EndAdventureHandler());

        // --- 2. Global CORS Handler ---
        server.createContext("/", (exchange) -> {
            String response = "404 Not Found";
            exchange.sendResponseHeaders(404, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        });

        server.setExecutor(null);
        server.start();
        System.out.println("API Server started on port " + port);
    }

    // =================================================================
    // HANDLER 1: CREATE CREW
    // =================================================================
    static class CreateCrewHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            handleCORS(exchange);
            String response = "{}";
            int statusCode = 200;

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                try {
                    String requestBody = new String(exchange.getRequestBody().readAllBytes());
                    Map<String, String> data = parseJsonToMap(requestBody);

                    Crew crew = new Crew(0, data.get("crewName"), data.get("captainName"), null, data.get("alias"));
                    crew.setCrewMembers(new EnumMap<>(Role.class));

                    crew.addCrewMember(createSpecificPirate(data, Role.SECOND, "second"));
                    crew.addCrewMember(createSpecificPirate(data, Role.NAVIGATOR, "navigator"));
                    crew.addCrewMember(createSpecificPirate(data, Role.SNIPER, "sniper"));
                    crew.addCrewMember(createSpecificPirate(data, Role.COOK, "cook"));
                    crew.addCrewMember(createSpecificPirate(data, Role.ARCHEOLOGIST, "archeologist"));
                    crew.addCrewMember(createSpecificPirate(data, Role.DOCTOR, "doctor"));
                    crew.addCrewMember(createSpecificPirate(data, Role.SHIPWRIGHT, "shipwright"));
                    crew.addCrewMember(createSpecificPirate(data, Role.MUSICIAN, "musician"));
                    crew.addCrewMember(createSpecificPirate(data, Role.HELMSMAN, "helmsman"));

                    InsertQuery insertQuery = new InsertQuery();

                    // FIX: Call this ONLY ONCE and capture the ID
                    int newCrewId = insertQuery.insertFullCrewTransaction(crew);

                    response = String.format("{\"message\": \"Set Sail!\", \"crewId\": %d}", newCrewId);

                } catch (Exception e) {
                    e.printStackTrace();
                    statusCode = 500;
                    response = "{\"error\": \"Server Error: " + e.getMessage() + "\"}";
                }
            } else if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                statusCode = 204;
                response = "";
            } else {
                statusCode = 405;
                response = "{\"error\": \"Method Not Allowed\"}";
            }
            sendResponse(exchange, statusCode, response);
        }

        private Pirate createSpecificPirate(Map<String, String> data, Role role, String prefix) {
            PirateStatSet stats = new PirateStatSet(0,
                    parseInt(data.get(prefix + "_strength")),
                    parseInt(data.get(prefix + "_agility")),
                    parseInt(data.get(prefix + "_endurance")),
                    parseInt(data.get(prefix + "_intelligence")),
                    parseInt(data.get(prefix + "_charisma")),
                    parseInt(data.get(prefix + "_willpower"))
            );

            String name = data.get(prefix + "_name");
            String alias = data.get(prefix + "_alias");
            String sex = data.get(prefix + "_sex");
            int id = 0;

            switch (role) {
                case SECOND:
                    return new Second(id, role, alias, name, sex, stats, 0,
                            parseInt(data.get(prefix + "_leadership")),
                            parseInt(data.get(prefix + "_tactics")),
                            parseInt(data.get(prefix + "_morale_boost")));
                case NAVIGATOR:
                    return new Navigator(id, role, alias, name, sex, stats, 0,
                            parseInt(data.get(prefix + "_navigation")),
                            parseInt(data.get(prefix + "_weather_prediction")),
                            parseInt(data.get(prefix + "_map_reading")));
                case SNIPER:
                    return new Sniper(id, role, alias, name, sex, stats, 0,
                            parseInt(data.get(prefix + "_accuracy")),
                            parseInt(data.get(prefix + "_weapon_range")),
                            parseInt(data.get(prefix + "_critical_chance")));
                case COOK:
                    return new Cook(id, role, alias, name, sex, stats,
                            parseInt(data.get(prefix + "_morale_impact")),
                            0,
                            parseInt(data.get(prefix + "_cooking")),
                            parseInt(data.get(prefix + "_meal_quality")));
                case DOCTOR:
                    return new Doctor(id, role, alias, name, sex, stats, 0,
                            parseInt(data.get(prefix + "_medical_ability")),
                            parseInt(data.get(prefix + "_healing_speed")),
                            parseInt(data.get(prefix + "_diagnosis")));
                case ARCHEOLOGIST:
                    return new Archeologist(id, role, alias, name, sex, stats, 0,
                            parseInt(data.get(prefix + "_trap_detection")),
                            parseInt(data.get(prefix + "_digging")),
                            parseInt(data.get(prefix + "_artifact_knowledge")));
                case SHIPWRIGHT:
                    return new Shipwright(id, role, alias, name, sex, stats, 0,
                            parseInt(data.get(prefix + "_repair")),
                            parseInt(data.get(prefix + "_construction")),
                            parseInt(data.get(prefix + "_materials")));
                case MUSICIAN:
                    return new Musician(id, role, alias, name, sex, stats, 0,
                            parseInt(data.get(prefix + "_music")),
                            parseInt(data.get(prefix + "_inspiration")),
                            parseInt(data.get(prefix + "_buff_strength")));
                case HELMSMAN:
                    return new Helmsman(id, role, alias, name, sex, stats, 0,
                            parseInt(data.get(prefix + "_storm_riding")),
                            parseInt(data.get(prefix + "_precision")),
                            parseInt(data.get(prefix + "_maneuvering")));
                default: return null;
            }
        }

        private int parseInt(String val) {
            try { return Integer.parseInt(val); } catch (Exception e) { return 0; }
        }
    }

    // =================================================================
    // HANDLER 2: CANDIDATES
    // =================================================================
    static class CandidatesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            handleCORS(exchange);
            String response;
            int statusCode = 200;

            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                String role = getQueryParam(query, "role");

                if (role != null) {
                    List<Map<String, Object>> candidates = retrieveRandomCandidates(role);
                    response = convertListMapToJson(candidates);
                } else {
                    statusCode = 400;
                    response = "{\"error\": \"Missing role parameter\"}";
                }
            } else if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                statusCode = 204;
                response = "";
            } else {
                statusCode = 405;
                response = "{\"error\": \"Method Not Allowed\"}";
            }
            sendResponse(exchange, statusCode, response);
        }
    }

    // =================================================================
    // HANDLER 3: ADVENTURE START
    // =================================================================
    static class AdventureHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            handleCORS(exchange);

            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                try {
                    // FIX: Removed duplicate logic block here
                    Map<String, Object> result = retrieveFullAdventure();

                    // Convert the wrapper map to JSON manually
                    String jsonEvents = convertListMapToJson((List<Map<String, Object>>) result.get("events"));
                    String response = String.format("{\"eventSetId\": %s, \"events\": %s}", result.get("set_id"), jsonEvents);

                    sendResponse(exchange, 200, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    sendResponse(exchange, 500, "{\"error\": \"Server Error\"}");
                }
            }
            else if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendResponse(exchange, 204, "");
            }
            else {
                sendResponse(exchange, 405, "{}");
            }
        }
    }

    // =================================================================
    // HANDLER 4: ADVENTURE END
    // =================================================================
    static class EndAdventureHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            handleCORS(exchange);
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                try {
                    String body = new String(exchange.getRequestBody().readAllBytes());
                    Map<String, String> data = parseJsonToMap(body);

                    int crewId = Integer.parseInt(data.get("crewId"));
                    int setId = Integer.parseInt(data.get("eventSetId"));
                    int success = Integer.parseInt(data.get("success"));
                    int fail = Integer.parseInt(data.get("fail"));

                    InsertQuery iq = new InsertQuery();
                    iq.insertAdventure(crewId, setId, success, fail);

                    sendResponse(exchange, 200, "{\"message\": \"History Recorded\"}");
                } catch (Exception e) {
                    e.printStackTrace();
                    sendResponse(exchange, 500, "{\"error\": \"Save failed\"}");
                }
            } else if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendResponse(exchange, 204, "");
            }
        }
    }

    // =================================================================
    // DB METHODS
    // =================================================================

    private static Map<String, Object> retrieveFullAdventure() {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> fullEvents = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DBC.url, DBC.user, DBC.password)) {
            // Get Set ID and Event IDs
            String setQuery = "SELECT * FROM public.event_sets ORDER BY RANDOM() LIMIT 1";
            List<Integer> eventIds = new ArrayList<>();

            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(setQuery)) {
                if (rs.next()) {
                    result.put("set_id", rs.getInt("event_set_id")); // Capture Set ID
                    for(int i=1; i<=10; i++) eventIds.add(rs.getInt("event" + i + "_id"));
                }
            }

            for (int id : eventIds) {
                String typeQuery = "SELECT event_type, enemy_event_id, subclass_event_id FROM public.events WHERE event_id = ?";
                try (PreparedStatement ps = conn.prepareStatement(typeQuery)) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            String type = rs.getString("event_type");
                            Map<String, Object> eventData = new HashMap<>();
                            eventData.put("type", type);

                            if ("ENEMY".equals(type)) {
                                int enemyEventId = rs.getInt("enemy_event_id");
                                fetchEnemyEventDetails(conn, enemyEventId, eventData);
                            } else {
                                int subclassEventId = rs.getInt("subclass_event_id");
                                fetchSubclassEventDetails(conn, subclassEventId, eventData);
                            }
                            fullEvents.add(eventData);
                        }
                    }
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }

        result.put("events", fullEvents);
        return result;
    }

    private static void fetchEnemyEventDetails(Connection conn, int id, Map<String, Object> data) throws SQLException {
        String sql = "SELECT ee.prompt, e.name, e.alias, e.power, e.faction, e.sex " +
                "FROM public.enemies_events ee " +
                "JOIN public.enemies e ON ee.enemy_id = e.enemy_id " +
                "WHERE ee.enemy_event_id = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    data.put("prompt", rs.getString("prompt"));
                    data.put("enemyName", rs.getString("name"));
                    data.put("enemyAlias", rs.getString("alias"));
                    data.put("enemyPower", rs.getInt("power"));
                    data.put("enemyFaction", rs.getString("faction"));
                    data.put("enemySex", rs.getString("sex"));
                }
            }
        }
    }

    private static void fetchSubclassEventDetails(Connection conn, int id, Map<String, Object> data) throws SQLException {
        String sql = "SELECT subclass, prompt, required_stat1, required_stat2, required_stat3 " +
                "FROM public.subclass_events WHERE subclass_event_id = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    data.put("subclassRole", rs.getString("subclass"));
                    data.put("prompt", rs.getString("prompt"));
                    data.put("req1", rs.getInt("required_stat1"));
                    data.put("req2", rs.getInt("required_stat2"));
                    data.put("req3", rs.getInt("required_stat3"));
                }
            }
        }
    }

    private static List<Map<String, Object>> retrieveRandomCandidates(String role) {
        List<Map<String, Object>> list = new ArrayList<>();
        String query = "";

        switch (role.toUpperCase()){
            case "SECOND":
                query = "SELECT p.name, p.alias, p.sex, leadership, tactics, morale_boost, " +
                        "ss.strength, ss.agility, ss.endurance, ss.intelligence, ss.charisma, ss.willpower " +
                        "FROM public.seconds JOIN public.pirates p on seconds.pirate_id = p.pirate_id " +
                        "JOIN public.stat_sets ss on p.stat_set = ss.stat_set_id ORDER BY random() LIMIT 3";
                break;
            case "NAVIGATOR":
                query = "SELECT p.name, p.alias, p.sex, navigation, weather_prediction, map_reading, " +
                        "ss.strength, ss.agility, ss.endurance, ss.intelligence, ss.charisma, ss.willpower " +
                        "FROM public.navigators JOIN public.pirates p on navigators.pirate_id = p.pirate_id " +
                        "JOIN public.stat_sets ss on p.stat_set = ss.stat_set_id ORDER BY random() LIMIT 3";
                break;
            case "SNIPER":
                query = "SELECT p.name, p.alias, p.sex, accuracy, weapon_range, critical_chance, " +
                        "ss.strength, ss.agility, ss.endurance, ss.intelligence, ss.charisma, ss.willpower " +
                        "FROM public.snipers JOIN public.pirates p on snipers.pirate_id = p.pirate_id " +
                        "JOIN public.stat_sets ss on p.stat_set = ss.stat_set_id ORDER BY random() LIMIT 3";
                break;
            case "COOK":
                query = "SELECT p.name, p.alias, p.sex, cooking, meal_quality, morale_impact, " +
                        "ss.strength, ss.agility, ss.endurance, ss.intelligence, ss.charisma, ss.willpower " +
                        "FROM public.cooks JOIN public.pirates p on cooks.pirate_id = p.pirate_id " +
                        "JOIN public.stat_sets ss on p.stat_set = ss.stat_set_id ORDER BY random() LIMIT 3";
                break;
            case "ARCHEOLOGIST":
                query = "SELECT p.name, p.alias, p.sex, artifact_knowledge, digging, trap_detection, " +
                        "ss.strength, ss.agility, ss.endurance, ss.intelligence, ss.charisma, ss.willpower " +
                        "FROM public.archeologists JOIN public.pirates p on archeologists.pirate_id = p.pirate_id " +
                        "JOIN public.stat_sets ss on p.stat_set = ss.stat_set_id ORDER BY random() LIMIT 3";
                break;
            case "DOCTOR":
                query = "SELECT p.name, p.alias, p.sex, medical_ability, healing_speed, diagnosis, " +
                        "ss.strength, ss.agility, ss.endurance, ss.intelligence, ss.charisma, ss.willpower " +
                        "FROM public.doctors JOIN public.pirates p on doctors.pirate_id = p.pirate_id " +
                        "JOIN public.stat_sets ss on p.stat_set = ss.stat_set_id ORDER BY random() LIMIT 3";
                break;
            case "SHIPWRIGHT":
                query = "SELECT p.name, p.alias, p.sex, repair, construction, materials, " +
                        "ss.strength, ss.agility, ss.endurance, ss.intelligence, ss.charisma, ss.willpower " +
                        "FROM public.shipwrights JOIN public.pirates p on shipwrights.pirate_id = p.pirate_id " +
                        "JOIN public.stat_sets ss on p.stat_set = ss.stat_set_id ORDER BY random() LIMIT 3";
                break;
            case "MUSICIAN":
                query = "SELECT p.name, p.alias, p.sex, music, inspiration, buff_strength, " +
                        "ss.strength, ss.agility, ss.endurance, ss.intelligence, ss.charisma, ss.willpower " +
                        "FROM public.musicians JOIN public.pirates p on musicians.pirate_id = p.pirate_id " +
                        "JOIN public.stat_sets ss on p.stat_set = ss.stat_set_id ORDER BY random() LIMIT 3";
                break;
            case "HELMSMAN":
                query = "SELECT p.name, p.alias, p.sex, maneuvering, precision, storm_riding, " +
                        "ss.strength, ss.agility, ss.endurance, ss.intelligence, ss.charisma, ss.willpower " +
                        "FROM public.helmsmen JOIN public.pirates p on helmsmen.pirate_id = p.pirate_id " +
                        "JOIN public.stat_sets ss on p.stat_set = ss.stat_set_id ORDER BY random() LIMIT 3";
                break;
        }

        try (Connection conn = DriverManager.getConnection(DBC.url, DBC.user, DBC.password);
             PreparedStatement ps = conn.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> pirate = new HashMap<>();
                    pirate.put("name", rs.getString("name"));
                    pirate.put("alias", rs.getString("alias"));
                    pirate.put("sex", rs.getString("sex"));

                    Map<String, Integer> stats = new HashMap<>();
                    stats.put("strength", rs.getInt("strength"));
                    stats.put("agility", rs.getInt("agility"));
                    stats.put("endurance", rs.getInt("endurance"));
                    stats.put("intelligence", rs.getInt("intelligence"));
                    stats.put("charisma", rs.getInt("charisma"));
                    stats.put("willpower", rs.getInt("willpower"));
                    pirate.put("stats", stats);

                    Map<String, Integer> roleData = new HashMap<>();
                    switch (role.toUpperCase()) {
                        case "SECOND":
                            roleData.put("leadership", rs.getInt("leadership"));
                            roleData.put("tactics", rs.getInt("tactics"));
                            roleData.put("morale_boost", rs.getInt("morale_boost"));
                            break;
                        case "NAVIGATOR":
                            roleData.put("navigation", rs.getInt("navigation"));
                            roleData.put("weather_prediction", rs.getInt("weather_prediction"));
                            roleData.put("map_reading", rs.getInt("map_reading"));
                            break;
                        case "SNIPER":
                            roleData.put("accuracy", rs.getInt("accuracy"));
                            roleData.put("weapon_range", rs.getInt("weapon_range"));
                            roleData.put("critical_chance", rs.getInt("critical_chance"));
                            break;
                        case "COOK":
                            roleData.put("cooking", rs.getInt("cooking"));
                            roleData.put("meal_quality", rs.getInt("meal_quality"));
                            roleData.put("morale_impact", rs.getInt("morale_impact"));
                            break;
                        case "ARCHEOLOGIST":
                            roleData.put("artifact_knowledge", rs.getInt("artifact_knowledge"));
                            roleData.put("digging", rs.getInt("digging"));
                            roleData.put("trap_detection", rs.getInt("trap_detection"));
                            break;
                        case "DOCTOR":
                            roleData.put("medical_ability", rs.getInt("medical_ability"));
                            roleData.put("healing_speed", rs.getInt("healing_speed"));
                            roleData.put("diagnosis", rs.getInt("diagnosis"));
                            break;
                        case "SHIPWRIGHT":
                            roleData.put("repair", rs.getInt("repair"));
                            roleData.put("construction", rs.getInt("construction"));
                            roleData.put("materials", rs.getInt("materials"));
                            break;
                        case "MUSICIAN":
                            roleData.put("music", rs.getInt("music"));
                            roleData.put("inspiration", rs.getInt("inspiration"));
                            roleData.put("buff_strength", rs.getInt("buff_strength"));
                            break;
                        case "HELMSMAN":
                            roleData.put("maneuvering", rs.getInt("maneuvering"));
                            roleData.put("precision", rs.getInt("precision"));
                            roleData.put("storm_riding", rs.getInt("storm_riding"));
                            break;
                    }
                    pirate.put("roleData", roleData);
                    list.add(pirate);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // =================================================================
    // UTILS
    // =================================================================

    private static void handleCORS(HttpExchange exchange) {
        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
    }

    private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        if (statusCode == 204) {
            exchange.sendResponseHeaders(statusCode, -1);
        } else {
            byte[] bytes = response.getBytes();
            exchange.sendResponseHeaders(statusCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    private static Map<String, String> parseJsonToMap(String json) {
        Map<String, String> map = new HashMap<>();
        json = json.trim().replace("{", "").replace("}", "");
        if (json.isEmpty()) return map;
        String[] pairs = json.split(",");
        for (String pair : pairs) {
            String[] entry = pair.split(":");
            if (entry.length >= 2) {
                map.put(entry[0].trim().replace("\"", ""), entry[1].trim().replace("\"", ""));
            }
        }
        return map;
    }

    private static String convertListMapToJson(List<Map<String, Object>> list) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            json.append(convertMapToJson(list.get(i)));
            if (i < list.size() - 1) json.append(",");
        }
        json.append("]");
        return json.toString();
    }

    private static String convertMapToJson(Map<String, Object> map) {
        StringBuilder json = new StringBuilder("{");
        int count = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            json.append("\"").append(entry.getKey()).append("\":");
            if (entry.getValue() instanceof String) {
                json.append("\"").append(entry.getValue()).append("\"");
            } else if (entry.getValue() instanceof Map) {
                json.append(convertMapToJson((Map<String, Object>) entry.getValue()));
            } else {
                json.append(entry.getValue());
            }
            if (count < map.size() - 1) json.append(",");
            count++;
        }
        json.append("}");
        return json.toString();
    }

    private static String getQueryParam(String query, String param) {
        if (query == null) return null;
        for (String pair : query.split("&")) {
            String[] parts = pair.split("=");
            if (parts.length == 2 && parts[0].equals(param)) {
                return parts[1];
            }
        }
        return null;
    }
}