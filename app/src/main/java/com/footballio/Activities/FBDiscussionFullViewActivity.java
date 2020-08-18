package com.footballio.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.footballio.Adapter.DiscussionFullAdapter;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.model.BasicResponse;
import com.footballio.model.DiscussionCommentRequest;
import com.footballio.model.community.DiscussionUnlikeRequest;
import com.footballio.model.community.DiscussionUnlikeResponse;
import com.footballio.model.community.blogview.BlogViewResponse;
import com.footballio.model.community.discussionview.DiscussionFullViewResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FBDiscussionFullViewActivity extends AppCompatActivity {

    private static final String TAG = FBDiscussionFullViewActivity.class.getSimpleName();

    @BindView(R.id.fb_workout_view_title)
    TextView fbWorkoutViewTitle;
    @BindView(R.id.fb_discussion_title)
    TextView fbDiscussionTitle;
    @BindView(R.id.fb_discussion_recycler)
    RecyclerView fbDiscussionRecycler;
    @BindView(R.id.vendor_ads_image)
    ImageView vendorAdsImage;
    @BindView(R.id.vendor_ads_product_name)
    TextView vendorAdsProductName;
    @BindView(R.id.category_view_checkbox)
    CheckBox categoryViewCheckbox;
    @BindView(R.id.fb_discussion_linear)
    LinearLayout fbDiscussionLinear;
    @BindView(R.id.fb_discussion_back)
    LinearLayout fbDiscussionBack;
    @BindView(R.id.txt_date_discussion)
    TextView txtDateDiscussion;
    @BindView(R.id.txt_fav_count)
    TextView txtFavCount;
    @BindView(R.id.ic_comment_count)
    TextView icCommentCount;
    @BindView(R.id.text_disc_desc)
    TextView textDiscDesc;
    @BindView(R.id.nodatafound)
    TextView nodatafound;
    @BindView(R.id.tags1)
    TextView tags1;
    @BindView(R.id.tags2)
    TextView tags2;
    @BindView(R.id.tags3)
    TextView tags3;
    @BindView(R.id.btn_delete)
    ImageView btnDelete;
    @BindView(R.id.chat_image)
    ImageView chatImage;
    @BindView(R.id.edt_chat)
    EditText edtChat;
    @BindView(R.id.chat_btn)
    ImageView chatBtn;
    @BindView(R.id.txt_share)
    ImageView txtShare;
    private Context mycontext;
    private DiscussionFullAdapter discussionFullAdapter;
    private BlogViewResponse blogviewResponse;
    private List<DiscussionFullViewResponse> discussionViewResponse;
    private String discussionid = "";
    private String discussiontags = "";
    private DiscussionUnlikeResponse discussionunlikeResponse;
    private BasicResponse deleteResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_full_view);
        ButterKnife.bind(this);

        initUi();
    }

    private void initUi() {
        mycontext = this;

        categoryViewCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Discussionlike(discussionid);
                } else {
                    DiscussionUnlike(discussionid);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDiscussion(discussionid);
            }
        });

        getBundle();

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtChat.getText().toString().isEmpty()) {
                    Toast.makeText(mycontext, "Please enter comment", Toast.LENGTH_SHORT).show();
                } else {
                    DiscussionComment(discussionid, edtChat.getText().toString());
                }
            }
        });
        txtShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (discussionViewResponse!=null) {
                    String shareBody = discussionViewResponse.get(0).getTitle()+discussionViewResponse.get(0).getDescription();
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, discussionViewResponse.get(0).getTitle());
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Football 3.0 share vis"));
                }
            }
        });
    }

    private void getBundle() {
        Intent intent = getIntent();
        if (intent.getStringExtra(APPConst.DISCUSSIONID) != null) {
            discussionid = intent.getStringExtra(APPConst.DISCUSSIONID);
            getDiscussionView(discussionid);
        }
        if (intent.getStringExtra(APPConst.DISCUSSIONTAGS) != null) {
            discussiontags = intent.getStringExtra(APPConst.DISCUSSIONTAGS);
            tags1.setVisibility(View.GONE);
            tags2.setVisibility(View.GONE);
            tags3.setVisibility(View.GONE);
            tags1.setVisibility(View.GONE);
            String[] separated = discussiontags.split(",");
            Log.d(TAG, "onBindViewHolder: " + separated.length);
            if (separated.length > 0) {
                tags1.setVisibility(View.VISIBLE);
                tags1.setText(separated[0]);
                Log.d(TAG, "onBindViewHolder:1 " + separated.length);
            }
            if (separated.length > 1) {
                tags2.setVisibility(View.VISIBLE);
                tags2.setText(separated[1]);
                Log.d(TAG, "onBindViewHolder:2 " + separated.length);
            }
            if (separated.length > 2) {
                tags3.setVisibility(View.VISIBLE);
                tags3.setText(separated[2]);
                Log.d(TAG, "onBindViewHolder:3 " + separated.length);
            }
        }
    }

    private RecyclerView.LayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(mycontext, LinearLayoutManager.VERTICAL, false);
    }

    @OnClick({R.id.fb_discussion_back, R.id.fb_discussion_linear})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.fb_discussion_back:
                finish();
                break;
            case R.id.fb_discussion_linear:
              /*  Intent i = new Intent(mycontext, FBDiscussionUploadActivity.class);
                startActivity(i);*/
                break;
        }
    }

    Call<List<DiscussionFullViewResponse>> discussionFullViewResponseCall;

    private void getDiscussionView(String discussionId) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        discussionFullViewResponseCall = apiService.getDiscussionFullView(discussionId, Prefs.getString(APPConst.TOKEN, ""));


        discussionFullViewResponseCall.enqueue(new Callback<List<DiscussionFullViewResponse>>() {
            @Override
            public void onResponse(Call<List<DiscussionFullViewResponse>> call, Response<List<DiscussionFullViewResponse>> response) {
                Log.d(TAG, "onResponse:DiscussionFullViewResponse " + new Gson().toJson(response.body()));
                discussionViewResponse = response.body();

                fbDiscussionTitle.setText(discussionViewResponse.get(0).getTitle());
                txtDateDiscussion.setText(discussionViewResponse.get(0).getCreatedOn());
                txtFavCount.setText(discussionViewResponse.get(0).getLikeCount());
                textDiscDesc.setText(Html.fromHtml(discussionViewResponse.get(0).getDescription()));
                icCommentCount.setText(discussionViewResponse.get(0).getCommentsCount());

                if (discussionViewResponse.get(0).getPinStatus().equals("true")) {
                    categoryViewCheckbox.setChecked(true);
                } else {
                    categoryViewCheckbox.setChecked(false);
                }

                Glide.with(mycontext)
                        .load(discussionViewResponse.get(0).getUserphoto())
                        .placeholder(R.drawable.ic_comm_article)
                        .error(R.drawable.ic_comm_article)
                        .into(vendorAdsImage);
                Glide.with(mycontext)
                        .load(discussionViewResponse.get(0).getUserphoto())
                        .placeholder(R.drawable.ic_comm_article)
                        .error(R.drawable.ic_comm_article)
                        .into(chatImage);
                if (discussionViewResponse.get(0).getComments().size() > 0) {
                    discussionFullAdapter = new DiscussionFullAdapter(mycontext, discussionViewResponse.get(0).getComments());
                    fbDiscussionRecycler.setLayoutManager(getLinearLayoutManager());
                    fbDiscussionRecycler.setAdapter(discussionFullAdapter);
                    fbDiscussionRecycler.setVisibility(View.VISIBLE);
                    nodatafound.setVisibility(View.GONE);
                } else {
                    fbDiscussionRecycler.setVisibility(View.GONE);
                    nodatafound.setVisibility(View.VISIBLE);
                }
                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<List<DiscussionFullViewResponse>> call, Throwable t) {
                Log.d(TAG, "onFailure: DiscussionFullViewResponse" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void DiscussionComment(String discussionId, String comment) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        DiscussionCommentRequest discussionCommentRequest = new DiscussionCommentRequest();
        discussionCommentRequest.setUserid(Prefs.getString(APPConst.TOKEN, ""));
        discussionCommentRequest.setDiscussionId(discussionId);
        discussionCommentRequest.setComment(comment);
        Log.d(TAG, "DiscussionComment: " + new Gson().toJson(discussionCommentRequest));
        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        discussionFullViewResponseCall = apiService.discussionComment(discussionCommentRequest);


        discussionFullViewResponseCall.enqueue(new Callback<List<DiscussionFullViewResponse>>() {
            @Override
            public void onResponse(Call<List<DiscussionFullViewResponse>> call, Response<List<DiscussionFullViewResponse>> response) {
                Log.d(TAG, "onResponse:DiscussionFullViewResponse " + new Gson().toJson(response.body()));
                discussionViewResponse = response.body();

                fbDiscussionTitle.setText(discussionViewResponse.get(0).getTitle());
                txtDateDiscussion.setText(discussionViewResponse.get(0).getCreatedOn());
                txtFavCount.setText(discussionViewResponse.get(0).getLikeCount());
                textDiscDesc.setText(Html.fromHtml(discussionViewResponse.get(0).getDescription()));
                icCommentCount.setText(discussionViewResponse.get(0).getCommentsCount());

                if (discussionViewResponse.get(0).getPinStatus().equals("true")) {
                    categoryViewCheckbox.setChecked(true);
                } else {
                    categoryViewCheckbox.setChecked(false);
                }

                Glide.with(mycontext)
                        .load(discussionViewResponse.get(0).getUserphoto())
                        .placeholder(R.drawable.ic_comm_article)
                        .error(R.drawable.ic_comm_article)
                        .into(vendorAdsImage);
                Glide.with(mycontext)
                        .load(discussionViewResponse.get(0).getUserphoto())
                        .placeholder(R.drawable.ic_comm_article)
                        .error(R.drawable.ic_comm_article)
                        .into(chatImage);
                if (discussionViewResponse.get(0).getComments().size() > 0) {
                    discussionFullAdapter = new DiscussionFullAdapter(mycontext, discussionViewResponse.get(0).getComments());
                    fbDiscussionRecycler.setLayoutManager(getLinearLayoutManager());
                    fbDiscussionRecycler.setAdapter(discussionFullAdapter);
                    fbDiscussionRecycler.setVisibility(View.VISIBLE);
                    nodatafound.setVisibility(View.GONE);
                } else {
                    fbDiscussionRecycler.setVisibility(View.GONE);
                    nodatafound.setVisibility(View.VISIBLE);
                }
                edtChat.setText("");
                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<List<DiscussionFullViewResponse>> call, Throwable t) {
                Log.d(TAG, "onFailure: DiscussionFullViewResponse" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    Call<DiscussionUnlikeResponse> discussionUnlikeResponseCall;

    private void DiscussionUnlike(String discussionId) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        DiscussionUnlikeRequest discussionUnlikeRequest = new DiscussionUnlikeRequest();
        discussionUnlikeRequest.setTopicid(discussionId);
        discussionUnlikeRequest.setUserid(Prefs.getString(APPConst.TOKEN, ""));
        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        discussionUnlikeResponseCall = apiService.discussionunlike(discussionUnlikeRequest);


        discussionUnlikeResponseCall.enqueue(new Callback<DiscussionUnlikeResponse>() {
            @Override
            public void onResponse(Call<DiscussionUnlikeResponse> call, Response<DiscussionUnlikeResponse> response) {
                Log.d(TAG, "onResponse:DiscussionFullViewResponse " + new Gson().toJson(response.body()));
                discussionunlikeResponse = response.body();

                if (response.body().isStatus()) {
                    categoryViewCheckbox.setChecked(false);
                    int likecount = Integer.parseInt(discussionViewResponse.get(0).getLikeCount()) - 1;
                    txtFavCount.setText("" + likecount);
                    Toast.makeText(mycontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mycontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<DiscussionUnlikeResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: DiscussionFullViewResponse" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    Call<DiscussionUnlikeResponse> discussionlikeResponseCall;

    private void Discussionlike(String discussionId) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        DiscussionUnlikeRequest discussionUnlikeRequest = new DiscussionUnlikeRequest();
        discussionUnlikeRequest.setTopicid(discussionId);
        discussionUnlikeRequest.setUserid(Prefs.getString(APPConst.TOKEN, ""));
        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        discussionlikeResponseCall = apiService.discussionlike(discussionUnlikeRequest);


        discussionlikeResponseCall.enqueue(new Callback<DiscussionUnlikeResponse>() {
            @Override
            public void onResponse(Call<DiscussionUnlikeResponse> call, Response<DiscussionUnlikeResponse> response) {
                Log.d(TAG, "onResponse:DiscussionUnlikeResponse " + new Gson().toJson(response.body()));
                discussionunlikeResponse = response.body();

                if (response.body().isStatus()) {
                    categoryViewCheckbox.setChecked(true);
                   /* int likecount = Integer.parseInt(discussionViewResponse.get(0).getLikeCount())+1;
                    txtFavCount.setText(""+likecount);*/
                    Toast.makeText(mycontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mycontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<DiscussionUnlikeResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: DiscussionUnlikeResponse" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    Call<BasicResponse> basicResponseCall;

    private void deleteDiscussion(String discussionId) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        basicResponseCall = apiService.deletedisccsion(Prefs.getString(APPConst.TOKEN, ""), discussionId);


        basicResponseCall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                Log.d(TAG, "onResponse:DiscussionDeleteResponse " + new Gson().toJson(response.body()));
                deleteResponse = response.body();

                if (response.body().isStatus()) {
                    finish();
                    Toast.makeText(mycontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mycontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: DiscussionDeleteResponse" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
