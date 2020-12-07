package com.footballio.model.login.dashboard;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Philosophy {

    @SerializedName("Philosophy")
    private List<Philosophy> philosophyList;
    private int id;
    private String categoryName;
    private String description;
    private String bgcolour;



    public List<Philosophy> getPhilosophyList() {
        return philosophyList;
    }

    public int getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getDescription() {
        return description;
    }

    public String getBgcolour() {
        return bgcolour;
    }
}
