package com.footballio.model.chanllenge;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ChanllengeUnlikeResponse{

	@SerializedName("challangeid")
	private String challangeid;

	@SerializedName("postid")
	private String postid;

	@SerializedName("message")
	private String message;

	@SerializedName("userid")
	private String userid;

	@SerializedName("status")
	private boolean status;

	public void setChallangeid(String challangeid){
		this.challangeid = challangeid;
	}

	public String getChallangeid(){
		return challangeid;
	}

	public void setPostid(String postid){
		this.postid = postid;
	}

	public String getPostid(){
		return postid;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setUserid(String userid){
		this.userid = userid;
	}

	public String getUserid(){
		return userid;
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
			"ChanllengeUnlikeResponse{" + 
			"challangeid = '" + challangeid + '\'' + 
			",postid = '" + postid + '\'' + 
			",message = '" + message + '\'' + 
			",userid = '" + userid + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}