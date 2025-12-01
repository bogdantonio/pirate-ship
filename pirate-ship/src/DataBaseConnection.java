import java.sql.*;

public class DataBaseConnection{
    public static void main(String[] args) throws SQLException{
        DataBaseCredentials DBC = new DataBaseCredentials();

        Connection connection = DriverManager.getConnection(DBC.url, DBC.user, DBC.password);
        Statement statement = connection.createStatement();

        System.out.println(connection);
        String tableName = "public.pirates";

        String query = "INSERT into "+tableName+" (pirate_id, name, alias, role, strength, agility, endurance, intelligence, charisma, willpower)"
                                    +" VALUES(?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setInt(1,121);
        preparedStmt.setString(2,"Usopp");
        preparedStmt.setString(3,"Sogeking");
        preparedStmt.setString(4, "Sniper");
        preparedStmt.setInt(5,43);
        preparedStmt.setInt(6,56);
        preparedStmt.setInt(7,25);
        preparedStmt.setInt(8,60);
        preparedStmt.setInt(9,39);
        preparedStmt.setInt(10,55);

        System.out.println(preparedStmt);
        preparedStmt.executeUpdate();

        System.out.println(query);
    }
}
