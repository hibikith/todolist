package model;

import java.util.List;

import dao.TaskDAO;

public class GetTaskList {

	public List<Task> execute(User user) {
		TaskDAO taskDAO = new TaskDAO();
		List<Task> taskList = taskDAO.findList();
		return taskList;
	}

}
