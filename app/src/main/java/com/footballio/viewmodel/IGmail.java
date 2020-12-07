package com.footballio.viewmodel;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface IGmail {
    void createGmailUser(GoogleSignInAccount account);
}

