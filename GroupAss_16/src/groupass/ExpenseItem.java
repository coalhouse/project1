/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package groupass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author Proficience
 */
public class ExpenseItem {

    private long expenseItemID;
    private long expenseID;
    private long costCentreID;
    private double amount;
    private String description;

    public ExpenseItem(long expenseItemID, long expenseID, long costCentreID, double amount, String description) {
        this.expenseItemID = expenseItemID;
        this.expenseID = expenseID;
        this.costCentreID = costCentreID;
        this.amount = amount;
        this.description = description;
    }

    /**
     * @return the expenseItemID
     */
    public long getExpenseItemID() {
        return expenseItemID;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    public boolean addExpenseItem() {
        System.out.println("");
        
        String query = "INSERT INTO EXPENSEITEM (EXPENSEITEMID, EXPENSEID, COSTCENTREID, AMOUNT, DESCRIPTION) "
                + "VALUES (ID.nextval, " + expenseID + ", " + costCentreID + ", " + amount + ", '" + description + "')";
        try {
            Connection con = DatabaseConnector.getConnector();
            PreparedStatement ps = con.prepareStatement(query);   
            
            ps.executeUpdate();
            DatabaseConnector.closeConnection(con, ps, null);
            
            return true;            
        } catch (SQLException e) {
            System.out.println("Unable to connect to database, process the "
                    + "results or close the connections: ");
            System.out.println(e.toString());
        }
        return false;    
    }    
    
    public Expense getExpense() {
        // Expense variable was renamed from 'e' to 'exp' as it clashed
        // with SQLException
        Expense exp = null; // placeholder
        String query = "";
        try {
            Connection con = DatabaseConnector.getConnector();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();

            // Insert function here

            DatabaseConnector.closeConnection(con, ps, null);
        } catch (SQLException e) {
            System.out.println("Unable to connect to database, process the "
                    + "results or close the connections: ");
            System.out.println(e.toString());
        }
        return exp;
    }

    public CostCentre getCostCentre() {
        CostCentre ccResult = null; // placeholder
        String query = "SELECT cc.COSTCENTREID, cc.NAME, cc.STARTDATE, cc.ENDDATE, cc.LIMITTYPE, cc.LIMITAMOUNT FROM COSTCENTRE cc, EXPENSEITEM ei "
                + "WHERE cc.COSTCENTREID = EI.COSTCENTREID AND EI.EXPENSEITEMID = '" + expenseItemID + "'";
        try {
            Connection con = DatabaseConnector.getConnector();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int resultID = resultSet.getInt(1);
                String resultName = resultSet.getString(2);
                GregorianCalendar resultStartDate = new GregorianCalendar();
                Date dateStart = resultSet.getDate(3);
                int sYear = dateStart.getYear();
                int sMonth = dateStart.getMonth();
                int sDay = dateStart.getDate();
                resultStartDate.set(sYear, sMonth, sDay);
                GregorianCalendar resultEndDate = null; // test this!
                Date dateEnd = resultSet.getDate(4);
                int eYear = dateEnd.getYear();
                int eMonth = dateEnd.getMonth();
                int eDay = dateEnd.getDate();
                resultEndDate.set(eYear, eMonth, eDay);
                boolean resultIsHard = true;
                double resultLimitAmount = resultSet.getDouble(6);
                if ("Soft".equals(resultSet.getString(5))) {
                    resultIsHard = false;
                }
                ccResult = new CostCentre(resultID, resultName, resultStartDate, resultEndDate, resultIsHard, resultLimitAmount);
                                
            }

            DatabaseConnector.closeConnection(con, ps, null);
        } catch (SQLException e) {
            System.out.println("Unable to connect to database, process the "
                    + "results or close the connections: ");
            System.out.println(e.toString());
        }
        return ccResult;
    }

    public boolean setAmount(double amount) {
        this.amount = amount;
        String query = "UPDATE EXPENSEITEM SET AMOUNT = ? WHERE EXPENSEITEMID = '" + expenseItemID + "'";
        try {
            Connection con = DatabaseConnector.getConnector();
            PreparedStatement ps = con.prepareStatement(query);
            
            ps.setDouble(4, amount);
            
            ps.executeUpdate();
            DatabaseConnector.closeConnection(con, ps, null);
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Unable to connect to database, process the "
                    + "results or close the connections: ");
            System.out.println(e.toString());
        }
        return false;
    }

    public boolean setDescription(String description) {
        this.description = description;
        String query = "UPDATE EXPENSEITEM SET DESCRIPTION = ? WHERE EXPENSEITEMID = '" + expenseItemID + "'";
        try {
            Connection con = DatabaseConnector.getConnector();
            PreparedStatement ps = con.prepareStatement(query);
            
            ps.setString(5, description);
            
            ps.executeUpdate();
            DatabaseConnector.closeConnection(con, ps, null);
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Unable to connect to database, process the "
                    + "results or close the connections: ");
            System.out.println(e.toString());
        }
        return false;
    }
}
