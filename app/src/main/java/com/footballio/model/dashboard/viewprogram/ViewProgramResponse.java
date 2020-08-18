package com.footballio.model.dashboard.viewprogram;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ViewProgramResponse{

	@SerializedName("Programs")
	private List<ProgramsItem> programs;

	public void setPrograms(List<ProgramsItem> programs){
		this.programs = programs;
	}

	public List<ProgramsItem> getPrograms(){
		return programs;
	}

	@Override
 	public String toString(){
		return 
			"ViewProgramResponse{" + 
			"programs = '" + programs + '\'' + 
			"}";
		}
}