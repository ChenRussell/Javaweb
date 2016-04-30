package action;

import service.UsersDAO_Me;
import serviceImpl.UsersDAO_Me_Impl;

import com.opensymphony.xwork2.ModelDriven;

import entity.Users;

public class UsersAction extends SuperAction implements ModelDriven<Users> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Users user = new Users();
	
	//ÓÃ»§µÇÂ¼
	public String login(){
		UsersDAO_Me udao = new UsersDAO_Me_Impl();
		if(udao.usersLogin(user)){
			return "login_success";
		}
		else{
			return "login_failure";
		}
	}
	@Override
	public Users getModel() {
		// TODO Auto-generated method stub
		return this.user;
	}

}
