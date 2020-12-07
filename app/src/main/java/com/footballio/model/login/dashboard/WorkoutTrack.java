package com.footballio.model.login.dashboard;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Track {
    private int tid;
    private String TrackerName;
    private String Video;
    private String Type;
    @SerializedName("Track")
    private List<Track> getTrackList;

    public int getTid() {
        return tid;
    }

    public String getTrackerName() {
        return TrackerName;
    }

    public String getVideo() {
        return Video;
    }

    public String getType() {
        return Type;
    }

    public List<Track> getGetTrackList() {
        return getTrackList;
    }

}
