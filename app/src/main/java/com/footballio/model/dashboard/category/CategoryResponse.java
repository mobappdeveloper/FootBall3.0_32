package com.footballio.model.dashboard.category;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class CategoryResponse{

	@SerializedName("records")
	private List<RecordsItem> records;

	public void setRecords(List<RecordsItem> records){
		this.records = records;
	}

	public List<RecordsItem> getRecords(){
		return records;
	}

	@Override
 	public String toString(){
		return 
			"CategoryResponse{" + 
			"records = '" + records + '\'' + 
			"}";
		}
}