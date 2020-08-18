package com.footballio.Adapter;

import android.os.Parcelable;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.footballio.Fragment.DashboardAllFragment;
import com.footballio.Fragment.DashboardFragment;
import com.footballio.model.dashboard.category.CategoryResponse;

import java.util.ArrayList;
import java.util.List;

public class ViewPageAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "ViewPageAdapter";
    CategoryResponse categoryResponse;
    FragmentManager fm;
    int noOfItems;
    public ViewPageAdapter(FragmentManager fm, int noOfItems, CategoryResponse categoryResponse) {
        super(fm);
        this.fm = fm;
        this.noOfItems = noOfItems;
        this.categoryResponse = categoryResponse;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem: po"+position);
        if (position==0){
            Log.d(TAG, "getItem: DashboardAllFragment"+position);
            return DashboardAllFragment.newInstance(categoryResponse.getRecords().get(position).getId());

        }else {
            Log.d(TAG, "getItem: DashboardFragment"+position);
            return DashboardFragment.newInstance(position + 1,categoryResponse.getRecords().get(position).getId());
        }

    }

    @Override
    public int getCount() {
        return noOfItems;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Log.d(TAG, "getPageTitle: "+position);
        Log.d(TAG, "getPageTitle: "+categoryResponse.getRecords().get(position).getName());
        return categoryResponse.getRecords().get(position).getName();
    }

    // We don't keep states
    @Override
    public Parcelable saveState() {
        return null;
    }
    // We don't keep states
    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }
}
