package com.footballio.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class RegisterRequest{

	@SerializedName("status")
	private String status;

	@SerializedName("firstName")
	private String firstName;

	@SerializedName("emailAddress")
	private String emailAddress;

	@SerializedName("password")
	private String password;

	@SerializedName("lastName ")
	private String lastName;

	@SerializedName("photo")
	private String photo;

	@SerializedName("logintype")
	private String loginType;

	@SerializedName("createdOn")
	private String createdOn;

	@SerializedName("userName ")
	private String userName;

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setEmailAddress(String emailAddress){
		this.emailAddress = emailAddress;
	}

	public String getEmailAddress(){
		return emailAddress;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setPhoto(String photo){
		this.photo = photo;
	}

	public String getPhoto(){
		return photo;
	}

	public void setLoginType(String loginType){
		this.loginType = loginType;
	}

	public String getLoginType(){
		return loginType;
	}

	public void setCreatedOn(String createdOn){
		this.createdOn = createdOn;
	}

	public String getCreatedOn(){
		return createdOn;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	@Override
 	public String toString(){
		return 
			"RegisterRequest{" + 
			"status = '" + status + '\'' + 
			",firstName = '" + firstName + '\'' + 
			",emailAddress = '" + emailAddress + '\'' + 
			",password = '" + password + '\'' + 
			",lastName  = '" + lastName + '\'' + 
			",photo = '" + photo + '\'' + 
			",loginType = '" + loginType + '\'' + 
			",createdOn = '" + createdOn + '\'' + 
			",userName  = '" + userName + '\'' + 
			"}";
		}
}