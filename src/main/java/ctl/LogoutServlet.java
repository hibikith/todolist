package ctl;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * ユーザーのログアウト処理を管理するサーブレット。
 * セッションの無効化とログインページへのリダイレクトを実行。
 */
@WebServlet("/logout") 
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * GETリクエストの処理。
     * セッションの無効化と、ログインページへのリダイレクト。
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // セッションの取得と、存在する場合の無効化
        HttpSession session = request.getSession(false); // セッションが存在しない場合は新しく作成しない設定
        if (session != null) {
            session.invalidate(); // セッションの無効化処理
        }

        // ログインページまたはトップページへのリダイレクト処理
        // コンテキストパスを考慮したリダイレクト先の指定
        response.sendRedirect(request.getContextPath() + "/login.jsp"); 
    }

    /**
     * POSTリクエストの処理。
     * GETリクエストと同じログアウト処理を実行。
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}