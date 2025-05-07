package model;

import dao.UserDAO;

public class LoginLogic {
	
	UserDAO dao = new UserDAO();
	
	public boolean execute(User user){
		boolean a = dao.findUser(user);
		return a;
	}

}
