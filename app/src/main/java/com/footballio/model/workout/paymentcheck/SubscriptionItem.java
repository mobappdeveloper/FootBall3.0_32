package com.footballio.model.workout.paymentcheck;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class SubscriptionItem{

	@SerializedName("type")
	private String type;

	@SerializedName("userid")
	private String userid;

	@SerializedName("status")
	private String status;

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setUserid(String userid){
		this.userid = userid;
	}

	public String getUserid(){
		return userid;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"SubscriptionItem{" + 
			"type = '" + type + '\'' + 
			",userid = '" + userid + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}