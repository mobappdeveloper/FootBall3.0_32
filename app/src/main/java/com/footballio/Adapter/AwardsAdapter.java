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
import com.footballio.Activities.FBTrophyFullViewActivity;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.model.chanllenge.awardsuser.AwardsUserResponse;
import com.footballio.model.community.MeistgeleseneArtikelItem;

import java.util.List;


public class AwardsAdapter extends RecyclerView.Adapter<AwardsAdapter.ViewHolder> {
    public static final String TAG = AwardsAdapter.class.getSimpleName();
    Context mycontext;
    String whichadapter;
    AwardsUserResponse chanllengeAwardsResponse;
    public AwardsAdapter(Context mycontext, AwardsUserResponse chanllengeAwardsResponse, String whichadapter) {
        this.mycontext = mycontext;
        this.chanllengeAwardsResponse = chanllengeAwardsResponse;
        this.whichadapter = whichadapter;
    }

    @NonNull
    @Override
    public AwardsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_item_trophy, viewGroup, false);
        return new AwardsAdapter.ViewHolder(view,i) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull AwardsAdapter.ViewHolder viewHolder, final int i) {
        if (whichadapter.equals("1")){
            Glide.with(mycontext)
                    .load(chanllengeAwardsResponse.getChallange().get(i).getIcon())
                    .placeholder(R.color.colorTransparent)
                    .error(R.color.colorTransparent)
                    .into(viewHolder.trophy_img);
            viewHolder.trophy_name.setText(chanllengeAwardsResponse.getChallange().get(i).getAwardName());

            viewHolder.card_trophy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mycontext, FBTrophyFullViewActivity.class);
                    intent.putExtra(APPConst.TROPHYICON,chanllengeAwardsResponse.getChallange().get(i).getIcon());
                    intent.putExtra(APPConst.TROPHYNAME,chanllengeAwardsResponse.getChallange().get(i).getAwardName());
                    mycontext.startActivity(intent);
                }
            });
        }else if (whichadapter.equals("2")){
            Glide.with(mycontext)
                    .load(chanllengeAwardsResponse.getWorkout().get(i).getIcon())
                    .placeholder(R.color.colorTransparent)
                    .error(R.color.colorTransparent)
                    .into(viewHolder.trophy_img);
            viewHolder.trophy_name.setText(chanllengeAwardsResponse.getWorkout().get(i).getAwardName());

            viewHolder.card_trophy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mycontext, FBTrophyFullViewActivity.class);
                    intent.putExtra(APPConst.TROPHYICON,chanllengeAwardsResponse.getWorkout().get(i).getIcon());
                    intent.putExtra(APPConst.TROPHYNAME,chanllengeAwardsResponse.getWorkout().get(i).getAwardName());
                    mycontext.startActivity(intent);
                }
            });
        }else{  Glide.with(mycontext)
                .load(chanllengeAwardsResponse.getRinge().get(i).getIcon())
                .placeholder(R.color.colorTransparent)
                .error(R.color.colorTransparent)
                .into(viewHolder.trophy_img);
            viewHolder.trophy_name.setText(chanllengeAwardsResponse.getRinge().get(i).getAwardName());

            viewHolder.card_trophy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mycontext, FBTrophyFullViewActivity.class);
                    intent.putExtra(APPConst.TROPHYICON,chanllengeAwardsResponse.getRinge().get(i).getIcon());
                    intent.putExtra(APPConst.TROPHYNAME,chanllengeAwardsResponse.getRinge().get(i).getAwardName());
                    mycontext.startActivity(intent);
                }
            });
        }


    }


    @Override
    public int getItemCount() {
        if (whichadapter.equals("1")){
            return chanllengeAwardsResponse.getChallange().size();
        }else if (whichadapter.equals("2")){
            return chanllengeAwardsResponse.getWorkout().size();
        }else{ return chanllengeAwardsResponse.getRinge().size();

        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView trophy_img;
        TextView trophy_name;
        LinearLayout card_trophy;

        ViewHolder(View view, int i) {
            super(view);
            trophy_img=view.findViewById(R.id.trophy_img);
            trophy_name=view.findViewById(R.id.trophy_name);
            card_trophy=view.findViewById(R.id.card_trophy);

        }
    }
}
