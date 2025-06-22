package model;

import java.util.List;

import dao.TaskDAO;

public class GetTaskList {

	public List<Task> execute(int userId) {
		TaskDAO taskDAO = new TaskDAO();
		List<Task> taskList = taskDAO.getTaskByUserId(userId);
		return taskList;
	}

}
