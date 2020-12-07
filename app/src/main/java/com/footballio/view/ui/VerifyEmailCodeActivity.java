package com.footballio.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.footballio.R;
import com.footballio.Utils.Utils;
import com.footballio.databinding.ActivityVerifyEmailcodeBinding;
import com.footballio.model.login.User;
import com.footballio.viewmodel.LoginViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class VerifyEmailCodeActivity extends AppCompatActivity implements IProgressBar {
    private ActivityVerifyEmailcodeBinding verifyEmailcodeBinding;
    private LoginViewModel emailCodeViewModel;
    private String code;
    private Bundle bundle;

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
                    if (bundle == null) {
                        verifyEmailcodeBinding.linearLayoutVerifyemailcode.setVisibility(View.INVISIBLE);
                        verifyEmailcodeBinding.linearLayoutVerifyemailcodePasswordGroup.setVisibility(View.VISIBLE);
                        verifyEmailcodeBinding.txtVerifycodeAttributeheader.setText("Please Enter New password");
                        verifyEmailcodeBinding.txtVerifycodeDnrcode.setVisibility(View.INVISIBLE);
                        verifyEmailcodeBinding.txtVerifycodeResend.setVisibility(View.INVISIBLE);
                    } else {
                        showProgressBar();
                        emailCodeViewModel.ConfirmRegisteredEmail(code, bundle.getString("email"));
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
    }

    private void setUpActivity() {
        verifyEmailcodeBinding = DataBindingUtil.setContentView(this, R.layout.activity_verify_emailcode);
        emailCodeViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        verifyEmailcodeBinding.setVerifyemailcode(emailCodeViewModel);
        verifyEmailcodeBinding.setLifecycleOwner(this);
        emailCodeViewModel.iProgressBar = this;
        bundle = getIntent().getExtras();
        getresponse();
    }

    private void getresponse() {
        emailCodeViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.showToast(s, VerifyEmailCodeActivity.this);
                hideProgressBar();
            }
        });

        emailCodeViewModel.getSuccessMessage().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User s) {
                Utils.showToast(s.getResponse(), VerifyEmailCodeActivity.this);
                hideProgressBar();
                if (bundle == null) {
                    Intent intent = new Intent(VerifyEmailCodeActivity.this, ELoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    startActivity(new Intent(VerifyEmailCodeActivity.this, WelcomeActivity.class));
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
