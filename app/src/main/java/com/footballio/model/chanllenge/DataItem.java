package com.footballio.model.chanllenge;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataItem{

	@SerializedName("pize")
	private String pize;

	@SerializedName("videoUrl")
	private String videoUrl;

	@SerializedName("ChallengeId")
	private String challengeId;

	@SerializedName("description")
	private String description;

	@SerializedName("photo")
	private String photo;

	@SerializedName("bigphoto")
	private String bigphoto;

	@SerializedName("createdOn")
	private String createdOn;

	@SerializedName("Date")
	private String date;

	@SerializedName("ChallengeTitle")
	private String challengeTitle;

	@SerializedName("status")
	private String status;

	public void setPize(String pize){
		this.pize = pize;
	}

	public String getPize(){
		return pize;
	}

	public void setVideoUrl(String videoUrl){
		this.videoUrl = videoUrl;
	}

	public String getVideoUrl(){
		return videoUrl;
	}

	public void setChallengeId(String challengeId){
		this.challengeId = challengeId;
	}

	public String getChallengeId(){
		return challengeId;
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

	public void setBigphoto(String bigphoto){
		this.bigphoto = bigphoto;
	}

	public String getBigphoto(){
		return bigphoto;
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

	public void setChallengeTitle(String challengeTitle){
		this.challengeTitle = challengeTitle;
	}

	public String getChallengeTitle(){
		return challengeTitle;
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
			"DataItem{" + 
			"pize = '" + pize + '\'' + 
			",videoUrl = '" + videoUrl + '\'' + 
			",challengeId = '" + challengeId + '\'' + 
			",description = '" + description + '\'' + 
			",photo = '" + photo + '\'' + 
			",bigphoto = '" + bigphoto + '\'' + 
			",createdOn = '" + createdOn + '\'' + 
			",date = '" + date + '\'' + 
			",challengeTitle = '" + challengeTitle + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}