package com.footballio.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.footballio.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OlympicAdapter extends RecyclerView.Adapter<OlympicAdapter.OlympicViewHolder> {

    private List<String> userList = new ArrayList<>();
    private Integer[] s = new Integer[]{R.drawable.ic_progress_violet, R.drawable.ic_progress_tarqueue, R.drawable.ic_progress_blue, R.drawable.ic_progress_brown, R.drawable.ic_progress_red, R.drawable.ic_progress_violet, R.drawable.ic_progress_violet};

    public static class OlympicViewHolder extends RecyclerView.ViewHolder {
        private ImageView progress;
        private TextView txt_name,txt_description;
        public OlympicViewHolder(@NonNull View itemView) {
            super(itemView);
            progress = itemView.findViewById(R.id.img_library_progress);
            txt_name=itemView.findViewById(R.id.txt_library_name);
            txt_description=itemView.findViewById(R.id.txt_library_description);
        }
    }

    @NonNull
    @Override
    public OlympicAdapter.OlympicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_olympic, parent, false);
        return new OlympicAdapter.OlympicViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull OlympicAdapter.OlympicViewHolder holder, int position) {
        holder.progress.setImageResource(s[position]);
        //txt_name.

    }

    public void setTrainingList(List<String> trainingList) {
        this.userList = trainingList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


}
