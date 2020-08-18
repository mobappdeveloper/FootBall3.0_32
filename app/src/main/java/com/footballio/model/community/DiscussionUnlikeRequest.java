package com.footballio.model.community;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DiscussionUnlikeRequest{

	@SerializedName("topicid")
	private String topicid;

	@SerializedName("userid")
	private String userid;

	public void setTopicid(String topicid){
		this.topicid = topicid;
	}

	public String getTopicid(){
		return topicid;
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
			"DiscussionUnlikeRequest{" + 
			"topicid = '" + topicid + '\'' + 
			",userid = '" + userid + '\'' + 
			"}";
		}
}