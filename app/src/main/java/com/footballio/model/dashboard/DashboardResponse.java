package com.footballio.model.dashboard;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DashboardResponse{

	@SerializedName("Week")
	private String week;

	@SerializedName("items")
	private List<ItemsItem> items;

	public void setWeek(String week){
		this.week = week;
	}

	public String getWeek(){
		return week;
	}

	public void setItems(List<ItemsItem> items){
		this.items = items;
	}

	public List<ItemsItem> getItems(){
		return items;
	}

	@Override
 	public String toString(){
		return 
			"DashboardResponse{" + 
			"week = '" + week + '\'' + 
			",items = '" + items + '\'' + 
			"}";
		}
}