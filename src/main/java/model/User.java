package model;

public class User {
    private int userId;
    private String userName;
    private String password; 
    
    // デフォルトコンストラクタ
    public User() {}
    // 引数アリのコンストラクタ
    public User(String username , String password) {
        this.userName = username;
        this.password = password;
    }

    public int getUserId() {
    	return userId;
    }
    
    public void setUserId(int id) {
    	this.userId = userId; 
    }
    
    public String getUsername() {
    	return userName; 
    }
    
    public void setUsername(String username) {
    	this.userName = username; 
    }
    
    public String getPassword() {
    	return password;
    } 
    
    public void setPassword(String password) {
    	this.password = password;
    }
    
}
