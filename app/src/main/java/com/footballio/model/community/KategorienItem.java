package com.footballio.model.community;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class KategorienItem{

	@SerializedName("CategoryId")
	private String categoryId;

	@SerializedName("CategoryName")
	private String categoryName;

	@SerializedName("CategoryPhoto")
	private String categoryPhoto;

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return categoryId;
	}

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public void setCategoryPhoto(String categoryPhoto){
		this.categoryPhoto = categoryPhoto;
	}

	public String getCategoryPhoto(){
		return categoryPhoto;
	}

	@Override
 	public String toString(){
		return 
			"KategorienItem{" + 
			"categoryId = '" + categoryId + '\'' + 
			",categoryName = '" + categoryName + '\'' + 
			",categoryPhoto = '" + categoryPhoto + '\'' + 
			"}";
		}
}