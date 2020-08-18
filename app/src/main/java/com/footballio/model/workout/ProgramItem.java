package com.footballio.model.workout;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ProgramItem{

	@SerializedName("fitness")
	private String fitness;

	@SerializedName("photo")
	private String photo;

	@SerializedName("description")
	private String description;

	@SerializedName("technique")
	private String technique;

	@SerializedName("health")
	private String health;

	@SerializedName("mental")
	private String mental;

	@SerializedName("behaviour")
	private String behaviour;

	@SerializedName("body")
	private String body;

	@SerializedName("ProgramName")
	private String programName;

	public void setFitness(String fitness){
		this.fitness = fitness;
	}

	public String getFitness(){
		return fitness;
	}

	public void setPhoto(String photo){
		this.photo = photo;
	}

	public String getPhoto(){
		return photo;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setTechnique(String technique){
		this.technique = technique;
	}

	public String getTechnique(){
		return technique;
	}

	public void setHealth(String health){
		this.health = health;
	}

	public String getHealth(){
		return health;
	}

	public void setMental(String mental){
		this.mental = mental;
	}

	public String getMental(){
		return mental;
	}

	public void setBehaviour(String behaviour){
		this.behaviour = behaviour;
	}

	public String getBehaviour(){
		return behaviour;
	}

	public void setBody(String body){
		this.body = body;
	}

	public String getBody(){
		return body;
	}

	public void setProgramName(String programName){
		this.programName = programName;
	}

	public String getProgramName(){
		return programName;
	}

	@Override
 	public String toString(){
		return 
			"ProgramItem{" + 
			"fitness = '" + fitness + '\'' + 
			",photo = '" + photo + '\'' + 
			",description = '" + description + '\'' + 
			",technique = '" + technique + '\'' + 
			",health = '" + health + '\'' + 
			",mental = '" + mental + '\'' + 
			",behaviour = '" + behaviour + '\'' + 
			",body = '" + body + '\'' + 
			",programName = '" + programName + '\'' + 
			"}";
		}
}