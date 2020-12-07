package com.footballio.viewmodel;

import com.footballio.model.login.dashboard.LibraryProgram;
import com.footballio.model.login.dashboard.Philosophy;
import com.footballio.model.login.dashboard.WorkoutTrack;

import androidx.lifecycle.LiveData;

public interface IDashboardViewModel {
    void getWeekReport(String id, String lang);
    LiveData<WorkoutTrack> getWorkoutVideoList(int pid, String lang);
    LiveData<Philosophy> getPhilosophyList();
    LiveData<LibraryProgram> getWorkoutList(int userid, String lang, String type);
    void getWorkoutDetails(int programId, String lang);
    LiveData<String> beginWorkout(int pid);
    LiveData<String> finishWorkout(int pid);
}
