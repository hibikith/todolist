package model;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
	
    private int user_id;
    private String userName;
    private String password;

    
	// デフォルトコンストラクタ
    public User() {
    	
    }
    
    // 引数アリのコンストラクタ
    public User(int user_id , String userName, String password) {
        this.user_id = user_id;
        this.userName = userName;
        this.password = password;
    }

    public int getUserId() {
    	return user_id;
    }
    
    public void setUserId(int user_id){
    	this.user_id = user_id; 
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
               "id=" + user_id +
               ", username='" + userName + '\'' +
               ", password='" + password  +
               '}';
    }
    
}
