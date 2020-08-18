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

import androidx.appcompat.app.AppCompatActivity;

import com.footballio.Adapter.BlogCategoryAdapter;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.model.BasicResponse;
import com.footballio.model.DiscussionTopicCreateRequest;
import com.footballio.model.community.BlogCategoryViewResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FBDiscussionUploadActivity extends AppCompatActivity {
    private static final String TAG = FBDiscussionUploadActivity.class.getSimpleName();
    @BindView(R.id.fb_discussion_upload_back)
    ImageView fbDiscussionUploadBack;
    @BindView(R.id.fb_workout_view_title)
    TextView fbWorkoutViewTitle;
    @BindView(R.id.btn_fb_discussion_hold)
    Button btnFbDiscussionHold;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.text_email_head)
    TextInputLayout textEmailHead;
    @BindView(R.id.edt_tags1)
    EditText edtTags1;
    @BindView(R.id.edt_tags2)
    EditText edtTags2;
    @BindView(R.id.edt_tags3)
    EditText edtTags3;
    @BindView(R.id.edt_desc)
    EditText edtDesc;
    @BindView(R.id.text_desc_head)
    TextInputLayout textDescHead;

    private Context mycontext;
    private BasicResponse basicResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_upload);
        ButterKnife.bind(this);

        initUi();
    }

    private void initUi() {
        mycontext = this;
    }

    @OnClick({R.id.fb_discussion_upload_back, R.id.btn_fb_discussion_hold})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_discussion_upload_back:
                finish();
                break;
            case R.id.btn_fb_discussion_hold:
               vaildation();
                break;
        }
    }

    private void vaildation() {
        String title = fbWorkoutViewTitle.getText().toString();
        String desc = edtDesc.getText().toString();
        String tags1 = edtTags1.getText().toString();
        String tags2 = edtTags2.getText().toString();
        String tags3 = edtTags3.getText().toString();
        if (title.isEmpty()){
            Toast.makeText(mycontext, "Please enter email id", Toast.LENGTH_SHORT).show();
        }else  if (desc.isEmpty()){
            Toast.makeText(mycontext, "Please enter description", Toast.LENGTH_SHORT).show();
        }else if (tags1.isEmpty()){
            Toast.makeText(mycontext, "Please enter tags1", Toast.LENGTH_SHORT).show();
        }else if (tags2.isEmpty()){
            Toast.makeText(mycontext, "Please enter tags2", Toast.LENGTH_SHORT).show();
        }else if (tags3.isEmpty()){
            Toast.makeText(mycontext, "Please enter tags3", Toast.LENGTH_SHORT).show();
        }else{
            creatediscussion(desc,title,tags1,tags2,tags3);
        }
    }

    Call<BasicResponse> basicResponseCall;

    private void creatediscussion(String comment,String title,String tags1,String tags2,String tags3) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
DiscussionTopicCreateRequest discussionTopicCreateRequest = new DiscussionTopicCreateRequest();
        discussionTopicCreateRequest.setUserid(Prefs.getString(APPConst.TOKEN,""));
        discussionTopicCreateRequest.setPost(comment);
        discussionTopicCreateRequest.setTitle(title);
        discussionTopicCreateRequest.setTag1(tags1);
        discussionTopicCreateRequest.setTag2(tags2);
        discussionTopicCreateRequest.setTag3(tags3);
        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        basicResponseCall = apiService.creatediscussiontopic(discussionTopicCreateRequest);


        basicResponseCall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                Log.d(TAG, "onResponse:BlogCategoryViewResponse " + new Gson().toJson(response.body()));
                basicResponse = response.body();
               if (response.body().isStatus()){
                   Toast.makeText(mycontext, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                   Intent i = new Intent(mycontext, FBDiscussionUploadedActivity.class);
                   startActivity(i);
                   finish();
               }else{
                   Toast.makeText(mycontext, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
               }
                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: BlogCategoryViewResponse" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
