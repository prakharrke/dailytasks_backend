package subtask;

import java.sql.SQLException;

import model.DBCommunication;

public class SubTask {
	String taskID;
	String subTaskID;
	String description;
	String timeSheet;
	char special;
	char approved = 'n';
	char reviewed = 'n';
	public String getTaskID() {
		return taskID;
	}
	public SubTask setTaskID(String taskID) {
		this.taskID = taskID;
		return this;
	}
	public String getSubTaskID() {
		return subTaskID;
	}
	public SubTask setSubTaskID(String subTaskID) {
		this.subTaskID = subTaskID;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public SubTask setDescription(String description) {
		this.description = description;
		return this;
	}
	public String getTimeSheet() {
		return timeSheet;
	}
	public SubTask setTimeSheet(String timeSheet) {
		this.timeSheet = timeSheet;
		return this;
	}
	public char getSpecial() {
		return special;
	}
	public SubTask setSpecial(char special) {
		this.special = special;
		return this;
	}
	public char getApproved() {
		return approved;
	}
	public SubTask setApproved(char approved) {
		this.approved = approved;
		return this;
	}
	public char getReviewed() {
		return reviewed;
	}
	public SubTask setReviewed(char reviewed) {
		this.reviewed = reviewed;
		return this;
	}
	
	public void insertSubTask() throws ClassNotFoundException, SQLException {
		DBCommunication dbCommunication = new DBCommunication();
		dbCommunication.insertSubTask(this);
	}
	
	
}
