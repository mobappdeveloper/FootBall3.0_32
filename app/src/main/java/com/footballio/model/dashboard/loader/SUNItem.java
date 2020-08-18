package com.footballio.model.dashboard.loader;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class SUNItem{

	@SerializedName("fitness")
	private int fitness;

	@SerializedName("technique")
	private int technique;

	@SerializedName("health")
	private int health;

	@SerializedName("mental")
	private int mental;

	@SerializedName("behaviour")
	private int behaviour;

	@SerializedName("body")
	private int body;

	public void setFitness(int fitness){
		this.fitness = fitness;
	}

	public int getFitness(){
		return fitness;
	}

	public void setTechnique(int technique){
		this.technique = technique;
	}

	public int getTechnique(){
		return technique;
	}

	public void setHealth(int health){
		this.health = health;
	}

	public int getHealth(){
		return health;
	}

	public void setMental(int mental){
		this.mental = mental;
	}

	public int getMental(){
		return mental;
	}

	public void setBehaviour(int behaviour){
		this.behaviour = behaviour;
	}

	public int getBehaviour(){
		return behaviour;
	}

	public void setBody(int body){
		this.body = body;
	}

	public int getBody(){
		return body;
	}

	@Override
 	public String toString(){
		return 
			"SUNItem{" + 
			"fitness = '" + fitness + '\'' + 
			",technique = '" + technique + '\'' + 
			",health = '" + health + '\'' + 
			",mental = '" + mental + '\'' + 
			",behaviour = '" + behaviour + '\'' + 
			",body = '" + body + '\'' + 
			"}";
		}
}