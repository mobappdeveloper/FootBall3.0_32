package com.footballio.Utils;

import android.graphics.drawable.Drawable;

import androidx.fragment.app.Fragment;

public class Navigation {
    private String title;
    private Drawable icon;
    private Fragment fragment;

    public Navigation(String title, Drawable icon, Fragment fragment) {
        this.title = title;
        this.icon = icon;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public Fragment getFragment() {
        return fragment;
    }
}
