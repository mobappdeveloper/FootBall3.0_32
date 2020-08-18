package com.footballio.model.workout.videoview;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class WorkoutVideoViewResponse{

	@SerializedName("Tracks")
	private List<TracksItem> tracks;

	public void setTracks(List<TracksItem> tracks){
		this.tracks = tracks;
	}

	public List<TracksItem> getTracks(){
		return tracks;
	}

	@Override
 	public String toString(){
		return 
			"WorkoutVideoViewResponse{" + 
			"tracks = '" + tracks + '\'' + 
			"}";
		}
}