package com.footballio.view.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.footballio.R;
import com.footballio.Utils.AppConst;
import com.footballio.model.login.dashboard.Philosophy;
import com.footballio.view.ui.LibraryDetailActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.scopes.ActivityRetainedScoped;

@ActivityRetainedScoped
public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {
    private List<Philosophy> userList = new ArrayList<>();
    private Integer[] s = new Integer[]{R.drawable.ic_progress_tarqueue, R.drawable.ic_progress_yellow, R.drawable.ic_progress_red, R.drawable.ic_progress_blue, R.drawable.ic_progress_brown, R.drawable.ic_progress_violet};
    private Integer[] shadow = new Integer[]{R.drawable.tarqueue_shadow, R.drawable.yellow_shadow, R.drawable.red_shadow, R.drawable.blue_shadow, R.drawable.brown_shadow, R.drawable.violet_shadow};
    private int SpareTime=0;

    @Inject
    public LibraryAdapter() {
    }

    // private String[] shadow = new String[]{"#29F2D9","#FEB92B","#E8505B","#1FBAFD","#FD5E1F","#B758FC"};

    public static class LibraryViewHolder extends RecyclerView.ViewHolder {
        private ImageView progress, progress_shadow;
        private TextView txt_name, txt_description;

        public LibraryViewHolder(@NonNull View itemView) {
            super(itemView);
            progress = itemView.findViewById(R.id.img_library_progress);
            progress_shadow = itemView.findViewById(R.id.img_library_progress_shadow);
            txt_name = itemView.findViewById(R.id.txt_library_name);
            txt_description = itemView.findViewById(R.id.txt_library_description);
        }


    }

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_olympic, parent, false);
        return new LibraryViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, int position) {
        holder.progress.setImageResource(s[position]);
        holder.progress_shadow.setImageResource(shadow[position]);
        holder.txt_name.setText(userList.get(position).getCategoryName());
        holder.txt_description.setText(userList.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), LibraryDetailActivity.class).putExtra(AppConst.CN, userList.get(position).getCategoryName()).putExtra(AppConst.SP,SpareTime));
            }
        });

    }

    public void setTrainingList(List<Philosophy> trainingList) {
        this.userList = trainingList;
        notifyDataSetChanged();
    }
    public void setSpareTime(int spareTime) {
        this.SpareTime=spareTime;
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


}
