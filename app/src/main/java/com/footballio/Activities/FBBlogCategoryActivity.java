package com.footballio.Activities;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.footballio.Adapter.BlogCategoryAdapter;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.model.community.BlogCategoryViewResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FBBlogCategoryActivity extends AppCompatActivity {
    private static final String TAG = FBBlogCategoryActivity.class.getSimpleName();
    @BindView(R.id.recycle_blog_cat)
    RecyclerView recycleBlogCat;
    @BindView(R.id.fb_nutrion_back)
    LinearLayout fbNutrionBack;
    @BindView(R.id.catname)
    TextView catname;
    private Context mycontext;


    private GridView workout_gridView;
    private TextView empty;
    ArrayList<ClipData.Item> gridArray = new ArrayList<ClipData.Item>();
    private BlogCategoryAdapter blogCategoryAdapter;
    private String blogcatid = "";
    private BlogCategoryViewResponse blogcatResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);
        ButterKnife.bind(this);

        initUi();
    }

    private void initUi() {
        mycontext = this;

        getBundle();
    }

    private void getBundle() {
        Intent intent = getIntent();
        if (intent.getStringExtra(APPConst.COMMCATID) != null) {
            blogcatid = intent.getStringExtra(APPConst.COMMCATID);
            getBlogCatView(blogcatid);
        }
    }

    @OnClick(R.id.fb_nutrion_back)
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.fb_nutrion_back:
                finish();
                break;
        }


    }

    Call<BlogCategoryViewResponse> blogCategoryViewResponseCall;

    private void getBlogCatView(String blogId) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        blogCategoryViewResponseCall = apiService.getBlogCategoryFullView(blogId);


        blogCategoryViewResponseCall.enqueue(new Callback<BlogCategoryViewResponse>() {
            @Override
            public void onResponse(Call<BlogCategoryViewResponse> call, Response<BlogCategoryViewResponse> response) {
                Log.d(TAG, "onResponse:BlogCategoryViewResponse " + new Gson().toJson(response.body()));
                blogcatResponse = response.body();
                blogCategoryAdapter = new BlogCategoryAdapter(mycontext, blogcatResponse.getBlogs());
                recycleBlogCat.setLayoutManager(getLinearLayoutManager1());
                recycleBlogCat.setAdapter(blogCategoryAdapter);
                catname.setText(blogcatResponse.getCategories().get(0).get(0).getCategoryName());
                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<BlogCategoryViewResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: BlogCategoryViewResponse" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private RecyclerView.LayoutManager getLinearLayoutManager1() {
        return new GridLayoutManager(mycontext, 2, GridLayoutManager.VERTICAL, false);

    }

}
