package com.footballio.model.profile;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ProfileViewResponse{

	@SerializedName("lastName")
	private String lastName;

	@SerializedName("dateofBirth")
	private String dateofBirth;

	@SerializedName("Email")
	private String email;

	@SerializedName("coins")
	private String coins;

	@SerializedName("photo")
	private String photo;

	@SerializedName("userName")
	private String userName;

	@SerializedName("points")
	private String points;

	@SerializedName("userAwards")
	private List<Object> userAwards;

	@SerializedName("firstName")
	private String firstName;

	@SerializedName("size")
	private String size;

	@SerializedName("nationality")
	private String nationality;

	@SerializedName("club")
	private String club;

	@SerializedName("id")
	private String id;

	@SerializedName("position")
	private String position;

	@SerializedName("CurrentMonth")
	private String currentMonth;

	@SerializedName("height")
	private String height;

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setDateofBirth(String dateofBirth){
		this.dateofBirth = dateofBirth;
	}

	public String getDateofBirth(){
		return dateofBirth;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setCoins(String coins){
		this.coins = coins;
	}

	public String getCoins(){
		return coins;
	}

	public void setPhoto(String photo){
		this.photo = photo;
	}

	public String getPhoto(){
		return photo;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setPoints(String points){
		this.points = points;
	}

	public String getPoints(){
		return points;
	}

	public void setUserAwards(List<Object> userAwards){
		this.userAwards = userAwards;
	}

	public List<Object> getUserAwards(){
		return userAwards;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setSize(String size){
		this.size = size;
	}

	public String getSize(){
		return size;
	}

	public void setNationality(String nationality){
		this.nationality = nationality;
	}

	public String getNationality(){
		return nationality;
	}

	public void setClub(String club){
		this.club = club;
	}

	public String getClub(){
		return club;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPosition(String position){
		this.position = position;
	}

	public String getPosition(){
		return position;
	}

	public void setCurrentMonth(String currentMonth){
		this.currentMonth = currentMonth;
	}

	public String getCurrentMonth(){
		return currentMonth;
	}

	public void setHeight(String height){
		this.height = height;
	}

	public String getHeight(){
		return height;
	}

	@Override
 	public String toString(){
		return 
			"ProfileViewResponse{" + 
			"lastName = '" + lastName + '\'' + 
			",dateofBirth = '" + dateofBirth + '\'' + 
			",email = '" + email + '\'' + 
			",coins = '" + coins + '\'' + 
			",photo = '" + photo + '\'' + 
			",userName = '" + userName + '\'' + 
			",points = '" + points + '\'' + 
			",userAwards = '" + userAwards + '\'' + 
			",firstName = '" + firstName + '\'' + 
			",size = '" + size + '\'' + 
			",nationality = '" + nationality + '\'' + 
			",club = '" + club + '\'' + 
			",id = '" + id + '\'' + 
			",position = '" + position + '\'' + 
			",currentMonth = '" + currentMonth + '\'' + 
			",height = '" + height + '\'' + 
			"}";
		}
}