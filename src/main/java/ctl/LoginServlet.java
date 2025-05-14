package ctl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.GetTaskList;
import model.LoginLogic;
import model.Task;
import model.User;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public LoginServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
	    // login.jspにフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
        dispatcher.forward(request, response);
        // login.jspからuserNameとpasswordを取得
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		User user = new User(userName, password);
		LoginLogic loginLogic = new LoginLogic();
		boolean bo = loginLogic.execute(user);
	
	
	//　ログイン成功時
    if(bo == true) {
    //セッションスコープにuserを保存する
    	HttpSession session = request.getSession();
    	session.setAttribute("user", user);
    //todoリストの取得
    	List<Task> taskList = new ArrayList<Task>();
    	GetTaskList gtl = new GetTaskList();
    	taskList = gtl.execute(user);
    // taskListに格納されたリストをリクエストスコープに保存する
    	request.setAttribute("taskLiset", taskList);
    //メイン画面に推移
    	RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
    	dispatcher.forward(request, response);
    }else {
    	request.setAttribute("errorMessage", "ユーザー名またはパスワードが間違っています。");
    	
    	// JSPにフォワードして表示
    	RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
        dispatcher.forward(request, response);
        return;
    }
  }
}
