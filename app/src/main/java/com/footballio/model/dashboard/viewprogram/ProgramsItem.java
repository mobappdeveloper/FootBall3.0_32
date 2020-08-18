package com.footballio.model.dashboard.viewprogram;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ProgramsItem{

	@SerializedName("Red")
	private String red;

	@SerializedName("Yellow")
	private String yellow;

	@SerializedName("Blue")
	private String blue;

	@SerializedName("programName")
	private String programName;

	@SerializedName("bigdesc")
	private String bigdesc;

	@SerializedName("bigphoto")
	private String bigphoto;

	@SerializedName("Orange")
	private String orange;

	@SerializedName("programId")
	private String programId;

	@SerializedName("Green")
	private String green;

	@SerializedName("Purpule")
	private String purpule;

	@SerializedName("status")
	private String status;

	public void setRed(String red){
		this.red = red;
	}

	public String getRed(){
		return red;
	}

	public void setYellow(String yellow){
		this.yellow = yellow;
	}

	public String getYellow(){
		return yellow;
	}

	public void setBlue(String blue){
		this.blue = blue;
	}

	public String getBlue(){
		return blue;
	}

	public void setProgramName(String programName){
		this.programName = programName;
	}

	public String getProgramName(){
		return programName;
	}

	public void setBigdesc(String bigdesc){
		this.bigdesc = bigdesc;
	}

	public String getBigdesc(){
		return bigdesc;
	}

	public void setBigphoto(String bigphoto){
		this.bigphoto = bigphoto;
	}

	public String getBigphoto(){
		return bigphoto;
	}

	public void setOrange(String orange){
		this.orange = orange;
	}

	public String getOrange(){
		return orange;
	}

	public void setProgramId(String programId){
		this.programId = programId;
	}

	public String getProgramId(){
		return programId;
	}

	public void setGreen(String green){
		this.green = green;
	}

	public String getGreen(){
		return green;
	}

	public void setPurpule(String purpule){
		this.purpule = purpule;
	}

	public String getPurpule(){
		return purpule;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ProgramsItem{" + 
			"red = '" + red + '\'' + 
			",yellow = '" + yellow + '\'' + 
			",blue = '" + blue + '\'' + 
			",programName = '" + programName + '\'' + 
			",bigdesc = '" + bigdesc + '\'' + 
			",bigphoto = '" + bigphoto + '\'' + 
			",orange = '" + orange + '\'' + 
			",programId = '" + programId + '\'' + 
			",green = '" + green + '\'' + 
			",purpule = '" + purpule + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}