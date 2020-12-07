package com.footballio.view.adapter;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.footballio.R;
import com.footballio.Utils.AppConst;
import com.footballio.Utils.Utils;
import com.footballio.model.login.dashboard.LibraryProgram;
import com.footballio.model.dashboard.Program;
import com.footballio.view.callback.OnBottomReachedListener;
import com.footballio.view.ui.WorkoutDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dagger.hilt.android.scopes.ActivityRetainedScoped;


@ActivityRetainedScoped
public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {
    private List<Program> programList = new ArrayList<>();
    private List<LibraryProgram> libraryProgramList = new ArrayList<>();
    private int viewType = 0;
    OnBottomReachedListener onBottomReachedListener;
    @Inject
    public WorkoutAdapter() {
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_workoutName, txt_desription, txt_time;
        private ImageView imageView_workout, img_checked;

        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            txt_workoutName = itemView.findViewById(R.id.txt_name);
            txt_desription = itemView.findViewById(R.id.txt_description);
            txt_time = itemView.findViewById(R.id.txt_interval);
            imageView_workout = itemView.findViewById(R.id.img_category);
            img_checked = itemView.findViewById(R.id.img_checked);
        }
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {

        this.onBottomReachedListener = onBottomReachedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout, parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (viewType == 0) {
            holder.txt_workoutName.setText(programList.get(position).getProgramname());
            holder.itemView.setTag(position + "");
            holder.txt_desription.setText(programList.get(position).getTopicCategoryName());
            holder.txt_time.setText(programList.get(position).getTime());
            Glide.with(holder.imageView_workout)
                    .load(programList.get(position).getBigphoto())
                    .placeholder(R.drawable.group_72683)
                    .error(R.drawable.group_72683)
                    .fallback(R.drawable.group_72683)
                    .into(holder.imageView_workout);
            int colorPosition = getColorPosition(position);
            holder.txt_time.setBackgroundResource(shapeGradient[colorPosition]);
            holder.txt_desription.setBackgroundResource(shapeGradient[colorPosition]);
            if (programList.get(position).getCompletedStatus().equals("Y")) {
                int v = programList.get(position).getCompletedStatus().equals("Y") ? View.VISIBLE : View.INVISIBLE;
                holder.img_checked.setVisibility(v);
                getCheckedDrawable(colorPosition, holder.img_checked);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.img_checked.setElevation((float) 10.0);
                } else {
                    Utils.showToast("Completed Workout!", holder.imageView_workout.getContext());
                }
            } else {
                holder.img_checked.setVisibility(View.GONE);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tag = Integer.parseInt(v.getTag().toString());
                        if (programList.get(tag).getCompletedStatus().equals("N")) {
                            Intent intent = new Intent(v.getContext(), WorkoutDetailsActivity.class);
                            intent.putExtra(AppConst.PID, programList.get(position).getPid());
                            v.getContext().startActivity(intent);
                        }

                    }
                });
            }
            if (position == programList.size() - 1) {
                onBottomReachedListener.onBottomReached(position);

            } else {
                onBottomReachedListener.onFromBottomToTop(position);
            }


        } else {
            try {
                holder.txt_workoutName.setText(libraryProgramList.get(position).getProgramName());
                holder.txt_desription.setText(libraryProgramList.get(position).getCategoryName());
                holder.txt_time.setText(libraryProgramList.get(position).getTime());
                Glide.with(holder.imageView_workout)
                        .load(libraryProgramList.get(position).getImage())
                        .placeholder(R.drawable.group_72683)
                        .error(R.drawable.group_72683)
                        .fallback(R.drawable.group_72683)
                        .into(holder.imageView_workout);
            } catch (Exception e) {
                String mm = e.getMessage().toString();
            }

        }
    }

    @Override
    public int getItemCount() {
        if (viewType == 0) {
            return programList.size();
        } else {
            return libraryProgramList.size();
        }

    }

    public void setProgramList(List<Program> list) {
        programList = list;
        notifyDataSetChanged();
    }

    public void setLibraryProgramList(List<LibraryProgram> list) {
        libraryProgramList = list;
        notifyDataSetChanged();

    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    private int getColorPosition(int position) {
        if (position > 5) {
            return getColorPosition(position / 2);
        } else {
            return position;
        }

    }

    private Integer[] shapeGradient = new Integer[]
            {
                    R.drawable.custom_txt_tarqueue,
                    R.drawable.custom_txt_yellow,
                    R.drawable.custom_txt_red,
                    R.drawable.custom_txt_blue,
                    R.drawable.custom_txt_brown,
                    R.drawable.custom_txt_violet
            };


    private void getCheckedDrawable(int d, View view) {
        switch (d) {
            case 0:
                view.setBackgroundResource(R.drawable.ic_checkmark_circle_tarqueue);
                break;
            case 1:
                view.setBackgroundResource(R.drawable.ic_checkmark_circle_yellow);
                break;
            case 2:
                view.setBackgroundResource(R.drawable.ic_checkmark_circle_red);
                break;
            case 3:
                view.setBackgroundResource(R.drawable.ic_checkmark_circle_blue);
                break;
            case 4:
                view.setBackgroundResource(R.drawable.ic_checkmark_circle_brown);
                break;
            case 5:
                view.setBackgroundResource(R.drawable.ic_checkmark_circle_violet);
                break;

            default:
                view.setBackgroundResource(R.drawable.ic_checkmark_circle_tarqueue);
        }
    }


}
