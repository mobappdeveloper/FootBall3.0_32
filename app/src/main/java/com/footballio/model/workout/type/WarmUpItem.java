package com.footballio.model.workout.type;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class WarmUpItem{

	@SerializedName("GifImage")
	private String gifImage;

	@SerializedName("Video")
	private String video;

	@SerializedName("sticker")
	private String sticker;

	@SerializedName("description")
	private String description;

	@SerializedName("title")
	private String title;

	@SerializedName("type")
	private String type;

	@SerializedName("repitations")
	private String repitations;

	@SerializedName("tid")
	private String tid;

	public void setGifImage(String gifImage){
		this.gifImage = gifImage;
	}

	public String getGifImage(){
		return gifImage;
	}

	public void setVideo(String video){
		this.video = video;
	}

	public String getVideo(){
		return video;
	}

	public void setSticker(String sticker){
		this.sticker = sticker;
	}

	public String getSticker(){
		return sticker;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setRepitations(String repitations){
		this.repitations = repitations;
	}

	public String getRepitations(){
		return repitations;
	}

	public void setTid(String tid){
		this.tid = tid;
	}

	public String getTid(){
		return tid;
	}

	@Override
 	public String toString(){
		return 
			"WarmUpItem{" + 
			"gifImage = '" + gifImage + '\'' + 
			",video = '" + video + '\'' + 
			",sticker = '" + sticker + '\'' + 
			",description = '" + description + '\'' + 
			",title = '" + title + '\'' + 
			",type = '" + type + '\'' + 
			",repitations = '" + repitations + '\'' + 
			",tid = '" + tid + '\'' + 
			"}";
		}
}