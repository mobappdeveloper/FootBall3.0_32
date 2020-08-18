package com.footballio.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.footballio.Activities.FBDiscussionUploadActivity;
import com.footballio.Activities.FBLoaderActivity;
import com.footballio.Adapter.ArticleAdapter;
import com.footballio.Adapter.CommunityGroupAdapter;
import com.footballio.Adapter.DiscussionAdapter;
import com.footballio.Adapter.FavouriteAdapter;
import com.footballio.Adapter.TrendAdapter;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.model.community.CommunityMainResponse;
import com.footballio.model.community.discussion.DiscussionMainResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class CommunityFragment extends Fragment {

    @BindView(R.id.fb_comm_trend)
    TextView fbCommTrend;
    @BindView(R.id.fb_comm_favourite)
    TextView fbCommFavourite;
    @BindView(R.id.fb_comm_articles)
    TextView fbCommArticles;
    @BindView(R.id.linear_trend)
    LinearLayout linearTrend;
    @BindView(R.id.linear_fav)
    LinearLayout linearFav;
    @BindView(R.id.linear_mat)
    LinearLayout linearMat;
    @BindView(R.id.fab)
    FloatingActionButton fab;


    private OnFragmentInteractionListener listener;

    private Activity mycontext;
    private Unbinder unbinder;
    private TrendAdapter trendAdapter;
    private FavouriteAdapter favouriteAdapter;
    private ArticleAdapter articleAdapter;
    private CommunityGroupAdapter communityGroupAdapter;
    private DiscussionAdapter discussionAdapter;
    private RecyclerView comm_trend_recycle, comm_favourite_recycle, community_groups, comm_article_recycle_1, comm_article_recycle_2, comm_discussion_recycle;
    private TextView comm_blog_tv, comm_tv_discussion, fb_home_tv_name, fb_comm_categories;
    private ImageView comm_img_blog, comm_img_discussion;
    private FloatingActionButton comm_discussion_fab;
    private LinearLayout comm_blog, comm_discussion, comm_linear;
    private CircularProgressBar circularProgressBarGreen, circularProgressBarBlue;
    private Handler handler;
    private CommunityMainResponse communityResponse;
    private DiscussionMainResponse discussionResponse;
    private LinearLayout linear_category,payment_info;

    public static CommunityFragment newInstance() {
        return new CommunityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View aView = inflater.inflate(R.layout.fragment_community, container, false);
        unbinder = ButterKnife.bind(this, aView);
        initUi(aView);
        // Inflate the layout for this fragment
        //  setHasOptionsMenu(true);
        return aView;
    }

    private void initUi(View aView) {
        mycontext = getActivity();


        comm_trend_recycle = aView.findViewById(R.id.fb_comm_trend_recycler);
        comm_discussion_recycle = aView.findViewById(R.id.fb_comm_discussion_recycler);
        comm_favourite_recycle = aView.findViewById(R.id.fb_comm_favourite_recycler);
        comm_article_recycle_1 = aView.findViewById(R.id.fb_comm_articles_recycler1);
        comm_article_recycle_2 = aView.findViewById(R.id.fb_comm_articles_recycler2);
        comm_blog_tv = aView.findViewById(R.id.fb_tv_blog);
        comm_tv_discussion = aView.findViewById(R.id.fb_tv_discussion);
        comm_img_blog = aView.findViewById(R.id.fb_img_blog);
        comm_img_discussion = aView.findViewById(R.id.fb_img_discussion);
        comm_blog = aView.findViewById(R.id.fb_comm_blog);
        comm_discussion = aView.findViewById(R.id.fb_comm_discussion);
        community_groups = aView.findViewById(R.id.community_groups);
        fb_home_tv_name = aView.findViewById(R.id.fb_comm_tv_name1);
        fb_comm_categories = aView.findViewById(R.id.fb_comm_categories);
        linear_category = aView.findViewById(R.id.linear_category);
        payment_info = aView.findViewById(R.id.payment_info);
        //  comm_discussion_fab = aView.findViewById(R.id.fb_comm_discussion_fab);

        circularProgressBarGreen = aView.findViewById(R.id.progressgreen);
        circularProgressBarBlue = aView.findViewById(R.id.progressblue);
        handler = new Handler();
        circularProgressBarGreen.setProgressWithAnimation(100, (long) 700);
        circularProgressBarBlue.setProgressWithAnimation(100, (long) 700);
        final Runnable r = new Runnable() {
            public void run() {
                circularProgressBarGreen.setProgressWithAnimation(Prefs.getInt(APPConst.PGREENPERCENTAGE,0), (long) 500);
                circularProgressBarBlue.setProgressWithAnimation(Prefs.getInt(APPConst.PBLUEPERCENTAGE,0), (long) 500);
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);
        fb_home_tv_name.setText(Prefs.getString(APPConst.PROFILEUSERNAME, ""));

        comm_blog.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {


                linear_category.setVisibility(View.VISIBLE);
                linearFav.setVisibility(View.VISIBLE);
                linearMat.setVisibility(View.VISIBLE);
                linearTrend.setVisibility(View.VISIBLE);
                //fb_comm_categories.setVisibility(View.VISIBLE);


                comm_blog.setBackgroundColor(Color.parseColor("#454D62"));
                comm_img_blog.setColorFilter(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorMajor));
                comm_blog_tv.setTextColor(Color.parseColor("#00FE7F"));

                //   comm_discussion_fab.setVisibility(View.GONE);
                comm_discussion_recycle.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

                comm_discussion.setBackgroundResource(R.drawable.ic_dashbaord_button_bg);
                comm_img_discussion.setColorFilter(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorWhite));
                comm_tv_discussion.setTextColor(Color.parseColor("#FFFFFF"));

                dashboard_category();


            }
        });

        comm_discussion.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {


                comm_discussion_recycle.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
                linear_category.setVisibility(View.GONE);
                linearFav.setVisibility(View.GONE);
                linearMat.setVisibility(View.GONE);
                linearTrend.setVisibility(View.GONE);
                // fb_comm_categories.setVisibility(View.GONE);
                //  comm_discussion_fab.setVisibility(View.VISIBLE);

                dashboard_discussion();


                comm_discussion.setBackgroundColor(Color.parseColor("#454D62"));
                comm_img_discussion.setColorFilter(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorMajor));
                comm_tv_discussion.setTextColor(Color.parseColor("#00FE7F"));
                comm_blog.setBackgroundResource(R.drawable.ic_dashbaord_button_bg);
                comm_img_blog.setColorFilter(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorWhite));
                comm_blog_tv.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
        comm_blog.performClick();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mycontext, FBDiscussionUploadActivity.class);
                startActivity(intent);
            }
        });
        payment_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mycontext, FBLoaderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        dashboard_category();
        dashboard_discussion();
    }

    private void circularanimation() {
        final Runnable r = new Runnable() {
            public void run() {
                circularanimation();
                circularProgressBarGreen.setProgressWithAnimation(Prefs.getInt(APPConst.PGREENPERCENTAGE, 0), (long) 500);
                circularProgressBarBlue.setProgressWithAnimation(Prefs.getInt(APPConst.PBLUEPERCENTAGE, 0), (long) 500);
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);
    }

    private RecyclerView.LayoutManager getLinearLayoutManager1() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

    }

    private RecyclerView.LayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

/*    @OnClick({R.id.fb_comm_nutrition, R.id.fb_comm_health, R.id.fb_comm_tactics})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_comm_nutrition:
                Intent i = new Intent(getActivity(), FBBlogCategoryActivity.class);
                startActivity(i);

                break;
            *//*case R.id.fb_comm_health:
                break;
            case R.id.fb_comm_tactics:
                break;*//*
        }
    }*/

    public interface OnFragmentInteractionListener {
    }


    Call<CommunityMainResponse> communityMainResponseCall;

    private void dashboard_category() {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        communityMainResponseCall = apiService.getCommunity();


        communityMainResponseCall.enqueue(new Callback<CommunityMainResponse>() {
            @Override
            public void onResponse(Call<CommunityMainResponse> call, Response<CommunityMainResponse> response) {
                Log.d(TAG, "onResponse:CommunityMainResponse " + new Gson().toJson(response.body()));
                communityResponse = response.body();

                trendAdapter = new TrendAdapter(mycontext, communityResponse.getTrends());
                comm_trend_recycle.setLayoutManager(getLinearLayoutManager());
                comm_trend_recycle.setAdapter(trendAdapter);


                favouriteAdapter = new FavouriteAdapter(mycontext, communityResponse.getFavoriten());
                comm_favourite_recycle.setLayoutManager(getLinearLayoutManager());
                comm_favourite_recycle.setAdapter(favouriteAdapter);


                articleAdapter = new ArticleAdapter(mycontext, communityResponse.getMeistgeleseneArtikel());
                comm_article_recycle_1.setLayoutManager(getLinearLayoutManager());
                comm_article_recycle_1.setAdapter(articleAdapter);

                communityGroupAdapter = new CommunityGroupAdapter(mycontext, communityResponse.getKategorien());
                community_groups.setLayoutManager(getLinearLayoutManager1());
                community_groups.setAdapter(communityGroupAdapter);

                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<CommunityMainResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: CommunityMainResponse" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    Call<DiscussionMainResponse> discussionMainResponseCall;

    private void dashboard_discussion() {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        discussionMainResponseCall = apiService.getDiscussionList(Prefs.getString(APPConst.TOKEN, ""));


        discussionMainResponseCall.enqueue(new Callback<DiscussionMainResponse>() {
            @Override
            public void onResponse(Call<DiscussionMainResponse> call, Response<DiscussionMainResponse> response) {
                Log.d(TAG, "onResponse:CommunityMainResponse " + new Gson().toJson(response.body()));
                discussionResponse = response.body();

                discussionAdapter = new DiscussionAdapter(mycontext, discussionResponse);
                comm_discussion_recycle.setLayoutManager(getLinearLayoutManager1());
                comm_discussion_recycle.setAdapter(discussionAdapter);

                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<DiscussionMainResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: CommunityMainResponse" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
