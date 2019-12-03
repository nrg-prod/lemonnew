package com.nrg.lemon.domain;

import java.io.Serializable;

public class LoginDataBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1806058246603916669L;
	private String userName;
	private String password;
	private String userStatus;
	private String passwordStatus;
	private String status;
	private String personID;
	private String clientID;
	
	
	private String newusername;
	private String newpassword;
	private String cfusername;
	private String cfpassword;
	
	
	
	public String getNewusername() {
		return newusername;
	}
	public void setNewusername(String newusername) {
		this.newusername = newusername;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getCfusername() {
		return cfusername;
	}
	public void setCfusername(String cfusername) {
		this.cfusername = cfusername;
	}
	public String getCfpassword() {
		return cfpassword;
	}
	public void setCfpassword(String cfpassword) {
		this.cfpassword = cfpassword;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public String getPasswordStatus() {
		return passwordStatus;
	}
	public void setPasswordStatus(String passwordStatus) {
		this.passwordStatus = passwordStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPersonID() {
		return personID;
	}
	public void setPersonID(String personID) {
		this.personID = personID;
	}
	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	
}

