package com.footballio.model.chanllenge;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class VideoUploadResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("status")
	private String status;

	@SerializedName("VideoUrl")
	private String videoUrl;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setVideoUrl(String videoUrl){
		this.videoUrl = videoUrl;
	}

	public String getVideoUrl(){
		return videoUrl;
	}

	@Override
 	public String toString(){
		return 
			"VideoUploadResponse{" + 
			"msg = '" + msg + '\'' + 
			",status = '" + status + '\'' + 
			",videoUrl = '" + videoUrl + '\'' + 
			"}";
		}
}