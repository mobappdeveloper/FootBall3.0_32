package com.footballio.model.community.discussionview;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class CommentsItem{

	@SerializedName("lastName")
	private String lastName;

	@SerializedName("fristName")
	private String fristName;

	@SerializedName("UserId")
	private String userId;

	@SerializedName("photo")
	private String photo;

	@SerializedName("comment")
	private String comment;

	@SerializedName("Date")
	private String date;

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setFristName(String fristName){
		this.fristName = fristName;
	}

	public String getFristName(){
		return fristName;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setPhoto(String photo){
		this.photo = photo;
	}

	public String getPhoto(){
		return photo;
	}

	public void setComment(String comment){
		this.comment = comment;
	}

	public String getComment(){
		return comment;
	}

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	@Override
 	public String toString(){
		return 
			"CommentsItem{" + 
			"lastName = '" + lastName + '\'' + 
			",fristName = '" + fristName + '\'' + 
			",userId = '" + userId + '\'' + 
			",photo = '" + photo + '\'' + 
			",comment = '" + comment + '\'' + 
			",date = '" + date + '\'' + 
			"}";
		}
}