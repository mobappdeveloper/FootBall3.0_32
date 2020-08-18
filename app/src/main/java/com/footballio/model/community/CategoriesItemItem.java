package com.footballio.model.community;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class CategoriesItemItem{

	@SerializedName("categoryName")
	private String categoryName;

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
	}

	@Override
 	public String toString(){
		return 
			"CategoriesItemItem{" + 
			"categoryName = '" + categoryName + '\'' + 
			"}";
		}
}