package com.footballio.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.Utils.Utils;
import com.footballio.model.BasicResponse;
import com.footballio.model.LoginResponse;
import com.footballio.model.RegisterRequest;
import com.footballio.model.RegisterResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FBLoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = FBLoginActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 120;

    private Context mycontext;
    private EditText login_email, login_password;
    private Button btn_login;
    private ImageView login_facebook, login_twiiter, login_google;
    private TextView login_forget_password, login_signup;
    private String mobileno = "", password = "";
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient googleApiClient;
    private static final String EMAIL = "email";
    private static final String PROFILE = "public_profile";
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private SignInButton signInButton;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUi();
    }

    private void initUi() {

        mycontext = this;

        login_email = findViewById(R.id.fb_login_email);
        login_password = findViewById(R.id.fb_login_password);
        login_facebook = findViewById(R.id.fb_facebook_integerate);
        login_twiiter = findViewById(R.id.fb_twitter_integerate);
        login_google = findViewById(R.id.fb_google_integerate);
        login_forget_password = findViewById(R.id.fb_forget_password);
        login_signup = findViewById(R.id.fb_login_signup);
        btn_login = findViewById(R.id.btn_fb_login);
        signInButton = findViewById(R.id.sign_in_button);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(mycontext, gso);

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        login_forget_password.setOnClickListener(this);
        login_signup.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        login_facebook.setOnClickListener(this);
        login_google.setOnClickListener(this);
        signInButton.setOnClickListener(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                // App code
            }
        };

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


        loginButton = (LoginButton) findViewById(R.id.loginbutton);
        loginButton.setReadPermissions(Arrays.asList(EMAIL, PROFILE));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess:registration loginResult" + new Gson().toJson(loginResult));
                boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
                Log.d(TAG, "onSuccess:registration loginResult " + loggedIn + " ??");
                getUserProfile(AccessToken.getCurrentAccessToken());
                // App code
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
    private void deleteAccessToken() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null){
                    //User logged out
                   // prefUtil.clearToken();
                    LoginManager.getInstance().logOut();
                }
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Log.d(TAG, "updateUI: FirebaseUser" + new Gson().toJson(currentUser));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fb_forget_password:
                Intent intent = new Intent(mycontext, FBForgetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.fb_login_signup:
                Intent i = new Intent(mycontext, FBRegisterActivity.class);
                startActivity(i);
                break;
            case R.id.btn_fb_login:
                validation();
                break;
            case R.id.fb_google_integerate:
                signOut();
                signIn();
                break;
            case R.id.fb_facebook_integerate:
                Profile profile = Profile.getCurrentProfile().getCurrentProfile();
                if (profile != null) {
                    LoginManager.getInstance().logOut();
                    // user has logged in
                    Log.d(TAG, "onClick:fb_logout facebook ");
                } else {
                    // user has not logged in
                    Log.d(TAG, "onClick:fb_logout facebook not");
                }
                loginButton.performClick();
                break;
            case R.id.sign_in_button:
                signIn();
                break;


        }

    }

    private void getUserProfile(AccessToken currentAccessToken) {
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

                            VerifyEmail(email, first_name, last_name, "1234", Utils.CurrentDate(),
                                    first_name+" "+last_name, "1", image_url);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
 /*   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }*/

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

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //     Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Log.d(TAG, "signInResult:failed code=" + e.getMessage());
            //   updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            Log.d(TAG, "updateUI: GoogleSignInAccount" + new Gson().toJson(account));
            Log.d(TAG, "updateUI: getDisplayName" + account.getDisplayName());
            Log.d(TAG, "updateUI: getGivenName" + account.getGivenName());
            //Log.d(TAG, "updateUI: GoogleSignInAccount"+new Gson().toJson(account));
String photourl ="";
            if (account.getPhotoUrl()!=null){
                photourl = account.getPhotoUrl().toString();
}
            VerifyEmail(account.getEmail(), account.getGivenName(), account.getFamilyName(), "1234", Utils.CurrentDate(),
                    account.getDisplayName(), "3", photourl);
        }
    }

    private void validation() {
        mobileno = login_email.getText().toString();
        password = login_password.getText().toString();
        if (mobileno.isEmpty()) {
            Toast.makeText(mycontext, "Please enter email ID", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(mycontext, "Please enter password", Toast.LENGTH_SHORT).show();
        } else {
            customer_login(mobileno, password);
        }

    }


    Call<LoginResponse> loginResponseCall;

    private void customer_login(String mobile, String password) {
        Loader.showLoad(mycontext, true);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        loginResponseCall = apiService.customer_login(mobile, password);


        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.body().isStatus() == true) {
                    Intent intent = new Intent(mycontext, FBDashboardActivity.class);
                    intent.putExtra(APPConst.PWORKSTATUS, "0");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    Prefs.putString(APPConst.TOKEN, response.body().getId());
                    Prefs.putString(APPConst.PROFILEUSERNAME, response.body().getUserName());
                    Prefs.putString(APPConst.PROFIEIMAGE, response.body().getPhoto());
                    Prefs.putInt(APPConst.PGREENPERCENTAGE, (int) response.body().getGreenPercentageValue());
                    Prefs.putInt(APPConst.PBLUEPERCENTAGE, (int) response.body().getBluePercentageValue());
                    Prefs.putBoolean(APPConst.ISLOGIN, true);
                    //   Toast.makeText(mycontext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: register sucess\"");

                } else {

                    Toast.makeText(mycontext, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "onResponse: register failed");
                }
                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    Call<RegisterResponse> registerResponseCall;

    private void register(String email, String firstname, String lastname, String password, String createon,
                          String username, String logintype, String photo) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setCreatedOn(createon);
        registerRequest.setEmailAddress(email);
        registerRequest.setFirstName(firstname);
        registerRequest.setLastName(lastname);
        registerRequest.setPassword(password);
        registerRequest.setUserName(username);
        registerRequest.setLoginType(logintype);
        registerRequest.setPhoto(photo);
        registerRequest.setStatus("Y");
        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        registerResponseCall = apiService.register(registerRequest);


        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if (response.body().isStatus() == true) {
                    Intent intent = new Intent(mycontext, FBEmailVerificationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    Prefs.putString(APPConst.TOKEN, response.body().getId());
                    Prefs.putString(APPConst.PROFILEEMAIL, response.body().getEmailAddress());

                    //   Toast.makeText(mycontext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: register sucess\"");

                } else {

                    Toast.makeText(mycontext, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "onResponse: register failed");
                }
                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    Call<BasicResponse> verifyOTPResponseCall;

    private void VerifyEmail(String email, String firstname, String lastname, String password, String createon,
                             String username, String logintype, String photo) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        verifyOTPResponseCall = apiService.verifyEmail(email);


        verifyOTPResponseCall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {

                if (response.body().isStatus() == true) {

                    if (response.body().getLogintype().equals("0")){
                        Toast.makeText(mycontext, "User already register this email", Toast.LENGTH_SHORT).show();
                    }else{
                        customer_login(email, password);
                    }

                    //  Toast.makeText(mycontext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: register sucess\"");

                } else {
                    register(email, firstname, lastname, password, createon,
                            username, logintype, photo);
                    //Toast.makeText(mycontext, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "onResponse: register failed");
                }
                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: "+new Gson().toJson(task));
                        // ...
                    }
                });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
        deleteAccessToken();
        signOut();
    }
}
