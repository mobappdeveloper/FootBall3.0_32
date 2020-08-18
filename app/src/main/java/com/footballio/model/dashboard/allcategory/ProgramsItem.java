package com.footballio.model.dashboard.allcategory;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ProgramsItem{

	@SerializedName("topicCategoryName")
	private String topicCategoryName;

	@SerializedName("smallphoto")
	private String smallphoto;

	@SerializedName("programName")
	private String programName;

	@SerializedName("overviewId")
	private String overviewId;

	@SerializedName("bigdesc")
	private String bigdesc;

	@SerializedName("mediumphoto")
	private String mediumphoto;

	@SerializedName("bigphoto")
	private String bigphoto;

	@SerializedName("programId")
	private String programId;

	@SerializedName("programdec")
	private String programdec;

	@SerializedName("status")
	private String status;

	public void setTopicCategoryName(String topicCategoryName){
		this.topicCategoryName = topicCategoryName;
	}

	public String getTopicCategoryName(){
		return topicCategoryName;
	}

	public void setSmallphoto(String smallphoto){
		this.smallphoto = smallphoto;
	}

	public String getSmallphoto(){
		return smallphoto;
	}

	public void setProgramName(String programName){
		this.programName = programName;
	}

	public String getProgramName(){
		return programName;
	}

	public void setOverviewId(String overviewId){
		this.overviewId = overviewId;
	}

	public String getOverviewId(){
		return overviewId;
	}

	public void setBigdesc(String bigdesc){
		this.bigdesc = bigdesc;
	}

	public String getBigdesc(){
		return bigdesc;
	}

	public void setMediumphoto(String mediumphoto){
		this.mediumphoto = mediumphoto;
	}

	public String getMediumphoto(){
		return mediumphoto;
	}

	public void setBigphoto(String bigphoto){
		this.bigphoto = bigphoto;
	}

	public String getBigphoto(){
		return bigphoto;
	}

	public void setProgramId(String programId){
		this.programId = programId;
	}

	public String getProgramId(){
		return programId;
	}

	public void setProgramdec(String programdec){
		this.programdec = programdec;
	}

	public String getProgramdec(){
		return programdec;
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
			"topicCategoryName = '" + topicCategoryName + '\'' + 
			",smallphoto = '" + smallphoto + '\'' + 
			",programName = '" + programName + '\'' + 
			",overviewId = '" + overviewId + '\'' + 
			",bigdesc = '" + bigdesc + '\'' + 
			",mediumphoto = '" + mediumphoto + '\'' + 
			",bigphoto = '" + bigphoto + '\'' + 
			",programId = '" + programId + '\'' + 
			",programdec = '" + programdec + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}