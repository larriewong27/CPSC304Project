package connector;

import database.DatabaseManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuestConnector {
    public static String currentGuest = null;

    public static boolean guestExist(String guestNum) throws ConnectorErrorException {
        ResultSet res;
        try {
            DatabaseManager dbm = DatabaseManager.getInstance();
            res = dbm.queryWithPrepareStatement (
                    "SELECT * FROM Guest WHERE guest_num = ?", guestNum);
        }
        catch(SQLException e) {
            throw new ConnectorErrorException(e.getMessage());
        }

        try {
            if (res.next()) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (SQLException e) {
            throw new ConnectorErrorException("Could not read guest data: " + e);
        }
    }

    public static void setCurrentGuest(String guestNum) {
        currentGuest = guestNum;
    }

    public static ResultSet getGuestInfo(String selectString, String projectAttr) throws ConnectorErrorException {
        DatabaseManager dbm = DatabaseManager.getInstance();
        ResultSet res;
        try {
            res = dbm.queryWithPrepareStatement(
                    "SELECT " + projectAttr + " FROM Guest " + selectString);
        } catch (SQLException e) {
            throw new ConnectorErrorException(e.getMessage());
        }
        return res;
    }

    public static String getGuestName(String guestNum) throws ConnectorErrorException {
        DatabaseManager dbm = DatabaseManager.getInstance();
        ResultSet res;
        String guestName = "";
        try {
            res = dbm.queryWithPrepareStatement(
                    "SELECT G.First_name, G.Last_name " +
                            "FROM Guest G " +
                            "WHERE G.Guest_num = ?", guestNum);
            while (res.next()) {
                guestName = res.getString(1) + " " + res.getString(2);

            }
        }
        catch(SQLException e) {
            throw new ConnectorErrorException(e.getMessage());
        }
        return guestName;
    }

    protected static void addGuest (String guest_num, String fname, String lname, String card_num, String addr, String phone) throws ConnectorErrorException {
        DatabaseManager dbm = DatabaseManager.getInstance();
        try {
            dbm.updateWithPrepareStatement(
                    "INSERT INTO Guest(Guest_num, First_name, Last_name, ID, CreditCard_num, Address, Phone)"
                    + " VALUES(?, ?, ?, ?, ?, ?, ?)",
                    guest_num, fname, lname, card_num, addr, phone);
        }
        catch (SQLException e) {
            throw new ConnectorErrorException(e.getMessage());
        }
    }

    public static boolean deleteGuest(String guest_num) throws ConnectorErrorException {
        DatabaseManager dbm = DatabaseManager.getInstance();
        Connection con = dbm.getConnection();

        try {
            con.setAutoCommit(false);
        } catch (SQLException e) {
            throw new ConnectorErrorException(e.getMessage());
        }

        int numRowChanged = 0;
        try {
            ResultSet res = null;
            String bookingNumForThisGuest = "";
            res = dbm.queryWithPrepareStatement(
                    "SELECT Booking_num FROM Booking WHERE Guest_num = ?", guest_num);
            while(res.next()) {
                bookingNumForThisGuest = res.getString(1);
            }
            dbm.updateWithPrepareStatement("DELETE FROM Bill WHERE Booking_num = ?", bookingNumForThisGuest);
            dbm.updateWithPrepareStatement("DELETE FROM AsksFor WHERE Booking_num = ?", bookingNumForThisGuest);
            dbm.updateWithPrepareStatement("DELETE FROM Booking WHERE Guest_num = ?", guest_num);
            numRowChanged = dbm.updateWithPrepareStatement("DELETE FROM Guest WHERE Guest_num = ?", guest_num);
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                throw new ConnectorErrorException(e.getMessage());
            }
            throw new ConnectorErrorException(e.getMessage());
        }
        finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                throw new ConnectorErrorException(e.getMessage());
            }
            return (numRowChanged == 1);
        }
    }

    public static boolean updateGuestData (String guestNum, String attr, Object newData) throws ConnectorErrorException {
        if (guestNum == null)
            throw new NullPointerException("Guest_num cannot be null.");

        DatabaseManager dbm = DatabaseManager.getInstance();
        int res;
        try {
            res = dbm.updateWithPrepareStatement(
                    "UPDATE Guest SET " + attr + " = ? " +
                            "WHERE Guest_num = ?", newData, guestNum);

            return (res == 1);
        }
        catch (SQLException e) {
            throw new ConnectorErrorException(e.getMessage());
        }
    }

    public static ResultSet getALLGuestInfo() throws ConnectorErrorException {
        DatabaseManager dbm = DatabaseManager.getInstance();
        ResultSet res;
        try {
            res = dbm.queryWithPrepareStatement(
                    "SELECT * " +
                            "FROM Guest G ");
        }
        catch(SQLException e) {
            throw new ConnectorErrorException(e.getMessage());
        }
        // return guestName;
        return res;
    }

    // Division
    public static ResultSet getGuestUsedAllServiceTypes () throws ConnectorErrorException {
        DatabaseManager dbm = DatabaseManager.getInstance();
        ResultSet res;
        try {
            res = dbm.query(
                    "SELECT G.Guest_num, G.First_name, G.Last_name " +
                            "FROM Booking B, Guest G " +
                            "WHERE B.Guest_num = G.Guest_num AND B.PromoCode = 0 AND " +
                                    "NOT EXISTS ((SELECT S.ServiceType FROM Service S) " +
                                                "MINUS " +
                                                "(SELECT A.ServiceType " +
                                                "FROM AsksFor A " +
                                                "WHERE A.Booking_num = B.Booking_num))");
        }
        catch(SQLException e) {

            throw new ConnectorErrorException(e.getMessage());
        }
        return res;
    }
}
