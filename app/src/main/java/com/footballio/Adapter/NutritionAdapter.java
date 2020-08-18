package com.footballio.Adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class NutritionAdapter extends ArrayAdapter<ClipData.Item> {
    public static final String TAG=NutritionAdapter.class.getSimpleName();
    Context context;

    int layoutResourceId;
    ArrayList<ClipData.Item> data=new ArrayList<ClipData.Item>();

    public NutritionAdapter(Context contextAdminDashboardAdapter, int layoutResourceId,
                          ArrayList<ClipData.Item> data){
        super(contextAdminDashboardAdapter,layoutResourceId,data);
        this.layoutResourceId=layoutResourceId;
        this.context=contextAdminDashboardAdapter;
        this.data=data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        NutritionAdapter.RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new NutritionAdapter.RecordHolder();


            row.setTag(holder);
        } else {
            holder = (NutritionAdapter.RecordHolder) row.getTag();
        }

        ClipData.Item item = data.get(position);

        return row;
    }

    public static class RecordHolder {




    }
}
