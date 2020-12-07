package com.footballio.viewmodel;

import android.content.Context;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.footballio.Utils.AppConst;
import com.footballio.Utils.Utils;
import com.footballio.model.login.User;
import com.footballio.network.FacebookApi;
import com.footballio.repository.AppSession;
import com.footballio.repository.LoginRepository;
import com.footballio.view.callback.IProgressBar;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

public class LoginViewModel extends ViewModel implements ILoginViewModel {
    public MutableLiveData<String> email = null;
    public MutableLiveData<String> password = null;
    public IProgressBar iProgressBar;

    private MutableLiveData<String> showError = null;
    private MutableLiveData<User> showSuccess = null;
    private MutableLiveData<User> fbUser = null;


    // registration
    public MutableLiveData<String> firstName_register = null;
    public MutableLiveData<String> lastName_register = null;
    public MutableLiveData<String> userName_register = null;
    public MutableLiveData<String> email_register = null;
    public MutableLiveData<String> phoneNo_register = null;
    public MutableLiveData<String> password_register = null;

    private final LoginRepository repository;
    private final SavedStateHandle savedStateHandle;
    private final Context application;

    @ViewModelInject
    public LoginViewModel(LoginRepository repository, @ApplicationContext Context application, @Assisted SavedStateHandle savedStateHandle) {
        showError = new MutableLiveData<>();
        showSuccess = new MutableLiveData<>();
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;
        this.application = application;
    }

    public void initRegisterLiveData() {
        firstName_register = new MutableLiveData<>();
        lastName_register = new MutableLiveData<>();
        userName_register = new MutableLiveData<>();
        email_register = new MutableLiveData<>();
        phoneNo_register = new MutableLiveData<>();
        password_register = new MutableLiveData<>();
    }

    public void initLoginOptionLiveData() {
        fbUser = new MutableLiveData<>();
    }

    public void initEloginLivedata() {
        email = new MutableLiveData<>();
        password = new MutableLiveData<>();
    }

    public void initVerifyEmailLiveData() {
        email = new MutableLiveData<>();
    }

    public void onRegisterButtonClick() {
        User user = new User(firstName_register.getValue(), lastName_register.getValue(), userName_register.getValue(), email_register.getValue(), password_register.getValue(), AppConst.LOGIN_TYPE_EMAIL, "", AppConst.LOGIN_STATUS, "d2", phoneNo_register.getValue(), Utils.CurrentDate());
        if (user.getFirstName().isEmpty()) {
            showError.setValue("Enter FirstName");
        } else if (user.getLastName().isEmpty()) {
            showError.setValue("Enter SurName");
        } else if (user.getUserName().isEmpty()) {
            showError.setValue("Enter UserName");
        } else if (!Utils.isValidEmail(user.getEmailAddress())) {
            showError.setValue("Enter valid emailAddress");
        } else if (user.getDigitCode().isEmpty()) {
            showError.setValue("Enter Mobile Number");
        } else if (user.getPassword().isEmpty()) {
            showError.setValue("Enter Password");
        } else {
            iProgressBar.showProgressBar();
            createRegistration(user);
        }

    }

    public void onLoginButtonClick() {
        //email.setValue("kalpanaaramanan@gmail.com");
        //password.setValue("k");
        if (email.getValue() == null) {
            showError.setValue("Enter email");
        } else if (password.getValue() == null) {
            showError.setValue("Enter Password");
        } else {
            getLogindetails(email.getValue(), password.getValue());
        }

    }


    public void onEmailVerifyButtonClick() {
        String emailValue = email.getValue();
        if (emailValue == null || emailValue.isEmpty()) {
            showError.setValue("Enter Email");
        } else if (!Utils.isValidEmail(emailValue)) {
            showError.setValue("Enter Valid Email");
        } else {
            iProgressBar.showProgressBar();
            ForgotPassword(emailValue);

        }
    }

    @Override
    public void createRegistration(User user) {
        repository.createRegistration(user, showError, showSuccess);
    }

    @Override
    public void getLogindetails(String email, String pwd) {
        repository.getLogindetails(email, pwd, showError, showSuccess);
    }

    @Override
    public void ForgotPassword(String email) {
        repository.ForgotPassword(email, showError, showSuccess);

    }

    @Override
    public void UpdatePassword(String code, String newpassword) {
        User user = new User("", "", "", "", newpassword, AppConst.LOGIN_TYPE_EMAIL, "", "", "", code, "");
        repository.UpdatePassword(user, showError, showSuccess);
    }

    @Override
    public void ConfirmRegisteredEmail(String code, String email) {
        repository.ConfirmRegisteredEmail(code, email, showError, showSuccess);
    }

    @Override
    public void ValidateFbCredential(AccessToken currentAccessToken) {
        getFacebookUserDetails(currentAccessToken);
    }

    @Override
    public void createGmailUser(GoogleSignInAccount account) {
        createLogin(account);
    }

    public void updateProfile(Bundle bundle) {
        User user = getProfileDetails(bundle);
        repository.UpdateProfile(user, showError, showSuccess);

    }

    // social login
    public void createLogin(GoogleSignInAccount account) {
        String url = account.getPhotoUrl() == null ? "" : account.getPhotoUrl().toString();
        User user = new User(account.getEmail(), account.getGivenName(), account.getFamilyName(), account.getEmail(), account.getId(), AppConst.LOGIN_TYPE_GOOGLE, url, "", "", "", Utils.CurrentDate());
        repository.createRegistration(user, showError, showSuccess);
    }


    public LiveData<User> getFacebookUserDetails(AccessToken currentAccessToken) {
        FacebookApi.getInstance().getUserProfile(currentAccessToken, showError, fbUser);
        return fbUser;
    }

    public LiveData<String> getErrorResponse() {
        return showError;

    }

    public LiveData<User> getSuccessResponse() {
        return showSuccess;
    }

    private User getProfileDetails(Bundle bundle) {
        String gender = bundle.getString(AppConst.G, "");
        String dob = bundle.getString(AppConst.DOB, "");
        String height = bundle.getString(AppConst.H, "");
        String weight = bundle.getString(AppConst.W, "");
        Bundle bundle_club = bundle.getBundle(AppConst.CLUB_BUNDLE);
        Bundle bundle_login = bundle.getBundle(AppConst.LOGIN_BUNDLE);
        String society = bundle_club.getString(AppConst.SOC, "");
        String position = bundle_club.getString(AppConst.POS, "");
        boolean doYouPlayforClub = bundle_club.getBoolean(AppConst.AUINC, false);
        String favouritePlayer = bundle_club.getString(AppConst.FP, "");
        String nationality = bundle_club.getString(AppConst.NATIONALITY, "");
        String username = bundle_login.getString(AppConst.UN, "");
        String firstname = bundle_login.getString(AppConst.FN, "");
        String lastname = bundle_login.getString(AppConst.LN, "");
        int userId = bundle_login.getInt(AppConst.UID, 0);

        User user = new User(76, username, firstname, lastname, height, weight, society, nationality, dob, position);
        return user;
    }

    //SP

    public boolean checkLastLoginAvailability() {
        return AppSession.isLastLogin(application);
    }

    public int getLastLoginUserId() {

        return AppSession.getLastLoginUserId(application);
    }

    public boolean addSession(String key, int value) {

        return AppSession.addSession(key, value);
    }

    public boolean addSession(String key, boolean value) {

        return AppSession.addSession(key, value);
    }


    public void createUserSession(User user) {
        addSession(AppSession.PREV_LOGIN, true);
        addSession(AppSession.PREV_USERID, user.getId());
        addSession(AppSession.PREV_LOGINTYPE, user.getLogintype());
    }

    public void createUserSession(Bundle login) {
        addSession(AppSession.PREV_LOGIN, true);
        addSession(AppSession.PREV_USERID, login.getInt("UserId", 0));
        addSession(AppSession.PREV_LOGINTYPE, login.getInt("LoginType", 0));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        //showError = null;
        //showSuccess = null;
    }


}
