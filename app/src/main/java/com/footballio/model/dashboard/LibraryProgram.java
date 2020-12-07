package com.footballio.model.login.dashboard;

import java.util.List;

public class LibraryProgram {
    private List<LibraryProgram> data;
    private int programId;
    private int categoryId;
    private String categoryName;
    private String programName;
    private String Time;

    public List<LibraryProgram> getData() {
        return data;
    }

    public void setData(List<LibraryProgram> data) {
        this.data = data;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;
}
