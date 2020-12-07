package com.footballio.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.footballio.R;
import com.footballio.model.dashboard.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class WorkoutDetailsAdapter extends RecyclerView.Adapter<WorkoutDetailsAdapter.ViewHolder> {
    private HashMap<Integer, List<Session>> workoutList = new HashMap<>();
    private List<String> keyList = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView aSwitch, img_category;
        private LinearLayout listView, listView_Parent;
        private TextView textView_title, textView_subtitle, txt_categoryName, txt_categoryDesc;
        private View divider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            aSwitch = itemView.findViewById(R.id.img_switch);
            textView_title = itemView.findViewById(R.id.listTitle);
            //textView_subtitle = itemView.findViewById(R.id.list_subTitle);
            listView_Parent = itemView.findViewById(R.id.expandable_listView);
            listView = itemView.findViewById(R.id.listview_expand);


        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewParent = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_details, parent, false);
        return new ViewHolder(viewParent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView_title.setText(keyList.get(position));
        //holder.textView_subtitle.setText("2 Runden");
        List<Session> indexList = workoutList.get(position);
        if (indexList != null) {
            for (int i = 0; i < indexList.size(); i++) {
                View viewChild = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.expand_list_item_category, null);
                viewChild.setId(i);
                setChildViews(viewChild, i,position,indexList);
                holder.listView.addView(viewChild);
            }
        }

        //listView.setVisibility(View.GONE);
        //aSwitch.setColorFilter(ContextCompat.getColor(aSwitch.getContext(), R.color.doveGray), android.graphics.PorterDuff.Mode.MULTIPLY);
        holder.aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.listView.isShown()) {
                    holder.listView.setVisibility(View.GONE);
                    holder.aSwitch.setColorFilter(ContextCompat.getColor(holder.aSwitch.getContext(), R.color.doveGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                } else {
                    holder.listView.setVisibility(View.VISIBLE);
                    holder.aSwitch.clearColorFilter();
                }

            }
        });
    }

    private void setChildViews(View viewChild, int id,int position, List<Session> indexList) {
        TextView txt_videoName, txt_videoDuration;
        ImageView img_videoPreview;
        txt_videoName = viewChild.findViewById(R.id.txt_workoutDetails_videoName);
        txt_videoDuration = viewChild.findViewById(R.id.txt_workoutDetails_videoIntvl);
        img_videoPreview = viewChild.findViewById(R.id.img_workoutDetails_video);
        txt_videoName.setText(indexList.get(id).getTitle());
        txt_videoDuration.setText(indexList.get(id).getDuration());
        Glide.with(img_videoPreview)
                .load(indexList.get(id).getPhoto())
                .placeholder(R.drawable.group_72683)
                .error(R.drawable.group_72683)
                .fallback(R.drawable.group_72683)
                .into(img_videoPreview);
        if (id == 1) {
            viewChild.findViewById(R.id.divider_view).setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public void setList(HashMap<String, List<Session>> list) {
        int index = 0;
        for (Map.Entry map : list.entrySet()) {
            workoutList.put(index, (List<Session>) map.getValue());
            keyList.add((String) map.getKey());
            index++;
        }

    }

}
