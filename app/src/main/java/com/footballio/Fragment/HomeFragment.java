package com.footballio.Fragment;

import android.annotation.SuppressLint;
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
import androidx.viewpager.widget.ViewPager;

import com.bikomobile.donutprogress.DonutProgress;
import com.footballio.Activities.FBLoaderActivity;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.model.LoaderResponse;
import com.footballio.model.dashboard.DashboardResponse;
import com.footballio.model.dashboard.category.CategoryResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.android.material.tabs.TabLayout;
import com.footballio.Adapter.MainProgramAdapter;
import com.footballio.Adapter.ViewPageAdapter;
import com.google.gson.Gson;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment  {

    private OnFragmentInteractionListener listener;
    Context mycontext;
    public static final String TAG = "HomeFragment";
    private TabLayout home_sub_program_tab;
    private ViewPager home_sub_program_view;
    private RecyclerView home_main_program_recycle;
    private ViewPageAdapter viewPageAdapter;
    private TextView home_main_program_tv,home_tv_sub_program,fb_home_tv_name;
    private ImageView home_img_main_program,home_img_sub_program;
    private LinearLayout home_sub_program,home_main_program;
    private MainProgramAdapter mainProgramAdapter;
    private List<DashboardResponse> dashboardResponse;
    private ViewPageAdapter viewPagerAdapter;
    private CategoryResponse categoryResponse;
    private LinearLayout layout_payment;
   private CircularProgressBar circularProgressBarGreen,circularProgressBarBlue;
   // private DonutProgress circularProgressBarGreen,circularProgressBarBlue;
    private Handler handler,handler1;
    private String dashClick = "0";
    private LoaderResponse loaderResponse;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View aView = inflater.inflate(R.layout.fragment_home, container, false);

        initUi(aView);
        
        getBundle();

        // Inflate the layout for this fragment
        //  setHasOptionsMenu(true);
        return aView;
    }

    private void getBundle() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            dashClick = bundle.getString(APPConst.DASHREDIRECT);
            Log.d(TAG, "getBundle:data "+dashClick);
            if (dashClick.equals("1")){
                home_sub_program.performClick();
                dashClick="0";
            }
        }else{
            Log.d(TAG, "getBundle:data empty"+dashClick);
        }
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

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private void initUi(View aView) {
        Log.d(TAG, "initUi:data "+dashClick);
        mycontext = getActivity();
        getLoader();
        home_main_program_recycle = aView.findViewById(R.id.fb_home_main_program_recycler);
        home_main_program_tv = aView.findViewById(R.id.fb_tv_main_program);
        home_tv_sub_program = aView.findViewById(R.id.fb_tv_sub_program);
        home_img_main_program = aView.findViewById(R.id.fb_img_main_program);
        home_sub_program_tab = aView.findViewById(R.id.fb_home_sub_program_tab);
        home_sub_program_view = aView.findViewById(R.id.fb_home_sub_program_view_pager);
        home_img_sub_program = aView.findViewById(R.id.fb_img_sub_program);
        home_sub_program = aView.findViewById(R.id.fb_home_sub_program);
        home_main_program = aView.findViewById(R.id.fb_home_main_program);
        fb_home_tv_name = aView.findViewById(R.id.fb_home_tv_name);
        layout_payment = aView.findViewById(R.id.layout_payment);

        dashboardResponse = new ArrayList<>();

        circularProgressBarGreen = aView.findViewById(R.id.progressgreen);
        circularProgressBarBlue = aView.findViewById(R.id.progressblue);
        handler = new Handler();
        handler1 = new Handler();
        circularProgressBarGreen.setProgressWithAnimation(100, (long) 700);
        circularProgressBarBlue.setProgressWithAnimation(100, (long) 700);

        fb_home_tv_name.setText(Prefs.getString(APPConst.PROFILEUSERNAME,""));
        home_main_program.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                home_sub_program_tab.setVisibility(View.GONE);
                home_sub_program_view.setVisibility(View.GONE);
                home_main_program_recycle.setVisibility(View.VISIBLE);
                dashboardList();
                home_main_program.setBackgroundColor(Color.parseColor("#454D62"));
                home_sub_program.setBackgroundResource(R.drawable.ic_dashbaord_button_bg);
                home_img_sub_program.setColorFilter(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorWhite));
                home_tv_sub_program.setTextColor(Color.parseColor("#FFFFFF"));
                home_img_main_program.setColorFilter(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorMajor));
                home_main_program_tv.setTextColor(Color.parseColor("#00FE7F"));

            }
        });

        home_sub_program.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                dashboard_category(aView);
                home_sub_program_tab.setVisibility(View.VISIBLE);
                home_sub_program_view.setVisibility(View.VISIBLE);
                home_main_program_recycle.setVisibility(View.GONE);
                home_sub_program_tab.setSelectedTabIndicatorColor(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorMajor));
                home_sub_program_tab.setSelectedTabIndicatorHeight((int) (1 * getResources().getDisplayMetrics().density));
                home_sub_program_tab.setTabTextColors(Color.parseColor("#727272"),Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorMajor));
                home_img_main_program.setColorFilter(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorWhite));
                home_main_program.setBackgroundResource(R.drawable.ic_dashbaord_button_bg);
                home_main_program_tv.setTextColor(Color.parseColor("#FFFFFF"));
                home_sub_program.setBackgroundColor(Color.parseColor("#454D62"));
                home_img_sub_program.setColorFilter(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorMajor));
                home_tv_sub_program.setTextColor(Color.parseColor("#00FE7F"));

            }
        });


layout_payment.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mycontext, FBLoaderActivity.class);
        startActivity(intent);
    }
});


      final Runnable r = new Runnable() {
            public void run() {
                Log.d(TAG, "run:BLUE "+Prefs.getInt(APPConst.PGREENPERCENTAGE,0));
                Log.d(TAG, "run:GREEN "+Prefs.getInt(APPConst.PBLUEPERCENTAGE,0));
                circularProgressBarGreen.setProgressWithAnimation(Prefs.getInt(APPConst.PGREENPERCENTAGE,0),  (long)500);
                circularProgressBarBlue.setProgressWithAnimation(Prefs.getInt(APPConst.PBLUEPERCENTAGE,0),  (long)500);
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);

     /*   final Runnable r1 = new Runnable() {
            public void run() {
                circularProgressBarGreen.setProgressWithAnimation(Prefs.getInt(APPConst.PGREENPERCENTAGE,0), (long) 500);
                circularProgressBarBlue.setProgressWithAnimation(Prefs.getInt(APPConst.PBLUEPERCENTAGE,0), (long) 500);

                handler1.postDelayed(this, 2000);
            }
        };
        handler1.postDelayed(r1, 2000);*/

      home_main_program.performClick();
    }

    private RecyclerView.LayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

    }


    public interface OnFragmentInteractionListener {
    }

    Call<List<DashboardResponse>> dashboardResponseCall;

    private void dashboardList() {

        Loader.showLoad(mycontext,true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        dashboardResponseCall = apiService.dashboard(Prefs.getString(APPConst.TOKEN,""));


        dashboardResponseCall.enqueue(new Callback<List<DashboardResponse>>() {
            @Override
            public void onResponse(Call<List<DashboardResponse>> call, Response<List<DashboardResponse>> response) {
                Log.d(TAG, "onResponse: "+new Gson().toJson(response.body()));
              dashboardResponse = response.body();
                Log.d(TAG, "onResponse:size "+dashboardResponse.size());

                mainProgramAdapter = new MainProgramAdapter(mycontext,dashboardResponse);
                home_main_program_recycle.setLayoutManager(getLinearLayoutManager());
                home_main_program_recycle.setAdapter(mainProgramAdapter);
                Loader.showLoad(mycontext,false);
            }

            @Override
            public void onFailure(Call<List<DashboardResponse>> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext,false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });



    }

    Call<CategoryResponse> categoryResponseCall;

    private void dashboard_category(View aView) {

        Loader.showLoad(mycontext,true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        categoryResponseCall = apiService.dashboard_catgory();


        categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                Log.d(TAG, "onResponse:categoryResponse "+new Gson().toJson(response.body()));
                categoryResponse = response.body();
                home_sub_program_view = aView.findViewById(R.id.fb_home_sub_program_view_pager);
                viewPagerAdapter = new ViewPageAdapter(getFragmentManager(), categoryResponse.getRecords().size(),categoryResponse);

                home_sub_program_tab.setupWithViewPager(home_sub_program_view);
                home_sub_program_view.setAdapter(viewPagerAdapter);
                Loader.showLoad(mycontext,false);
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext,false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    Call<LoaderResponse> loaderResponseCall;

    private void getLoader() {

        Loader.showLoad(mycontext,true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        loaderResponseCall = apiService.getLoader(Prefs.getString(APPConst.TOKEN,""));


        loaderResponseCall.enqueue(new Callback<LoaderResponse>() {
            @Override
            public void onResponse(Call<LoaderResponse> call, Response<LoaderResponse> response) {
                Log.d(TAG, "onResponse:categoryResponse "+new Gson().toJson(response.body()));
                loaderResponse = response.body();
                Prefs.putInt(APPConst.PBLUEPERCENTAGE, (int) loaderResponse.getBluePercentageValue());
                //Prefs.putInt(APPConst.PBLUEPERCENTAGE,50);
                Prefs.putInt(APPConst.PGREENPERCENTAGE,(int)loaderResponse.getGreenPercentageValue());

                Loader.showLoad(mycontext,false);
            }

            @Override
            public void onFailure(Call<LoaderResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext,false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
