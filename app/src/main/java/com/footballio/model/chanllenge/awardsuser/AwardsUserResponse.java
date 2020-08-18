package com.footballio.model.chanllenge.awardsuser;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class AwardsUserResponse{

	@SerializedName("Ringe")
	private List<RingeItem> ringe;

	@SerializedName("Challange")
	private List<ChallangeItem> challange;

	@SerializedName("Workout")
	private List<WorkoutItem> workout;

	public void setRinge(List<RingeItem> ringe){
		this.ringe = ringe;
	}

	public List<RingeItem> getRinge(){
		return ringe;
	}

	public void setChallange(List<ChallangeItem> challange){
		this.challange = challange;
	}

	public List<ChallangeItem> getChallange(){
		return challange;
	}

	public void setWorkout(List<WorkoutItem> workout){
		this.workout = workout;
	}

	public List<WorkoutItem> getWorkout(){
		return workout;
	}

	@Override
 	public String toString(){
		return 
			"AwardsUserResponse{" + 
			"ringe = '" + ringe + '\'' + 
			",challange = '" + challange + '\'' + 
			",workout = '" + workout + '\'' + 
			"}";
		}
}