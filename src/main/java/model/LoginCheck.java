package model;


import dao.UserDAO;

public class LoginCheck {
	public boolean execute(User user) {
		UserDAO dao = new UserDAO();
		boolean a = dao.findUser(user);
		return a;

	}
}
