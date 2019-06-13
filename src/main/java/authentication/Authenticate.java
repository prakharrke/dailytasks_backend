package authentication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.security.sasl.AuthenticationException;

import net.sf.json.JSONObject;
import user.User;

public class Authenticate {
	
	public static String sourceURL = "jdbc:oracle:thin:@localhost:1521:orcl";
	public static String sourceUserName = "DAILYTASKS";
	public static String sourcePassword = "dailytasks";
	public static String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
	public static Connection conn;
	public static Statement stmt;
	
	
	public  User getUserVerificationDetails(JSONObject userDetails) throws ClassNotFoundException, SQLException, AuthenticationException {
		
		Class.forName(jdbcDriver);
		try {
		conn = DriverManager.getConnection(sourceURL, sourceUserName, sourcePassword);
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
		String username = userDetails.getString("username");
		String password = userDetails.getString("password");
		
		String query = "SELECT * FROM USERS WHERE USERNAME='" + username + "'";
		stmt = conn.createStatement();
		ResultSet rs =  stmt.executeQuery(query);
		
//		String validUserName = rs.getString("USERNAME");
//		if(!validUserName.equals(username)) {
//			rs.close();
//			conn.close();
//			return false;
//		}
		if(rs.next()) {
			
			String dbUserName = rs.getString("USERNAME");
			String dbPassword = rs.getString("PASSWORD");
			String name = rs.getString("NAME");
			String designation = rs.getString("DESIGNATION");
			String role = rs.getString("ROLE");
			rs.close();
			conn.close();
			if(dbPassword.equals(password)) {
				User user = new User();
				user.setUserName(userDetails.getString("username"))
				.setDesignation(designation)
				.setName(name)
				.setRole(role);
				return user;
			}
			else
				throw new AuthenticationException();
		}
		else {
			
			rs.close();
			conn.close();
			throw new AuthenticationException();
		}
		}catch(Exception e) {
			e.printStackTrace();
			throw new AuthenticationException();
		}
		finally {
			if(stmt!=null)
				stmt.close();
		}
	}
}
