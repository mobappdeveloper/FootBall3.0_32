package com.footballio.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.footballio.Activities.FBBlogActivity;
import com.footballio.R;
import com.footballio.Utils.APPConst;

public class TrendViewAdapter extends RecyclerView.Adapter<TrendViewAdapter.ViewHolder> {
    public static final String TAG = TrendViewAdapter.class.getSimpleName();
    Context contextTrendViewAdapter;


//  public HelpListAdapter(Context contextHelpListAdapter) {
//    this.contextHelpListAdapter = contextHelpListAdapter;


    public TrendViewAdapter(Context mycontext) {

        this.contextTrendViewAdapter = mycontext;
    }

    @NonNull
    @Override
    public TrendViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_item_comm_trend_view, viewGroup, false);
        return new TrendViewAdapter.ViewHolder(view, i) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull TrendViewAdapter.ViewHolder viewHolder, final int i) {


    }


    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        Button trend_card;


        ViewHolder(View view, int i) {
            super(view);


            trend_card = itemView.findViewById(R.id.btn_trend_full_view);


            trend_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(contextTrendViewAdapter, FBBlogActivity.class);

                    contextTrendViewAdapter.startActivity(intent);
                }
            });


        }
    }
}
