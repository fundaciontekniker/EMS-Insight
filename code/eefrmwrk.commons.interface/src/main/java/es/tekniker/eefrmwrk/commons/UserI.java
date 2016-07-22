package es.tekniker.eefrmwrk.commons;

import java.io.Serializable;

/**
 * @author fdiez
 *
 */
public class UserI implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3113676977986764913L;
	private String name;
	private String role;
	/**
	 * 
	 */
	public UserI() {
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

}

