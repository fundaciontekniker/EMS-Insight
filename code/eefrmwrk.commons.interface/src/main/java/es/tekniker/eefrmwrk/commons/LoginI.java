package es.tekniker.eefrmwrk.commons;

import java.io.Serializable;

/**
 * @author fdiez
 *
 */
public class LoginI implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5468162444737849891L;
	private String login;
	private String password;
	

	/**
	 * 
	 */
	public LoginI() {
		// TODO Auto-generated constructor stub
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

}
