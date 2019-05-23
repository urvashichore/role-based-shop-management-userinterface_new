package smgmt;

import java.io.Serializable;

public class UserProfile implements Serializable{

	private static final long serialVersionUID = 1L;
	private String username;
	private String role;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String userId) {
		this.username = userId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String name) {
		this.role = name;
	}
	
	@Override
	public String toString() {
		return "userProfile [username=" + username + ", role=" + role + "]";
	}
	
	public UserProfile() {
		// TODO Auto-generated constructor stub
	}
	
	public UserProfile(String userId, String name) {
		super();
		this.username = userId;
		this.role = name;
	}
}
