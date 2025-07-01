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

import model.Task;
import model.TaskService;
import model.User;

/**
 * ユーザーのタスクリスト表示と新しいタスクの追加を管理するサーブレット。
 * GETリクエストでタスク一覧を表示し、POSTリクエストでタスクを追加。
 */
@WebServlet("/tasks") 
public class TaskListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // TaskServiceのインスタンス（サーブレット初期化時に一度だけ作成）
    private TaskService taskService;

    @Override
    public void init() throws ServletException {
        super.init();
        // サーブレット初期化時のTaskServiceインスタンス作成
        taskService = new TaskService();
    }

    /**
     * GETリクエストの処理。
     * ログインユーザーのタスクリストを取得し、taskList.jspに表示。
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser"); // セッションからのログインユーザー情報取得

        // ログインしていない場合のログインページへのリダイレクト
        if (loginUser == null) {
            response.sendRedirect("login.jsp"); 
            return;
        }

        int userId = loginUser.getUserId(); // ログインユーザーIDの取得
        
        // TaskServiceメソッド呼び出しによるタスクリストの取得
        List<Task> taskList = taskService.getUserTasks(userId);

        // リクエストスコープへのタスクリスト保存
        request.setAttribute("taskList", taskList);

        // JSPへのフォワードと表示
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/taskList.jsp"); 
        dispatcher.forward(request, response);
    }

    /**
     * POSTリクエストの処理。
     * フォームから送信された新しいタスク情報の取得と追加。
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // リクエストのエンコーディング設定

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        // ログインしていない場合のログインページへのリダイレクト
        if (loginUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = loginUser.getUserId();

        // リクエストパラメータからのタスク情報取得
        String taskName = request.getParameter("taskName");
        String status = request.getParameter("status"); 
        String priority = request.getParameter("priority"); 
        String dueDateStr = request.getParameter("dueDate"); // 日付文字列

        // Taskオブジェクトの作成と情報設定
        Task newTask = new Task();
        newTask.setUserId(userId);
        newTask.setTaskName(taskName);
        newTask.setStatus(status != null ? status : "Not Started"); // デフォルト値設定
        newTask.setPriority(priority != null ? priority : "Low"); // デフォルト値設定

        // 期限日の解析（文字列からLocalDateへ）
        if (dueDateStr != null && !dueDateStr.isEmpty()) {
            try {
                newTask.setDueDate(java.time.LocalDate.parse(dueDateStr));
            } catch (java.time.format.DateTimeParseException e) {
                // 日付形式不正時のエラー出力
                System.err.println("Invalid due date format: " + dueDateStr);
                
            }
        }

        // TaskServiceメソッド呼び出しによるタスク追加
        boolean isAdded = taskService.addTask(newTask);

        if (isAdded) {
            // 追加成功時のタスクリスト表示ページへのリダイレクト（doGet呼び出し）
            response.sendRedirect(request.getContextPath() + "/tasks"); 
        } else {
            // 追加失敗時のエラーメッセージ設定と再表示
            request.setAttribute("errorMessage", "タスクの追加に失敗しました。");
            doGet(request, response); // 現在のタスクリストを再表示
        }
    }
}