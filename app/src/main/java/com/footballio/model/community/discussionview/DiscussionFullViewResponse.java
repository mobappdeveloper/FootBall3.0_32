package com.footballio.model.community.discussionview;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DiscussionFullViewResponse{

	@SerializedName("userphoto")
	private String userphoto;

	@SerializedName("RequestId")
	private String requestId;

	@SerializedName("commentsCount")
	private String commentsCount;

	@SerializedName("Comments")
	private List<CommentsItem> comments;

	@SerializedName("description")
	private String description;

	@SerializedName("likeCount")
	private String likeCount;

	@SerializedName("pinStatus")
	private String pinStatus;

	@SerializedName("title")
	private String title;

	@SerializedName("createdOn")
	private String createdOn;

	public void setUserphoto(String userphoto){
		this.userphoto = userphoto;
	}

	public String getUserphoto(){
		return userphoto;
	}

	public void setRequestId(String requestId){
		this.requestId = requestId;
	}

	public String getRequestId(){
		return requestId;
	}

	public void setCommentsCount(String commentsCount){
		this.commentsCount = commentsCount;
	}

	public String getCommentsCount(){
		return commentsCount;
	}

	public void setComments(List<CommentsItem> comments){
		this.comments = comments;
	}

	public List<CommentsItem> getComments(){
		return comments;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setLikeCount(String likeCount){
		this.likeCount = likeCount;
	}

	public String getLikeCount(){
		return likeCount;
	}

	public void setPinStatus(String pinStatus){
		this.pinStatus = pinStatus;
	}

	public String getPinStatus(){
		return pinStatus;
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

	@Override
 	public String toString(){
		return 
			"DiscussionFullViewResponse{" + 
			"userphoto = '" + userphoto + '\'' + 
			",requestId = '" + requestId + '\'' + 
			",commentsCount = '" + commentsCount + '\'' + 
			",comments = '" + comments + '\'' + 
			",description = '" + description + '\'' + 
			",likeCount = '" + likeCount + '\'' + 
			",pinStatus = '" + pinStatus + '\'' + 
			",title = '" + title + '\'' + 
			",createdOn = '" + createdOn + '\'' + 
			"}";
		}
}