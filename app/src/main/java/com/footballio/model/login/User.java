package com.footballio.model.login;

import com.google.gson.annotations.SerializedName;

public class User {

    private String userName;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private int logintype;
    private String photo;
    private String status;
    private String deviceid;
    private String digitCode;
    private String createdOn;

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getClub() {
        return club;
    }

    public String getNationality() {
        return nationality;
    }

    public String getDob() {
        return dob;
    }

    public String getPosition() {
        return position;
    }

    private String height;
    private String weight;
    private String club;
    private String nationality;
    private String dob;
    private String position;
    private int id;

    private String Email;

    public String getEmail() {
        return Email;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }

    public String getSize() {
        return size;
    }

    public String getFavourite_player() {
        return favourite_player;
    }

    private String dateofBirth;
    private String size;
    private String favourite_player;

    public void setResponse(String response) {
        this.response = response;
    }

    @SerializedName("message")
    private String response;

    public User(String userName, String firstName, String lastName, String emailAddress, String password, int logintype, String photo, String status, String deviceid, String digitCode, String createdOn) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.logintype = logintype;
        this.photo = photo;
        this.status = status;
        this.deviceid = deviceid;
        this.digitCode = digitCode;
        this.createdOn = createdOn;
    }

    public User(int id,String userName, String firstName, String lastName, String height, String weight, String club, String nationality, String dob, String position) {
        this.id=id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
        this.weight = weight;
        this.club = club;
        this.nationality = nationality;
        this.dob = dob;
        this.position = position;
    }


    public String getUserName() {
        if (userName == null) {
            return "";
        }
        return userName;
    }

    public String getFirstName() {
        if (firstName == null) {
            return "";
        }
        return firstName;
    }

    public String getLastName() {
        if (lastName == null) {
            return "";
        }
        return lastName;
    }

    public String getEmailAddress() {
        if (emailAddress == null) {
            return "";
        }
        return emailAddress;
    }

    public String getPassword() {
        if (password == null) {
            return "";
        }
        return password;
    }

    public int getLogintype() {
        return logintype;
    }

    public String getPhoto() {
        return photo;
    }

    public String getStatus() {
        return status;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public String getDigitCode() {
        if (digitCode == null) {
            return "";
        }
        return digitCode;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public int getId() {
        return id;
    }

    public String getResponse() {
        return response;
    }


}
