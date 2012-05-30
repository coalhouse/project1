
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
public class Expense {

    private long expenseID;
    private GregorianCalendar date;
    private String payee;
    private String description;

    public Expense(int expenseID, GregorianCalendar date, String payee, String description) {
        this.expenseID = expenseID;
        this.date = date;
        this.payee = payee;
        this.description = description;
    }

    public long getExpenseID() {
        return expenseID;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public String getPayee() {
        return payee;
    }

    public String getDescription() {
        return description;
    }
    
    public boolean addExpense() {        
        String query = "INSERT INTO EXPENSE (EXPENSEID, PAYEE, DESCRIPTION, EXPENSEDATE) "
                + "VALUES ('" + expenseID + "', '" + payee + "', '" + description + "', " 
                + "to_date('" + date.get(Calendar.YEAR) + "-" + date.get(Calendar.MONTH) + "-" + date.get(Calendar.DAY_OF_MONTH) + "', 'yyyy-mm-dd'))";
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
    
    public boolean setDate(GregorianCalendar newDate) {
        this.date = newDate;
        String query = "UPDATE EXPENSE SET EXPENSEDATE = to_date('" + newDate.get(Calendar.YEAR) + "-" + newDate.get(Calendar.MONTH) + "-" + newDate.get(Calendar.DAY_OF_MONTH) + "', 'yyyy-mm-dd')" 
                + "WHERE EXPENSEID= '" + expenseID + "'";
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

    public boolean setPayee(String newPayee) {
        this.payee = newPayee;
        String query = "UPDATE EXPENSE SET PAYEE = '" + newPayee + "' WHERE EXPENSEID = '" + expenseID + "'";
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

    public boolean setDescription(String newDescription) {
        this.description = newDescription;
        String query = "UPDATE EXPENSE SET DESCRIPTION = '" + newDescription + "' WHERE EXPENSEID = '" + expenseID + "'";
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

    public List<ExpenseItem> getExpenseItems() {
        List<ExpenseItem> expenseItems = new ArrayList<ExpenseItem>();
        
        String query = "SELECT * FROM EXPENSEITEM ei, EXPENSE e WHERE e.EXPENSEID = ei.EXPENSEID AND e.EXPENSEID = '" + expenseID + "'";
        try {
            Connection con = DatabaseConnector.getConnector();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int eiID = resultSet.getInt(1);
                int eID = resultSet.getInt(2);
                int ccID = resultSet.getInt(3);
                double amt = resultSet.getDouble(4);
                String desc = resultSet.getString(5);
                expenseItems.add(new ExpenseItem(eiID, eID, ccID, amt, desc));
            }

            DatabaseConnector.closeConnection(con, ps, null);
        } catch (SQLException e) {
            System.out.println("Unable to connect to database, process the "
                    + "results or close the connections: ");
            System.out.println(e.toString());
        }
        return expenseItems;
    }

    public double getTotalAmount() {
        ArrayList<Double> total = new ArrayList<Double>();        
        double totalAmount = 0;
        String query = ("SELECT ei.AMOUNT FROM EXPENSEITEM ei, EXPENSE e WHERE ei.EXPENSEID = e.EXPENSEID AND e.EXPENSEID = '" + expenseID + "'");

        try {
            Connection con = DatabaseConnector.getConnector();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet == null) {
                total.add(0.0);            
            }
            while (resultSet.next()) {
                totalAmount =+ resultSet.getDouble(1);
                total.add(totalAmount);
            }            
            DatabaseConnector.closeConnection(con, ps, null);
        } catch (SQLException e) {
            System.out.println("Unable to connect to database, process the "
                    + "results or close the connections: ");
            System.out.println(e.toString());
        }
        return totalAmount;
    }

    public boolean deleteExpense() {
        String query = "DELETE FROM EXPENSE WHERE EXPENSEID = " + expenseID;
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

    public boolean deleteExpenseItem(long expenseID) {
        String query = "DELETE FROM EXPENSEITEM WHERE EXPENSEID = " + expenseID;
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
    
    public static List<Expense> getExpenses() {
        ArrayList<Expense> expenseList = new ArrayList<Expense>();
        String query = ("SELECT * FROM EXPENSE e");

        try {
            Connection con = DatabaseConnector.getConnector();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                int expenseID = resultSet.getInt(1);
                Date d = resultSet.getDate(4);
                int year = d.getYear();
                int month = d.getMonth();
                int date1 = d.getDate();
                GregorianCalendar expenseDate = new GregorianCalendar(year, month, date1);
//                expenseDate.setTime(resultSet.getDate(2)); // test this!         
                String expensePayee = resultSet.getString(2);
                String expenseDesc = resultSet.getString(3);
                expenseList.add(new Expense(expenseID, expenseDate, expensePayee, expenseDesc));
            }

            DatabaseConnector.closeConnection(con, ps, null);
        } catch (SQLException e) {
            System.out.println("Unable to connect to database, process the "
                    + "results or close the connections: ");
            System.out.println(e.toString());
        }
        return expenseList;
    }
    
        public static Expense getExpenseByID(long expenseId) {
        Expense returnedExpense = null;
        String query = ("SELECT * FROM EXPENSE e WHERE e.EXPENSEID = " + expenseId);

        try {
            Connection con = DatabaseConnector.getConnector();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                int expenseID = resultSet.getInt(1);
                Date d = resultSet.getDate(4);
                int year = d.getYear();
                int month = d.getMonth();
                int date1 = d.getDate();
                GregorianCalendar expenseDate = new GregorianCalendar(year, month, date1);      
                String expensePayee = resultSet.getString(2);
                String expenseDesc = resultSet.getString(3);
                returnedExpense = new Expense(expenseID, expenseDate, expensePayee, expenseDesc);
            }

            DatabaseConnector.closeConnection(con, ps, null);
        } catch (SQLException e) {
            System.out.println("Unable to connect to database, process the "
                    + "results or close the connections: ");
            System.out.println(e.toString());
        }
        return returnedExpense;
    }
}
