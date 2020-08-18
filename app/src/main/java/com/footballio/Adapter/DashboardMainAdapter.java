package com.footballio.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.footballio.R;
import com.footballio.model.dashboard.ItemsItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardMainAdapter extends RecyclerView.Adapter<DashboardMainAdapter.ViewHolder> {
    public static final String TAG = DashboardMainAdapter.class.getSimpleName();
    Context contextWorkoutWarmAdapter;
    List<ItemsItem> items;

    public DashboardMainAdapter(Context mycontext, List<ItemsItem> items) {

        this.contextWorkoutWarmAdapter = mycontext;
        this.items = items;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_item_dash_main, viewGroup, false);
        return new ViewHolder(view, i) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.fitness.setText(items.get(i).getFitness());
        viewHolder.technique.setText(items.get(i).getTechnique());
        viewHolder.behaviour.setText(items.get(i).getBehaviour());
        viewHolder.body.setText(items.get(i).getBody());
        viewHolder.health.setText(items.get(i).getHealth());
        viewHolder.mental.setText(items.get(i).getMental());
        viewHolder.fbWorkoutViewCategory.setText(items.get(i).getProgramname());
        Glide.with(contextWorkoutWarmAdapter)
                .load(items.get(i).getBigphoto())
                .placeholder(R.color.colorTransparent)
                .error(R.color.colorTransparent)
                .into(viewHolder.fbWorkoutViewImage);
    }

  

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.fb_workout_view_image)
        ImageView fbWorkoutViewImage;
        @BindView(R.id.fb_workout_view_category)
        TextView fbWorkoutViewCategory;
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

        ViewHolder(View view, int i) {
            super(view);
            ButterKnife.bind(this,view);

        }
    }
}
