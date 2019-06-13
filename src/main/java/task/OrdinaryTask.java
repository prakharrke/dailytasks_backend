package task;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DBCommunication;
import subtask.SubTask;

public class OrdinaryTask extends Task{
	
	public OrdinaryTask() {
		
		//TODO		
	}

	@Override
	public void insertTask() throws SQLException {
		
		DBCommunication dbCommunication = null;
		try {
			dbCommunication = new DBCommunication();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		dbCommunication.insertTask(this);
		
	}

	@Override
	public void insertSubTasks() throws ClassNotFoundException, SQLException {
		
		List<SubTask> subTasks  = this.getSubTasks();
		
		for(SubTask subTask : subTasks) {
			
			subTask.insertSubTask();
		}
		
	}

}
