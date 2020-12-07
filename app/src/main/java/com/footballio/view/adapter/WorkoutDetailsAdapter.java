package com.footballio.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.footballio.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryDetailsAdapter extends RecyclerView.Adapter<CategoryDetailsAdapter.ViewHolder> {
    private List<String> list = new ArrayList<>();
    private static final String[] maintitle = {
            "Title 1", "Title 2",
            "Title 3", "Title 4",
            "Title 5",
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView aSwitch, img_category;
        private LinearLayout listView, listView_Parent;
        private TextView textView_title, textView_subtitle, txt_categoryName, txt_categoryDesc;
        private View divider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            aSwitch = itemView.findViewById(R.id.img_switch);
            textView_title = itemView.findViewById(R.id.listTitle);
            textView_subtitle = itemView.findViewById(R.id.list_subTitle);
            listView_Parent = itemView.findViewById(R.id.expandable_listView);
            listView = itemView.findViewById(R.id.listview_expand);

            for (int i = 0; i < 2; i++) {
                View viewChild = LayoutInflater.from(itemView.getContext()).inflate(R.layout.expand_list_item_category, null);
                viewChild.setId(i);
                if(i==1)
                {
                    viewChild.findViewById(R.id.divider_view).setVisibility(View.GONE);
                }
                listView.addView(viewChild);
            }
            listView.setVisibility(View.GONE);
            aSwitch.setColorFilter(ContextCompat.getColor(aSwitch.getContext(), R.color.doveGray), android.graphics.PorterDuff.Mode.MULTIPLY);
            aSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listView.isShown()) {
                        listView.setVisibility(View.GONE);
                        aSwitch.setColorFilter(ContextCompat.getColor(aSwitch.getContext(), R.color.doveGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    } else {
                        listView.setVisibility(View.VISIBLE);
                        aSwitch.clearColorFilter();
                    }

                }
            });
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

        holder.textView_title.setText("Warm-Up");
        holder.textView_subtitle.setText("2 Runden");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<String> list) {
        this.list = list;
    }


//    public static class MyList extends ArrayAdapter<String> {
//
//        public MyList(@NonNull Context context, int resource) {
//            super(context, resource,maintitle);
//        }
//
//        public View getView(int position, View view, ViewGroup parent) {
//
//            View listview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_details, null, true);
//            ImageView imageView = listview.findViewById(R.id.img_item_subcategory);
//            TextView textView_title = listview.findViewById(R.id.txt_title_subcategory);
//            TextView textView_sub_title = listview.findViewById(R.id.txt_subtitle_subcategory);
//
//            listview.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
//
//            return listview;
//        }
//    }


}
