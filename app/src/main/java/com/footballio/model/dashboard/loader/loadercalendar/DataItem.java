package com.footballio.model.dashboard.loader.loadercalendar;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataItem{

	@SerializedName("CountofMonth")
	private int countofMonth;

	@SerializedName("WeekDayStart")
	private String weekDayStart;

	public void setCountofMonth(int countofMonth){
		this.countofMonth = countofMonth;
	}

	public int getCountofMonth(){
		return countofMonth;
	}

	public void setWeekDayStart(String weekDayStart){
		this.weekDayStart = weekDayStart;
	}

	public String getWeekDayStart(){
		return weekDayStart;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"countofMonth = '" + countofMonth + '\'' + 
			",weekDayStart = '" + weekDayStart + '\'' + 
			"}";
		}
}