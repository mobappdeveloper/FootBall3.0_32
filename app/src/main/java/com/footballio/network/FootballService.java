package com.footballio.network;

import com.footballio.model.dashboard.MyResponse;
import com.footballio.model.dashboard.UserWeekReport;
import com.footballio.model.login.User;
import com.footballio.model.login.dashboard.LibraryProgram;
import com.footballio.model.login.dashboard.Philosophy;
import com.footballio.model.dashboard.ProgramDetails;
import com.footballio.model.login.dashboard.WorkoutTrack;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface FootballService {

    @POST("user/signup")
    Call<User> createUser(@Body User user);

    @GET("user/login")
    Call<User> getLogindetails(@Query("email") String email, @Query("password") String password);

    @GET("user/ForgotPassword")
    Call<User> ForgotPassword(@Query("email") String email);

    @POST("user/UpdatePassword")
    Call<User> UpadatePassword(@Body User user);

    @GET("user/verifyotp")
    Call<User> ConfirmRegisteredEmail(@Query("email") String email, @Query("code") String code);

    @POST("user/updateprofile")
    Call<User> UpadateProfile(@Body User user);

    //dashboard

    @GET("user/dashreport_v3")
    Call<UserWeekReport> getWeekReport(@Query("id") String id, @Query("lang") String lang);

    @GET("programs/viewprogram")
    Call<ProgramDetails> getMainProgramData(@Query("pid") int pid, @Query("userid") int userid, @Query("lang") String lang);

    @GET("trackers/trackersbyProgram")
    Call<ProgramDetails> getProgramData(@Query("id") int id, @Query("lang") String lang);

    @GET("workouts/startworkout")
    Call<WorkoutTrack> startWorkout(@Query("pid") int tid, @Query("lang") String lang);

    @GET("philosophy/philosophylist")
    Call<Philosophy> getPhilosophy();


    @GET("workouts/workoutsByType")
    Call<LibraryProgram> getWorkouts(@Query("userid") int userid, @Query("lang") String lang, @Query("type") String type);


    @GET("workouts/addWorkout")
    Call<User> beginWorkouts(@Query("pid") int pid, @Query("userid") int userid);


    @GET("workouts/completedWorkout")
    Call<User> finishWorkouts(@Query("pid") int pid, @Query("uid") int uid);

    @GET("user/userprofile")
    Call<User> getUserProfileInfo(@Query("id") int id);

    @GET("user/changepassword")
    Call<User> ChangePassword(@Query("id") int id, @Query("password") String password);

    @Multipart
    @POST("user/updatephoto")
    Call<JsonObject> updateProfilePic(@Part("id") RequestBody id, @Part MultipartBody.Part file);
}
