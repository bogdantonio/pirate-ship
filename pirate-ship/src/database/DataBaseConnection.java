package database;

import java.sql.*;

public class DataBaseConnection{
    public static void main(String[] args) throws SQLException{
        DataBaseCredentials DBC = new DataBaseCredentials();

        Connection connection = DriverManager.getConnection(DBC.url, DBC.user, DBC.password);
        Statement statement = connection.createStatement();

        System.out.println(connection);
        String tableName = "public.stat_sets";

        String query = "INSERT into "+tableName+" (stat_set_id, strength, agility, endurance, intelligence, charisma, willpower)"
                                    +" VALUES(?,?,?,?,?,?,?)";

        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setInt(1,1001);
        preparedStmt.setInt(2,60);
        preparedStmt.setInt(3,71);
        preparedStmt.setInt(4,89);
        preparedStmt.setInt(5,69);
        preparedStmt.setInt(6,82);
        preparedStmt.setInt(7,91);

        System.out.println(preparedStmt);
        preparedStmt.executeUpdate();

        System.out.println(query);
    }
}
