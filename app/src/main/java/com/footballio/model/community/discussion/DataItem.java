package com.footballio.model.community.discussion;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataItem{

	@SerializedName("postedby")
	private String postedby;

	@SerializedName("favouriteCount")
	private String favouriteCount;

	@SerializedName("discussionId")
	private String discussionId;

	@SerializedName("UserId")
	private String userId;

	@SerializedName("favouriteStatus")
	private String favouriteStatus;

	@SerializedName("photo")
	private String photo;

	@SerializedName("title")
	private String title;

	@SerializedName("createdOn")
	private String createdOn;

	@SerializedName("Tags")
	private String tags;

	@SerializedName("commentCount")
	private String commentCount;

	public void setPostedby(String postedby){
		this.postedby = postedby;
	}

	public String getPostedby(){
		return postedby;
	}

	public void setFavouriteCount(String favouriteCount){
		this.favouriteCount = favouriteCount;
	}

	public String getFavouriteCount(){
		return favouriteCount;
	}

	public void setDiscussionId(String discussionId){
		this.discussionId = discussionId;
	}

	public String getDiscussionId(){
		return discussionId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setFavouriteStatus(String favouriteStatus){
		this.favouriteStatus = favouriteStatus;
	}

	public String getFavouriteStatus(){
		return favouriteStatus;
	}

	public void setPhoto(String photo){
		this.photo = photo;
	}

	public String getPhoto(){
		return photo;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setCreatedOn(String createdOn){
		this.createdOn = createdOn;
	}

	public String getCreatedOn(){
		return createdOn;
	}

	public void setTags(String tags){
		this.tags = tags;
	}

	public String getTags(){
		return tags;
	}

	public void setCommentCount(String commentCount){
		this.commentCount = commentCount;
	}

	public String getCommentCount(){
		return commentCount;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"postedby = '" + postedby + '\'' + 
			",favouriteCount = '" + favouriteCount + '\'' + 
			",discussionId = '" + discussionId + '\'' + 
			",userId = '" + userId + '\'' + 
			",favouriteStatus = '" + favouriteStatus + '\'' + 
			",photo = '" + photo + '\'' + 
			",title = '" + title + '\'' + 
			",createdOn = '" + createdOn + '\'' + 
			",tags = '" + tags + '\'' + 
			",commentCount = '" + commentCount + '\'' + 
			"}";
		}
}