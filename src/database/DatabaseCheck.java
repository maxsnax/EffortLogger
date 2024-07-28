package database;
import java.sql.*;

public class DatabaseCheck {

	public static boolean queryLogin(String username, String password) {
		  // JDBC 
	      String url = "jdbc:mysql://localhost/login_database";
	      
	      // Test case
	      String userToCheck = username;
	      String passwordToCheck = password;

	      Connection conn = null;
	      Statement stmt = null;

	      try {
	         // Open a connection
	         conn = DriverManager.getConnection(url, "username", "password");

	         // Execute a query
	         stmt = conn.createStatement();
	         String sql;
	         sql = "SELECT * FROM users WHERE username='" + userToCheck + "' AND password = '" + passwordToCheck + "'";
	         ResultSet rs = stmt.executeQuery(sql);

	         // If user exists
	         if (rs.next()) {
	            System.out.println("User " + userToCheck + " found in database and logged in");
	            return true;
	         } else {
	            System.out.println("User " + userToCheck + " not found in database or password incorrect");
	            return false;
	         }
	      } catch (SQLException se) {
	         // error handling
	         se.printStackTrace();
	      } catch (Exception e) {
	         // error handling
	         e.printStackTrace();
	      } finally {
	         // close resources
	         try {
	            if (stmt != null) stmt.close();
	         } catch (SQLException se2) {
	         }
	         try {
	            if (conn != null) conn.close();
	         } catch (SQLException se) {
	            se.printStackTrace();
	         }
	      }
		return false;
	}

	public static boolean getProjects() {
		  // JDBC 
	      String url = "jdbc:mysql://localhost/project_database";
	      
	      Connection conn = null;
	      Statement stmt = null;

	      try {
	         // Open a connection
	         conn = DriverManager.getConnection(url, "username", "password");

	         // Execute a query
	         stmt = conn.createStatement();
	         String sql;
	         sql = "SELECT * FROM Projects";
	         ResultSet rs = stmt.executeQuery(sql);

	         // If user exists
	         while (rs.next()) {
	            int id = rs.getInt("id");
	            String name = rs.getString("name");
	            Date date = rs.getDate("date");
	         }
	      } catch (SQLException se) {
	         // error handling
	         se.printStackTrace();
	      } catch (Exception e) {
	         // error handling
	         e.printStackTrace();
	      } finally {
	         // close resources
	         try {
	            if (stmt != null) stmt.close();
	         } catch (SQLException se2) {
	         }
	         try {
	            if (conn != null) conn.close();
	         } catch (SQLException se) {
	            se.printStackTrace();
	         }
	      }
		return false;
	}
	
}
