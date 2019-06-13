package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import subtask.SubTask;
import task.Task;

public class DBCommunication {
	
	public  String sourceURL = "jdbc:oracle:thin:@localhost:1521:orcl";
	public  String sourceUserName = "DAILYTASKS";
	public  String sourcePassword = "dailytasks";
	public  String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
	public  Connection conn;
	public  Statement stmt;
	
	public DBCommunication() throws ClassNotFoundException  {
		createConnection();
	}
	
	private void createConnection() throws ClassNotFoundException {
		Class.forName(jdbcDriver);
		try {
		conn = DriverManager.getConnection(sourceURL, sourceUserName, sourcePassword);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertTask(Task task) throws SQLException  {
		// INSERTING TASK TO DB
		PreparedStatement stmt = null;
		try {
		String query =  "INSERT INTO ORDINARY_TASKS VALUES(?, ?, ?, ?, ?, ?, ?)";
		stmt = conn.prepareStatement(query);
		stmt.setString(1, task.getTaskID());
		stmt.setString(2, task.getTitle());
		stmt.setString(3, task.getStatus());
		stmt.setString(4, task.getCategory());
		stmt.setString(5, String.valueOf(task.getCreatedOn()));
		stmt.setString(6, String.valueOf(task.getCreatedOn()));
		stmt.setString(7, task.getUserName());
		
		stmt.executeQuery();
		}catch(Exception e) {
			
			e.printStackTrace();
			throw new SQLException();
		}finally {
			if(stmt!=null)
				stmt.close();
		}
	}
	
	public void insertSpecialTask() {
		
	}
	
	public void insertSubTask(SubTask subTask) throws SQLException {
		String query = "INSERT INTO SUB_TASKS VALUES (?, ?, ?, ?, 'N', 'N', ?)";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setString(1, subTask.getSubTaskID());
			stmt.setString(2, subTask.getTaskID());
			stmt.setString(3, subTask.getDescription());
			stmt.setString(4, subTask.getTimeSheet());
			stmt.setString(5, String.valueOf(subTask.getSpecial()));
			
			stmt.executeQuery();
			
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new SQLException();
		}finally {
			if(stmt!=null)
				stmt.close();
		}
		}
	
	public String fetchTasks(String userName) throws SQLException {
		
		Statement stmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM ORDINARY_TASKS WHERE USERNAME = '" + userName + "'";
		try {
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			JSONArray tasks = new JSONArray();
			while(rs.next()) {
				JSONObject task = new JSONObject();
				task.put("taskID", rs.getString("O_TASK_ID"));
				task.put("title", rs.getString("O_NAME"));
				task.put("status", rs.getString("STATUS"));
				task.put("category", rs.getString("CATEGORY_ID"));
				task.put("createdOn", rs.getString("O_CREATED_ON"));
				task.put("userName", rs.getString("USERNAME"));
				tasks.add(task);
			}
			
			return new Gson().toJson(tasks);
			
		}catch(Exception e) {
			
			throw new SQLException();
		}finally {
			
			if(stmt!=null)
				stmt.close();
		}
		
	}
	public void fetchSpecialTasks() {
		
	}
	
	public String getTaskDetails(String userName, String taskID) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		String query = "SELECT DISTINCT * FROM ORDINARY_TASKS  JOIN SUB_TASKS ON ORDINARY_TASKS.O_TASK_ID = SUB_TASKS.TASK_ID WHERE ORDINARY_TASKS.O_TASK_ID = '" + taskID + "'";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			JSONArray task = new JSONArray();
			while(rs.next()) {
				JSONObject taskObject = new JSONObject();
				taskObject.put("taskID", rs.getString("O_TASK_ID"));
				taskObject.put("taskName", rs.getString("O_NAME"));
				taskObject.put("taskStatus", rs.getString("STATUS"));
				taskObject.put("taskCategory", rs.getString("CATEGORY_ID"));
				taskObject.put("taskCreatedOn", rs.getString("O_CREATED_ON"));
				taskObject.put("subTaskID", rs.getString("SUBTASK_ID"));
				taskObject.put("subTaskDescription", rs.getString("DESCRIPTION"));
				taskObject.put("timeSheet", rs.getString("TIMESHEET"));
				taskObject.put("approved", rs.getString("APPROVED"));
				taskObject.put("reviewed", rs.getString("REVIEWED"));
				taskObject.put("special", rs.getString("SPECIAL"));
				task.add(taskObject);
			}
			
			return new Gson().toJson(task);
		}catch(Exception e) {
			e.printStackTrace();
			throw new SQLException();
		}
	}
}
