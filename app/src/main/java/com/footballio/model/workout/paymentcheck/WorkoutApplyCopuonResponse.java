package com.footballio.model.workout.paymentcheck;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class WorkoutApplyCopuonResponse{

	@SerializedName("Monthly/YearlyPrice")
	private double monthlyYearlyPrice;

	@SerializedName("Monthly")
	private String monthly;

	@SerializedName("Yearly")
	private String yearly;

	@SerializedName("MonthlyPrice")
	private String monthlyPrice;

	@SerializedName("YearlyPrice")
	private String yearlyPrice;

	@SerializedName("discount")
	private String discount;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setMonthlyYearlyPrice(double monthlyYearlyPrice){
		this.monthlyYearlyPrice = monthlyYearlyPrice;
	}

	public double getMonthlyYearlyPrice(){
		return monthlyYearlyPrice;
	}

	public void setMonthly(String monthly){
		this.monthly = monthly;
	}

	public String getMonthly(){
		return monthly;
	}

	public void setYearly(String yearly){
		this.yearly = yearly;
	}

	public String getYearly(){
		return yearly;
	}

	public void setMonthlyPrice(String monthlyPrice){
		this.monthlyPrice = monthlyPrice;
	}

	public String getMonthlyPrice(){
		return monthlyPrice;
	}

	public void setYearlyPrice(String yearlyPrice){
		this.yearlyPrice = yearlyPrice;
	}

	public String getYearlyPrice(){
		return yearlyPrice;
	}

	public void setDiscount(String discount){
		this.discount = discount;
	}

	public String getDiscount(){
		return discount;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"WorkoutApplyCopuonResponse{" + 
			"monthly/YearlyPrice = '" + monthlyYearlyPrice + '\'' + 
			",monthly = '" + monthly + '\'' + 
			",yearly = '" + yearly + '\'' + 
			",monthlyPrice = '" + monthlyPrice + '\'' + 
			",yearlyPrice = '" + yearlyPrice + '\'' + 
			",discount = '" + discount + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}