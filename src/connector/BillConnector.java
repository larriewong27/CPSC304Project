package connector;

import database.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BillConnector {

    // Aggregation
    public static double aggregateBill (String fromDate, String toDate, String aggrOp) throws ParseException, ConnectorErrorException, InvalidInputException {

        SimpleDateFormat dfm = new SimpleDateFormat("MM/dd/yy");
        java.util.Date utilFromDate = dfm.parse(fromDate);
        java.util.Date utilToDate = dfm.parse(toDate);

        if(utilFromDate.after(utilToDate)){
            throw new InvalidInputException("Invalid dates");
        }

        java.sql.Date sqlFromDate = new java.sql.Date(utilFromDate.getTime());
        java.sql.Date sqlToDate = new java.sql.Date(utilToDate.getTime());

        DatabaseManager dbm = DatabaseManager.getInstance();
        ResultSet res;

        try {
            res = dbm.queryWithPrepareStatement(
                    "SELECT " + aggrOp+ "(amount) AS " + aggrOp +
                    " FROM Bill " +
                    "WHERE dateGenerated <= ? AND dateGenerated >= ?",
                    sqlToDate, sqlFromDate);

            if (res.next()) {
                return res.getDouble(1);
            }
        }
        catch(SQLException e) {
            throw new ConnectorErrorException(e.getMessage());
        }
        return -1;
    }

    // Group By
    public static ResultSet getBillForEachRoomType() throws ConnectorErrorException {
        DatabaseManager dbm = DatabaseManager.getInstance();
        ResultSet res;

        try {
            res = dbm.query("SELECT BK.TypeName, avg(B.amount), min(B.amount), max(B.amount) " +
                            "FROM Booking BK, Bill B " +
                            "WHERE BK.Booking_num = B.Booking_num " +
                            "GROUP BY BK.TypeName");
        } catch (SQLException e) {
            throw new ConnectorErrorException(e.getMessage());
        }
        return res;
    }

    // Nested Aggregation with Group By
    public static ResultSet getMBillForEachRoomType(String aggrOp, String op) throws ConnectorErrorException {
        DatabaseManager dbm = DatabaseManager.getInstance();
        ResultSet res;

        try {
            res = dbm.query(
                    "SELECT BK.TypeName, " + aggrOp + "(B.amount) " +
                            "FROM Booking BK, Bill B " +
                            "WHERE BK.Booking_num = B.Booking_num " +
                            "GROUP BY BK.TypeName " +
                            "HAVING avg(B.amount) " + op +
                            " ALL (SELECT avg(B2.amount) " +
                            "FROM Booking BK2, Bill B2 " +
                            "WHERE BK2.Booking_num = B2.Booking_num " +
                            "GROUP BY BK2.TypeName)");
        } catch (SQLException e) {
            throw new ConnectorErrorException(e.getMessage());
        }
        return res;
    }

    public static ResultSet getSBillForEachRoomType(String aggrOp) throws ConnectorErrorException {
        DatabaseManager dbm = DatabaseManager.getInstance();
        ResultSet res = null;

        try {
            dbm.query("CREATE VIEW A AS " +
                    "SELECT " + aggrOp + "(B.amount) AS avg " +
                    "FROM Booking BK, Bill B " +
                    "WHERE BK.Booking_num = B.Booking_num " +
                    "GROUP BY BK.TypeName");

            res = dbm.query("SELECT sum(A.avg) FROM A");
            dbm.query("DROP VIEW A");
        }
        catch (SQLException e) {
            throw new ConnectorErrorException(e.getMessage());
        }
        return res;
    }
}
