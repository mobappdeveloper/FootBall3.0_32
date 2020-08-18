package com.footballio.model.workout.videoview;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class TracksItem{

	@SerializedName("GifImage")
	private String gifImage;

	@SerializedName("TrackerName")
	private String trackerName;

	@SerializedName("Type")
	private String type;

	@SerializedName("Video")
	private String video;

	@SerializedName("pid")
	private String pid;

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

	public void setTrackerName(String trackerName){
		this.trackerName = trackerName;
	}

	public String getTrackerName(){
		return trackerName;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setVideo(String video){
		this.video = video;
	}

	public String getVideo(){
		return video;
	}

	public void setPid(String pid){
		this.pid = pid;
	}

	public String getPid(){
		return pid;
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
			"TracksItem{" + 
			"gifImage = '" + gifImage + '\'' + 
			",trackerName = '" + trackerName + '\'' + 
			",type = '" + type + '\'' + 
			",video = '" + video + '\'' + 
			",pid = '" + pid + '\'' + 
			",repitations = '" + repitations + '\'' + 
			",tid = '" + tid + '\'' + 
			"}";
		}
}