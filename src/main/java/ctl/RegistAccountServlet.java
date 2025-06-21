package ctl;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.UserDAO;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/registAccount")
public class RegistAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// GETリクエスト（アカウント登録画面の表示）
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/registAccount.jsp").forward(request, response);
	}
	
	// POSTリクエスト（アカウント登録フォームからのデータ送信）
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
		
		String username = request.getParameter("userName");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword"); // 確認用パスワード
				

		System.out.println("111");
		// 入力値のバリデーション
		if (username == null || username.isEmpty() || password == null || password.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
			System.out.println("222");
			request.setAttribute("errorMessage", "すべての項目を入力してください。");
			request.getRequestDispatcher("/WEB-INF/jsp/registAccount.jsp").forward(request, response);
			return;
		}
		if (!password.equals(confirmPassword)) {
			request.setAttribute("errorMessage", "パスワードと確認用パスワードが一致しません。");
			request.getRequestDispatcher("/WEB-INF/jsp/registAccount.jsp").forward(request, response);
			return;	
		}
		System.out.println("3333");
		UserDAO userDAO = new UserDAO();
		
		// ユーザー名が既に存在するかチェック
		if (userDAO.isUsernameExists(username)) {
			request.setAttribute("errorMessage", "このユーザー名は既に使用されています。");
			request.getRequestDispatcher("/WEB-INF/jsp/registAccount.jsp").forward(request, response);
			return;
		}
		// ユーザーの登録					
		if (userDAO.registerUser(username, password)) {
			// 登録成功					
			// ログインページへリダイレクトする				
			response.sendRedirect(request.getContextPath() + "/LoginServlet?registered=true"); // 登録成功を伝えるパラメータ
		} else {
			request.setAttribute("errorMessage", "ユーザー登録に失敗しました。時間をおいて再度お試しください。");
			request.getRequestDispatcher("/WEB-INF/jsp/registAccount.jsp").forward(request, response);
		}
	}
}
