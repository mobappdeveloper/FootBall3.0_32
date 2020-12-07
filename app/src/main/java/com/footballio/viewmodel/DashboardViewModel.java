package com.footballio.viewmodel;

import android.content.Context;

import com.footballio.model.dashboard.ProgramDetails;
import com.footballio.model.dashboard.UserWeekReport;
import com.footballio.model.login.dashboard.LibraryProgram;
import com.footballio.model.login.dashboard.Philosophy;
import com.footballio.model.login.dashboard.WorkoutTrack;
import com.footballio.repository.AppSession;
import com.footballio.repository.DashboardRepository;

import java.util.List;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

public class DashboardViewModel extends ViewModel implements IDashboardViewModel {
    private MutableLiveData<String> errorResponse = new MutableLiveData<>();
    private MutableLiveData<String> workoutBegin = null;
    private MutableLiveData<String> workoutFinish = null;

    private MutableLiveData<UserWeekReport> weekReportMutableLiveData = null;
    private MutableLiveData<ProgramDetails> programDetailsMutableLiveData = null;
    private MutableLiveData<WorkoutTrack> trackMutableLiveData = null;
    private MutableLiveData<Philosophy> philosophyMutableLiveData = null;
    private MutableLiveData<LibraryProgram> programMutableLiveData = null;

    private MutableLiveData<Integer> reloadDashboard = null;

    private final DashboardRepository repository;
    private final SavedStateHandle savedStateHandle;
    private final Context application;

    @ViewModelInject
    public DashboardViewModel(DashboardRepository repository, @ApplicationContext Context application, @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;
        this.application = application;
    }

    public void initDashboardLiveData() {
        reloadDashboard = new MutableLiveData<>();
        weekReportMutableLiveData = new MutableLiveData<>();
    }

    public void initLibraryLiveData() {
        philosophyMutableLiveData = new MutableLiveData<>();
    }

    public void initLibraryDetailLiveData() {
        programMutableLiveData = new MutableLiveData<>();
    }

    public void initWorkoutDetailLiveData() {
        programDetailsMutableLiveData = new MutableLiveData<>();
        workoutBegin = new MutableLiveData<>();
    }

    public void initWorkoutBeginLiveData() {
        trackMutableLiveData = new MutableLiveData<>();
        workoutFinish = new MutableLiveData<>();
    }

    @Override
    public LiveData<WorkoutTrack> getWorkoutVideoList(int pid, String lang) {
        //DashboardRepository.getInstance().startWorkout(pid, lang, errorResponse, trackMutableLiveData);
        repository.startWorkout(pid, lang, errorResponse, trackMutableLiveData);
        return trackMutableLiveData;
    }

    @Override
    public LiveData<Philosophy> getPhilosophyList() {
        repository.getPhilosphy(errorResponse, philosophyMutableLiveData);
        return philosophyMutableLiveData;
    }

    @Override
    public LiveData<LibraryProgram> getWorkoutList(int userId, String lang, String type) {
        repository.getWorkouts(userId, lang, type, errorResponse, programMutableLiveData);
        return programMutableLiveData;
    }

    @Override
    public void getWeekReport(String id, String lang) {
        repository.getWeeklyWorkoutData(id, lang, errorResponse, weekReportMutableLiveData);

    }

    @Override
    public void getWorkoutDetails(int programId, String lang) {
        repository.getProgramDetailsData(programId, lang, errorResponse, programDetailsMutableLiveData);
    }

    @Override
    public LiveData<String> beginWorkout(int pid) {
        repository.beginWorkout(pid, AppSession.getLastLoginUserId(application), errorResponse, workoutBegin);
        return workoutBegin;

    }

    @Override
    public LiveData<String> finishWorkout(int pid) {
        repository.finishWorkout(pid, AppSession.getLastLoginUserId(application), errorResponse, workoutFinish, reloadDashboard);
        return workoutFinish;
    }

    public LiveData<UserWeekReport> getUserWeekReport() {
        return weekReportMutableLiveData;
    }

    public LiveData<ProgramDetails> getProgramDetailsData() {
        return programDetailsMutableLiveData;
    }


    public LiveData<String> getErrorResponse() {
        return errorResponse;

    }

    public LiveData<Integer> refreshDashboardData() {
        return reloadDashboard;
    }

    public List<LibraryProgram> filterProgram(List<LibraryProgram> list, int spareTime) {
        FilterProgram filterProgram = FilterProgram.getInstance();
        return filterProgram.performFiltering(spareTime, list);


    }


    //SP
    public int getLastLoginUserId() {
        return AppSession.getLastLoginUserId(application);
    }


}
