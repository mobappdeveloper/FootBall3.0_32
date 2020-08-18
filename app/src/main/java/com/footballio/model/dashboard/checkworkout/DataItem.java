package com.footballio.model.dashboard.checkworkout;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataItem{

	@SerializedName("image")
	private String image;

	@SerializedName("workoutId")
	private String workoutId;

	@SerializedName("pid")
	private String pid;

	@SerializedName("ProgramName")
	private String programName;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setWorkoutId(String workoutId){
		this.workoutId = workoutId;
	}

	public String getWorkoutId(){
		return workoutId;
	}

	public void setPid(String pid){
		this.pid = pid;
	}

	public String getPid(){
		return pid;
	}

	public void setProgramName(String programName){
		this.programName = programName;
	}

	public String getProgramName(){
		return programName;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"image = '" + image + '\'' + 
			",workoutId = '" + workoutId + '\'' + 
			",pid = '" + pid + '\'' + 
			",programName = '" + programName + '\'' + 
			"}";
		}
}