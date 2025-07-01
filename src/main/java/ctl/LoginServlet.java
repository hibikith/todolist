package ctl;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.TaskDAO;
import model.LoginCheck;
import model.Task;
import model.User;

/**
 * ユーザーのログイン処理を管理するサーブレット。
 * GETリクエストではログイン画面を表示。
 * POSTリクエストではユーザー認証とログイン後のタスクリスト表示を実行。
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * GETリクエストの処理。
	 * ログイン画面の表示。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// login.jspへのフォワード処理
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * POSTリクエストの処理。
	 * ログインフォームからのデータ送信と認証処理。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// リクエストパラメータのエンコーディング設定
        request.setCharacterEncoding("UTF-8");
		
		// リクエストパラメータの取得
		String username = request.getParameter("userName"); // ユーザー名の取得
		String password = request.getParameter("password"); // パスワードの取得
		
		// 入力値のバリデーション
		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			request.setAttribute("errorMessage", "ユーザー名とパスワードを入力してください。");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return; // 処理の終了
		}
		
		// ユーザー認証をModelに委譲
		LoginCheck loginCheck = new LoginCheck();
		User loginUser = loginCheck.authenticateUser(username, password); // 認証メソッドの呼び出し
		
		if (loginUser != null) {
			
			// 認証成功時のセッション取得とユーザー情報保存（ログイン状態の維持）
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser); 
			
			// ログインユーザーIDを使用したタスクリストの取得
            TaskDAO taskDAO = new TaskDAO(); // TaskDAOインスタンスの生成
            List<Task> taskList = taskDAO.getTasksByUserId(loginUser.getUserId()); // ユーザーIDでのタスク取得
            
            // 取得したタスクリストのリクエストスコープへの保存
            request.setAttribute("taskList", taskList);
			
			// ログイン成功後のToDoリストメイン画面へのフォワード
			request.getRequestDispatcher("/WEB-INF/jsp/taskList.jsp").forward(request, response);
		} else {
			// 認証失敗時のエラーメッセージ設定
			request.setAttribute("errorMessage", "ユーザー名またはパスワードが正しくありません。");
			// ログイン画面へのエラーメッセージ表示とフォワード
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
}