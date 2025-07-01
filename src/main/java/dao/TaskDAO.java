package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Task;

/**
 * タスクデータへのデータベースアクセスを提供するDAO（Data Access Object）クラス。
 * データベース接続の管理と、タスク情報のCRUD操作を実行。
 */
public class TaskDAO {
    // データベース接続情報
    private static final String JDBC_URL = "jdbc:mariadb://localhost:3306/todolist";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin";

    /**
     * データベースへの接続を取得。
     * @return データベース接続オブジェクト
     * @throws SQLException データベース接続エラー発生
     */
    private Connection getConnection() throws SQLException {
        try {
            // MariaDBのJDBCドライバーロード
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MariaDB JDBCドライバーが見つかりません。", e);
        }
        // 指定されたURL、ユーザー名、パスワードでの接続確立
        return java.sql.DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * 特定のユーザーに紐づく全てのタスクを取得。
     * @param userId タスクを所有するユーザーのID
     * @return 指定ユーザーのタスクリスト
     */
    public List<Task> getTasksByUserId(int userId) { 
        List<Task> taskList = new ArrayList<>();
        // SQL: user_idに紐づく全てのタスクを期限と作成日でソートして取得
        String sql = "SELECT id, user_id, task_name, status, priority, due_date, created_at FROM tasks WHERE user_id = ? ORDER BY due_date ASC, created_at DESC";

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Task task = new Task();
                task.setTaskId(rs.getInt("id"));
                task.setUserId(rs.getInt("user_id"));
                task.setTaskName(rs.getString("task_name"));
                task.setStatus(rs.getString("status"));
                task.setPriority(rs.getString("priority"));

                // due_dateがnullでない場合のみLocalDateへ変換
                if (rs.getDate("due_date") != null) {
                    task.setDueDate(rs.getDate("due_date").toLocalDate());
                }
                
                task.setCreatedAt(rs.getTimestamp("created_at"));
                taskList.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // エラー出力
        }
        return taskList;
    }

    /**
     * 特定のタスクIDとユーザーIDに紐づく単一のタスクを取得。
     * @param taskId 取得対象タスクのID
     * @param userId タスクを所有するユーザーのID
     * @return 該当するタスクオブジェクト。見つからなければnull
     */
    public Task getTaskById(int taskId, int userId) {
        Task task = null;
        // SQL: idとuser_idの両方を条件に単一タスクを取得
        String sql = "SELECT id, user_id, task_name, status, priority, due_date, created_at FROM tasks WHERE id = ? AND user_id = ?";

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, taskId);
            pstmt.setInt(2, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) { // 該当タスクが見つかった場合
                task = new Task();
                task.setTaskId(rs.getInt("id"));
                task.setUserId(rs.getInt("user_id"));
                task.setTaskName(rs.getString("task_name"));
                task.setStatus(rs.getString("status"));
                task.setPriority(rs.getString("priority"));
                // due_dateがnullでない場合のみLocalDateへ変換
                if (rs.getDate("due_date") != null) {
                    task.setDueDate(rs.getDate("due_date").toLocalDate());
                }
                task.setCreatedAt(rs.getTimestamp("created_at"));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // エラー出力
        }
        return task;
    }

    /**
     * 新しいタスクをデータベースに追加。
     * @param task 追加するタスクオブジェクト
     * @return 追加成功の場合true、失敗の場合false
     */
    public boolean addTask(Task task) {
        String sql = "INSERT INTO tasks (user_id, task_name, status, priority, due_date, created_at) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, task.getUserId());
            pstmt.setString(2, task.getTaskName());
            pstmt.setString(3, task.getStatus());
            pstmt.setString(4, task.getPriority());
            
            // due_dateがnullでないかチェックし、対応する値を設定
            if (task.getDueDate() != null) {
                // LocalDateをjava.sql.Dateに変換して設定
                pstmt.setDate(5, java.sql.Date.valueOf(task.getDueDate()));
            } else {
                // nullの場合はデータベースにNULLを設定
                pstmt.setNull(5, java.sql.Types.DATE);
            }

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // エラー出力
            return false;
        }
    }

    /**
     * 既存のタスクを更新。
     * @param task 更新するタスクオブジェクト
     * @return 更新成功の場合true、失敗の場合false
     */
    public boolean updateTask(Task task) {
        String sql = "UPDATE tasks SET task_name = ?, status = ?, priority = ?, due_date = ? WHERE id = ? AND user_id = ?";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, task.getTaskName());
            pstmt.setString(2, task.getStatus());
            pstmt.setString(3, task.getPriority());
            // due_dateがnullでないかチェックし、対応する値を設定
            if (task.getDueDate() != null) {
                pstmt.setDate(4, java.sql.Date.valueOf(task.getDueDate()));
            } else {
                pstmt.setNull(4, java.sql.Types.DATE);
            }
            pstmt.setInt(5, task.getTaskId()); 
            pstmt.setInt(6, task.getUserId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // エラー出力
            return false;
        }
    }

    /**
     * タスクを削除。
     * @param taskId 削除対象タスクのID
     * @param userId 削除を試みるユーザーのID
     * @return 削除成功の場合true、失敗の場合false
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
            e.printStackTrace(); // エラー出力
            return false;
        }
    }
}