package com.footballio.model.dashboard.loader;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class GraphItem{

	@SerializedName("THU")
	private List<THUItem> tHU;

	@SerializedName("TUE")
	private List<TUEItem> tUE;

	@SerializedName("WED")
	private List<WEDItem> wED;

	@SerializedName("SAT")
	private List<SATItem> sAT;

	@SerializedName("FRI")
	private List<FRIItem> fRI;

	@SerializedName("MON")
	private List<MONItem> mON;

	@SerializedName("SUN")
	private List<SUNItem> sUN;

	public void setTHU(List<THUItem> tHU){
		this.tHU = tHU;
	}

	public List<THUItem> getTHU(){
		return tHU;
	}

	public void setTUE(List<TUEItem> tUE){
		this.tUE = tUE;
	}

	public List<TUEItem> getTUE(){
		return tUE;
	}

	public void setWED(List<WEDItem> wED){
		this.wED = wED;
	}

	public List<WEDItem> getWED(){
		return wED;
	}

	public void setSAT(List<SATItem> sAT){
		this.sAT = sAT;
	}

	public List<SATItem> getSAT(){
		return sAT;
	}

	public void setFRI(List<FRIItem> fRI){
		this.fRI = fRI;
	}

	public List<FRIItem> getFRI(){
		return fRI;
	}

	public void setMON(List<MONItem> mON){
		this.mON = mON;
	}

	public List<MONItem> getMON(){
		return mON;
	}

	public void setSUN(List<SUNItem> sUN){
		this.sUN = sUN;
	}

	public List<SUNItem> getSUN(){
		return sUN;
	}

	@Override
 	public String toString(){
		return 
			"GraphItem{" + 
			"tHU = '" + tHU + '\'' + 
			",tUE = '" + tUE + '\'' + 
			",wED = '" + wED + '\'' + 
			",sAT = '" + sAT + '\'' + 
			",fRI = '" + fRI + '\'' + 
			",mON = '" + mON + '\'' + 
			",sUN = '" + sUN + '\'' + 
			"}";
		}
}