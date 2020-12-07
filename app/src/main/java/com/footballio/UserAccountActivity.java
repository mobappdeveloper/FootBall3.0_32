package com.footballio;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.footballio.Utils.Utils;
import com.footballio.model.login.User;
import com.footballio.view.adapter.UserAccountAdapter;
import com.footballio.view.ui.PasswordActivity;
import com.footballio.viewmodel.ProfileViewModel;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UserAccountActivity extends AppCompatActivity {
    private ListView listView_userAccount;
    private UserAccountAdapter accountAdapter;
    private ProfileViewModel profileModel;
    private String[] values = new String[]{"Name", "Vorname", "Username", "E-Mail", "Passwort ändern"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        init();
    }

    private void init() {
        listView_userAccount = findViewById(R.id.list_userAccount);
        profileModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        setUpListView();
    }


    private void setUpListView() {
        accountAdapter = new UserAccountAdapter(this, android.R.layout.simple_list_item_1, values);
        listView_userAccount.setAdapter(accountAdapter);
        listView_userAccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 4) {
                    alertPasswordDialog();
                }

            }
        });
    }

    private void alertPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Passwort eingeben");
        builder.setMessage("Bitte gib dein aktuelles Passwort ein.");
        builder.setCancelable(false);
        final EditText input = new EditText(UserAccountActivity.this);
        input.setHint("Password");
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setPositiveButton("Weiter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                verifyCredential(input.getText().toString());

            }
        });
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void verifyCredential(String pwd) {
        profileModel.verifyCredential(values[3], pwd).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                startActivity(new Intent(UserAccountActivity.this, PasswordActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        profileModel.getErrorResponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.showToast(s, UserAccountActivity.this);
            }
        });
    }
}