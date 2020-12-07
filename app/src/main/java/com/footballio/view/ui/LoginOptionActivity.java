package com.footballio.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.footballio.R;
import com.footballio.Utils.AppConst;
import com.footballio.Utils.Utils;
import com.footballio.databinding.ActivityLoginOptionBinding;
import com.footballio.model.login.User;
import com.footballio.viewmodel.LoginViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginOptionActivity extends AppCompatActivity implements View.OnClickListener {
    // FB
    private CallbackManager callbackManager;
    private LoginButton facebookLoginButton;
    private static final String EMAIL = "email";
    private static final String PROFILE = "public_profile";
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    //GOOGLE
    private static final int RC_SIGN_IN = 120;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton gmailSignInButton;

    //Activity
    //private FirebaseAuth mAuth;
    private static final String TAG = LoginOptionActivity.class.getSimpleName();
    private ActivityLoginOptionBinding loginOptionBinding;
    private LoginViewModel login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpBinding();
        setupFaceBook();
        setupGmail();
        setupButtonClick();
        login.getErrorResponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.showToast(s, LoginOptionActivity.this);
            }
        });

        login.getSuccessResponse().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    startActivity(new Intent(LoginOptionActivity.this, VerifyEmailCodeActivity.class)
                            //.putExtra("FirstTimeRegistration", "0")
                            //.putExtra("Logintype", user.getLogintype())
                            .putExtra(AppConst.LOGIN_BUNDLE, getUserDetails(user)));
                    //.putExtra("email", user.getEmailAddress()));
                } else {
                    Utils.showToast("Error", LoginOptionActivity.this);
                }
            }
        });

    }

    private Bundle getUserDetails(User user) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConst.FN, user.getFirstName());
        bundle.putString(AppConst.LN, user.getLastName());
        bundle.putString(AppConst.UN, user.getUserName());
        bundle.putInt(AppConst.UID, user.getId());
        bundle.putInt(AppConst.LOGIN_TYPE, user.getLogintype());
        bundle.putString(AppConst.EM, user.getEmailAddress());
        return bundle;
    }

    private void setUpBinding() {
        loginOptionBinding = DataBindingUtil.setContentView(this, R.layout.activity_login_option);
        login = new ViewModelProvider(this).get(LoginViewModel.class);
        loginOptionBinding.setLogin(login);
        loginOptionBinding.setLifecycleOwner(this);
        login.initLoginOptionLiveData();
    }

    private void setupButtonClick() {
        loginOptionBinding.linearLayoutEmail.setOnClickListener(this);
        loginOptionBinding.linearLayoutFb.setOnClickListener(this);
        loginOptionBinding.linearLayoutGmail.setOnClickListener(this);
        loginOptionBinding.signInButton.setOnClickListener(this);

    }

    private void setupGmail() {
        // mAuth = FirebaseAuth.getInstance();
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        initGmailUI();
    }

    private void initGmailUI() {
        gmailSignInButton = findViewById(R.id.sign_in_button);

    }

    private void setupFaceBook() {
        intFacebookUI();
        setupFacebookSDK();
        acessTokenTracker();
        getProfileTracker();
        //callBackLoginManager();
    }

    private void intFacebookUI() {
        facebookLoginButton = (LoginButton) findViewById(R.id.loginbutton);
        facebookLoginButton.setReadPermissions(Arrays.asList(EMAIL, PROFILE));
    }

    private void setupFacebookSDK() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    private void acessTokenTracker() {
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };
    }

    private void getProfileTracker() {
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                // App code
            }
        };
    }

    private void callBackLoginManager() {
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.d(TAG, "onSuccess:LoginManager loginResult" + new Gson().toJson(loginResult));
                        boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
                        Log.d(TAG, "onSuccess:LoginManager loginResult " + loggedIn + " ??");
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.d(TAG, "onSuccess:LoginManager loginResult onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d(TAG, "onSuccess:LoginManager loginResult onError");
                        // App code
                    }
                });
    }


    private void callBackFacebook() {
        // Callback registration
        loginOptionBinding.loginbutton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getFacebookLoginUserBasicInfo();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onSuccess:registration loginResult onCancel");
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d(TAG, "onSuccess:registration loginResult onError");
                deleteAccessToken();
                // App code
            }
        });
    }

    private void getFacebookLoginUserBasicInfo() {
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token != null) {
            login.getFacebookUserDetails(token).observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    login.createRegistration(user);
                }
            });
        } else {
            Utils.showToast("Error Validating Facebook Login", LoginOptionActivity.this);
        }

    }


    private void deleteAccessToken() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    //User logged out
                    // prefUtil.clearToken();
                    LoginManager.getInstance().logOut();
                }
            }
        };
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                updateUI(account);
            } else {
                Utils.showToast("Problem Signing In", this);
            }

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //     Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Log.d(TAG, "signInResult:failed code=" + e.getMessage());
            //   updateUI(null);
            Utils.showToast("Problem Signing In" + e.getMessage(), this);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            login.createLogin(account);
        }
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: " + new Gson().toJson(task));
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        if (account != null) {
//            startActivity(new Intent(LoginOptionActivity.this, DashboardActivity.class));
//            finish();
//        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
        deleteAccessToken();
        signOut();
        //login.getErrorMessage().removeObservers(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode" + requestCode);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearLayout_email:
                startActivity(new Intent(LoginOptionActivity.this, ELoginActivity.class));
                break;
            case R.id.linearLayout_fb:
                Profile profile = Profile.getCurrentProfile().getCurrentProfile();
                if (profile != null) {
                    LoginManager.getInstance().logOut();
                }
                callBackFacebook();
                facebookLoginButton.performClick();
                break;
            case R.id.linearLayout_gmail:
                signOut();
                signIn();
                break;
            case R.id.sign_in_button:
                signIn();
                break;
            default:
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
