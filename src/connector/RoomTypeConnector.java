package connector;

import database.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class RoomTypeConnector {
    public static final String ATTR_NAME = "Name";
    public static final String ATTR_BASEPRICE = "BasePrice";
    public static final String ATTR_ACCESSIBILITY = "Accessibility";
    public static final String ATTR_BEDTYPE = "BedType";
    public static final String ATTR_MAXPPL = "MaxPeople";

    public static void addRoomType (String type_name, String basePrice, Boolean accessibility, String bedType) throws ConnectorErrorException, BedTypeNotFoundException {
        DatabaseManager dbm = DatabaseManager.getInstance();
        try {
            if (!bedTypeExists(bedType)) {
                throw new BedTypeNotFoundException("Bed type: " + bedType + "was not found.");
            }
            else {
                dbm.updateWithPrepareStatement("INSERT INTO RoomType1 " +
                        "(" + ATTR_NAME + ", " + ATTR_BASEPRICE + ", " + ATTR_ACCESSIBILITY + ", " +
                        ATTR_BEDTYPE + ")" +
                        " VALUES (?, ?, ?, ?)", type_name, basePrice, accessibility, bedType);
            }
        }
        catch (SQLException e) {
            throw new ConnectorErrorException(e.getMessage());
        }
    }

    private static boolean bedTypeExists(String bedType) throws ConnectorErrorException{
        DatabaseManager dbm = DatabaseManager.getInstance();
        ResultSet res;

        try {
            res = dbm.queryWithPrepareStatement (
                    "SELECT * FROM RoomType1 WHERE " + ATTR_BEDTYPE + " = ?", bedType);
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
            throw new ConnectorErrorException("Could not read bed type data: " + e);
        }
    }

    public static void addBedType(String bedType, int maxPeople) throws ConnectorErrorException{
        DatabaseManager dbm = DatabaseManager.getInstance();
        try {
            dbm.updateWithPrepareStatement("INSERT INTO RoomType2 " +
                    "(" + ATTR_BEDTYPE + ", " + ATTR_MAXPPL + ")" +
                    " VALUES (?, ?)", bedType, maxPeople);
        } catch (SQLException e) {
            throw new ConnectorErrorException(e.getMessage());
        }
    }

    public static boolean checkRoomTypeAvailability (String checkInDate, String checkOutDate, String selectedRoomType,
                                              Integer selectedAccessibility, String selectedBedType) throws ConnectorErrorException {
        DatabaseManager dbm = DatabaseManager.getInstance();

        SimpleDateFormat dfm = new SimpleDateFormat("dd/MM/yy");
        java.util.Date utilFromDate = null;
        java.util.Date utilToDate = null;
        try {
            utilFromDate = dfm.parse(checkInDate);
            utilToDate = dfm.parse(checkOutDate);
        } catch (ParseException e) {
            System.out.println("Invalid dates.");
        }

        if (utilFromDate.after(utilToDate)) {
            System.out.println("Invalid dates.");
        }

        Calendar fromDate = new GregorianCalendar();
        fromDate.setTime(utilFromDate);
        Calendar toDate = new GregorianCalendar();
        toDate.setTime(utilToDate);

        for (Date curDate = fromDate.getTime(); fromDate.before(toDate); fromDate.add(Calendar.DATE, 1), curDate = fromDate.getTime()) {
            java.sql.Date sqlCurDate = new java.sql.Date(curDate.getTime());
            try {
                ResultSet res1 = dbm.queryWithPrepareStatement(
                        "SELECT count(*) FROM Booking " +
                                "WHERE \"From\" <= ? AND \"To\" > ? AND TypeName = ? " +
                                "AND Accessibility = ? AND BedType = ?",
                        sqlCurDate, sqlCurDate, selectedRoomType, selectedAccessibility, selectedBedType);

                int numBooked = 0;
                if (res1.next()) {
                    numBooked = res1.getInt(1);
                    System.out.println(numBooked);
                }

                ResultSet res2 = dbm.queryWithPrepareStatement(
                        "SELECT count(*) From RoomHas " +
                                "WHERE TypeName = ? AND " +
                                "Accessibility = ? AND BedType = ?",
                        selectedRoomType, selectedAccessibility, selectedBedType);

                int totalRoomNumOfSelectedType = 0;
                if(res2.next()) {
                    totalRoomNumOfSelectedType = res2.getInt(1);
                    System.out.println(totalRoomNumOfSelectedType);
                }

                if (totalRoomNumOfSelectedType == numBooked)
                    return false;
            }
            catch(SQLException e) {
                throw new ConnectorErrorException(e.getMessage());
            }
        }
        return true;
    }


    public static class BedTypeNotFoundException extends Exception {
        BedTypeNotFoundException(String cause) {
            super(cause);
        }
    }
}

