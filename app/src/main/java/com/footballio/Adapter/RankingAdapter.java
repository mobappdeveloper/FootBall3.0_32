package com.footballio.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.footballio.R;
import com.footballio.model.chanllenge.ranking.RankingResponse;


public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {
    public static final String TAG = RankingAdapter.class.getSimpleName();
    Context contextRankingAdapter;
    RankingResponse chanllengeRankingResponse;

    public RankingAdapter(Context mycontext, RankingResponse chanllengeRankingResponse) {

        this.contextRankingAdapter = mycontext;
        this.chanllengeRankingResponse = chanllengeRankingResponse;

    }

    @NonNull
    @Override
    public RankingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_item_ranking, viewGroup, false);
        return new RankingAdapter.ViewHolder(view, i) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RankingAdapter.ViewHolder viewHolder, final int i) {
viewHolder.txt_name.setText(chanllengeRankingResponse.getData().get(i).getUserName());
viewHolder.txt_price.setText(chanllengeRankingResponse.getData().get(i).getPrize());
        Glide.with(contextRankingAdapter)
                .load(chanllengeRankingResponse.getData().get(i).getPhoto())
                .error(R.color.colorTransparent)
                .placeholder(R.color.colorTransparent)
                .into(viewHolder.vendor_ads_image);
        if (i==0){
            viewHolder.img_rank.setImageDrawable(contextRankingAdapter.getResources().getDrawable(R.drawable.ic_rank_1));
            viewHolder.rank_number.setText("");
        }else if (i==1){
            viewHolder.img_rank.setImageDrawable(contextRankingAdapter.getResources().getDrawable(R.drawable.ic_rank_2));
            viewHolder.rank_number.setText(""+(i+1));
        }else if (i==2){
            viewHolder.img_rank.setImageDrawable(contextRankingAdapter.getResources().getDrawable(R.drawable.ic_rank_3));
            viewHolder.rank_number.setText(""+(i+1));
        }else{ viewHolder.img_rank.setImageDrawable(contextRankingAdapter.getResources().getDrawable(R.drawable.ic_rank_others));
            viewHolder.rank_number.setText("");



        }
    }


    @Override
    public int getItemCount() {
        return chanllengeRankingResponse.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView vendor_ads_image,img_rank;
        TextView txt_name,txt_price,rank_number;


        ViewHolder(View view, int i) {
            super(view);
            vendor_ads_image = itemView.findViewById(R.id.vendor_ads_image);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_price = itemView.findViewById(R.id.txt_price);
            rank_number = itemView.findViewById(R.id.rank_number);
            img_rank = itemView.findViewById(R.id.img_rank);

        }
    }
}
