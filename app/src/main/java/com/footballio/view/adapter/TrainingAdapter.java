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

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.TraininigViewHolder> {
    private List<String> userList = new ArrayList<>();

    public class TraininigViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        private TextView textView_name;
        private ImageView imageView_training, imageView_check;

        public TraininigViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.txt_training_name);
            imageView_training = itemView.findViewById(R.id.img_training_pic);
            imageView_check = itemView.findViewById(R.id.img_training_check);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            ImageView imageView= v.findViewById(R.id.img_training_check);
            imageView.setImageResource(R.drawable.ic_checkmark_circle_tarqueue);
        }
    }

    @NonNull
    @Override
    public TraininigViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training, parent, false);
        return new TraininigViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TraininigViewHolder holder, int position) {

        //User user = userList.get(position);
        //holder.imageView_check.setImageBitmap();
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
