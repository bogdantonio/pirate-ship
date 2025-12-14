package database;

import crew.Crew;
import pirateSubclasses.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

}
