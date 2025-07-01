package ctl;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.UserDAO;

/**
 * アカウント登録リクエストを処理するサーブレット。
 * GETリクエストでは登録フォームを表示し、
 * POSTリクエストではユーザー情報の登録を実行。
 */
@WebServlet("/registAccount")
public class RegistAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * GETリクエストの処理。
	 * アカウント登録画面の表示。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/registAccount.jsp").forward(request, response);
	}
	
	/**
	 * POSTリクエストの処理。
	 * アカウント登録フォームからのデータ送信とユーザー登録の実行。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
		
		String username = request.getParameter("userName");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
				
		// 入力値のバリデーション
		if (username == null || username.isEmpty() || password == null || password.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
			request.setAttribute("errorMessage", "すべての項目を入力してください。");
			request.getRequestDispatcher("/WEB-INF/jsp/registAccount.jsp").forward(request, response);
			return;
		}
		
		// パスワードと確認用パスワードの一致チェック
		if (!password.equals(confirmPassword)) {
			request.setAttribute("errorMessage", "パスワードと確認用パスワードが一致しません。");
			request.getRequestDispatcher("/WEB-INF/jsp/registAccount.jsp").forward(request, response);
			return;	
		}
		
		UserDAO userDAO = new UserDAO();
		
		// ユーザー名の存在チェック
		if (userDAO.isUsernameExists(username)) {
			request.setAttribute("errorMessage", "このユーザー名は既に使用されています。");
			request.getRequestDispatcher("/WEB-INF/jsp/registAccount.jsp").forward(request, response);
			return;
		}
		
		// ユーザーの登録処理
		if (userDAO.registerUser(username, password)) {
			// 登録成功時のログインページへのリダイレクト
			response.sendRedirect(request.getContextPath() + "/LoginServlet?registered=true"); 
		} else {
			// 登録失敗時のエラーメッセージ設定
			request.setAttribute("errorMessage", "ユーザー登録に失敗しました。時間をおいて再度お試しください。");
			request.getRequestDispatcher("/WEB-INF/jsp/registAccount.jsp").forward(request, response);
		}
	}
}