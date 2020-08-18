package com.footballio.model.community;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DiscussionUnlikeResponse{

	@SerializedName("topicid")
	private String topicid;

	@SerializedName("message")
	private String message;

	@SerializedName("userid")
	private String userid;

	@SerializedName("status")
	private boolean status;

	public void setTopicid(String topicid){
		this.topicid = topicid;
	}

	public String getTopicid(){
		return topicid;
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
			"DiscussionUnlikeResponse{" + 
			"topicid = '" + topicid + '\'' + 
			",message = '" + message + '\'' + 
			",userid = '" + userid + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}