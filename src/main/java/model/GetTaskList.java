package model;

import java.util.List;

import dao.TaskDAO;

/**
 * ユーザーIDに基づきタスクリストを取得する。
 * DAOを利用し、データベースからタスク情報を取得する処理を実行。
 */
public class GetTaskList {
	/**
	 * 指定されたユーザーIDのタスクリストを取得。
	 * @param userId タスクを取得するユーザーのID
	 * @return ユーザーに紐づくタスクのリスト
	 */
	public List<Task> execute(int userId) {
		TaskDAO taskDAO = new TaskDAO();
		// DAOメソッド呼び出しによるタスクリストの取得
		List<Task> taskList = taskDAO.getTasksByUserId(userId);
		return taskList;
	}

}
