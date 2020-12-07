package com.footballio.model.dashboard;


import java.util.HashMap;
import java.util.List;

public class ProgramDetails {



    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getProgramName() {
        return ProgramName;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto() {
        return photo;
    }

    public String getTime() {
        return time;
    }

    private String categoryName;
    private String ProgramName;
    private String description;
    private String photo;
    private String time;

    public HashMap<String, List<Session>> getSessions() {
        return Sessions;
    }

    private HashMap<String, List<Session>> Sessions;
    private int categoryId;
}
