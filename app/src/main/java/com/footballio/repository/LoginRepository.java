package com.footballio.repository;

import com.footballio.Utils.AppConst;
import com.footballio.model.login.User;
import com.footballio.network.FootballService;
import com.footballio.network.InternetUtil;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private final FootballService service;
    //private static LoginRepository loginRepository;


//    public static LoginRepository getInstance() {
//        if (loginRepository == null) {
//            loginRepository = new LoginRepository();
//        }
//        return loginRepository;
//    }

    public LoginRepository(FootballService service)
    {
       this.service=service;
    }

    public void createRegistration(User user, MutableLiveData<String> errorObserver, MutableLiveData<User> successObserver) {

        Call<User> createUser = service.createUser(user);
        if (InternetUtil.isInternetOn()) {
            createUser.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getId() > 0) {
                            successObserver.setValue(response.body());
                        } else if (response.body().getResponse().equals("User already exists!")) {
                            successObserver.setValue(user);
                        } else {
                            errorObserver.setValue(response.body().getResponse());
                        }

                    } else {
                        errorObserver.setValue(response.body().getResponse());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    errorObserver.setValue(t.getMessage());
                }
            });
        } else {
            errorObserver.setValue(AppConst.NC);
        }

    }


    public void getLogindetails(String email, String pwd, MutableLiveData<String> errorObserver, MutableLiveData<User> successObserver) {

        Call<User> login = service.getLogindetails(email, pwd);
        if (InternetUtil.isInternetOn()) {
            login.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getId() > 0) {
                            successObserver.postValue(response.body());
                        } else {
                            errorObserver.postValue(response.body().getResponse());
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    errorObserver.postValue(t.getMessage());
                }
            });

        } else {
            errorObserver.postValue(AppConst.NC);
        }

    }

    public void ForgotPassword(String email, MutableLiveData<String> errorObserver, MutableLiveData<User> successObserver) {

        Call<User> forgotPassword = service.ForgotPassword(email);
        if (InternetUtil.isInternetOn()) {
            forgotPassword.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getId() > 0) {
                            successObserver.postValue(response.body());
                        } else {
                            errorObserver.postValue(response.body().getResponse());
                        }

                    } else {
                        errorObserver.postValue("Unknown error");
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    errorObserver.postValue(t.getMessage());
                }
            });
        } else {
            errorObserver.postValue(AppConst.NC);
        }
    }

    public void UpdatePassword(User user, MutableLiveData<String> errorObserver, MutableLiveData<User> successObserver) {

        Call<User> updatepass = service.UpadatePassword(user);
        if (InternetUtil.isInternetOn()) {
            updatepass.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.body().getStatus().toString().equals("true")) {
                        successObserver.postValue(response.body());
                    } else {
                        errorObserver.postValue(response.body().getResponse());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    errorObserver.postValue(t.getMessage());
                }
            });

        } else {
            errorObserver.postValue(AppConst.NC);
        }
    }

    public void ConfirmRegisteredEmail(String code, String email, MutableLiveData<String> errorObserver, MutableLiveData<User> successObserver) {

        Call<User> otp = service.ConfirmRegisteredEmail(email, code);
        if (InternetUtil.isInternetOn()) {
            otp.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.body().getStatus().equals("true")) {
                        successObserver.setValue(response.body());
                    } else {
                        errorObserver.setValue(response.body().getResponse());
                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    errorObserver.setValue(t.getMessage());
                }
            });

        } else {
            errorObserver.postValue(AppConst.NC);
        }

    }

    public void UpdateProfile(User user, MutableLiveData<String> errorObserver, MutableLiveData<User> successObserver) {

        Call<User> upadateProfile = service.UpadateProfile(user);
        if (InternetUtil.isInternetOn()) {
            upadateProfile.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        successObserver.setValue(response.body());
                    } else {
                        errorObserver.setValue(response.body().getResponse());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    errorObserver.setValue(t.getMessage());
                }
            });
        } else {
            errorObserver.postValue(AppConst.NC);
        }
    }


//    private void getLoginService() {
//        //service = ApiClientV2.getFootballService();
//    }
}
