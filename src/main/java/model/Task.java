package model;

import java.io.Serializable;

public class Task implements Serializable{
	private int taskId;         // タスクの一意なID (DBの主キーに対応)
    private int userId;         // このタスクを所有するユーザーのID (DBの外部キーに対応)
    private String description;	// タスクの内容
    private boolean done;       // タスクが完了したかどうか (true:完, false:未完了)
    private String taskName;    // タスクの名前
    
    

	//引数なしコンストラクタ
    public Task() {}
    
    //引数ありのコンストラクタ
    public Task(int taskId, String taskName, String description) {
    	this.taskId = taskId;
    	this.taskName = taskName;
    	this.description = description;
    }
    
    //各privateフィールドに対するgetter/setter
    public int getId() {
        return taskId;
    }

    public void setId(int id) {
        this.taskId = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
    public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}
