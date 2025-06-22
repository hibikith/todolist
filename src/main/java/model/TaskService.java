package model;

import java.time.LocalDate;
import java.util.List;

import dao.TaskDAO;

public class TaskService {
	private TaskDAO taskDAO;

    public TaskService() {
        this.taskDAO = new TaskDAO();
    }

    public List<Task> getTasksForUser(int userId) {
        return taskDAO.getTaskByUserId(userId);
    }

    // タスク追加メソッドを更新（初期値を設定）
    public boolean addTask(int userId, String taskName, String status, String priority, LocalDate dueDate) {
        if (taskName == null || taskName.trim().isEmpty()) {
            return false; // タスク名が空の場合は追加しない
        }
        Task newTask = new Task();
        newTask.setUserId(userId);
        newTask.setTaskName(taskName.trim());
        newTask.setStatus(status != null && !status.isEmpty() ? status : "未着手"); // デフォルト値
        newTask.setPriority(priority != null && !priority.isEmpty() ? priority : "中"); // デフォルト値
        newTask.setDueDate(dueDate);
        return taskDAO.addTask(newTask);
    }

    /**
     * 既存のタスクを更新します。
     * @param taskId タスクID
     * @param userId ユーザーID
     * @param taskName タスク名
     * @param status ステータス
     * @param priority 優先度
     * @param dueDate 期限
     * @return 更新が成功した場合はtrue、失敗した場合はfalse
     */
    public boolean updateTask(int taskId, int userId, String taskName, String status, String priority, LocalDate dueDate) {
        if (taskName == null || taskName.trim().isEmpty()) {
            return false;
        }
        Task taskToUpdate = new Task();
        taskToUpdate.setTaskId(taskId);
        taskToUpdate.setUserId(userId);
        taskToUpdate.setTaskName(taskName.trim());
        taskToUpdate.setStatus(status);
        taskToUpdate.setPriority(priority);
        taskToUpdate.setDueDate(dueDate);
        return taskDAO.updateTask(taskToUpdate);
    }

    /**
     * タスクを削除します。
     * @param taskId 削除するタスクID
     * @param userId ユーザーID
     * @return 削除が成功した場合はtrue、失敗した場合はfalse
     */
    public boolean deleteTask(int taskId, int userId) {
        return taskDAO.deleteTask(taskId, userId);
    }

}
