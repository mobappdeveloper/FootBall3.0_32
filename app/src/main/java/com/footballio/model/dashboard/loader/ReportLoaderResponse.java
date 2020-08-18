package com.footballio.model.dashboard.loader;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ReportLoaderResponse{

	@SerializedName("Graph")
	private List<GraphItem> graph;

	@SerializedName("CalendarData")
	private List<CalendarDataItem> calendarData;

	@SerializedName("Data")
	private Data data;

	@SerializedName("Cal")
	private List<CalItem> cal;

	public void setGraph(List<GraphItem> graph){
		this.graph = graph;
	}

	public List<GraphItem> getGraph(){
		return graph;
	}

	public void setCalendarData(List<CalendarDataItem> calendarData){
		this.calendarData = calendarData;
	}

	public List<CalendarDataItem> getCalendarData(){
		return calendarData;
	}

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setCal(List<CalItem> cal){
		this.cal = cal;
	}

	public List<CalItem> getCal(){
		return cal;
	}

	@Override
 	public String toString(){
		return 
			"ReportLoaderResponse{" + 
			"graph = '" + graph + '\'' + 
			",calendarData = '" + calendarData + '\'' + 
			",data = '" + data + '\'' + 
			",cal = '" + cal + '\'' + 
			"}";
		}
}