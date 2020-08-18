package com.footballio.model.chanllenge.ranking;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataItem{

	@SerializedName("UserName")
	private String userName;

	@SerializedName("rank")
	private String rank;

	@SerializedName("photo")
	private String photo;

	@SerializedName("userid")
	private String userid;

	@SerializedName("Prize")
	private String prize;

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setRank(String rank){
		this.rank = rank;
	}

	public String getRank(){
		return rank;
	}

	public void setPhoto(String photo){
		this.photo = photo;
	}

	public String getPhoto(){
		return photo;
	}

	public void setUserid(String userid){
		this.userid = userid;
	}

	public String getUserid(){
		return userid;
	}

	public void setPrize(String prize){
		this.prize = prize;
	}

	public String getPrize(){
		return prize;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"userName = '" + userName + '\'' + 
			",rank = '" + rank + '\'' + 
			",photo = '" + photo + '\'' + 
			",userid = '" + userid + '\'' + 
			",prize = '" + prize + '\'' + 
			"}";
		}
}