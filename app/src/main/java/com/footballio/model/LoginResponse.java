package com.footballio.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class LoginResponse{

	@SerializedName("Email")
	private String email;

	@SerializedName("UserName")
	private String userName;

	@SerializedName("build")
	private String build;

	@SerializedName("greenPercentageValue")
	private double greenPercentageValue;

	@SerializedName("Photo")
	private String photo;

	@SerializedName("id")
	private String id;

	@SerializedName("message")
	private String message;

	@SerializedName("bluePercentageValue")
	private double bluePercentageValue;

	@SerializedName("status")
	private boolean status;

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setBuild(String build){
		this.build = build;
	}

	public String getBuild(){
		return build;
	}

	public void setGreenPercentageValue(double greenPercentageValue){
		this.greenPercentageValue = greenPercentageValue;
	}

	public double getGreenPercentageValue(){
		return greenPercentageValue;
	}

	public void setPhoto(String photo){
		this.photo = photo;
	}

	public String getPhoto(){
		return photo;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setBluePercentageValue(double bluePercentageValue){
		this.bluePercentageValue = bluePercentageValue;
	}

	public double getBluePercentageValue(){
		return bluePercentageValue;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"LoginResponse{" + 
			"email = '" + email + '\'' + 
			",userName = '" + userName + '\'' + 
			",build = '" + build + '\'' + 
			",greenPercentageValue = '" + greenPercentageValue + '\'' + 
			",photo = '" + photo + '\'' + 
			",id = '" + id + '\'' + 
			",message = '" + message + '\'' + 
			",bluePercentageValue = '" + bluePercentageValue + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}