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
import com.footballio.Activities.FBChallengeVideoActivity;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.model.chanllenge.chanllengeview.LearnboardItem;
import com.footballio.model.community.MeistgeleseneArtikelItem;

import java.util.List;

public class ChanllengeViewListAdapter extends RecyclerView.Adapter<ChanllengeViewListAdapter.ViewHolder> {
    public static final String TAG = ChanllengeViewListAdapter.class.getSimpleName();
    Context mycontext;
    List<LearnboardItem> learnboard;
    String cid;
    public ChanllengeViewListAdapter(Context mycontext, List<LearnboardItem> learnboard, String cid) {

        this.mycontext=mycontext;
        this.learnboard=learnboard;
        this.cid=cid;
    }

    @NonNull
    @Override
    public ChanllengeViewListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_item_challenge_view, viewGroup, false);
        return new ChanllengeViewListAdapter.ViewHolder(view,i) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ChanllengeViewListAdapter.ViewHolder viewHolder, final int i) {

        if (learnboard.get(i).getUserName().length()>0) {
            viewHolder.vendor_ads_image.setVisibility(View.VISIBLE);
            viewHolder.fb_challenge_member_name.setVisibility(View.VISIBLE);
            viewHolder.linear_card.setVisibility(View.VISIBLE);
            Glide.with(mycontext)
                    .load(learnboard.get(i).getUserphoto())
                    .placeholder(R.color.colorTransparent)
                    .error(R.color.colorTransparent)
                    .into(viewHolder.vendor_ads_image);
            viewHolder.linear_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mycontext, FBChallengeVideoActivity.class);
                    intent.putExtra(APPConst.CHANLLENGEID, cid);
                    intent.putExtra(APPConst.PID, learnboard.get(i).getUserPostid());
                    mycontext.startActivity(intent);
                }
            });
            viewHolder.fb_challenge_member_name.setText(learnboard.get(i).getUserName());
        }else{
            viewHolder.vendor_ads_image.setVisibility(View.GONE);
            viewHolder.fb_challenge_member_name.setVisibility(View.GONE);
            viewHolder.linear_card.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return learnboard.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView vendor_ads_image;
        TextView fb_challenge_member_name;
        LinearLayout linear_card;

        ViewHolder(View view, int i) {
            super(view);
            vendor_ads_image=view.findViewById(R.id.vendor_ads_image);
            linear_card=view.findViewById(R.id.linear_card);
            fb_challenge_member_name=view.findViewById(R.id.fb_challenge_member_name);

        }
    }
}
