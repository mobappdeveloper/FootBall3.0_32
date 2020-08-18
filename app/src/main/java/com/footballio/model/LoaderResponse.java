package com.footballio.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class LoaderResponse{

	@SerializedName("greenPercentageValue")
	private float greenPercentageValue;

	@SerializedName("bluePercentageValue")
	private float bluePercentageValue;

	public void setGreenPercentageValue(float greenPercentageValue){
		this.greenPercentageValue = greenPercentageValue;
	}

	public float getGreenPercentageValue(){
		return greenPercentageValue;
	}

	public void setBluePercentageValue(float bluePercentageValue){
		this.bluePercentageValue = bluePercentageValue;
	}

	public float getBluePercentageValue(){
		return bluePercentageValue;
	}

	@Override
 	public String toString(){
		return 
			"LoaderResponse{" + 
			"greenPercentageValue = '" + greenPercentageValue + '\'' + 
			",bluePercentageValue = '" + bluePercentageValue + '\'' + 
			"}";
		}
}