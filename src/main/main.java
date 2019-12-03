package main;

import connector.ConnectorErrorException;
import connector.GuestConnector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class main {
    public static void main(String[] args)
    {
        try {
            ResultSet res = GuestConnector.getGuestUsedAllServiceTypes();

            while (res.next()) {
                System.out.println(res.getString(1) + res.getString(2) + res.getString(3));
            }
        } catch (ConnectorErrorException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
