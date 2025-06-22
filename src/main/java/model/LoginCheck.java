package model;


import dao.UserDAO;

public class LoginCheck {

		 private UserDAO userDAO; // UserDAOのインスタンスを保持する
		 
		 //コンストラクタでインスタンスを生成する
		 public LoginCheck() {
		        this.userDAO = new UserDAO();
		 }
		 
		 /**
		  * ユーザー名とパスワードを使ってユーザー認証を行う。
		  * @param username 認証するユーザー名
		  * @param password 認証するパスワード
		  * @return 認証に成功した場合はUserオブジェクト、失敗した場合はnullを返す。
		  */
		 public User authenticateUser(String username, String password) {
			 
			 	// DAOのauthenticateUserメソッドを呼び出す
		        User authenticatedUser = userDAO.authenticateUser(username, password);
		        
		        return authenticatedUser;
			 
		 }
}