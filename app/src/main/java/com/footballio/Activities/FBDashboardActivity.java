package com.footballio.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.footballio.BottomNavigationView;
import com.footballio.Fragment.ChallengeFragment;
import com.footballio.Fragment.CommunityFragment;
import com.footballio.Fragment.HomeFragment;
import com.footballio.Fragment.ProfileFragment;
import com.footballio.Fragment.WorkoutFragment;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Navigation;

import java.util.ArrayList;
import java.util.List;



public class FBDashboardActivity extends BottomNavigationView implements HomeFragment.OnFragmentInteractionListener, CommunityFragment.OnFragmentInteractionListener,WorkoutFragment.OnFragmentInteractionListener,
        ChallengeFragment.OnFragmentInteractionListener,ProfileFragment.OnFragmentInteractionListener {

    private static final String TAG ="FBDashboardActivity" ;
    private String workstart="0";
    private String dashclick = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Navigation page1 = new Navigation("HOME", ContextCompat.getDrawable(this, R.drawable.ic_home), HomeFragment.newInstance());
        Navigation page2 = new Navigation("COMMUNITY", ContextCompat.getDrawable(this, R.drawable.ic_community), CommunityFragment.newInstance());
        Navigation page3 = new Navigation("WORKOUT", ContextCompat.getDrawable(this, R.drawable.ic_text), WorkoutFragment.newInstance());
        Navigation page4 = new Navigation("CHALLENGE", ContextCompat.getDrawable(this, R.drawable.ic_challenge), ChallengeFragment.newInstance());
        Navigation page5 = new Navigation("PROFIL", ContextCompat.getDrawable(this, R.drawable.ic_profil), ProfileFragment.newInstance());

        List<Navigation> navigationPages = new ArrayList<>();
        navigationPages.add(page1);
        navigationPages.add(page2);
        navigationPages.add(page3);
        navigationPages.add(page4);
        navigationPages.add(page5);

        getBundle();
        Log.d(TAG, "setupBottomBarHolderActivity:getBundle before "+workstart);
        super.setupBottomBarHolderActivity(navigationPages,workstart,dashclick);
        dashclick="0";
    }
    private void getBundle() {
        Intent intent = getIntent();
        if (intent.getStringExtra(APPConst.PWORKSTATUS) != null) {
            workstart = intent.getStringExtra(APPConst.PWORKSTATUS);
            Log.d(TAG, "setupBottomBarHolderActivity:getBundle after "+workstart);
        }
        if (intent.getStringExtra(APPConst.DASHREDIRECT) != null) {
            dashclick = intent.getStringExtra(APPConst.DASHREDIRECT);
            Log.d(TAG, "setupBottomBarHolderActivity:getBundle after dashclick"+dashclick);
        }
    }

}
