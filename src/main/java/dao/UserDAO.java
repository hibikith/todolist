package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserDAO {
	private final String JDBC_URL = "jdbc:mariadb://localhost/teama";
	private final String DB_USER = "root";
	private final String DB_PASS = "admin";
	
	
	
	 // ユーザIDとパスワードがデータベースに登録されているか確認
	public boolean findUser(User user) {
		String sql = "SELECT * FROM userlist WHERE userName=? AND password =?";
		try(Connection conn =DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
			PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	user.setUserId(rs.getInt("userId"));
            	return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
 
	}
	
	
}
