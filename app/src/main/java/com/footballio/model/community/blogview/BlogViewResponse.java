package com.footballio.model.community.blogview;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class BlogViewResponse{

	@SerializedName("RequestId")
	private String requestId;

	@SerializedName("Also Intersting")
	private List<AlsoInterstingItem> alsoIntersting;

	@SerializedName("description")
	private String description;

	@SerializedName("photo")
	private String photo;

	@SerializedName("categoryName")
	private String categoryName;

	@SerializedName("createdOn")
	private String createdOn;

	@SerializedName("Date")
	private String date;

	@SerializedName("blogTitle")
	private String blogTitle;

	public void setRequestId(String requestId){
		this.requestId = requestId;
	}

	public String getRequestId(){
		return requestId;
	}

	public void setAlsoIntersting(List<AlsoInterstingItem> alsoIntersting){
		this.alsoIntersting = alsoIntersting;
	}

	public List<AlsoInterstingItem> getAlsoIntersting(){
		return alsoIntersting;
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

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
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
			"BlogViewResponse{" + 
			"requestId = '" + requestId + '\'' + 
			",also Intersting = '" + alsoIntersting + '\'' + 
			",description = '" + description + '\'' + 
			",photo = '" + photo + '\'' + 
			",categoryName = '" + categoryName + '\'' + 
			",createdOn = '" + createdOn + '\'' + 
			",date = '" + date + '\'' + 
			",blogTitle = '" + blogTitle + '\'' + 
			"}";
		}
}