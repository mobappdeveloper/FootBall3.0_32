package com.footballio.viewmodel;

import androidx.lifecycle.LiveData;

public interface ILogin {

    LiveData<String> doLogin();
}
