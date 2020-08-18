package com.footballio.model.community;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class BlogsItem{

	@SerializedName("blogCategoryName")
	private String blogCategoryName;

	@SerializedName("rating")
	private Object rating;

	@SerializedName("description")
	private String description;

	@SerializedName("photo")
	private String photo;

	@SerializedName("blogCategoryId")
	private String blogCategoryId;

	@SerializedName("blogId")
	private String blogId;

	@SerializedName("createdOn")
	private String createdOn;

	@SerializedName("Date")
	private String date;

	@SerializedName("blogTitle")
	private String blogTitle;

	public void setBlogCategoryName(String blogCategoryName){
		this.blogCategoryName = blogCategoryName;
	}

	public String getBlogCategoryName(){
		return blogCategoryName;
	}

	public void setRating(Object rating){
		this.rating = rating;
	}

	public Object getRating(){
		return rating;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setPhoto(String photo){
		this.photo = photo;
	}

	public String getPhoto(){
		return photo;
	}

	public void setBlogCategoryId(String blogCategoryId){
		this.blogCategoryId = blogCategoryId;
	}

	public String getBlogCategoryId(){
		return blogCategoryId;
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
			"BlogsItem{" + 
			"blogCategoryName = '" + blogCategoryName + '\'' + 
			",rating = '" + rating + '\'' + 
			",description = '" + description + '\'' + 
			",photo = '" + photo + '\'' + 
			",blogCategoryId = '" + blogCategoryId + '\'' + 
			",blogId = '" + blogId + '\'' + 
			",createdOn = '" + createdOn + '\'' + 
			",date = '" + date + '\'' + 
			",blogTitle = '" + blogTitle + '\'' + 
			"}";
		}
}