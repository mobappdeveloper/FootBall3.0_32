package com.footballio.Adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.footballio.Activities.FBTrophyFullViewActivity;
import com.footballio.R;

import java.util.ArrayList;

public class TrophyAdapter extends ArrayAdapter<ClipData.Item> {
    public static final String TAG=TrophyAdapter.class.getSimpleName();
    Context context;

    int layoutResourceId;
    ArrayList<ClipData.Item> data=new ArrayList<ClipData.Item>();

    public TrophyAdapter(Context contextAdminDashboardAdapter, int layoutResourceId,
                            ArrayList<ClipData.Item> data){
        super(contextAdminDashboardAdapter,layoutResourceId,data);
        this.layoutResourceId=layoutResourceId;
        this.context=contextAdminDashboardAdapter;
        this.data=data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TrophyAdapter.RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new TrophyAdapter.RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.trophy_name);
            holder. image= (ImageView) row.findViewById(R.id.trophy_img);

            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FBTrophyFullViewActivity.class);
                    context.startActivity(intent);
                }
            });

            row.setTag(holder);
        } else {
            holder = (TrophyAdapter.RecordHolder) row.getTag();
        }

        ClipData.Item item = data.get(position);
        holder.txtTitle.setText(item.getText());
        return row;
    }

    public static class RecordHolder {
        TextView txtTitle;
        ImageView image;




    }
}
