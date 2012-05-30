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
public class CostCentre {

    private int costCentreId;
    private String name;
    private GregorianCalendar startDate;
    private GregorianCalendar endDate;
    private boolean hardLimit;
    private double limitAmount;

    public CostCentre(int costCentreId, String name, GregorianCalendar startDate, GregorianCalendar endDate, boolean hardLimit, double limitAmount) {
        this.costCentreId = costCentreId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hardLimit = hardLimit;
        this.limitAmount = limitAmount;
    }

    /**
     * @return the costCentreId
     */
    public int getCostCentreId() {
        return costCentreId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the startDate
     */
    public GregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * @return the endDate
     */
    public GregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * @return the limitAmount
     */
    public double getLimitAmount() {
        return limitAmount;
    }

    public boolean isHardLimit() {
        return hardLimit;
    }

    public boolean isActive() {
        GregorianCalendar sysDate = new GregorianCalendar();
        if (sysDate.after(startDate) && sysDate.before(endDate)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean setName(String name) {
        this.name = name;
        String query = "UPDATE COSTCENTRE SET NAME = '" + name + "' WHERE COSTCENTREID = '" + costCentreId + "'";
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

    public boolean setStartDate(GregorianCalendar startDate) {
        this.startDate = startDate;
        String query = "UPDATE COSTCENTRE SET STARTDATE = to_date('" + startDate.get(Calendar.YEAR) + "-" + startDate.get(Calendar.MONTH) + "-" + startDate.get(Calendar.DAY_OF_MONTH) + "', 'yyyy-mm-dd')"
                + "WHERE COSTCENTREID = '" + costCentreId + "'";
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

    public boolean setEndDate(GregorianCalendar endDate) {
        this.endDate = endDate;
        String query = "UPDATE COSTCENTRE SET ENDDATE = to_date('" + endDate.get(Calendar.YEAR) + "-" + endDate.get(Calendar.MONTH) + "-" + endDate.get(Calendar.DAY_OF_MONTH) + "', 'yyyy-mm-dd')"
                + "WHERE COSTCENTREID = '" + costCentreId + "'";
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

    public boolean setHardLimit(boolean isHard) {
        this.hardLimit = hardLimit;
        String limit;
        if (isHard) {
            limit = "Hard";
        } else {
            limit = "Soft";
        }
        String query = "UPDATE COSTCENTRE SET LIMITTYPE = '" + limit + "' WHERE COSTCENTREID = '" + costCentreId + "'";
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

    public boolean setLimitAmount(double limitAmount) {
        this.limitAmount = limitAmount;
        String query = "UPDATE COSTCENTRE SET LIMITAMOUNT = '" + limitAmount + "' WHERE COSTCENTREID = '" + costCentreId + "'";
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

    public boolean delete() {
        String query = "DELETE FROM COSTCENTRE WHERE COSTCENTREID = '" + costCentreId + "'";
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

    public static double getTotalExpenses(int costCentreID) {
        ArrayList<Double> expenses = new ArrayList<Double>();
        double totalExpenses = 0;
        String query = ("SELECT AMOUNT FROM EXPENSEITEM ei WHERE ei.COSTCENTREID = '" + costCentreID + "'");

        try {
            Connection con = DatabaseConnector.getConnector();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                totalExpenses += resultSet.getDouble(1);
                expenses.add(totalExpenses);
            }
            if (resultSet == null) {
                expenses.add(0.0);
            }
            DatabaseConnector.closeConnection(con, ps, null);
        } catch (SQLException e) {
            System.out.println("Unable to connect to database, process the "
                    + "results or close the connections: ");
            System.out.println(e.toString());
        }

        return totalExpenses;
    }

    public double getAvailableFunds() {
        double availableFunds = this.limitAmount - this.getTotalExpenses(costCentreId);
        return availableFunds;
    }

    public List<Expense> getExpenses() {
        ArrayList<Expense> expenseList = new ArrayList<Expense>();
        String query = ("SELECT DISTINCT e.EXPENSEID, e.PAYEE, e.DESCRIPTION, e.EXPENSEDATE FROM EXPENSE e, EXPENSEITEM ei"
                + " WHERE ei.COSTCENTREID = '" + costCentreId + "' AND e.EXPENSEID = ei.EXPENSEID");

        try {
            Connection con = DatabaseConnector.getConnector();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int expenseID = resultSet.getInt(1);
                GregorianCalendar expenseDate = new GregorianCalendar();
                expenseDate.setTime(resultSet.getDate(4)); // test this!         
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

    public static List<CostCentre> getActiveCostCentres() {
        List<CostCentre> activeCostCentres = new ArrayList<CostCentre>();
        String query = "SELECT * FROM COSTCENTRE "
                + "WHERE STARTDATE < sysdate AND ENDDATE > sysdate";
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
                GregorianCalendar resultEndDate = new GregorianCalendar(); // test this!
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
                activeCostCentres.add(new CostCentre(resultID, resultName, resultStartDate, resultEndDate, resultIsHard, resultLimitAmount));
            }

            DatabaseConnector.closeConnection(con, ps, null);
        } catch (SQLException e) {
            System.out.println("Unable to connect to database, process the "
                    + "results or close the connections: ");
            System.out.println(e.toString());
        }
        return activeCostCentres;
    }

    public static List<CostCentre> getCostCentresExceedingLimit() {
        List<CostCentre> allCostCentres = new ArrayList<CostCentre>();
        List<CostCentre> costCentresExceedingLimit = new ArrayList<CostCentre>();
        String query = "SELECT * FROM COSTCENTRE";
        try {
            Connection con = DatabaseConnector.getConnector();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int resultID = resultSet.getInt(1);
                String resultName = resultSet.getString(2);
                GregorianCalendar resultStartDate = new GregorianCalendar(); // test this!
                resultStartDate.setTime(resultSet.getDate(3));
                GregorianCalendar resultEndDate = new GregorianCalendar(); // test this!
                resultEndDate.setTime(resultSet.getDate(4));
                boolean resultIsHard = true;
                double resultLimitAmount = resultSet.getDouble(6);
                if ("Soft".equals(resultSet.getString(5))) {
                    resultIsHard = false;
                }
                allCostCentres.add(new CostCentre(resultID, resultName, resultStartDate, resultEndDate, resultIsHard, resultLimitAmount));
            }

            for (int i = 0; i < allCostCentres.size(); i++) {
                if (allCostCentres.get(i).getAvailableFunds() < 0.0) {
                    costCentresExceedingLimit.add(allCostCentres.get(i));
                }
            }

            DatabaseConnector.closeConnection(con, ps, null);
        } catch (SQLException e) {
            System.out.println("Unable to connect to database, process the "
                    + "results or close the connections: ");
            System.out.println(e.toString());
        }
        return costCentresExceedingLimit;
    }

    public boolean addCostCentre() {
        String limit = "Hard";

        if (isHardLimit() == false) {
            limit = "Soft";
        }

        String query = "INSERT INTO COSTCENTRE (COSTCENTREID, NAME, STARTDATE, ENDDATE, LIMITTYPE, LIMITAMOUNT) "
                + "VALUES (" + costCentreId + ", '" + name + "', "
                + "to_date('" + startDate.get(Calendar.YEAR) + "-" + startDate.get(Calendar.MONTH) + "-" + startDate.get(Calendar.DAY_OF_MONTH) + "', 'yyyy-mm-dd'), "
                + "to_date('" + endDate.get(Calendar.YEAR) + "-" + endDate.get(Calendar.MONTH) + "-" + endDate.get(Calendar.DAY_OF_MONTH) + "', 'yyyy-mm-dd'), '"
                + limit + "'," + limitAmount + ")";
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

    public static CostCentre getCostCentreByID(int costCentreId) {
        CostCentre returnedCostCentre = null;
        String query = ("SELECT * FROM COSTCENTRE WHERE COSTCENTREID = " + costCentreId);

        try {
            Connection con = DatabaseConnector.getConnector();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int ccID = resultSet.getInt(1);
                String ccName = resultSet.getString(2);

                Date sd = resultSet.getDate(3);
                int startYear = sd.getYear();
                int startMonth = sd.getMonth();
                int startDay = sd.getDate();
                GregorianCalendar startDate = new GregorianCalendar();
                startDate.setTime(sd);

                Date ed = resultSet.getDate(4);
                //int endYear = sd.getYear();
                //int endMonth = sd.getMonth();
                //int endDay = sd.getDate();
                GregorianCalendar endDate = new GregorianCalendar();
                endDate.setTime(ed);

                String limitType = resultSet.getString(5);
                boolean isHard;
                if (limitType.equalsIgnoreCase("Hard")) {
                    isHard = true;
                } else {
                    isHard = false;
                }
                Double limitAmount = resultSet.getDouble(6);
                returnedCostCentre = new CostCentre(ccID, ccName, startDate, endDate, isHard, limitAmount);

            }

            DatabaseConnector.closeConnection(con, ps, null);
        } catch (SQLException e) {
            System.out.println("Unable to connect to database, process the "
                    + "results or close the connections: ");

            System.out.println(e.toString());

        }
        return returnedCostCentre;
    }
}
