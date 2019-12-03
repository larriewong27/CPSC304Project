package connector;

import database.DatabaseManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeConnector {
    public static String currentEmployee = null;

    public static boolean employeeExist(String employeeNum) throws ConnectorErrorException {
        DatabaseManager dbm = DatabaseManager.getInstance();
        ResultSet res;

        try {
            res = dbm.queryWithPrepareStatement (
                    "SELECT * FROM Employee WHERE Employee_num = ?", employeeNum);
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
            throw new ConnectorErrorException("Could not read employee data: " + e);
        }
    }

    public static void setCurrentEmployee(String employeeNum) {
        currentEmployee = employeeNum;
    }

    public static String getEmployeeName(String employeeNum) throws ConnectorErrorException {
        DatabaseManager dbm = DatabaseManager.getInstance();
        ResultSet res;
        String employeeName = "";
        try {
            res = dbm.queryWithPrepareStatement(
                    "SELECT E.First_name, E.Last_name " +
                            "FROM Employee E " +
                            "WHERE E.Employee_num = ?", employeeNum);
            while (res.next()) {
                employeeName = res.getString(1) + " " + res.getString(2);

            }
        }
        catch(SQLException e) {
            throw new ConnectorErrorException(e.getMessage());
        }
        return employeeName;
    }

    protected static void addEmployee (String employee_num, String fname, String lname, String role) throws ConnectorErrorException {
        DatabaseManager dbm = DatabaseManager.getInstance();
        try {
            dbm.updateWithPrepareStatement(
                    "INSERT INTO Employee(Employee_num, First_name, Last_name, Role)"
                            + " VALUES(?, ?, ?, ?)",
                    employee_num, fname, lname, role);
        }
        catch (SQLException e) {
            throw new ConnectorErrorException(e.getMessage());
        }
    }

    public static void deleteEmployee(String employee_num) throws ConnectorErrorException {
        DatabaseManager dbm = DatabaseManager.getInstance();
        try {
            dbm.updateWithPrepareStatement("DELETE FROM Employee"
                    +" WHERE employee_num = ?", employee_num );
            //TODO: set AskForEmployee to Default concierge
        } catch (SQLException e) {
            throw new ConnectorErrorException(e.getMessage());
        }
    }

    public static void updateEmployeeData (String employeeNum, String attr, Object newData) throws ConnectorErrorException {
        if (employeeNum == null)
            throw new NullPointerException("Employee_num cannot be null.");

        DatabaseManager dbm = DatabaseManager.getInstance();

        try {
            dbm.updateWithPrepareStatement(
                    "UPDATE Employee SET " + attr + " = ?" +
                            "WHERE Employee_num = ?", newData, employeeNum);
        }
        catch (SQLException e) {
            throw new ConnectorErrorException(e.getMessage());
        }
    }
}
