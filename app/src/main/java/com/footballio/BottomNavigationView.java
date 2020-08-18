package com.footballio;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.footballio.Utils.APPConst;
import com.footballio.Utils.Navigation;

import java.util.ArrayList;
import java.util.List;

public class BottomNavigationView extends AppCompatActivity implements BottomNavigation.BottomNavigationMenuClickListener {

    private static final String TAG = "BottomNavigationView";
    // helper class for handling UI and click events of bottom-nav-bar
    private BottomNavigation mBottomNav;

    // list of Navigation pages to be shown
    private List<Navigation> mNavigationPageList = new ArrayList<>();
    String workstart = "0";
    String dashclick = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
    }

    /**
     * initializes the BottomBarHolderActivity with sent list of Navigation
     * @param pages
     * @param workstart
     * @param dashclick
     */
    public void setupBottomBarHolderActivity(List<Navigation> pages, String workstart, String dashclick) {

        // throw error if pages does not have 4 elements
        this.workstart = workstart;
        this.dashclick = dashclick;
        Log.d(TAG, "setupBottomBarHolderActivity: "+workstart);
        if (pages.size() != 5) {
            throw new RuntimeException("List of Navigation must contain 4 members.");
        } else {
            mNavigationPageList = pages;
            mBottomNav = new BottomNavigation (this,pages,this,workstart);

            setupFragments(workstart);
        }

    }

    /**
     * sets up the fragments with initial view
     * @param workstart
     */
    private void setupFragments(String workstart) {
        Bundle args = new Bundle();
        args.putString(APPConst.DASHREDIRECT, dashclick);
        Fragment fragment = null;

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (workstart.equals("0")) {
            fragment = mNavigationPageList.get(0).getFragment();

            Log.d(TAG, "setupBottomBarHolderActivity:0 "+workstart);
        }else{
            fragment = mNavigationPageList.get(2).getFragment();

            Log.d(TAG, "setupBottomBarHolderActivity:2 "+workstart);
        }
        if (fragment != null) {
            fragment.setArguments(args);
            fragmentTransaction.replace(R.id.frame_Layout, fragment);
            fragmentTransaction.commit();

        }
    }

    /**
     * handling onclick events of bar items
     * @param menuType
     */
    @Override
    public void onClickedOnBottomNavigationMenu(int menuType) {

        // finding the selected fragment
        Bundle args = new Bundle();
        args.putString(APPConst.DASHREDIRECT, "0");
        Log.d(TAG, "onClickedOnBottomNavigationMenu: home"+dashclick);
        Fragment fragment = null;
        switch (menuType) {
            case BottomNavigation.MENU_BAR_1:
                fragment = mNavigationPageList.get(0).getFragment();


                break;
            case BottomNavigation.MENU_BAR_2:
                fragment = mNavigationPageList.get(1).getFragment();
                break;
            case BottomNavigation.MENU_BAR_3:
                fragment = mNavigationPageList.get(2).getFragment();
                break;
            case BottomNavigation.MENU_BAR_4:
                fragment = mNavigationPageList.get(3).getFragment();
                break;
            case BottomNavigation.MENU_BAR_5:
                fragment = mNavigationPageList.get(4).getFragment();
                break;
        }
        fragment.setArguments(args);
        // replacing fragment with the current one
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_Layout, fragment);
            fragmentTransaction.commit();
        }

    }
}
