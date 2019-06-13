package task;

public class SpecialTask extends Task {

	
	String assignedTo;
	char isApproved;
	
	public String getAssignedTo() {
		return assignedTo;
	}

	public Task setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
		return this;
	}

	public char getIsApproved() {
		return isApproved;
	}

	public Task setIsApproved(char isApproved) {
		this.isApproved = isApproved;
		return this;
	}

	@Override
	public void insertTask() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertSubTasks() {
		
		
	}

}
