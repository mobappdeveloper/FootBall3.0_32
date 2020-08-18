package com.footballio.Fragment;

import android.content.ClipData;
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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.footballio.Activities.FBLoaderActivity;
import com.footballio.Adapter.AwardsAdapter;
import com.footballio.Adapter.DiscussionAdapter;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.model.chanllenge.ChanllengeResponse;
import com.footballio.model.chanllenge.DataItem;
import com.footballio.model.chanllenge.awardsuser.AwardsUserResponse;
import com.footballio.model.chanllenge.ranking.RankingResponse;
import com.footballio.model.community.discussion.DiscussionMainResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.footballio.Adapter.ChallengeAdapter;
import com.footballio.Adapter.RankingAdapter;
import com.footballio.Adapter.TrophyAdapter;
import com.google.gson.Gson;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class ChallengeFragment extends Fragment {

    public static final String TAG = ChallengeFragment.class.getSimpleName();

    private Button btn_purchase;
    private Context mycontext;
    private TextView challenge_chlng_tv, challenge_ranking_tv, challenge_trophies_tv, challenge_trophy_chlng, fb_home_tv_name1, challenge_trophy_workout, challenge_trophy_call;
    private ImageView challenge_img_chlng, challenge_img_ranking, challenge_img_trophies;
    private LinearLayout challenge_chlng, challenge_ranking, challenge_trophies, payment_info;
    private HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager;
    private RecyclerView challenge_ranking_recycler;
    private RankingAdapter rankingAdapter;
    private RecyclerView gridView1, gridView2, gridView3;
    private AwardsAdapter awardsAdapter, awardsAdapter1, awardsAdapter2;
    ArrayList<ClipData.Item> gridArray = new ArrayList<ClipData.Item>();

    private ChallengeFragment.OnFragmentInteractionListener listener;
    private CircularProgressBar circularProgressBarGreen, circularProgressBarBlue;
    private Handler handler;
    private ChanllengeResponse chanllengeResponse;
    private ChanllengeResponse chanllengeResponsenew;
    private List<DataItem> dataItem;
    private RankingResponse chanllengeRankingResponse;
    private AwardsUserResponse chanllengeAwardsResponse;


    public static ChallengeFragment newInstance() {
        return new ChallengeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View aView = inflater.inflate(R.layout.fragment_challenge, container, false);
        initUi(aView);
        // Inflate the layout for this fragment
        //  setHasOptionsMenu(true);
        fb_home_tv_name1.setText(Prefs.getString(APPConst.PROFILEUSERNAME, ""));
        return aView;
    }

    private void initUi(View aView) {
        mycontext = getActivity();


        challenge_chlng_tv = aView.findViewById(R.id.fb_tv_challenge);
        challenge_ranking_tv = aView.findViewById(R.id.fb_tv_ranking);
        challenge_trophies_tv = aView.findViewById(R.id.fb_tv_trophies);
        challenge_img_chlng = aView.findViewById(R.id.fb_img_challenge);
        challenge_img_ranking = aView.findViewById(R.id.fb_img_ranking);
        challenge_img_trophies = aView.findViewById(R.id.fb_img_trophies);
        challenge_chlng = aView.findViewById(R.id.fb_challenge_chlng);
        challenge_ranking = aView.findViewById(R.id.fb_challenge_ranking);
        challenge_trophies = aView.findViewById(R.id.fb_challenge_trophies);
        challenge_trophy_call = aView.findViewById(R.id.fb_challenge_trophy_call);
        challenge_trophy_chlng = aView.findViewById(R.id.fb_challenge_trophy_chlng);
        challenge_trophy_workout = aView.findViewById(R.id.fb_challenge_trophy_workout);
        payment_info = aView.findViewById(R.id.payment_info);
        fb_home_tv_name1 = aView.findViewById(R.id.fb_challenge_tv_name);
        gridView1 = aView.findViewById(R.id.gridView1);
        gridView2 = aView.findViewById(R.id.gridView2);
        gridView3 = aView.findViewById(R.id.gridView3);
        challenge_ranking_recycler = aView.findViewById(R.id.fb_challenge_ranking_recycler);
        horizontalInfiniteCycleViewPager =
                aView.findViewById(R.id.fb_challenge_chlng_recycler);


        circularProgressBarGreen = aView.findViewById(R.id.progressgreen);
        circularProgressBarBlue = aView.findViewById(R.id.progressblue);
        handler = new Handler();
        /*circularProgressBarGreen.setProgressWithAnimation(100, (long) 700);
        circularProgressBarBlue.setProgressWithAnimation(100, (long) 700);*/

        Log.d(TAG, "initUi:PGREENPERCENTAGE " + Prefs.getInt(APPConst.PGREENPERCENTAGE, 0));
        Log.d(TAG, "initUi:PBLUEPERCENTAGE " + Prefs.getInt(APPConst.PBLUEPERCENTAGE, 0));
        final Runnable r = new Runnable() {
            public void run() {
                circularProgressBarGreen.setProgressWithAnimation(Prefs.getInt(APPConst.PGREENPERCENTAGE, 0), (long) 500);
                circularProgressBarBlue.setProgressWithAnimation(Prefs.getInt(APPConst.PBLUEPERCENTAGE, 0), (long) 500);
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);

        challenge_chlng.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                getChanllenge();
                challenge_ranking_recycler.setVisibility(View.GONE);
                horizontalInfiniteCycleViewPager.setVisibility(View.VISIBLE);
                challenge_trophy_call.setVisibility(View.GONE);
                challenge_trophy_chlng.setVisibility(View.GONE);
                challenge_trophy_workout.setVisibility(View.GONE);
                gridView1.setVisibility(View.GONE);
                gridView2.setVisibility(View.GONE);
                gridView3.setVisibility(View.GONE);

                challenge_chlng.setBackgroundColor(Color.parseColor("#454D62"));
                challenge_img_chlng.setColorFilter(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorMajor));
                challenge_chlng_tv.setTextColor(Color.parseColor("#00FE7F"));

                challenge_ranking.setBackgroundResource(R.drawable.ic_dashbaord_button_bg);
                challenge_img_ranking.setColorFilter(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorWhite));
                challenge_ranking_tv.setTextColor(Color.parseColor("#FFFFFF"));
                challenge_trophies.setBackgroundResource(R.drawable.ic_dashbaord_button_bg);
                challenge_img_trophies.setColorFilter(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorWhite));
                challenge_trophies_tv.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        challenge_ranking.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                horizontalInfiniteCycleViewPager.setVisibility(View.GONE);
                challenge_ranking_recycler.setVisibility(View.VISIBLE);
                challenge_trophy_call.setVisibility(View.GONE);
                challenge_trophy_chlng.setVisibility(View.GONE);
                challenge_trophy_workout.setVisibility(View.GONE);
                gridView1.setVisibility(View.GONE);
                gridView2.setVisibility(View.GONE);
                gridView3.setVisibility(View.GONE);

                getChanllengeRanking();
                challenge_ranking.setBackgroundColor(Color.parseColor("#454D62"));
                challenge_img_ranking.setColorFilter(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorMajor));
                challenge_ranking_tv.setTextColor(Color.parseColor("#00FE7F"));
                challenge_chlng.setBackgroundResource(R.drawable.ic_dashbaord_button_bg);
                challenge_img_chlng.setColorFilter(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorWhite));
                challenge_chlng_tv.setTextColor(Color.parseColor("#FFFFFF"));
                challenge_trophies.setBackgroundResource(R.drawable.ic_dashbaord_button_bg);
                challenge_img_trophies.setColorFilter(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorWhite));
                challenge_trophies_tv.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
        challenge_trophies.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                horizontalInfiniteCycleViewPager.setVisibility(View.GONE);
                challenge_ranking_recycler.setVisibility(View.GONE);

                getChanllengeAwards();

                challenge_trophy_call.setVisibility(View.VISIBLE);
                challenge_trophy_chlng.setVisibility(View.VISIBLE);
                challenge_trophy_workout.setVisibility(View.VISIBLE);
                gridView1.setVisibility(View.VISIBLE);
                gridView2.setVisibility(View.VISIBLE);
                gridView3.setVisibility(View.VISIBLE);


                challenge_trophies.setBackgroundColor(Color.parseColor("#454D62"));
                challenge_img_trophies.setColorFilter(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorMajor));
                challenge_trophies_tv.setTextColor(Color.parseColor("#00FE7F"));

                challenge_chlng.setBackgroundResource(R.drawable.ic_dashbaord_button_bg);
                challenge_img_chlng.setColorFilter(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorWhite));
                challenge_chlng_tv.setTextColor(Color.parseColor("#FFFFFF"));
                challenge_ranking.setBackgroundResource(R.drawable.ic_dashbaord_button_bg);
                challenge_img_ranking.setColorFilter(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorWhite));
                challenge_ranking_tv.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
        challenge_chlng.performClick();

        payment_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mycontext, FBLoaderActivity.class);
                startActivity(intent);
            }
        });
    }

    private Object getLinearLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChallengeFragment.OnFragmentInteractionListener) {
            listener = (ChallengeFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
    }


    Call<ChanllengeResponse> chanllengeResponseCall;

    private void getChanllenge() {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        chanllengeResponseCall = apiService.getChanllenge();


        chanllengeResponseCall.enqueue(new Callback<ChanllengeResponse>() {
            @Override
            public void onResponse(Call<ChanllengeResponse> call, Response<ChanllengeResponse> response) {
                Log.d(TAG, "onResponse:CommunityMainResponse " + new Gson().toJson(response.body()));
                chanllengeResponse = response.body();

                chanllengeResponsenew = new ChanllengeResponse();
                dataItem = new ArrayList<>();
                for (int i = 0; i < chanllengeResponse.getData().size(); i++) {
                    if (chanllengeResponse.getData().get(i).getStatus().equals("ACTIVE")) {
                        dataItem.add(chanllengeResponse.getData().get(i));
                    }

                }
                for (int i = 0; i < chanllengeResponse.getData().size(); i++) {
                    if (chanllengeResponse.getData().get(i).getStatus().equals("FINISHED")) {
                        dataItem.add(chanllengeResponse.getData().get(i));
                    }

                }
                chanllengeResponsenew.setData(dataItem);
                horizontalInfiniteCycleViewPager.setAdapter(new ChallengeAdapter(mycontext, chanllengeResponsenew, false));


                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<ChanllengeResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: CommunityMainResponse" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    Call<RankingResponse> responseCall;

    private void getChanllengeRanking() {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        responseCall = apiService.getChanllengeRanking();


        responseCall.enqueue(new Callback<RankingResponse>() {
            @Override
            public void onResponse(Call<RankingResponse> call, Response<RankingResponse> response) {
                Log.d(TAG, "onResponse:CommunityMainResponse " + new Gson().toJson(response.body()));
                chanllengeRankingResponse = response.body();

                rankingAdapter = new RankingAdapter(mycontext, chanllengeRankingResponse);
                challenge_ranking_recycler.setLayoutManager((RecyclerView.LayoutManager) getLinearLayoutManager());
                challenge_ranking_recycler.setAdapter(rankingAdapter);

                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<RankingResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: CommunityMainResponse" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    Call<AwardsUserResponse> awardsUserResponseCall;

    private void getChanllengeAwards() {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        awardsUserResponseCall = apiService.getChanllengeAwardsUser(Prefs.getString(APPConst.TOKEN, ""));


        awardsUserResponseCall.enqueue(new Callback<AwardsUserResponse>() {
            @Override
            public void onResponse(Call<AwardsUserResponse> call, Response<AwardsUserResponse> response) {
                Log.d(TAG, "onResponse:CommunityMainResponse " + new Gson().toJson(response.body()));
                chanllengeAwardsResponse = response.body();

                awardsAdapter = new AwardsAdapter(mycontext, chanllengeAwardsResponse, "1");
                awardsAdapter1 = new AwardsAdapter(mycontext, chanllengeAwardsResponse, "2");
                awardsAdapter2 = new AwardsAdapter(mycontext, chanllengeAwardsResponse, "3");
                gridView1.setLayoutManager(new GridLayoutManager(mycontext, 3));
                gridView2.setLayoutManager(new GridLayoutManager(mycontext, 3));
                gridView3.setLayoutManager(new GridLayoutManager(mycontext, 3));
                gridView1.setAdapter(awardsAdapter);
                gridView2.setAdapter(awardsAdapter1);
                gridView3.setAdapter(awardsAdapter2);


                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<AwardsUserResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: CommunityMainResponse" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
