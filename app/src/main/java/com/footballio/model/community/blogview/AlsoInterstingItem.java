package com.footballio.model.community.blogview;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class AlsoInterstingItem{

	@SerializedName("photo")
	private String photo;

	@SerializedName("blogId")
	private String blogId;

	@SerializedName("createdOn")
	private String createdOn;

	@SerializedName("Date")
	private String date;

	@SerializedName("blogTitle")
	private String blogTitle;

	public void setPhoto(String photo){
		this.photo = photo;
	}

	public String getPhoto(){
		return photo;
	}

	public void setBlogId(String blogId){
		this.blogId = blogId;
	}

	public String getBlogId(){
		return blogId;
	}

	public void setCreatedOn(String createdOn){
		this.createdOn = createdOn;
	}

	public String getCreatedOn(){
		return createdOn;
	}

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setBlogTitle(String blogTitle){
		this.blogTitle = blogTitle;
	}

	public String getBlogTitle(){
		return blogTitle;
	}

	@Override
 	public String toString(){
		return 
			"AlsoInterstingItem{" + 
			"photo = '" + photo + '\'' + 
			",blogId = '" + blogId + '\'' + 
			",createdOn = '" + createdOn + '\'' + 
			",date = '" + date + '\'' + 
			",blogTitle = '" + blogTitle + '\'' + 
			"}";
		}
}