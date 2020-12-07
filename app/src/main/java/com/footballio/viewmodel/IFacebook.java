package com.footballio.viewmodel;

import com.facebook.AccessToken;

public interface IFacebook {
    void ValidateFbCredential(AccessToken currentAccessToken);
}
