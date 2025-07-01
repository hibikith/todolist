package model;

import java.time.LocalDate;
import java.util.List;

import dao.TaskDAO;

public class TaskService {

    private TaskDAO taskDAO; // TaskDAOのインスタンス

    public TaskService() {
        this.taskDAO = new TaskDAO(); 
    }

    /**
     * 指定されたユーザーIDのタスクリストを取得する。
     * @param userId （ユーザーのID）
     * @return タスクリスト
     */
    public List<Task> getUserTasks(int userId) {
        return taskDAO.getTasksByUserId(userId);
    }

    /**
     * 新しいタスクを追加する。
     * @param task （追加するタスクオブジェクト）
     * @return 追加が成功した場合はtrue、失敗した場合はfalseを返す
     */
    public boolean addTask(Task task) {
        return taskDAO.addTask(task);
    }
    
    public Task getTaskById(int taskId, int userId) {
        return taskDAO.getTaskById(taskId, userId);
    }
    
    /**
     * タスク情報を更新する。
     * @param taskId 更新対象のタスクID
     * @param userId タスクを所有するユーザーのID 
     * @param taskName 新しいタスク名
     * @param status 新しいステータス
     * @param priority 新しい優先度
     * @param dueDate 新しい期限
     * @return 更新が成功した場合はtrue、失敗した場合はfalse
     */
    public boolean updateTask(int taskId, int userId, String taskName, String status, String priority, LocalDate dueDate) {
        // 更新対象のTaskオブジェクトを作成し、必要な情報をセット
        Task task = new Task();
        task.setTaskId(taskId);   
        task.setUserId(userId);   
        task.setTaskName(taskName);
        task.setStatus(status);
        task.setPriority(priority);
        task.setDueDate(dueDate);

        // TaskDAOのupdateTaskメソッドはTaskオブジェクトを引数に取るため、変換して渡す
        return taskDAO.updateTask(task);
    }

    /**
     * タスクを削除する
     * @param taskId （削除するタスクのID）
     * @param userId 削除を試みるユーザーのID 
     * @return 削除が成功した場合はtrue、失敗した場合はfalse
     */
    public boolean deleteTask(int taskId, int userId) {
        return taskDAO.deleteTask(taskId, userId);
    }
}