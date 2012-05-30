/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package groupass;

import java.sql.*;

/**
 *
 * @author Jasmine
 */
public class DatabaseConnector {

    private static String SERVER = "sage.business.unsw.edu.au";
    private static int PORT = 1521;
    private static String SID = "orcl02";
    private static String USERNAME = "z3375795";
    private static String PASSWORD = "P4HGmt";

    public static Connection getConnector() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String URL = "jdbc:oracle:thin:@//" + SERVER + ":" + PORT + "/" + SID;
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to load database driver.");
        } catch (SQLException e) {
            System.out.println("Unable to connect to database, process the "
                    + "results or close the connections: ");
            System.out.println(e.toString());
        }
        throw new RuntimeException("Cannot connect to SQL Developer.");
    }
    
    public static void closeConnection(Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        con.commit();
        
        if (con != null) {
            con.close();
        }     
        if (ps != null) {
            ps.close();
        }
        if (rs != null) {
            rs.close();
        }
    }

}
