package com.footballio.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.footballio.Activities.FBWorkoutVideoViewActivity;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.model.workout.type.WarmUpItem;
import com.footballio.model.workout.type.WorkoutTypeViewResponse;

import java.util.ArrayList;
import java.util.List;

public class WorkoutWarmAdapter extends RecyclerView.Adapter<WorkoutWarmAdapter.ViewHolder> {
    public static final String TAG = WorkoutWarmAdapter.class.getSimpleName();
    Context contextWorkoutWarmAdapter;
    List<WarmUpItem> workoutTypeViewResponse;

    public WorkoutWarmAdapter(Context mycontext, List<WarmUpItem> workoutTypeViewResponse) {

        this.contextWorkoutWarmAdapter = mycontext;
        this.workoutTypeViewResponse = workoutTypeViewResponse;

        Log.d(TAG, "WorkoutWarmAdapter: "+workoutTypeViewResponse.size());
    }

    @NonNull
    @Override
    public WorkoutWarmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_item_warmup, viewGroup, false);
        return new WorkoutWarmAdapter.ViewHolder(view, i) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutWarmAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.workout_warmup_name.setText(workoutTypeViewResponse.get(i).getTitle());
        viewHolder.workout_warmup_description.setText(workoutTypeViewResponse.get(i).getDescription());
        Glide.with(contextWorkoutWarmAdapter)
                .load(workoutTypeViewResponse.get(i).getSticker())
                .placeholder(R.drawable.ic_grid)
                .error(R.drawable.ic_grid)
                .into(viewHolder.workout_warmup_img);

        viewHolder.workout_warmup_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: videourl"+workoutTypeViewResponse.get(i).getVideo());
                Intent intent = new Intent(contextWorkoutWarmAdapter, FBWorkoutVideoViewActivity.class);
                intent.putExtra(APPConst.PVIDEOID,workoutTypeViewResponse.get(i).getVideo());
                contextWorkoutWarmAdapter.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return workoutTypeViewResponse.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView workout_warmup_name;
        TextView workout_warmup_description;
        ImageView workout_warmup_img;
        LinearLayout workout_warmup_layout;


        ViewHolder(View view, int i) {
            super(view);


            workout_warmup_name = itemView.findViewById(R.id.workout_warmup_name);
            workout_warmup_description = itemView.findViewById(R.id.workout_warmup_description);
            workout_warmup_img = itemView.findViewById(R.id.vendor_status_image);
            workout_warmup_layout = itemView.findViewById(R.id.workout_warmup_layout);




        }
    }
}
