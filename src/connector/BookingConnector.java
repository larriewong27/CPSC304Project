package connector;

import database.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.String;


public class BookingConnector {
    //Join
    public static ResultSet getbillsInfo (String BookingNum) throws ConnectorErrorException {
        DatabaseManager dbm = DatabaseManager.getInstance();
        ResultSet res;
        try {
            res = dbm.queryWithPrepareStatement(
                    "SELECT B.bill_num, B.amount, G.First_name, G.Last_name, B.dategenerated " +
                            "From Booking BK JOIN Bill B ON BK.Booking_num = B.Booking_num " +
                            "JOIN Guest G ON BK.Guest_num = G.Guest_num " +
                            "Where BK.booking_num = ?", BookingNum);
        }
        catch(SQLException e) {
            throw new ConnectorErrorException(e.getMessage());
        }
        return res;
    }

}
