package com.footballio.view.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.footballio.R;
import com.footballio.view.ui.CategoryDetails;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DasboardAdapter extends RecyclerView.Adapter<DasboardAdapter.ViewHolder> {
    private List<String> list = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txt_name, txt_desription, txt_time;
        private ImageView imageView_main, img_checked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_desription = itemView.findViewById(R.id.txt_description);
            txt_time = itemView.findViewById(R.id.txt_interval);
            imageView_main=itemView.findViewById(R.id.img_checked);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), CategoryDetails.class);
            v.getContext().startActivity(intent);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_name.setText(list.get(position));
        ImageView imageView=holder.imageView_main;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
