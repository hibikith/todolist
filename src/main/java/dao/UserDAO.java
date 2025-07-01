package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

/**
 * ユーザーデータへのデータベースアクセスを提供するDAOクラス。
 * データベース接続の管理と、ユーザー認証および登録操作を実行。
 */
public class UserDAO {
	
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
	  * ユーザー名とパスワードに基づくユーザー認証。
	  * @param username ユーザー名
	  * @param password 入力されたパスワード
	  * @return 認証成功したUserオブジェクト、または認証失敗の場合はnull
	  */
	public User authenticateUser(String username, String password) {
		User user = null;
		String sql = "SELECT user_id, username FROM users WHERE username = ? AND password = ?";
		
		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace(); // エラー出力
		}
		
		 return user;
	}

	 /**
	  * 新規ユーザーのデータベースへの登録。
	  * @param username ユーザー名
	  * @param password パスワード
	  * @return 登録成功の場合true、失敗の場合false
	  */
	public boolean registerUser(String username, String password) {
		String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
																		
		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			int affectedRows = pstmt.executeUpdate();
			return affectedRows > 0;
			
		} catch (SQLException e) {
			e.printStackTrace(); // エラー出力
			return false;
		}
	}
	
	/**
     * ユーザー名が既に存在するかどうかのチェック。
     * @param username ユーザー名
     * @return 存在する場合true、しない場合false
     */
	public boolean isUsernameExists(String username) {
		String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace(); // エラー出力
		}
		return false;
	}

}