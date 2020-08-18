package com.footballio.model.workout.paymentcheck;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class WorkoutPaymentPriceResponse{

	@SerializedName("Monthly/YearlyPrice")
	private double monthlyYearlyPrice;

	@SerializedName("Discount")
	private int discount;

	@SerializedName("Trail")
	private int trail;

	@SerializedName("CouponStatus")
	private String couponStatus;

	@SerializedName("Yearlykey")
	private String yearlykey;

	@SerializedName("MonthlyPrice")
	private String monthlyPrice;

	@SerializedName("YearlyPrice")
	private String yearlyPrice;

	@SerializedName("MonthlyKey")
	private String monthlyKey;

	@SerializedName("status")
	private boolean status;

	public void setMonthlyYearlyPrice(double monthlyYearlyPrice){
		this.monthlyYearlyPrice = monthlyYearlyPrice;
	}

	public double getMonthlyYearlyPrice(){
		return monthlyYearlyPrice;
	}

	public void setDiscount(int discount){
		this.discount = discount;
	}

	public int getDiscount(){
		return discount;
	}

	public void setTrail(int trail){
		this.trail = trail;
	}

	public int getTrail(){
		return trail;
	}

	public void setCouponStatus(String couponStatus){
		this.couponStatus = couponStatus;
	}

	public String getCouponStatus(){
		return couponStatus;
	}

	public void setYearlykey(String yearlykey){
		this.yearlykey = yearlykey;
	}

	public String getYearlykey(){
		return yearlykey;
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

	public void setMonthlyKey(String monthlyKey){
		this.monthlyKey = monthlyKey;
	}

	public String getMonthlyKey(){
		return monthlyKey;
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
			"WorkoutPaymentPriceResponse{" + 
			"monthly/YearlyPrice = '" + monthlyYearlyPrice + '\'' + 
			",discount = '" + discount + '\'' + 
			",trail = '" + trail + '\'' + 
			",couponStatus = '" + couponStatus + '\'' + 
			",yearlykey = '" + yearlykey + '\'' + 
			",monthlyPrice = '" + monthlyPrice + '\'' + 
			",yearlyPrice = '" + yearlyPrice + '\'' + 
			",monthlyKey = '" + monthlyKey + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}