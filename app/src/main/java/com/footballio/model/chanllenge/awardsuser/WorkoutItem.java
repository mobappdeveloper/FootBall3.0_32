package com.footballio.model.chanllenge.awardsuser;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class WorkoutItem{

	@SerializedName("awardid")
	private String awardid;

	@SerializedName("icon")
	private String icon;

	@SerializedName("awardName")
	private String awardName;

	public void setAwardid(String awardid){
		this.awardid = awardid;
	}

	public String getAwardid(){
		return awardid;
	}

	public void setIcon(String icon){
		this.icon = icon;
	}

	public String getIcon(){
		return icon;
	}

	public void setAwardName(String awardName){
		this.awardName = awardName;
	}

	public String getAwardName(){
		return awardName;
	}

	@Override
 	public String toString(){
		return 
			"WorkoutItem{" + 
			"awardid = '" + awardid + '\'' + 
			",icon = '" + icon + '\'' + 
			",awardName = '" + awardName + '\'' + 
			"}";
		}
}