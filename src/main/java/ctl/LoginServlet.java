package ctl;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.UserDAO;
import model.User;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*
	 * 
	 */
	//GETリクエスト（ログイン画面を表示する）
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// login.jspへフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}
	
	/*  POSTリクエスト（ログインフォームからデータを送信する）
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// リクエストパラメータのエンコーディングを設定
        request.setCharacterEncoding("UTF-8");
		
		// リクエストパラメータの取得
		String username = request.getParameter("userName");//name属性がuserNameを受け取る
		String password = request.getParameter("password");//name属性がpasswordを受け取る
		
		// 入力値のバリデーション
		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			request.setAttribute("errorMessage", "ユーザー名とパスワードを入力してください。");
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return; // 処理を終了
		}
		
		//  ユーザー認証をModel（UserDAO）に委譲
		UserDAO userDAO = new UserDAO();
		User loggedInUser = userDAO.authenticateUser(username, password); // 認証メソッドを呼び出し
		
		if (loggedInUser != null) {
			
			// 認証成功
			// セッションを取得し、ユーザー情報を保存（ログイン状態を維持）
			HttpSession session = request.getSession();
			session.setAttribute("loggedInUser", loggedInUser); 
			
			// ログイン成功後は、ToDoリストのメイン画面へフォワード
			request.getRequestDispatcher("/WEB-INF/jsp/taskList.jsp").forward(request, response);
		} else {
			// 4. 認証失敗
			request.setAttribute("errorMessage", "ユーザー名またはパスワードが正しくありません。");
			// ログイン画面にエラーメッセージを表示するため、フォワードする
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		}
	}
}