package ctl;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Task;
import model.TaskService;
import model.User;

/**
 * タスク編集リクエストの処理を行うサーブレット。
 * GETリクエストではタスク編集フォームの表示、
 * POSTリクエストではタスク情報の更新を実行。
 */
@WebServlet("/editTask")
public class EditTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * GETリクエストの処理。
     * タスク編集フォームの表示と、タスクIDに基づいた情報取得、JSPへのフォワード。
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // セッションの取得（存在しない場合はnullを返す）
        HttpSession session = request.getSession(false);

        // ログインユーザーがセッションに存在しない場合、ログインページへリダイレクト
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet?authError=true");
            return;
        }

        // ログインユーザー情報のセッションからの取得
        User loginUser = (User) session.getAttribute("loginUser");
        // タスクサービス初期化
        TaskService taskService = new TaskService();
        
        try {
            // リクエストパラメータからのタスクID取得
            int taskId = Integer.parseInt(request.getParameter("taskId"));
            
            // タスクIDとログインユーザーIDに基づくタスク情報取得
            Task taskToEdit = taskService.getTaskById(taskId, loginUser.getUserId()); 

            if (taskToEdit != null) {
                // 取得したタスク情報のリクエストスコープへの設定と、編集フォームへのフォワード
                request.setAttribute("task", taskToEdit);
                request.getRequestDispatcher("/WEB-INF/jsp/editTask.jsp").forward(request, response);
            } else {
                // タスクが見つからない、または編集権限がない場合のエラーメッセージ設定とリダイレクト
                session.setAttribute("errorMessage", "指定されたタスクが見つからないか、編集権限がありません。");
                response.sendRedirect(request.getContextPath() + "/tasks"); // タスクリストへリダイレクト
            }
        } catch (NumberFormatException e) {
            // タスクIDが不正な形式の場合のエラーメッセージ設定とリダイレクト
            session.setAttribute("errorMessage", "不正なタスクIDです。");
            response.sendRedirect(request.getContextPath() + "/tasks"); // タスクリストへリダイレクト
        }
    }

    /**
     * POSTリクエストの処理。
     * タスク情報の更新と、タスクリスト画面へのリダイレクト。
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // リクエストの文字エンコーディング設定
        request.setCharacterEncoding("UTF-8");
        // セッションの取得（存在しない場合はnullを返す）
        HttpSession session = request.getSession(false);

        // ログインユーザーがセッションに存在しない場合、ログインページへリダイレクト
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet?authError=true");
            return;
        }

        // ログインユーザー情報のセッションからの取得
        User loginUser = (User) session.getAttribute("loginUser");
        // タスクサービス初期化
        TaskService taskService = new TaskService();

        String message = "";
        boolean isError = false;

        try {
            // フォームから送信されたタスクIDと更新情報の取得
            int taskId = Integer.parseInt(request.getParameter("taskId"));
            String taskName = request.getParameter("taskName");
            String status = request.getParameter("status");
            String priority = request.getParameter("priority");
            String dueDateStr = request.getParameter("dueDate");
            
            LocalDate dueDate = null;
            // 期限が入力されている場合のみLocalDateへの変換
            if (dueDateStr != null && !dueDateStr.isEmpty()) {
                dueDate = LocalDate.parse(dueDateStr);
            }

            // TaskServiceのupdateTaskメソッド呼び出しによるタスク更新
            if (taskService.updateTask(taskId, loginUser.getUserId(), taskName, status, priority, dueDate)) { 
                message = "タスクを更新しました。";
            } else {
                message = "タスクの更新に失敗しました。";
                isError = true;
            }

        } catch (NumberFormatException e) {
            // タスクIDが不正な形式の場合のエラーメッセージ設定
            message = "不正なタスクIDです。";
            isError = true;
        }

        // 更新結果メッセージのセッションスコープへの保存と、タスクリスト画面へのリダイレクト
        // リダイレクト後にメッセージを表示するため、リクエストスコープではなくセッションスコープを使用
        if (isError) {
            session.setAttribute("errorMessage", message);
        } else {
            session.setAttribute("successMessage", message);
        }
        response.sendRedirect(request.getContextPath() + "/tasks"); // タスクリスト画面へリダイレクト
    }
}