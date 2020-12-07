package com.footballio.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.footballio.R;
import com.footballio.repository.AppSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserAccountAdapter extends ArrayAdapter<String> {
    private final Context context;
    private String[] values;

    public UserAccountAdapter(@NonNull Context context, int resource, String[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.content_user_account, parent, false);
        TextView txt_label = (TextView) rowView.findViewById(R.id.txt_title);
        TextView txt_value = (TextView) rowView.findViewById(R.id.txt_title_value);
        txt_label.setText(values[position]);
        txt_value.setText(AppSession.getStringValue(context, values[position]));
        return rowView;


    }
}
