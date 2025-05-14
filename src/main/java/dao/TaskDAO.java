package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Task;
	

public class TaskDAO{
	private final String JDBC_URL = "jdbc:mariadb://localhost/todolist";
	private final String DB_USER = "root";
	private final String DB_PASS = "admin";

	// タスク情報を全て取得し＜降順＞リストを返す
	public List<Task> findList() {
		List<Task> taskList = new ArrayList<>();
		String sql = "SELECT * FROM tasklist ORDER BY taskId DESC";
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			// SQL送信準備
			PreparedStatement stmt = conn.prepareStatement(sql);
			// 一致したものを取得
			ResultSet rs = stmt.executeQuery();
			//取得した情報をリストに格納していく
			while (rs.next()) {
				int taskId = rs.getInt("taskID");
				String taskName = rs.getString("taskName");
				int userId = rs.getInt("userID");
				String description = rs.getString("description");
				// 取得した情報をTaskインスタンスに格納してリストに追加する
				Task task = new Task(taskId, taskName,description);
				taskList.add(task);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return taskList;
	}
}
	
	
	
	

