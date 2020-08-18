package com.footballio.model.community;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class FavoritenItem{

	@SerializedName("blogCategoryName")
	private String blogCategoryName;

	@SerializedName("description")
	private String description;

	@SerializedName("photo")
	private String photo;

	@SerializedName("blogCategoryId")
	private String blogCategoryId;

	@SerializedName("blogId")
	private String blogId;

	@SerializedName("bigphoto")
	private String bigphoto;

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

	public void setBigphoto(String bigphoto){
		this.bigphoto = bigphoto;
	}

	public String getBigphoto(){
		return bigphoto;
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
			"FavoritenItem{" + 
			"blogCategoryName = '" + blogCategoryName + '\'' + 
			",description = '" + description + '\'' + 
			",photo = '" + photo + '\'' + 
			",blogCategoryId = '" + blogCategoryId + '\'' + 
			",blogId = '" + blogId + '\'' + 
			",bigphoto = '" + bigphoto + '\'' + 
			",date = '" + date + '\'' + 
			",blogTitle = '" + blogTitle + '\'' + 
			"}";
		}
}