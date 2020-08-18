package com.footballio.model.workout;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class WorkoutViewResponse{

	@SerializedName("Program")
	private List<ProgramItem> program;

	@SerializedName("workouttypes")
	private List<WorkouttypesItem> workouttypes;

	public void setProgram(List<ProgramItem> program){
		this.program = program;
	}

	public List<ProgramItem> getProgram(){
		return program;
	}

	public void setWorkouttypes(List<WorkouttypesItem> workouttypes){
		this.workouttypes = workouttypes;
	}

	public List<WorkouttypesItem> getWorkouttypes(){
		return workouttypes;
	}

	@Override
 	public String toString(){
		return 
			"WorkoutViewResponse{" + 
			"program = '" + program + '\'' + 
			",workouttypes = '" + workouttypes + '\'' + 
			"}";
		}
}