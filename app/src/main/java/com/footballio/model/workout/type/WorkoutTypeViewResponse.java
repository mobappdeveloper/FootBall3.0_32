package com.footballio.model.workout.type;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class WorkoutTypeViewResponse{

	public HashMap<String, WarmUpItem> getDataValues() {
		return dataValues;
	}

	public void setDataValues(HashMap<String, WarmUpItem> dataValues) {
		this.dataValues = dataValues;
	}

	//private final HashMap<Object, Object> dataVaues;
	public HashMap<String, WarmUpItem> dataValues;


	public WorkoutTypeViewResponse() {
		this.dataValues = new HashMap<>();
		//this.dataVaues = new HashMap<>();
	}
	/*public Map<String, WarmUpItem> data;

	public Map<String, WarmUpItem> getData() {
		return data;
	}

	public void setData(Map<String, WarmUpItem> data) {
		this.data = data;
	}

	@Override
 	public String toString(){
		return 
			"WorkoutTypeViewResponse{" + 
			"warm up = '" + data + '\'' +
			"}";
		}*/
}