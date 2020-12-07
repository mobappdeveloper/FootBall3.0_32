package com.footballio.retrofit;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class GmailApi {
    private static GoogleSignInClient mGoogleSignInClient;
    private static GoogleSignInOptions gso = null;

    public static GoogleSignInClient getGmailSignInClient(Context context) {
        setupGmail();
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        return mGoogleSignInClient;
    }

    private static void setupGmail() {
        if (gso == null) {
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
        }

    }


}
