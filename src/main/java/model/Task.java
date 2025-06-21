package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

public class Task implements Serializable{
	private int id;            // タスクの一意なID (DBの主キーに対応)
    private int userId;        // このタスクを所有するユーザーのID (DBの外部キーに対応)
    private String taskName;   
    private String status;     // 未着手, 進行中, 完了
    private String priority;   // 高, 中, 低
    private LocalDate dueDate; // 期限 (java.time.LocalDate)
    private Timestamp createdAt;

    //引数なしコンストラクタ
    public Task() {}
    
    //各privateフィールドに対するgetter/setter
    public int getId() {
        return id;
    }

    public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    @Override
    public String toString() {
        return "Task{" +
               "id=" + id +
               ", userId=" + userId +
               ", taskName='" + taskName + '\'' +
               ", status='" + status + '\'' +
               ", priority='" + priority + '\'' +
               ", dueDate=" + dueDate +
               ", createdAt=" + createdAt +
               '}';
    }
}
