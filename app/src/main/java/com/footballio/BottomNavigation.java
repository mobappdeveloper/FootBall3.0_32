package com.footballio;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.footballio.Utils.Navigation;

import java.util.ArrayList;
import java.util.List;

public class BottomNavigation implements View.OnClickListener {

    public static final int MENU_BAR_1 = 0;
    public static final int MENU_BAR_2 = 1;
    public static final int MENU_BAR_3 = 2;
    public static final int MENU_BAR_4 = 3;
    public static final int MENU_BAR_5 = 4;

    private List<Navigation> mNavigationPageList = new ArrayList<>();

    private Context mContext;
    private AppCompatActivity mActivity;
    private BottomNavigationMenuClickListener mListener;

    private LinearLayout mLLBar1, mLLBar2, mLLBar3, mLLBar4, mLLBar5;
    private View mViewBar1, mViewBar2, mViewBar3, mViewBar4, mViewBar5;
    private AppCompatImageView mImageViewBar1, mImageViewBar2, mImageViewBar3, mImageViewBar4, mImageViewBar5;
    private AppCompatTextView mTextViewBar1, mTextViewBar2, mTextViewBar3, mTextViewBar4, mTextViewBar5;
    String workstart = "0";

    public BottomNavigation(Context mContext, List<Navigation> pages, BottomNavigationMenuClickListener listener, String workstart) {

        // initialize variables
        this.mContext = mContext;
        this.mActivity = (AppCompatActivity) mContext;
        this.mListener = listener;
        this.mNavigationPageList = pages;
        this.workstart = workstart;

        // getting reference to bar linear layout viewgroups
        this.mLLBar1 = (LinearLayout) mActivity.findViewById(R.id.linearLayoutBar1);
        this.mLLBar2 = (LinearLayout) mActivity.findViewById(R.id.linearLayoutBar2);
        this.mLLBar3 = (LinearLayout) mActivity.findViewById(R.id.linearLayoutBar3);
        this.mLLBar4 = (LinearLayout) mActivity.findViewById(R.id.linearLayoutBar4);
        this.mLLBar5 = (LinearLayout) mActivity.findViewById(R.id.linearLayoutBar5);

        // getting reference to bar upper highlight
        this.mViewBar1 = (View) mActivity.findViewById(R.id.viewBar1);
        this.mViewBar2 = (View) mActivity.findViewById(R.id.viewBar2);
        this.mViewBar3 = (View) mActivity.findViewById(R.id.viewBar3);
        this.mViewBar4 = (View) mActivity.findViewById(R.id.viewBar4);
        this.mViewBar5 = (View) mActivity.findViewById(R.id.viewBar5);

        // getting reference to bar icons
        this.mImageViewBar1 = (AppCompatImageView) mActivity.findViewById(R.id.imageViewBar1);
        this.mImageViewBar2 = (AppCompatImageView) mActivity.findViewById(R.id.imageViewBar2);
        this.mImageViewBar3 = (AppCompatImageView) mActivity.findViewById(R.id.imageViewBar3);
        this.mImageViewBar4 = (AppCompatImageView) mActivity.findViewById(R.id.imageViewBar4);
        this.mImageViewBar5 = (AppCompatImageView) mActivity.findViewById(R.id.imageViewBar5);

        // setting the icons
        this.mImageViewBar1.setImageDrawable(mNavigationPageList.get(0).getIcon());
        this.mImageViewBar2.setImageDrawable(mNavigationPageList.get(1).getIcon());
        this.mImageViewBar3.setImageDrawable(mNavigationPageList.get(2).getIcon());
        this.mImageViewBar4.setImageDrawable(mNavigationPageList.get(3).getIcon());
        this.mImageViewBar5.setImageDrawable(mNavigationPageList.get(4).getIcon());

        // getting reference to bar titles
        this.mTextViewBar1 = (AppCompatTextView) mActivity.findViewById(R.id.textViewBar1);
        this.mTextViewBar2 = (AppCompatTextView) mActivity.findViewById(R.id.textViewBar2);
        this.mTextViewBar3 = (AppCompatTextView) mActivity.findViewById(R.id.textViewBar3);
        this.mTextViewBar4 = (AppCompatTextView) mActivity.findViewById(R.id.textViewBar4);
        this.mTextViewBar5 = (AppCompatTextView) mActivity.findViewById(R.id.textViewBar5);

        // 2etting the titles
        this.mTextViewBar1.setText(mNavigationPageList.get(0).getTitle());
        this.mTextViewBar2.setText(mNavigationPageList.get(1).getTitle());
        this.mTextViewBar3.setText(mNavigationPageList.get(2).getTitle());
        this.mTextViewBar4.setText(mNavigationPageList.get(3).getTitle());
        this.mTextViewBar5.setText(mNavigationPageList.get(4).getTitle());

        // setting click listeners
        this.mLLBar1.setOnClickListener(this);
        this.mLLBar2.setOnClickListener(this);
        this.mLLBar3.setOnClickListener(this);
        this.mLLBar4.setOnClickListener(this);
        this.mLLBar5.setOnClickListener(this);
        if (workstart.equals("0")) {
            setView(this.mLLBar1);
        } else {
            setView(this.mLLBar3);
        }
    }


    @Override
    public void onClick(View view) {

        // setting clicked bar as highlighted view
        setView(view);

        // triggering click listeners
        if (view.getId() == R.id.linearLayoutBar1) {
            mListener.onClickedOnBottomNavigationMenu(MENU_BAR_1);
            return;
        } else if (view.getId() == R.id.linearLayoutBar2) {
            mListener.onClickedOnBottomNavigationMenu(MENU_BAR_2);
            return;
        } else if (view.getId() == R.id.linearLayoutBar3) {
            mListener.onClickedOnBottomNavigationMenu(MENU_BAR_3);
            return;
        } else if (view.getId() == R.id.linearLayoutBar4) {
            mListener.onClickedOnBottomNavigationMenu(MENU_BAR_4);
            return;
        } else if (view.getId() == R.id.linearLayoutBar5) {
            mListener.onClickedOnBottomNavigationMenu(MENU_BAR_5);
            return;
        } else {
            return;
        }

    }

    /**
     * sets the clicked view as selected, resets other views
     *
     * @param view clicked view
     */
    private void setView(View view) {

        // seting all highlight bar as invisible
        this.mViewBar1.setVisibility(View.INVISIBLE);
        this.mViewBar2.setVisibility(View.INVISIBLE);
        this.mViewBar3.setVisibility(View.INVISIBLE);
        this.mViewBar4.setVisibility(View.INVISIBLE);
        this.mViewBar5.setVisibility(View.INVISIBLE);

        // resetting colors of all icons
        this.mImageViewBar1.setColorFilter(ContextCompat.getColor(mContext, R.color.colorunSelected));
        this.mImageViewBar2.setColorFilter(ContextCompat.getColor(mContext, R.color.colorunSelected));
        this.mImageViewBar3.setColorFilter(ContextCompat.getColor(mContext, R.color.colorunSelected));
        this.mImageViewBar4.setColorFilter(ContextCompat.getColor(mContext, R.color.colorunSelected));
        this.mImageViewBar5.setColorFilter(ContextCompat.getColor(mContext, R.color.colorunSelected));

        // resetting colors of all titles
        this.mTextViewBar1.setTextColor(ContextCompat.getColor(mContext, R.color.colorunSelected));
        this.mTextViewBar2.setTextColor(ContextCompat.getColor(mContext, R.color.colorunSelected));
        this.mTextViewBar3.setTextColor(ContextCompat.getColor(mContext, R.color.colorunSelected));
        this.mTextViewBar4.setTextColor(ContextCompat.getColor(mContext, R.color.colorunSelected));
        this.mTextViewBar5.setTextColor(ContextCompat.getColor(mContext, R.color.colorunSelected));

        // selectively colorizing the marked view
        if (view.getId() == R.id.linearLayoutBar1) {
            this.mViewBar1.setVisibility(View.VISIBLE);
            this.mImageViewBar1.setColorFilter(ContextCompat.getColor(mContext, R.color.colorMajor));
            this.mTextViewBar1.setTextColor(ContextCompat.getColor(mContext, R.color.colorMajor));
            return;
        } else if (view.getId() == R.id.linearLayoutBar2) {
            this.mViewBar2.setVisibility(View.VISIBLE);
            this.mImageViewBar2.setColorFilter(ContextCompat.getColor(mContext, R.color.colorMajor));
            this.mTextViewBar2.setTextColor(ContextCompat.getColor(mContext, R.color.colorMajor));
            return;
        } else if (view.getId() == R.id.linearLayoutBar3) {
            this.mViewBar3.setVisibility(View.VISIBLE);
            this.mImageViewBar3.setColorFilter(ContextCompat.getColor(mContext, R.color.colorMajor));
            this.mTextViewBar3.setTextColor(ContextCompat.getColor(mContext, R.color.colorMajor));
            return;
        } else if (view.getId() == R.id.linearLayoutBar4) {
            this.mViewBar4.setVisibility(View.VISIBLE);
            this.mImageViewBar4.setColorFilter(ContextCompat.getColor(mContext, R.color.colorMajor));
            this.mTextViewBar4.setTextColor(ContextCompat.getColor(mContext, R.color.colorMajor));
            return;
        } else if (view.getId() == R.id.linearLayoutBar5) {
            this.mViewBar5.setVisibility(View.VISIBLE);
            this.mImageViewBar5.setColorFilter(ContextCompat.getColor(mContext, R.color.colorMajor));
            this.mTextViewBar5.setTextColor(ContextCompat.getColor(mContext, R.color.colorMajor));
            return;
        } else {
            return;
        }

    }

    public interface BottomNavigationMenuClickListener {
        void onClickedOnBottomNavigationMenu(int menuType);
    }
}
