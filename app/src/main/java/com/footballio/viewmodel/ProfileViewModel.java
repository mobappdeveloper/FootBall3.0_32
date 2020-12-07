package com.footballio.viewmodel;

import android.content.Context;
import android.net.Uri;

import com.footballio.model.dashboard.MyResponse;
import com.footballio.model.login.User;
import com.footballio.repository.AppSession;
import com.footballio.repository.ProfileRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<String> pwdLiveData = new MutableLiveData<>();
    private MutableLiveData<MyResponse> responseLiveData = new MutableLiveData<>();
    private final ProfileRepository repository;
    private final SavedStateHandle savedStateHandle;
    private final Context application;

    @ViewModelInject
    public ProfileViewModel(ProfileRepository repository, @ApplicationContext Context application, @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.application = application;
        this.savedStateHandle = savedStateHandle;
    }


    public LiveData<User> getUserInfo() {
        repository.getUserPersonalInfo(AppSession.getLastLoginUserId(application), error, userLiveData);
        return userLiveData;
    }

    public LiveData<String> getErrorResponse() {
        return error;
    }

    public LiveData<User> verifyCredential(String value, String pwd) {
        repository.verifyCredentail(AppSession.getStringValue(application, value), pwd, error, userLiveData);
        return userLiveData;
    }

    public LiveData<String> updateUserPassword(String pwd) {
        repository.updateUserPassword(AppSession.getLastLoginUserId(application), pwd, pwdLiveData);
        return pwdLiveData;

    }

    public LiveData<MyResponse> uploadProfilePicToServer(Uri outputFileUri, Uri resultUri) {
        File file = new File(resultUri.getPath());
        RequestBody userId = RequestBody.create(okhttp3.MultipartBody.FORM, AppSession.getLastLoginUserId(application) + "");
        RequestBody requestFile = RequestBody.create(MediaType.parse(application.getContentResolver().getType(outputFileUri)), file);
        MultipartBody.Part profilePic = MultipartBody.Part.createFormData("profilePic", file.getName(), requestFile);
        repository.updateProfilePic(userId, profilePic, error,responseLiveData);
        return responseLiveData;

    }

    public void createUserInfoSession(User user) {
        AppSession.addSession("Name", user.getFirstName());
        AppSession.addSession("Vorname", user.getLastName());
        AppSession.addSession("Username", user.getUserName());
        AppSession.addSession("E-Mail", user.getEmail());
        AppSession.addSession("Passwort ändern", "*******");
    }

    private byte[] readFileToBytes(File filePath) throws IOException {

        byte[] bytes = new byte[(int) filePath.length()];

        FileInputStream fis = null;
        try {

            fis = new FileInputStream(filePath);

            //read file into bytes[]
            fis.read(bytes);

        } finally {
            if (fis != null) {
                fis.close();
            }
        }

        return bytes;

    }


}
