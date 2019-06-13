package user;

import jwt.JWTTokenCreation;

public class User {
	
	String userName;
	String name;
	String designation;
	String role;
	String password;
	JWTTokenCreation tokenCreation;
	
	public User() {
		
		this.tokenCreation = new JWTTokenCreation(this);
	}
	
	public String getUserName() {
		return userName;
	}
	public User setUserName(String userName) {
		this.userName = userName;
		return this;
	}
	public String getName() {
		return name;
	}
	public User setName(String name) {
		this.name = name;
		return this;
	}
	public String getDesignation() {
		return designation;
	}
	public User setDesignation(String designation) {
		this.designation = designation;
		return this;
	}
	public String getRole() {
		return role;
	}
	public User setRole(String role) {
		this.role = role;
		return this;
	}
	public String getPassword() {
		return password;
		
	}
	public User setPassword(String password) {
		this.password = password;
		return this;
	}
	
	public JWTTokenCreation getTokenCreator() {
		return this.tokenCreation;
	}
}
