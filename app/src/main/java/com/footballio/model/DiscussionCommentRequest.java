package com.footballio.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DiscussionCommentRequest{

	@SerializedName("discussionId")
	private String discussionId;

	@SerializedName("comment")
	private String comment;

	@SerializedName("userid")
	private String userid;

	public void setDiscussionId(String discussionId){
		this.discussionId = discussionId;
	}

	public String getDiscussionId(){
		return this.discussionId;
	}

	public void setComment(String comment){
		this.comment = comment;
	}

	public String getComment(){
		return this.comment;
	}

	public void setUserid(String userid){
		this.userid = userid;
	}

	public String getUserid(){
		return userid;
	}

	@Override
 	public String toString(){
		return 
			"DiscussionCommentRequest{" + 
			"discussionId = '" + discussionId + '\'' + 
			",comment = '" + comment + '\'' + 
			",userid = '" + userid + '\'' + 
			"}";
		}
}