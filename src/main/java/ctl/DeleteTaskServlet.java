package ctl;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.TaskService;
import model.User;

/**
 * タスク削除リクエストの処理を行うサーブレット。
 * GETリクエストではタスク一覧へリダイレクト。
 * POSTリクエストでは指定されたタスクをデータベースから削除。
 */
@WebServlet("/deleteTask")
public class DeleteTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * GETリクエストの処理。
     * タスク一覧ページへリダイレクト。
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/tasks");
    }

    /**
     * POSTリクエストの処理。
     * フォームから送信されたタスクIDに基づき、タスクを削除。
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // リクエストのエンコーディング設定
        request.setCharacterEncoding("UTF-8");
        // 現在のセッション取得（存在しない場合はnullを返す）
        HttpSession session = request.getSession(false);

        // セッションがない、またはログインユーザー情報がない場合はログインページへリダイレクト
        if (session == null || session.getAttribute("loginUser") == null) { 
            response.sendRedirect(request.getContextPath() + "/LoginServlet?authError=true");
            return; // 処理中断
        }
    
        // セッションからログインユーザー情報取得
        User loginUser = (User) session.getAttribute("loginUser"); 
        // タスク関連のビジネスロジックを処理するサービス初期化
        TaskService taskService = new TaskService();

        String message = "";
        boolean isError = false;

        try {
            // リクエストパラメータから削除対象のタスクID取得
            int taskId = Integer.parseInt(request.getParameter("taskId"));

            // タスクサービスを使用してタスクを削除
            if (taskService.deleteTask(taskId, loginUser.getUserId())) { 
                message = "タスクを削除しました。";
            } else {
                message = "タスクの削除に失敗しました。";
                isError = true;
            }

        } catch (NumberFormatException e) {
            // タスクIDが数値に変換できない場合の例外処理
            message = "不正なタスクIDです。";
            isError = true;
        }

        // 処理結果に応じた成功またはエラーメッセージのセッション設定
        if (isError) {
            session.setAttribute("errorMessage", message);
        } else {
            session.setAttribute("successMessage", message);
        }
        // タスク一覧ページへリダイレクトし、結果を表示
        response.sendRedirect(request.getContextPath() + "/tasks");
    }
}