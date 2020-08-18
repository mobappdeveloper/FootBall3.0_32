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
import com.footballio.Activities.FBBlogCategoryActivity;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.model.community.KategorienItem;
import com.google.gson.Gson;

import java.util.List;


public class CommunityGroupAdapter extends RecyclerView.Adapter<CommunityGroupAdapter.ViewHolder> {
    public static final String TAG = CommunityGroupAdapter.class.getSimpleName();
    Context mycontext;
     List<KategorienItem> kategorienItems;
    public CommunityGroupAdapter(Context mycontext, List<KategorienItem> kategorienItems) {

        this.mycontext=mycontext;
        this.kategorienItems=kategorienItems;
        Log.d(TAG, "CommunityGroupAdapter: "+new Gson().toJson(kategorienItems));
    }

    @NonNull
    @Override
    public CommunityGroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_item_community_group, viewGroup, false);
        return new CommunityGroupAdapter.ViewHolder(view,i) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityGroupAdapter.ViewHolder viewHolder, final int i) {

        Glide.with(mycontext)
                .load(kategorienItems.get(i).getCategoryPhoto())
                .placeholder(R.color.colorTransparent)
                .error(R.color.colorTransparent)
                .into(viewHolder.img_group_icon);
     viewHolder.fb_comm_nutrition.setText(kategorienItems.get(i).getCategoryName());

     viewHolder.linear_community_cat.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(mycontext, FBBlogCategoryActivity.class);
             intent.putExtra(APPConst.COMMCATID,kategorienItems.get(i).getCategoryId());
             mycontext.startActivity(intent);
         }
     });
    }


    @Override
    public int getItemCount() {
        return kategorienItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_group_icon;
        TextView fb_comm_nutrition;
        LinearLayout linear_community_cat;



        ViewHolder(View view, int i) {
            super(view);


            img_group_icon=view.findViewById(R.id.img_group_icon);
            fb_comm_nutrition=view.findViewById(R.id.fb_comm_nutrition);
            linear_community_cat=view.findViewById(R.id.linear_community_cat);





        }
    }
}
