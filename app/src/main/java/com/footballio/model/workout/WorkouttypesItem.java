package com.footballio.model.workout;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class WorkouttypesItem{

	@SerializedName("type")
	private String type;

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	@Override
 	public String toString(){
		return 
			"WorkouttypesItem{" + 
			"type = '" + type + '\'' + 
			"}";
		}
}