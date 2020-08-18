package com.footballio.Fragment;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.footballio.Activities.FBSettingsActivity;
import com.footballio.Adapter.ProfileAwardsAdapter;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.model.profile.ProfileViewResponse;
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

public class ProfileFragment extends Fragment {

    @BindView(R.id.profile_settings)
    ImageView profileSettings;
    @BindView(R.id.vendor_ads_image)
    ImageView vendorAdsImage;
    @BindView(R.id.vendor_ads_product_name)
    TextView vendorAdsProductName;
    @BindView(R.id.vendor_ads_product_date)
    TextView vendorAdsProductDate;
    @BindView(R.id.vendor_ads_seen)
    TextView vendorAdsSeen;
    @BindView(R.id.gridView1)
    RecyclerView gridView1;
    @BindView(R.id.txt_weight)
    TextView txtWeight;
    @BindView(R.id.txt_location)
    TextView txtLocation;
    @BindView(R.id.blue_points)
    TextView bluePoints;
    @BindView(R.id.current_month)
    TextView currentMonth;
    @BindView(R.id.green_points)
    TextView greenPoints;


    private OnFragmentInteractionListener listener;
    private Context mycontext;
    private RecyclerView Profile_gridView;
    private TextView empty;
    private ImageView profile_settings;
    ArrayList<ClipData.Item> gridArray = new ArrayList<ClipData.Item>();
    private ProfileAwardsAdapter profileAdapter;
    private ProfileViewResponse profileViewResponse;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View aView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, aView);
        initUi(aView);
        // Inflate the layout for this fragment
        //  setHasOptionsMenu(true);

        return aView;
    }

    private void initUi(View aView) {
        mycontext = getActivity();


        vendorAdsProductName.setText(Prefs.getString(APPConst.PROFILEUSERNAME, ""));

        gridArray.add(new ClipData.Item("The Roulette"));
        gridArray.add(new ClipData.Item("The Fox"));
        gridArray.add(new ClipData.Item("Hot Winger"));
        gridArray.add(new ClipData.Item("Samba"));


        Profile_gridView = aView.findViewById(R.id.gridView1);
        profile_settings = aView.findViewById(R.id.profile_settings);

        profileAdapter = new ProfileAwardsAdapter(mycontext);
        Profile_gridView.setLayoutManager(new GridLayoutManager(mycontext, 3));
        Profile_gridView.setAdapter(profileAdapter);

        profile_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FBSettingsActivity.class);
                startActivity(intent);
            }
        });

        dashboard_category();
    }

    private RecyclerView.LayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

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

    public interface OnFragmentInteractionListener {
    }


    Call<ProfileViewResponse> profileViewResponseCall;

    private void dashboard_category() {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        profileViewResponseCall = apiService.getProfileView(Prefs.getString(APPConst.TOKEN, ""));


        profileViewResponseCall.enqueue(new Callback<ProfileViewResponse>() {
            @Override
            public void onResponse(Call<ProfileViewResponse> call, Response<ProfileViewResponse> response) {
                Log.d(TAG, "onResponse:ProfileViewResponse " + new Gson().toJson(response.body()));
                profileViewResponse = response.body();

                vendorAdsProductName.setText(profileViewResponse.getFirstName() + " " + profileViewResponse.getLastName());
                vendorAdsProductDate.setText(profileViewResponse.getPosition() + ", " + profileViewResponse.getClub());
                vendorAdsSeen.setText(profileViewResponse.getDateofBirth());
                txtWeight.setText(profileViewResponse.getHeight() + " M" + ", " + profileViewResponse.getSize() + " kg");
                txtLocation.setText(profileViewResponse.getNationality());
                bluePoints.setText(profileViewResponse.getCoins());
                greenPoints.setText(profileViewResponse.getPoints());
                currentMonth.setText(profileViewResponse.getCurrentMonth());
                Glide.with(mycontext)
                        .load(profileViewResponse.getPhoto())
                        .placeholder(R.color.colorTransparent)
                        .error(R.color.colorTransparent)
                        .into(vendorAdsImage);

                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<ProfileViewResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
