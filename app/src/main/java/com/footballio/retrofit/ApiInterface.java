package com.footballio.retrofit;


import com.footballio.model.BasicResponse;
import com.footballio.model.DiscussionCommentRequest;
import com.footballio.model.DiscussionTopicCreateRequest;
import com.footballio.model.ForgotPasswordResponse;
import com.footballio.model.LoaderResponse;
import com.footballio.model.LoginResponse;
import com.footballio.model.PurchasedResponse;
import com.footballio.model.RegisterRequest;
import com.footballio.model.RegisterResponse;
import com.footballio.model.ResultObject;
import com.footballio.model.chanllenge.ChanllengeLikeResponse;
import com.footballio.model.chanllenge.ChanllengeResponse;
import com.footballio.model.chanllenge.ChanllengeUnlikeResponse;
import com.footballio.model.chanllenge.ChanllengeVideoListResponse;
import com.footballio.model.chanllenge.VideoUploadResponse;
import com.footballio.model.chanllenge.chanllengeview.ChanllengeViewResponse;
import com.footballio.model.chanllenge.ranking.RankingResponse;
import com.footballio.model.chanllenge.awardsuser.AwardsUserResponse;
import com.footballio.model.community.BlogCategoryViewResponse;
import com.footballio.model.community.CommunityMainResponse;
import com.footballio.model.community.DiscussionUnlikeRequest;
import com.footballio.model.community.DiscussionUnlikeResponse;
import com.footballio.model.community.blogview.BlogViewResponse;
import com.footballio.model.community.discussion.DiscussionMainResponse;
import com.footballio.model.community.discussionview.DiscussionFullViewResponse;
import com.footballio.model.dashboard.DashboardResponse;
import com.footballio.model.dashboard.allcategory.AllCategoryResponse;
import com.footballio.model.dashboard.category.CategoryResponse;
import com.footballio.model.dashboard.checkworkout.CheckWorkoutResponse;
import com.footballio.model.dashboard.loader.CalendarDataItem;
import com.footballio.model.dashboard.loader.ReportLoaderResponse;
import com.footballio.model.dashboard.loader.loadercalendar.CalendarLoaderResponse;
import com.footballio.model.dashboard.viewprogram.ViewProgramResponse;
import com.footballio.model.profile.ProfileViewResponse;
import com.footballio.model.workout.WorkoutViewResponse;
import com.footballio.model.workout.paymentcheck.WorkoutApplyCopuonResponse;
import com.footballio.model.workout.paymentcheck.WorkoutCheckPaymentResponse;
import com.footballio.model.workout.paymentcheck.WorkoutPaymentPriceResponse;
import com.footballio.model.workout.videoview.WorkoutVideoViewResponse;


import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;

import static com.footballio.retrofit.ApiClient.BASE_URL_VERSION;

/*

Created by Gowtham.P on 29-04-2019.
Copyright (c) 2018 Razrators. All rights reserved.

*/
public interface ApiInterface {

    @POST(BASE_URL_VERSION+"user/signup.php")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @FormUrlEncoded
    @POST(BASE_URL_VERSION+"user/feedback.php")
    Call<BasicResponse> feedback(@Field("emailAddress") String emailAddress,
                                 @Field("comment") String comment);
    @GET(BASE_URL_VERSION+"user/verifyotp.php")
    Call<BasicResponse> verify_otp(@Query("email") String email, @Query("code") String otp);

    @GET(BASE_URL_VERSION+"user/verifyEmail.php")
    Call<BasicResponse> verifyEmail(@Query("email") String email);
/*    @POST(BASE_URL_VERSION+"user/feedback.php")
    Call<BasicResponse> feedback(@Field("emailAddress") String emailAddress,*/


    @GET(BASE_URL_VERSION+"user/CheckActivation.php")
    Call<BasicResponse> activated_email(@Query("email") String email);

    @GET(BASE_URL_VERSION+"user/ForgotPassword.php")
    Call<ForgotPasswordResponse> forgot_password(@Query("email") String email);

    @GET(BASE_URL_VERSION+"user/UpdatePassword.php")
    Call<BasicResponse> update_password(@Query("password") String password,@Query("digitCode") String digitCode);

    @GET(BASE_URL_VERSION+"user/login.php")
    Call<LoginResponse> customer_login(@Query("email") String email,
                                    @Query("password") String password);

    @GET(BASE_URL_VERSION+"user/dashreport.php")
    Call<List<DashboardResponse>> dashboard(@Query("id") String id);

    @GET(BASE_URL_VERSION+"category/categories.php")
    Call<CategoryResponse> dashboard_catgory();

    @GET(BASE_URL_VERSION+"programs/programslist.php")
    Call<AllCategoryResponse> dashboard_allCatgoryDetails(@Query("cid") String cid,@Query("userid") String userid);

    @GET(BASE_URL_VERSION+"programs/viewprogram.php")
    Call<ViewProgramResponse> viewprogram(@Query("pid") String pid, @Query("userid") String userid);

    @GET(BASE_URL_VERSION+"workouts/addWorkout.php")
    Call<BasicResponse> addworkout(@Query("pid") String pid, @Query("userid") String userid);

    @GET(BASE_URL_VERSION+"workouts/deleteWorkout.php")
    Call<BasicResponse> deleteworkout(@Query("workid") String workid);

    @GET(BASE_URL_VERSION+"workouts/myWorkouts.php")
    Call<CheckWorkoutResponse> checkworkout(@Query("userid") String userid);

    @GET(BASE_URL_VERSION+"workouts/workuptypes.php")
    Call<WorkoutViewResponse> workoutView(@Query("pid") String pid);

    @GET(BASE_URL_VERSION+"workouts/startworkout.php")
    Call<WorkoutVideoViewResponse> workoutVideoView(@Query("pid") String pid);

    @GET(BASE_URL_VERSION+"workouts/completedWorkout.php")
    Call<BasicResponse> workcomplete(@Query("pid") String pid, @Query("uid") String uid);

    @GET
    Call<ResponseBody> workouttypeView(@Url String Url, @Query("pid") String pid, @Query("type") String type);

    @GET(BASE_URL_VERSION+"workouts/subscribtion.php")
    Call<WorkoutCheckPaymentResponse> workoutCheckPament(@Query("uid") String uid);

    @GET(BASE_URL_VERSION+"coupons/appprice.php")
    Call<WorkoutPaymentPriceResponse> workoutPamentPrice();

    @GET(BASE_URL_VERSION+"coupons/coupons.php")
    Call<WorkoutApplyCopuonResponse> workoutApplyCoupon(@Query("coupon") String coupon, @Query("userid") String userid);

    @GET(BASE_URL_VERSION+"user/userprofile.php")
    Call<ProfileViewResponse> getProfileView(@Query("id") String id);

    @GET(BASE_URL_VERSION+"user/userprofile.php")
    Call<ProfileViewResponse> getProfileUpdate(@Query("id") String id,@Query("firstName") String firstName,@Query("lastName") String lastName
            ,@Query("height") String height,@Query("weight") String weight,@Query("dateofBirth") String dateofBirth,@Query("club") String club
            ,@Query("nationality") String nationality,@Query("position") String position,@Query("userName") String userName);

    @GET(BASE_URL_VERSION+"blogs/BlogsDashboard.php")
    Call<CommunityMainResponse> getCommunity();

    @GET(BASE_URL_VERSION+"topic/topics.php")
    Call<DiscussionMainResponse> getDiscussionList(@Query("id") String id);

    @GET(BASE_URL_VERSION+"blogs/BlogViewById.php")
    Call<List<BlogViewResponse>> getBlogFullView(@Query("id") String id);

    @GET(BASE_URL_VERSION+"blogs/BlogViewBycategory.php")
    Call<BlogCategoryViewResponse> getBlogCategoryFullView(@Query("id") String id);

    @GET(BASE_URL_VERSION+"topic/topicbyid.php")
    Call<List<DiscussionFullViewResponse>> getDiscussionFullView(@Query("id") String id,@Query("userid") String userid);

    @POST(BASE_URL_VERSION+"topic/createtopic.php")
    Call<BasicResponse> creatediscussiontopic(@Body DiscussionTopicCreateRequest discussionTopicCreateRequest);

    @POST(BASE_URL_VERSION+"topic/postcomment.php")
    Call<List<DiscussionFullViewResponse>> discussionComment(@Body DiscussionCommentRequest discussionCommentRequest);

    @POST(BASE_URL_VERSION+"topic/unlike.php")
    Call<DiscussionUnlikeResponse> discussionunlike(@Body DiscussionUnlikeRequest discussionUnlikeRequest);

    @POST(BASE_URL_VERSION+"topic/like.php")
    Call<DiscussionUnlikeResponse> discussionlike(@Body DiscussionUnlikeRequest discussionlikeRequest);

    @GET(BASE_URL_VERSION+"topic/deleteusertopic.php")
    Call<BasicResponse> deletedisccsion(@Query("userid") String userid,@Query("discussionid") String discussionid);

    @GET(BASE_URL_VERSION+"challenges/challenges.php")
    Call<ChanllengeResponse> getChanllenge();

    @GET(BASE_URL_VERSION+"challenges/ranking.php")
    Call<RankingResponse> getChanllengeRanking();

    @GET(BASE_URL_VERSION+"awards/awardslist.php")
    Call<AwardsUserResponse> getChanllengeAwardsUser(@Query("userid") String userid);

    @GET(BASE_URL_VERSION+"challenges/learnboard.php")
    Call<List<ChanllengeViewResponse>> getChanllengeView(@Query("id") String id, @Query("userid") String userid);


    @GET(BASE_URL_VERSION+"challenges/viewchallange.php")
    Call<ChanllengeVideoListResponse> getChanllengeVideoView(@Query("postid") String postid, @Query("userid") String userid
            , @Query("challangeid") String challangeid);

    @GET(BASE_URL_VERSION+"challenges/unlikechallenge")
    Call<ChanllengeUnlikeResponse> getChanllengeunlike(@Query("postid") String postid, @Query("userid") String userid
            , @Query("challengeId") String challangeid);

    @GET(BASE_URL_VERSION+"challenges/likechallenge")
    Call<ChanllengeLikeResponse> getChanllengelike(@Query("postid") String postid, @Query("userid") String userid
            , @Query("challengeId") String challangeid);

    @GET(BASE_URL_VERSION+"challenges/deleteuserchallange.php")
    Call<BasicResponse> getChanllengedelete(@Query("postid") String postid, @Query("userid") String userid
            , @Query("challengeId") String challangeid);

    @Multipart
    @POST
    Call<VideoUploadResponse> uploadVideo(@Url String url, @Part MultipartBody.Part map,
                                          @Part("userid") RequestBody userid, @Part("challengeid") RequestBody challengeId);


    @GET(BASE_URL_VERSION+"user/loader.php")
    Call<LoaderResponse> getLoader(@Query("id") String id);

    @GET(BASE_URL_VERSION+"user/userReport.php")
    Call<ReportLoaderResponse> getReportLoader(@Query("id") String id);

    @GET(BASE_URL_VERSION+"workouts/buysubscription.php")
    Call<PurchasedResponse> getPurchaseData(@Query("uid") String id, @Query("identifier") String identifier, @Query("type") String type, @Query("coupon") String coupon);

    @GET(BASE_URL_VERSION+"user/userCalendar.php")
    Call<CalendarLoaderResponse> getCalendarData(@Query("id") String id, @Query("m") String m, @Query("y") String y);

    /*

    @GET("rescheduleTask/{task_id}")
    Call<BasicResponse> getExtaTime(@Path("task_id") String task_id,
                                    @Query("postpone_date") String postpone_date,
                                    @Query("staff_id") String staff_id);


    @Multipart
    @POST
    Call<ProfileImageResponse> uploadTaskImage(@Url String url, @PartMap Map<String, RequestBody> map);
*/

}
