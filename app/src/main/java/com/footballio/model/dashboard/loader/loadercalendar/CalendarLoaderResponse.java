package com.footballio.model.dashboard.loader.loadercalendar;

import java.util.List;
import javax.annotation.Generated;

import com.footballio.model.dashboard.loader.CalendarDataItem;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class CalendarLoaderResponse{

	@SerializedName("CalendarData")
	private List<com.footballio.model.dashboard.loader.CalendarDataItem> calendarData;

	@SerializedName("Data")
	private List<DataItem> data;

	public void setCalendarData(List<CalendarDataItem> calendarData){
		this.calendarData = calendarData;
	}

	public List<com.footballio.model.dashboard.loader.CalendarDataItem> getCalendarData(){
		return calendarData;
	}

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"CalendarLoaderResponse{" + 
			"calendarData = '" + calendarData + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}