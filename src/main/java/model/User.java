package model;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
	
    private int id;
    private String userName;
    private String password;

    
	// デフォルトコンストラクタ
    public User() {
    	
    }
    
    // 引数アリのコンストラクタ
    public User(int id , String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public int getUserId() {
    	return id;
    }
    
    public void setUserId(int id) {
    	this.id = id; 
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
    
    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", username='" + userName + '\'' +
               ", password='" + password  +
               '}';
    }
    
}
