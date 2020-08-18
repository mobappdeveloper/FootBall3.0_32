package com.footballio.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.footballio.Activities.FBDashboardActivity;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Utils;
import com.footballio.model.dashboard.DashboardResponse;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainProgramAdapter extends RecyclerView.Adapter<MainProgramAdapter.ViewHolder> {
    public static final String TAG = MainProgramAdapter.class.getSimpleName();
    Context mycontext;
    List<DashboardResponse> dashboardResponse = new ArrayList<>();
    private DashboardMainAdapter dashmainProgramAdapter;


    public MainProgramAdapter(Context mycontext, List<DashboardResponse> dashboardResponse) {

        this.mycontext = mycontext;
        this.dashboardResponse = dashboardResponse;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_item_main_program, viewGroup, false);
        return new ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        if (dashboardResponse.get(i).getItems().size() > 0) {
            viewHolder.layout1.setVisibility(View.GONE);
            viewHolder.layout2.setVisibility(View.VISIBLE);
            viewHolder.txtDate1.setText(dashboardResponse.get(i).getWeek());
            dashmainProgramAdapter = new DashboardMainAdapter(mycontext,dashboardResponse.get(i).getItems());
            viewHolder.itemDashRecycleview.setLayoutManager(getLinearLayoutManager());
            viewHolder.itemDashRecycleview.setAdapter(dashmainProgramAdapter);
        } else {
            viewHolder.layout1.setVisibility(View.VISIBLE);
            viewHolder.layout2.setVisibility(View.GONE);
            viewHolder.txtDate.setText(dashboardResponse.get(i).getWeek());

            String s = Utils.RemoveCharactersBeforeUnderscore("-", dashboardResponse.get(i).getWeek());
            try {
                if (Utils.currentDateGreater(s)) {
                    viewHolder.klBtnLogin.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.klBtnLogin.setVisibility(View.GONE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        viewHolder.klBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mycontext, FBDashboardActivity.class);
                intent.putExtra(APPConst.DASHREDIRECT,"1");
                mycontext.startActivity(intent);
                ((Activity)mycontext).finish();
            }
        });



    }
    private RecyclerView.LayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(mycontext, LinearLayoutManager.VERTICAL, false);

    }

    @Override
    public int getItemCount() {
        return dashboardResponse.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_date)
        TextView txtDate;
        @BindView(R.id.txt_date1)
        TextView txtDate1;
        @BindView(R.id.store_product_image)
        ImageView storeProductImage;
        @BindView(R.id.store_places)
        TextView storePlaces;
        @BindView(R.id.kl_btn_login)
        Button klBtnLogin;
        @BindView(R.id.layout1)
        LinearLayout layout1;
        @BindView(R.id.fb_workout_view_image)
        ImageView fbWorkoutViewImage;
        @BindView(R.id.fb_workout_view_category)
        TextView fbWorkoutViewCategory;
        @BindView(R.id.layout2)
        LinearLayout layout2;
        @BindView(R.id.fitness)
        TextView fitness;
        @BindView(R.id.technique)
        TextView technique;
        @BindView(R.id.body)
        TextView body;
        @BindView(R.id.mental)
        TextView mental;
        @BindView(R.id.health)
        TextView health;
        @BindView(R.id.behaviour)
        TextView behaviour;
        @BindView(R.id.item_dash_recycleview)
        RecyclerView itemDashRecycleview;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
       /* public ViewHolder(View view, int i) {
            super(view);
            ButterKnife.bind(this,view);

        }*/
    }
}
