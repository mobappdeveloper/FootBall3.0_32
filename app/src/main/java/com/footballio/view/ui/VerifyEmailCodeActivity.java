package com.footballio.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.footballio.R;
import com.footballio.Utils.AppConst;
import com.footballio.Utils.Utils;
import com.footballio.databinding.ActivityVerifyEmailcodeBinding;
import com.footballio.model.login.User;
import com.footballio.view.callback.IProgressBar;
import com.footballio.viewmodel.LoginViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class VerifyEmailCodeActivity extends AppCompatActivity implements IProgressBar {
    private ActivityVerifyEmailcodeBinding verifyEmailcodeBinding;
    private LoginViewModel emailCodeViewModel;
    private String code;
    private Bundle bundle_loginOption;
    private Bundle bundle_login;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpActivity();
        verifyEmailcodeBinding.buttVerifyemailValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyEmailcodeBinding.etxVerifyemailcodeC1.getText().toString().isEmpty() ||
                        verifyEmailcodeBinding.etxVerifyemailcodeC2.getText().toString().isEmpty() ||
                        verifyEmailcodeBinding.etxVerifyemailcodeC3.getText().toString().isEmpty() ||
                        verifyEmailcodeBinding.etxVerifyemailcodeC4.getText().toString().isEmpty() ||
                        verifyEmailcodeBinding.etxVerifyemailcodeC5.getText().toString().isEmpty() ||
                        verifyEmailcodeBinding.etxVerifyemailcodeC6.getText().toString().isEmpty()) {
                    Utils.showToast("Enter code", VerifyEmailCodeActivity.this);

                } else if (!verifyEmailcodeBinding.linearLayoutVerifyemailcodePasswordGroup.isShown()) {
                    code = appendCode();
                    if (bundle_loginOption == null) {
                        verifyEmailcodeBinding.linearLayoutVerifyemailcode.setVisibility(View.INVISIBLE);
                        verifyEmailcodeBinding.linearLayoutVerifyemailcodePasswordGroup.setVisibility(View.VISIBLE);
                        verifyEmailcodeBinding.txtVerifycodeAttributeheader.setText("Please Enter New password");
                        verifyEmailcodeBinding.txtVerifycodeDnrcode.setVisibility(View.INVISIBLE);
                        verifyEmailcodeBinding.txtVerifycodeResend.setVisibility(View.INVISIBLE);
                    } else {
                        showProgressBar();
                        VerifyEmailOTP(code);
                    }

                } else if (verifyEmailcodeBinding.etxtIncludePwd.isShown()) {
                    if (verifyEmailcodeBinding.etxtIncludePwd.getText().toString().isEmpty()) {
                        Utils.showToast("Enter New Password", VerifyEmailCodeActivity.this);
                    } else {
                        showProgressBar();
                        emailCodeViewModel.UpdatePassword(code, verifyEmailcodeBinding.etxtIncludePwd.getText().toString());
                    }

                }
            }
        });

        verifyEmailcodeBinding.etxVerifyemailcodeC1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                verifyEmailcodeBinding.etxVerifyemailcodeC2.requestFocus();
                verifyEmailcodeBinding.etxVerifyemailcodeC2.setCursorVisible(true);
            }
        });


        verifyEmailcodeBinding.etxVerifyemailcodeC2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                verifyEmailcodeBinding.etxVerifyemailcodeC3.requestFocus();
                verifyEmailcodeBinding.etxVerifyemailcodeC3.setCursorVisible(true);
            }
        });


        verifyEmailcodeBinding.etxVerifyemailcodeC3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                verifyEmailcodeBinding.etxVerifyemailcodeC4.requestFocus();
                verifyEmailcodeBinding.etxVerifyemailcodeC4.setCursorVisible(true);
            }
        });

        verifyEmailcodeBinding.etxVerifyemailcodeC4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                verifyEmailcodeBinding.etxVerifyemailcodeC5.requestFocus();
                verifyEmailcodeBinding.etxVerifyemailcodeC5.setCursorVisible(true);
            }
        });

        verifyEmailcodeBinding.etxVerifyemailcodeC5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                verifyEmailcodeBinding.etxVerifyemailcodeC6.requestFocus();
                verifyEmailcodeBinding.etxVerifyemailcodeC6.setCursorVisible(true);
            }
        });
    }

    private void setUpActivity() {
        verifyEmailcodeBinding = DataBindingUtil.setContentView(this, R.layout.activity_verify_emailcode);
        emailCodeViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        verifyEmailcodeBinding.setVerifyemailcode(emailCodeViewModel);
        verifyEmailcodeBinding.setLifecycleOwner(this);
        emailCodeViewModel.iProgressBar = this;
        bundle_loginOption = getIntent().getExtras();
        bundle_login= bundle_loginOption.getBundle(AppConst.LOGIN_BUNDLE);
    }

    private void VerifyEmailOTP(String code) {
        emailCodeViewModel.ConfirmRegisteredEmail(code, bundle_login.getString(AppConst.EM));
        emailCodeViewModel.getErrorResponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.showToast(s, VerifyEmailCodeActivity.this);
                hideProgressBar();
            }
        });

        emailCodeViewModel.getSuccessResponse().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User s) {
                Utils.showToast(s.getResponse(), VerifyEmailCodeActivity.this);
                hideProgressBar();
                if (bundle_loginOption == null) {
                    Intent intent = new Intent(VerifyEmailCodeActivity.this, ELoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else{
                    bundle_login.putInt(AppConst.UID,s.getId());
                    startActivity(new Intent(VerifyEmailCodeActivity.this, WelcomeActivity.class).putExtra(AppConst.LOGIN_BUNDLE, bundle_login));
                    finish();
                }

            }
        });
    }

    @Override
    public void showProgressBar() {
        verifyEmailcodeBinding.progressBaVerifyemailcode.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        verifyEmailcodeBinding.progressBaVerifyemailcode.setVisibility(View.INVISIBLE);
    }

    private String appendCode() {
        return verifyEmailcodeBinding.etxVerifyemailcodeC1.getText().toString() + verifyEmailcodeBinding.etxVerifyemailcodeC2.getText().toString() +
                verifyEmailcodeBinding.etxVerifyemailcodeC3.getText().toString() + verifyEmailcodeBinding.etxVerifyemailcodeC4.getText().toString() +
                verifyEmailcodeBinding.etxVerifyemailcodeC5.getText().toString() + verifyEmailcodeBinding.etxVerifyemailcodeC6.getText().toString();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
