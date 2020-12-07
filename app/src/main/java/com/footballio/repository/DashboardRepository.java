package com.footballio.repository;

import com.footballio.Utils.AppConst;
import com.footballio.model.dashboard.ProgramDetails;
import com.footballio.model.dashboard.UserWeekReport;
import com.footballio.model.login.User;
import com.footballio.model.login.dashboard.LibraryProgram;
import com.footballio.model.login.dashboard.Philosophy;
import com.footballio.model.login.dashboard.WorkoutTrack;
import com.footballio.network.FootballService;
import com.footballio.network.InternetUtil;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class DashboardRepository {
    //private FootballService service;
    private final FootballService footballService;
    //private static DashboardRepository dashboardRepository;
//
//    public static DashboardRepository getInstance() {
//        if (dashboardRepository == null) {
//            dashboardRepository = new DashboardRepository();
//        }
//        return dashboardRepository;
//    }

    public DashboardRepository(FootballService service) {
      this.footballService=service;
    }

    public void getWeeklyWorkoutData(String id, String lang, MutableLiveData<String> error, MutableLiveData<UserWeekReport> success) {
        //service = getFootballService();
        Call<UserWeekReport> weekReportCall = footballService.getWeekReport(id, lang);
        if (InternetUtil.isInternetOn()) {
            weekReportCall.enqueue(new Callback<UserWeekReport>() {
                @Override
                public void onResponse(Call<UserWeekReport> call, Response<UserWeekReport> response) {
                    if (response.isSuccessful()) {
                        //DashboardViewModel.successResponse.postValue(response.body());
                        success.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<UserWeekReport> call, Throwable t) {
                    //DashboardViewModel.errorResponse.postValue(t.getMessage());
                    error.setValue(t.getMessage());

                }
            });
        } else {
            //DashboardViewModel.errorResponse.postValue(AppConst.NC);
            error.setValue(AppConst.NC);
        }


    }

    public void getProgramDetailsData(int programId, String lang, MutableLiveData<String> error, MutableLiveData<ProgramDetails> success) {
        //service = getFootballService();
        Call<ProgramDetails> programDetailsCall = footballService.getProgramData(programId, lang);
        if (InternetUtil.isInternetOn()) {
            programDetailsCall.enqueue(new Callback<ProgramDetails>() {
                @Override
                public void onResponse(Call<ProgramDetails> call, Response<ProgramDetails> response) {
                    if (response.isSuccessful()) {
                        //DashboardViewModel.programDetailsMutableLiveData.postValue(response.body());
                        success.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<ProgramDetails> call, Throwable t) {
                    error.postValue(t.getMessage());
                }
            });
        } else {
            error.postValue(AppConst.NC);
        }

    }

//    private FootballService getFootballService() {
//        if (service == null) {
//            service = ApiClientV2.getFootballService();
//        }
//        return service;
//    }


    public void startWorkout(int pid, String lang, MutableLiveData<String> error, MutableLiveData<WorkoutTrack> success) {
        //service = getFootballService();
        Call<WorkoutTrack> programDetailsCall = footballService.startWorkout(pid, lang);
        if (InternetUtil.isInternetOn()) {
            programDetailsCall.enqueue(new Callback<WorkoutTrack>() {
                @Override
                public void onResponse(Call<WorkoutTrack> call, Response<WorkoutTrack> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getGetWorkoutTrackList() != null) {
                            //DashboardViewModel.trackMutableLiveData.postValue(response.body());
                            success.postValue(response.body());
                        } else {
                            error.postValue("No value Found");
                        }

                    }
                }

                @Override
                public void onFailure(Call<WorkoutTrack> call, Throwable t) {
                    error.postValue(t.getMessage());
                }
            });
        } else {
            error.postValue(AppConst.NC);
        }
    }


    public void getPhilosphy(MutableLiveData<String> error, MutableLiveData<Philosophy> success) {
        //service = getFootballService();
        Call<Philosophy> philosophyCall = footballService.getPhilosophy();
        if (InternetUtil.isInternetOn()) {
            philosophyCall.enqueue(new Callback<Philosophy>() {
                @Override
                public void onResponse(Call<Philosophy> call, Response<Philosophy> response) {
                    if (response.isSuccessful()) {
                        //DashboardViewModel.philosophyMutableLiveData.postValue(response.body());
                        success.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Philosophy> call, Throwable t) {
                    error.postValue(t.getMessage());
                }
            });

        } else {
            error.postValue(AppConst.NC);
        }
    }


    public void getWorkouts(int userid, String lang, String type, MutableLiveData<String> error, MutableLiveData<LibraryProgram> success) {
        //service = getFootballService();
        Call<LibraryProgram> workoutCall = footballService.getWorkouts(userid, lang, type);
        if (InternetUtil.isInternetOn()) {
            workoutCall.enqueue(new Callback<LibraryProgram>() {
                @Override
                public void onResponse(Call<LibraryProgram> call, Response<LibraryProgram> response) {
                    if (response.isSuccessful()) {
                        //DashboardViewModel.programMutableLiveData.postValue(response.body());
                        success.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<LibraryProgram> call, Throwable t) {
                    error.postValue(t.getMessage());
                }
            });
        } else {
            error.postValue(AppConst.NC);
        }
    }

    public void beginWorkout(int pid, int uid, MutableLiveData<String> error, MutableLiveData<String> success) {
        //service = getFootballService();
        Call<User> beginWorkout = footballService.beginWorkouts(pid, uid);
        if (InternetUtil.isInternetOn()) {
            beginWorkout.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        success.postValue(response.body().getResponse());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    error.postValue(t.getMessage());
                }
            });
        } else {
            error.postValue(AppConst.NC);
        }
    }


    public void finishWorkout(int pid, int uid, MutableLiveData<String> error, MutableLiveData<String> success, MutableLiveData<Integer> refresh) {
        //service = getFootballService();
        Call<User> finishWorkout = footballService.finishWorkouts(pid, uid);
        if (InternetUtil.isInternetOn()) {
            finishWorkout.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        //DashboardViewModel.workoutFinish.postValue(response.body().getResponse());
                        success.postValue(response.body().getResponse());
                        //DashboardViewModel.reloadDashboard.postValue(1);
                        refresh.postValue(1);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    error.postValue(t.getMessage());
                }
            });
        } else {
            error.postValue(AppConst.NC);
        }
    }
}
