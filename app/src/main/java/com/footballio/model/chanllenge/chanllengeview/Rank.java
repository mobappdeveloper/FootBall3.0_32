package com.footballio.model.chanllenge.chanllengeview;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Rank{

	@SerializedName("UserName")
	private String userName;

	@SerializedName("UserPostid")
	private String userPostid;

	@SerializedName("video")
	private String video;

	@SerializedName("userid")
	private String userid;

	@SerializedName("Userphoto")
	private String userphoto;

	@SerializedName("status")
	private String status;

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setUserPostid(String userPostid){
		this.userPostid = userPostid;
	}

	public String getUserPostid(){
		return userPostid;
	}

	public void setVideo(String video){
		this.video = video;
	}

	public String getVideo(){
		return video;
	}

	public void setUserid(String userid){
		this.userid = userid;
	}

	public String getUserid(){
		return userid;
	}

	public void setUserphoto(String userphoto){
		this.userphoto = userphoto;
	}

	public String getUserphoto(){
		return userphoto;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Rank{" + 
			"userName = '" + userName + '\'' + 
			",userPostid = '" + userPostid + '\'' + 
			",video = '" + video + '\'' + 
			",userid = '" + userid + '\'' + 
			",userphoto = '" + userphoto + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}