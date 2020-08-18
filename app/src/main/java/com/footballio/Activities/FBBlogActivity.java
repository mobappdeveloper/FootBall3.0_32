package com.footballio.Activities;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.footballio.Adapter.BlogRelatedAdapter;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.model.community.blogview.BlogViewResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FBBlogActivity extends AppCompatActivity {
    private static final String TAG = FBBlogActivity.class.getSimpleName();
    @BindView(R.id.fb_blog_back)
    LinearLayout fbBlogBack;
    @BindView(R.id.fb_workout_view_title)
    TextView fbWorkoutViewTitle;
    @BindView(R.id.fb_blog_image)
    ImageView fbBlogImage;
    @BindView(R.id.fb_blog_type_name)
    TextView fbBlogTypeName;
    @BindView(R.id.blog_date)
    TextView blogDate;
    @BindView(R.id.fb_blog_description)
    TextView fbBlogDescription;

    @BindView(R.id.fb_blog_type_category)
    TextView fbBlogTypeCategory;
    @BindView(R.id.recycle_relatetopic)
    RecyclerView recycleRelatetopic;


    private Context mycontext;
    private BlogRelatedAdapter blogAdapter;
    private TextView empty;
    ArrayList<ClipData.Item> gridArray = new ArrayList<ClipData.Item>();

    private String blogid = "";
    private List<BlogViewResponse> blogviewResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        ButterKnife.bind(this);

        initUi();
    }

    private void initUi() {
        mycontext = this;

        getBundle();
    }

    private void getBundle() {
        Intent intent = getIntent();
        if (intent.getStringExtra(APPConst.BLOGID) != null) {
            blogid = intent.getStringExtra(APPConst.BLOGID);
            getBlogView(blogid);
        }
    }

    @OnClick(R.id.fb_blog_back)
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.fb_blog_back:
                finish();
                break;
        }
    }

    Call<List<BlogViewResponse>> blogViewResponseCall;

    private void getBlogView(String blogId) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        blogViewResponseCall = apiService.getBlogFullView(blogId);


        blogViewResponseCall.enqueue(new Callback<List<BlogViewResponse>>() {
            @Override
            public void onResponse(Call<List<BlogViewResponse>> call, Response<List<BlogViewResponse>> response) {
                Log.d(TAG, "onResponse:BlogViewResponse " + new Gson().toJson(response.body()));
                blogviewResponse = response.body();
                Glide.with(mycontext)
                        .load(blogviewResponse.get(0).getPhoto())
                        .placeholder(R.color.colorTransparent)
                        .error(R.color.colorTransparent)
                        .into(fbBlogImage);
                fbWorkoutViewTitle.setText(blogviewResponse.get(0).getBlogTitle());
                blogDate.setText(blogviewResponse.get(0).getDate());
                fbBlogDescription.setText(Html.fromHtml(blogviewResponse.get(0).getDescription()));
                blogAdapter = new BlogRelatedAdapter(mycontext, blogviewResponse.get(0));
                recycleRelatetopic.setLayoutManager(getLinearLayoutManager1());
                recycleRelatetopic.setAdapter(blogAdapter);

                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<List<BlogViewResponse>> call, Throwable t) {
                Log.d(TAG, "onFailure: BlogViewResponse" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private RecyclerView.LayoutManager getLinearLayoutManager1() {
        return new GridLayoutManager(mycontext, 2,GridLayoutManager.VERTICAL, false);

    }
}
