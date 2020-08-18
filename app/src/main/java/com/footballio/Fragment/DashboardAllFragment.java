package com.footballio.Fragment;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.footballio.Adapter.DashboardCategoryAllAdapter;
import com.footballio.Adapter.ProfileAdapter;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.model.dashboard.allcategory.AllCategoryResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DashboardAllFragment extends Fragment {


    @BindView(R.id.gridView1)
    RecyclerView gridView1;
    private ProfileFragment.OnFragmentInteractionListener listener;
    private Context mycontext;

    ArrayList<ClipData.Item> gridArray = new ArrayList<ClipData.Item>();
    private ProfileAdapter profileAdapter;
    private AllCategoryResponse allCategoryResponse;
    private DashboardCategoryAllAdapter mainProgramAdapter;
    static String cid = "0";

    public static DashboardAllFragment newInstance(String id) {
       cid = id;
        return new DashboardAllFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View aView = inflater.inflate(R.layout.fragment_dashboardall, container, false);
        initUi(aView);
        // Inflate the layout for this fragment
        //  setHasOptionsMenu(true);
        ButterKnife.bind(this,aView);
        return aView;
    }

    private void initUi(View aView) {
        mycontext = getActivity();

        dashboardList(cid);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProfileFragment.OnFragmentInteractionListener) {
            listener = (ProfileFragment.OnFragmentInteractionListener) context;
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
                mainProgramAdapter = new DashboardCategoryAllAdapter(mycontext, allCategoryResponse);
                gridView1.setLayoutManager(new GridLayoutManager(mycontext, 3));
                gridView1.setAdapter(mainProgramAdapter);
                Loader.showLoad(mycontext,false);
            }

            @Override
            public void onFailure(Call<AllCategoryResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext,false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
