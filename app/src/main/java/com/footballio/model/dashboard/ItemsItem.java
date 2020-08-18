package com.footballio.model.dashboard;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ItemsItem{

	@SerializedName("fitness")
	private String fitness;

	@SerializedName("programname")
	private String programname;

	@SerializedName("technique")
	private String technique;

	@SerializedName("health")
	private String health;

	@SerializedName("pid")
	private String pid;

	@SerializedName("mental")
	private String mental;

	@SerializedName("behaviour")
	private String behaviour;

	@SerializedName("body")
	private String body;

	@SerializedName("bigphoto")
	private String bigphoto;

	public void setFitness(String fitness){
		this.fitness = fitness;
	}

	public String getFitness(){
		return fitness;
	}

	public void setProgramname(String programname){
		this.programname = programname;
	}

	public String getProgramname(){
		return programname;
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

	public void setPid(String pid){
		this.pid = pid;
	}

	public String getPid(){
		return pid;
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

	public void setBigphoto(String bigphoto){
		this.bigphoto = bigphoto;
	}

	public String getBigphoto(){
		return bigphoto;
	}

	@Override
 	public String toString(){
		return 
			"ItemsItem{" + 
			"fitness = '" + fitness + '\'' + 
			",programname = '" + programname + '\'' + 
			",technique = '" + technique + '\'' + 
			",health = '" + health + '\'' + 
			",pid = '" + pid + '\'' + 
			",mental = '" + mental + '\'' + 
			",behaviour = '" + behaviour + '\'' + 
			",body = '" + body + '\'' + 
			",bigphoto = '" + bigphoto + '\'' + 
			"}";
		}
}