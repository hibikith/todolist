package model;

import dao.UserDAO;

public class LoginLogic {
	
	UserDAO dao = new UserDAO();
	
	public boolean execute(User user){
		boolean result = dao.findUser(user);
		return result;
	}

}
