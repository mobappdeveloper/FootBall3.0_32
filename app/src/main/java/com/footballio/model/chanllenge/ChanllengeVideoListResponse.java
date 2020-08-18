package com.footballio.model.chanllenge;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ChanllengeVideoListResponse{

	@SerializedName("userphoto")
	private String userphoto;

	@SerializedName("UserName")
	private String userName;

	@SerializedName("videoUrl")
	private String videoUrl;

	@SerializedName("UserPostid")
	private int userPostid;

	@SerializedName("likeCount")
	private String likeCount;

	@SerializedName("userId")
	private int userId;

	@SerializedName("userLikeStatus")
	private String userLikeStatus;

	public void setUserphoto(String userphoto){
		this.userphoto = userphoto;
	}

	public String getUserphoto(){
		return userphoto;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setVideoUrl(String videoUrl){
		this.videoUrl = videoUrl;
	}

	public String getVideoUrl(){
		return videoUrl;
	}

	public void setUserPostid(int userPostid){
		this.userPostid = userPostid;
	}

	public int getUserPostid(){
		return userPostid;
	}

	public void setLikeCount(String likeCount){
		this.likeCount = likeCount;
	}

	public String getLikeCount(){
		return likeCount;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setUserLikeStatus(String userLikeStatus){
		this.userLikeStatus = userLikeStatus;
	}

	public String getUserLikeStatus(){
		return userLikeStatus;
	}

	@Override
 	public String toString(){
		return 
			"ChanllengeVideoListResponse{" + 
			"userphoto = '" + userphoto + '\'' + 
			",userName = '" + userName + '\'' + 
			",videoUrl = '" + videoUrl + '\'' + 
			",userPostid = '" + userPostid + '\'' + 
			",likeCount = '" + likeCount + '\'' + 
			",userId = '" + userId + '\'' + 
			",userLikeStatus = '" + userLikeStatus + '\'' + 
			"}";
		}
}