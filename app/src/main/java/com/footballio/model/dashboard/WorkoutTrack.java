package com.footballio.model.login.dashboard;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WorkoutTrack {
    private int tid;
    private int pid;
    private String TrackerName;

    public int getPid() {
        return pid;
    }

    public String getRepitations() {
        return repitations;
    }

    private String repitations;

    public String getGifImage() {
        return GifImage;
    }

    private String GifImage;
    public String getVideoUrl() {
        return VideoUrl;
    }

    @SerializedName("Video")
    private String VideoUrl;
    private String Type;
    @SerializedName("Tracks")
    private List<WorkoutTrack> getWorkoutTrackList;

    public int getTid() {
        return tid;
    }

    public String getTrackerName() {
        return TrackerName;
    }



    public String getType() {
        return Type;
    }

    public List<WorkoutTrack> getGetWorkoutTrackList() {
        return getWorkoutTrackList;
    }

}
