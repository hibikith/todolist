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
	 private static final String JDBC_URL = "jdbc:mysql://localhost:3306/todolist_db";
	 private static final String DB_USER = "root"; 
	 private static final String DB_PASSWORD = "adimin"; 
	 
	 private Connection getConnection() throws SQLException {
	        try {
	            Class.forName("org.mariadb.jdbc.Driver");
	        } catch (ClassNotFoundException e) {
	            throw new SQLException("MariaDB JDBCドライバーが見つかりません。", e);
	        }
	        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
	    }

	public List<Task> getTaskByUserId(int userId){
		List<Task> taskList = new ArrayList<>();
		// 
		String sql = "SELECT id, user_id, task_name, status, priority, due_date, created_at FROM tasks WHERE user_id = ? ORDER BY due_date ASC, created_at DESC";

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setUserId(rs.getInt("user_id"));
                task.setTaskName(rs.getString("task_name"));
                task.setStatus(rs.getString("status"));           // ステータス取得
                task.setPriority(rs.getString("priority"));       // 優先度取得

                // 期限の取得: DATE型はLocalDateにマッピング
                if (rs.getDate("due_date") != null) {
                    task.setDueDate(rs.getDate("due_date").toLocalDate());
                }
                task.setCreatedAt(rs.getTimestamp("created_at"));
                taskList.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskList;
    }

    public boolean addTask(Task task) {
        // SQL文に新しいカラムを追加
        String sql = "INSERT INTO tasks (user_id, task_name, status, priority, due_date, created_at) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, task.getUserId());
            pstmt.setString(2, task.getTaskName());
            pstmt.setString(3, task.getStatus());     // ステータス設定
            pstmt.setString(4, task.getPriority());   // 優先度設定

            // 期限の設定: LocalDateをjava.sql.Dateに変換
            if (task.getDueDate() != null) {
                pstmt.setDate(5, java.sql.Date.valueOf(task.getDueDate()));
            } else {
                pstmt.setNull(5, java.sql.Types.DATE); // 期限がない場合はNULL
            }

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * タスクを更新します。
     * @param task 更新するタスクオブジェクト（id, userId, taskName, status, priority, dueDateが必要）
     * @return 更新が成功した場合はtrue、失敗した場合はfalse
     */
    public boolean updateTask(Task task) {
        String sql = "UPDATE tasks SET task_name = ?, status = ?, priority = ?, due_date = ? WHERE id = ? AND user_id = ?";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, task.getTaskName());
            pstmt.setString(2, task.getStatus());
            pstmt.setString(3, task.getPriority());
            if (task.getDueDate() != null) {
                pstmt.setDate(4, java.sql.Date.valueOf(task.getDueDate()));
            } else {
                pstmt.setNull(4, java.sql.Types.DATE);
            }
            pstmt.setInt(5, task.getId());
            pstmt.setInt(6, task.getUserId()); // ユーザーIDも条件に含めることで、他人のタスクを更新できないようにする

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * タスクを削除します。
     * @param taskId 削除するタスクのID
     * @param userId 削除を試みるユーザーのID (セキュリティのため)
     * @return 削除が成功した場合はtrue、失敗した場合はfalse
     */
    public boolean deleteTask(int taskId, int userId) {
        String sql = "DELETE FROM tasks WHERE id = ? AND user_id = ?";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, taskId);
            pstmt.setInt(2, userId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

	
	

