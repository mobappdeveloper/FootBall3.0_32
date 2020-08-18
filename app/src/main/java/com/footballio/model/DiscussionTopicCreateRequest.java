package com.footballio.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DiscussionTopicCreateRequest{

	@SerializedName("tag1")
	private String tag1;

	@SerializedName("post")
	private String post;

	@SerializedName("title")
	private String title;

	@SerializedName("userid")
	private String userid;

	@SerializedName("tag2")
	private String tag2;

	@SerializedName("tag3")
	private String tag3;

	public void setTag1(String tag1){
		this.tag1 = tag1;
	}

	public String getTag1(){
		return tag1;
	}

	public void setPost(String post){
		this.post = post;
	}

	public String getPost(){
		return post;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setUserid(String userid){
		this.userid = userid;
	}

	public String getUserid(){
		return userid;
	}

	public void setTag2(String tag2){
		this.tag2 = tag2;
	}

	public String getTag2(){
		return tag2;
	}

	public void setTag3(String tag3){
		this.tag3 = tag3;
	}

	public String getTag3(){
		return tag3;
	}

	@Override
 	public String toString(){
		return 
			"DiscussionTopicCreateRequest{" + 
			"tag1 = '" + tag1 + '\'' + 
			",post = '" + post + '\'' + 
			",title = '" + title + '\'' + 
			",userid = '" + userid + '\'' + 
			",tag2 = '" + tag2 + '\'' + 
			",tag3 = '" + tag3 + '\'' + 
			"}";
		}
}