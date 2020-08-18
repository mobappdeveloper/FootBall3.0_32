package com.footballio.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.footballio.Adapter.DashboardCategoryAdapter;
import com.footballio.Adapter.DashboardCategoryAllAdapter;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.model.dashboard.allcategory.AllCategoryResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DashboardFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    @BindView(R.id.article_category)
    RecyclerView articleCategory;

    private int sectionNumber;
    private AllCategoryResponse allCategoryResponse;
    private DashboardCategoryAdapter mainProgramAdapter;

String cid="1";
    private Context mycontext;

    public DashboardFragment(String id) {
        // Required empty public constructor
        cid = id;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sectionNumber = getArguments() != null ? getArguments().getInt(ARG_SECTION_NUMBER) : 1;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        /*TextView textView = view.findViewById(R.id.txtTabItemNumber);
        textView.setText("TAB " + sectionNumber);*/
        mycontext=getActivity();
        ButterKnife.bind(this, view);
        dashboardList(cid);
        return view;
    }

    public static DashboardFragment newInstance(int sectionNumber, String id) {
        DashboardFragment fragment = new DashboardFragment(id);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    Call<AllCategoryResponse> allCategoryResponseCall;

    private void dashboardList(String cid) {

        Loader.showLoad(mycontext,true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        allCategoryResponseCall = apiService.dashboard_allCatgoryDetails(cid, Prefs.getString(APPConst.TOKEN, ""));


        allCategoryResponseCall.enqueue(new Callback<AllCategoryResponse>() {
            @Override
            public void onResponse(Call<AllCategoryResponse> call, Response<AllCategoryResponse> response) {
                Log.d(TAG, "onResponse: allCategoryResponse" + new Gson().toJson(response.body()));
                allCategoryResponse = response.body();
                //  Log.d(TAG, "onResponse:size "+allCategoryResponse.size());
                if (allCategoryResponse.getPrograms()!=null) {
                    mainProgramAdapter = new DashboardCategoryAdapter(getActivity(), allCategoryResponse);
                    articleCategory.setLayoutManager(getLinearLayoutManager());
                    articleCategory.setAdapter(mainProgramAdapter);
                }else{
                  //  dashboardList(cid);
                }

              //  txtArticleTitle.setText();
                Loader.showLoad(mycontext,false);
            }

            @Override
            public void onFailure(Call<AllCategoryResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext,false);
                Toast.makeText(getActivity(), "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private RecyclerView.LayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

    }
}