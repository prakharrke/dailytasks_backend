package task;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import subtask.SubTask;

public abstract class Task {
	
	String userName;
	String TaskID;
	String status;
	long createdOn;
	String category;
	String title;
	List<SubTask> subTasks = new ArrayList<SubTask>();
	public String getUserName() {
		return userName;
	}
	public Task setUserName(String userName) {
		this.userName = userName;
		return this;
	}
	public String getTaskID() {
		return TaskID;
	}
	public Task setTaskID(String taskID) {
		TaskID = taskID;
		return this;
	}
	public String getStatus() {
		return status;
	}
	public Task setStatus(String status) {
		this.status = status;
		return this;
	}
	public long getCreatedOn() {
		return createdOn;
	}
	public Task setCreatedOn(long createdOn) {
		this.createdOn = createdOn;
		return this;
	}
	public String getCategory() {
		return category;
	}
	public Task setCategory(String category) {
		this.category = category;
		return this;
	}
	public Task setTitle(String title) {
		this.title = title;
		return this;
	}
	public String getTitle() {
		return this.title;
	}
	public void addSubTasks(SubTask subTask) {
		subTasks.add(subTask);
	}
	public List<SubTask> getSubTasks(){
		return this.subTasks;
	}
	abstract public void insertTask() throws SQLException;
	
	abstract public void insertSubTasks() throws ClassNotFoundException, SQLException;
}
