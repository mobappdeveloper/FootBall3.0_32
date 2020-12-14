package com.footballio.repository;

import com.footballio.Utils.AppConst;
import com.footballio.model.dashboard.MyResponse;
import com.footballio.model.login.User;
import com.footballio.network.FootballService;
import com.footballio.network.InternetUtil;
import com.google.gson.JsonObject;

import androidx.lifecycle.MutableLiveData;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {
    private final FootballService footballService;

    public ProfileRepository(FootballService footballService) {
        this.footballService = footballService;
    }

    public void getUserPersonalInfo(int id, MutableLiveData<String> error, MutableLiveData<User> success) {
        Call<User> userInfoCall = footballService.getUserProfileInfo(id);
        if (InternetUtil.isInternetOn()) {
            userInfoCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        success.setValue(response.body());
                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    error.setValue(t.getMessage());

                }
            });
        } else {
            error.setValue(AppConst.NC);
        }


    }

    public void verifyCredentail(String value, String pwd, MutableLiveData<String> error, MutableLiveData<User> success) {
        Call<User> userInfoCall = footballService.getLogindetails(value, pwd);
        if (InternetUtil.isInternetOn()) {
            userInfoCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.body().getStatus().equals("true")) {
                        success.setValue(response.body());
                    }
                    else {
                        success.setValue(null);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    error.setValue(t.getMessage());

                }
            });
        } else {
            error.setValue(AppConst.NC);
        }
    }

    public void updateUserPassword(int lastLoginUserId, String pwd, MutableLiveData<String> pwdLiveData) {
        Call<User> userInfoCall = footballService.ChangePassword(lastLoginUserId, pwd);
        if (InternetUtil.isInternetOn()) {
            userInfoCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        String mm = response.body().getResponse();
                        pwdLiveData.setValue(mm);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    pwdLiveData.setValue(t.getMessage());

                }
            });
        } else {
            pwdLiveData.setValue(AppConst.NC);
        }
    }

    public void updateProfilePic(RequestBody lastLoginUserId, MultipartBody.Part file, MutableLiveData<String> error, MutableLiveData<MyResponse> success) {
        Call<JsonObject> updateProfilePic = footballService.updateProfilePic(lastLoginUserId, file);
        if (InternetUtil.isInternetOn()) {
            updateProfilePic.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        //success.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    error.setValue("Error updating profile picture please retry.." + t.getMessage());

                }
            });
        } else {
            error.setValue(AppConst.NC);
        }
    }


}
