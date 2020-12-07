package com.footballio.model.dashboard;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserWeekReport {
    private int userid;
    @SerializedName("Week")
    private String week;
    private String technique;
    private String fitness;
    private String body;

    public int getUserid() {
        return userid;
    }

    public String getWeek() {
        return week;
    }

    public String getTechnique() {
        return technique;
    }

    public String getFitness() {
        return fitness;
    }

    public String getBody() {
        return body;
    }

    public String getMental() {
        return mental;
    }

    public String getHealth() {
        return health;
    }

    public String getBehaviour() {
        return behaviour;
    }

    public List<Program> getProgramList() {
        return programList;
    }

    private String mental;
    private String health;
    private String behaviour;
    @SerializedName("items")
    private List<Program> programList;
}
