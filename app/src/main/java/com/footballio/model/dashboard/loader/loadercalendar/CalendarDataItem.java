package com.footballio.model.dashboard.loader.loadercalendar;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class CalendarDataItem{

	@SerializedName("blueLoaderValue")
	private int blueLoaderValue;

	@SerializedName("id")
	private String id;

	@SerializedName("Date")
	private String date;

	@SerializedName("greenLoaderValue")
	private int greenLoaderValue;

	public void setBlueLoaderValue(int blueLoaderValue){
		this.blueLoaderValue = blueLoaderValue;
	}

	public int getBlueLoaderValue(){
		return blueLoaderValue;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setGreenLoaderValue(int greenLoaderValue){
		this.greenLoaderValue = greenLoaderValue;
	}

	public int getGreenLoaderValue(){
		return greenLoaderValue;
	}

	@Override
 	public String toString(){
		return 
			"CalendarDataItem{" + 
			"blueLoaderValue = '" + blueLoaderValue + '\'' + 
			",id = '" + id + '\'' + 
			",date = '" + date + '\'' + 
			",greenLoaderValue = '" + greenLoaderValue + '\'' + 
			"}";
		}
}