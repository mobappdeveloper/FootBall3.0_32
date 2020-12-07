package com.footballio.network;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.footballio.Utils.AppConst;
import com.footballio.Utils.Utils;
import com.footballio.model.login.User;
import com.footballio.viewmodel.LoginViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.lifecycle.MutableLiveData;

public class FacebookApi {
    private static final String TAG = FacebookApi.class.getSimpleName();
    private static FacebookApi facebookApi = null;
    private User user = null;


    public static FacebookApi getInstance() {
        if (facebookApi == null) {
            facebookApi = new FacebookApi();
        }
        return facebookApi;
    }

    public User getUserProfile(AccessToken currentAccessToken, MutableLiveData<String> showError, MutableLiveData<User> userMutableLiveData) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d(TAG, "getUserProfile" + object.toString());
                        try {
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String email = object.getString("email");
                            String id = object.getString("id");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                            user = new User(email, first_name, last_name, email, id, AppConst.LOGIN_TYPE_FB, image_url, "true", "d2", "", Utils.CurrentDate());
                            //LoginViewModel.fbUser.postValue(user);
                            userMutableLiveData.postValue(user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showError.postValue(e.getMessage());
                        }

                    }
                });
        request.setParameters(getQueryParameters());
        request.executeAsync();

        return user;
    }

    private static Bundle getQueryParameters() {
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        return parameters;
    }
}
