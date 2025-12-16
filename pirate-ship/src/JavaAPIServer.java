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

        // --- 2. Global CORS Handler ---
        // This ensures your HTML file can talk to this Java server without security errors.
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

    static class CreateCrewHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            handleCORS(exchange);
            String response = "{}";
            int statusCode = 200;

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                try {
                    // 1. Parse the flattened JSON
                    String requestBody = new String(exchange.getRequestBody().readAllBytes());
                    Map<String, String> data = parseJsonToMap(requestBody);

                    // 2. Create the Crew Skeleton
                    // We pass '0' for ID initially; the DB will generate it.
                    Crew crew = new Crew(0, data.get("crewName"), data.get("captainName"), null, data.get("alias"));
                    crew.setCrewMembers(new EnumMap<>(Role.class));

                    // 3. Instantiate Members using the Factory Method
                    crew.addCrewMember(createSpecificPirate(data, Role.SECOND, "second"));
                    crew.addCrewMember(createSpecificPirate(data, Role.NAVIGATOR, "navigator"));
                    crew.addCrewMember(createSpecificPirate(data, Role.SNIPER, "sniper"));
                    crew.addCrewMember(createSpecificPirate(data, Role.COOK, "cook"));
                    crew.addCrewMember(createSpecificPirate(data, Role.ARCHEOLOGIST, "archeologist"));
                    crew.addCrewMember(createSpecificPirate(data, Role.DOCTOR, "doctor"));
                    crew.addCrewMember(createSpecificPirate(data, Role.SHIPWRIGHT, "shipwright"));
                    crew.addCrewMember(createSpecificPirate(data, Role.MUSICIAN, "musician"));
                    crew.addCrewMember(createSpecificPirate(data, Role.HELMSMAN, "helmsman"));

                    // 4. Save to Database
                    InsertQuery insertQuery = new InsertQuery();
                    insertQuery.insertFullCrewTransaction(crew);

                    response = "{\"message\": \"The " + crew.getCrewName() + " has set sail!\"}";

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
            int id = 0; // Placeholder, set by DB later

            switch (role) {
                case SECOND: //
                    return new Second(id, role, alias, name, sex, stats, 0,
                            parseInt(data.get(prefix + "_leadership")),
                            parseInt(data.get(prefix + "_tactics")),
                            parseInt(data.get(prefix + "_morale_boost")));

                case NAVIGATOR: //
                    return new Navigator(id, role, alias, name, sex, stats, 0,
                            parseInt(data.get(prefix + "_navigation")),
                            parseInt(data.get(prefix + "_weather_prediction")),
                            parseInt(data.get(prefix + "_map_reading")));

                case SNIPER: //
                    return new Sniper(id, role, alias, name, sex, stats, 0,
                            parseInt(data.get(prefix + "_accuracy")),
                            parseInt(data.get(prefix + "_weapon_range")),
                            parseInt(data.get(prefix + "_critical_chance")));

                case COOK: //
                    return new Cook(id, role, alias, name, sex, stats,
                            parseInt(data.get(prefix + "_morale_impact")), // Note: Constructor arg order matters!
                            0,
                            parseInt(data.get(prefix + "_cooking")),
                            parseInt(data.get(prefix + "_meal_quality")));

                case DOCTOR: //
                    return new Doctor(id, role, alias, name, sex, stats, 0,
                            parseInt(data.get(prefix + "_medical_ability")),
                            parseInt(data.get(prefix + "_healing_speed")),
                            parseInt(data.get(prefix + "_diagnosis")));

                case ARCHEOLOGIST: //
                    return new Archeologist(id, role, alias, name, sex, stats, 0,
                            parseInt(data.get(prefix + "_trap_detection")),
                            parseInt(data.get(prefix + "_digging")),
                            parseInt(data.get(prefix + "_artifact_knowledge")));

                case SHIPWRIGHT: //
                    return new Shipwright(id, role, alias, name, sex, stats, 0,
                            parseInt(data.get(prefix + "_repair")),
                            parseInt(data.get(prefix + "_construction")),
                            parseInt(data.get(prefix + "_materials")));

                case MUSICIAN: //
                    return new Musician(id, role, alias, name, sex, stats, 0,
                            parseInt(data.get(prefix + "_music")),
                            parseInt(data.get(prefix + "_inspiration")),
                            parseInt(data.get(prefix + "_buff_strength")));

                case HELMSMAN: //
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

    static class CandidatesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            handleCORS(exchange);

            String response;
            int statusCode = 200;

            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                // 1. Extract 'role' from query (e.g. ?role=Second)
                String query = exchange.getRequestURI().getQuery();
                String role = getQueryParam(query, "role");

                if (role != null) {
                    // 2. Get data from DB
                    List<Map<String, Object>> candidates = retrieveRandomCandidates(role);
                    // 3. Convert to JSON
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

    // retrieves 3 random candidates for a specific role
    private static List<Map<String, Object>> retrieveRandomCandidates(String role) {
        List<Map<String, Object>> list = new ArrayList<>();

        String query = "";

        // 1. Build the SQL Query based on the role
        // Note: We use the role-specific table (e.g., 'seconds') and join it
        // with the generic 'pirates' and 'stat_sets' tables to get all the data.
        switch (role.toUpperCase()){
            case "SECOND":
                query = "SELECT p.name, p.alias, p.sex," +
                        " leadership, tactics, morale_boost," +
                        " ss.strength, ss.agility, ss.endurance, ss.intelligence, ss.charisma, ss.willpower" +
                        " FROM public.seconds" +
                        " JOIN public.pirates p on seconds.pirate_id = p.pirate_id" +
                        " JOIN public.stat_sets ss on p.stat_set = ss.stat_set_id" +
                        " ORDER BY random()" +
                        " LIMIT 3";
                break;
            case "NAVIGATOR":
                query = "SELECT p.name, p.alias, p.sex," +
                        " navigation, weather_prediction, map_reading," +
                        " ss.strength, ss.agility, ss.endurance, ss.intelligence, ss.charisma, ss.willpower" +
                        " FROM public.navigators" +
                        " JOIN public.pirates p on navigators.pirate_id = p.pirate_id" +
                        " JOIN public.stat_sets ss on p.stat_set = ss.stat_set_id" +
                        " ORDER BY random()" +
                        " LIMIT 3";
                break;
            case "SNIPER":
                query = "SELECT p.name, p.alias, p.sex," +
                        " accuracy, weapon_range, critical_chance," +
                        " ss.strength, ss.agility, ss.endurance, ss.intelligence, ss.charisma, ss.willpower" +
                        " FROM public.snipers" +
                        " JOIN public.pirates p on snipers.pirate_id = p.pirate_id" +
                        " JOIN public.stat_sets ss on p.stat_set = ss.stat_set_id" +
                        " ORDER BY random()" +
                        " LIMIT 3";
                break;
            case "COOK":
                query = "SELECT p.name, p.alias, p.sex," +
                        " cooking, meal_quality, morale_impact," +
                        " ss.strength, ss.agility, ss.endurance, ss.intelligence, ss.charisma, ss.willpower" +
                        " FROM public.cooks" +
                        " JOIN public.pirates p on cooks.pirate_id = p.pirate_id" +
                        " JOIN public.stat_sets ss on p.stat_set = ss.stat_set_id" +
                        " ORDER BY random()" +
                        " LIMIT 3";
                break;
            case "ARCHEOLOGIST":
                query = "SELECT p.name, p.alias, p.sex," +
                        " artifact_knowledge, digging, trap_detection," +
                        " ss.strength, ss.agility, ss.endurance, ss.intelligence, ss.charisma, ss.willpower" +
                        " FROM public.archeologists" +
                        " JOIN public.pirates p on archeologists.pirate_id = p.pirate_id" +
                        " JOIN public.stat_sets ss on p.stat_set = ss.stat_set_id" +
                        " ORDER BY random()" +
                        " LIMIT 3";
                break;
            case "DOCTOR":
                query = "SELECT p.name, p.alias, p.sex," +
                        " medical_ability, healing_speed, diagnosis," +
                        " ss.strength, ss.agility, ss.endurance, ss.intelligence, ss.charisma, ss.willpower" +
                        " FROM public.doctors" +
                        " JOIN public.pirates p on doctors.pirate_id = p.pirate_id" +
                        " JOIN public.stat_sets ss on p.stat_set = ss.stat_set_id" +
                        " ORDER BY random()" +
                        " LIMIT 3";
                break;
            case "SHIPWRIGHT":
                query = "SELECT p.name, p.alias, p.sex," +
                        " repair, construction, materials," +
                        " ss.strength, ss.agility, ss.endurance, ss.intelligence, ss.charisma, ss.willpower" +
                        " FROM public.shipwrights" +
                        " JOIN public.pirates p on shipwrights.pirate_id = p.pirate_id" +
                        " JOIN public.stat_sets ss on p.stat_set = ss.stat_set_id" +
                        " ORDER BY random()" +
                        " LIMIT 3";
                break;
            case "MUSICIAN":
                query = "SELECT p.name, p.alias, p.sex," +
                        " music, inspiration, buff_strength," +
                        " ss.strength, ss.agility, ss.endurance, ss.intelligence, ss.charisma, ss.willpower" +
                        " FROM public.musicians" +
                        " JOIN public.pirates p on musicians.pirate_id = p.pirate_id" +
                        " JOIN public.stat_sets ss on p.stat_set = ss.stat_set_id" +
                        " ORDER BY random()" +
                        " LIMIT 3";
                break;
            case "HELMSMAN":
                query = "SELECT p.name, p.alias, p.sex," +
                        " maneuvering, precision, storm_riding," +
                        " ss.strength, ss.agility, ss.endurance, ss.intelligence, ss.charisma, ss.willpower" +
                        " FROM public.helmsmen" +
                        " JOIN public.pirates p on helmsmen.pirate_id = p.pirate_id" +
                        " JOIN public.stat_sets ss on p.stat_set = ss.stat_set_id" +
                        " ORDER BY random()" +
                        " LIMIT 3";
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

                    // --- FIX HERE: Use "strength", NOT "power" ---
                    Map<String, Integer> stats = new HashMap<>();
                    stats.put("strength", rs.getInt("strength")); // Fixed
                    stats.put("agility", rs.getInt("agility"));
                    stats.put("endurance", rs.getInt("endurance"));
                    stats.put("intelligence", rs.getInt("intelligence"));
                    stats.put("charisma", rs.getInt("charisma"));
                    stats.put("willpower", rs.getInt("willpower"));

                    // Optional: Calculate 'Power' for frontend display if needed
                    stats.put("power", (stats.get("strength") + stats.get("endurance")) / 2);

                    pirate.put("stats", stats);

                    // --- Extract Role Specific Data ---
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // retrieve the 10 events of a random event set
    private static List<Map<String, Object>> retrieveRandomEventSet() {
        List<Map<String, Object>> list = new ArrayList<>();

        String query = "SELECT event1_id, event2_id, event3_id, event4_id, event5_id," +
                " event6_id, event7_id, event8_id, event9_id, event10_id" +
                " FROM public.event_sets ORDER BY RANDOM() LIMIT 1";

        try(Connection conn = DriverManager.getConnection(DBC.url, DBC.user, DBC.password);
            PreparedStatement ps = conn.prepareStatement(query)) {

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Map<String, Object> events = new HashMap<>();
                    events.put("event1", rs.getString("event1_id"));
                    events.put("event2", rs.getString("event2_id"));
                    events.put("event3", rs.getString("event3_id"));
                    events.put("event4", rs.getString("event4_id"));
                    events.put("event5", rs.getString("event5_id"));
                    events.put("event6", rs.getString("event6_id"));
                    events.put("event7", rs.getString("event7_id"));
                    events.put("event8", rs.getString("event8_id"));
                    events.put("event9", rs.getString("event9_id"));
                    events.put("event10", rs.getString("event10_id"));

                    list.add(events);
                }
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private static void handleCORS(HttpExchange exchange) {
        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
    }

    private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        if (statusCode == 204) {
            // -1 tells the server: "No body coming, just headers"
            exchange.sendResponseHeaders(statusCode, -1);
        } else {
            // For 200, 404, etc., we send the length and the body
            byte[] bytes = response.getBytes();
            exchange.sendResponseHeaders(statusCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    // Manual JSON Parser (Simple key:value pairs only)
    private static Map<String, String> parseJsonToMap(String json) {
        Map<String, String> map = new HashMap<>();
        json = json.trim().replace("{", "").replace("}", "");
        String[] pairs = json.split(",");
        for (String pair : pairs) {
            String[] entry = pair.split(":");
            if (entry.length >= 2) {
                String key = entry[0].trim().replace("\"", "");
                String value = entry[1].trim().replace("\"", "");
                map.put(key, value);
            }
        }
        return map;
    }

    // Manual JSON Builder for Lists
    private static String convertListMapToJson(List<Map<String, Object>> list) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            json.append(convertMapToJson(list.get(i)));
            if (i < list.size() - 1) json.append(",");
        }
        json.append("]");
        return json.toString();
    }

    // Manual JSON Builder for Single Object (Handles nested Maps)
    private static String convertMapToJson(Map<String, Object> map) {
        StringBuilder json = new StringBuilder("{");
        int count = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            json.append("\"").append(entry.getKey()).append("\":");

            if (entry.getValue() instanceof String) {
                json.append("\"").append(entry.getValue()).append("\"");
            } else if (entry.getValue() instanceof Map) {
                // Recursively handle nested stats map
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