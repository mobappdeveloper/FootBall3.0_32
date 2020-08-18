package com.footballio.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.footballio.Activities.FBBlogActivity;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.model.community.TrendsItem;

import java.util.List;


public class TrendAdapter extends RecyclerView.Adapter<TrendAdapter.ViewHolder> {
    public static final String TAG = TrendAdapter.class.getSimpleName();
    Activity mycontext;

    List<TrendsItem> trends;


    public TrendAdapter(Activity mycontext, List<TrendsItem> trends) {
        this.mycontext=mycontext;
        this.trends=trends;
    }

    @NonNull
    @Override
    public TrendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_item_comm_trend, viewGroup, false);
        return new TrendAdapter.ViewHolder(view,i) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull TrendAdapter.ViewHolder viewHolder, final int i) {

        Glide.with(mycontext)
                .load(trends.get(i).getBigphoto())
                .placeholder(R.color.colorTransparent)
                .error(R.color.colorTransparent)
                .into(viewHolder.home_category_offer_image);
        if (i>0){
            viewHolder.trend_card.getLayoutParams().height = (int) mycontext.getResources().getDimension(R.dimen.layout_h3_sub1);
            viewHolder.home_category_offer_image.getLayoutParams().height = (int) mycontext.getResources().getDimension(R.dimen.layout_h3_sub1);
          //  viewHolder.home_category_offer_image.setScaleType(ImageView.ScaleType.CENTER);
        }

        viewHolder.trend_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rect displayRectangle = new Rect();

                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext(),R.style.CustomAlertDialog);
                ViewGroup viewGroup = viewHolder.itemView.findViewById(android.R.id.content);

                TrendViewAdapter trendViewAdapter;
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.li_item_comm_trend_view, viewGroup, false);
                TextView title=dialogView.findViewById(R.id.txt_comm_title);
                TextView category=dialogView.findViewById(R.id.txt_item);
                TextView description=dialogView.findViewById(R.id.txt_comm_desc);
                LinearLayout close=dialogView.findViewById(R.id.img_category_close);
                Button viewfull=dialogView.findViewById(R.id.btn_trend_full_view);
                ImageView imageviewviewfull=dialogView.findViewById(R.id.img_item);



                dialogView.setMinimumWidth((int)(displayRectangle.width() * 1f));
                dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                title.setText(trends.get(i).getBlogTitle());
                category.setText(trends.get(i).getBlogCategoryName());
                description.setText(trends.get(i).getDescription());
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                }); viewfull.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mycontext, FBBlogActivity.class);
                        intent.putExtra(APPConst.BLOGID,trends.get(i).getBlogId());
                        mycontext.startActivity(intent);
                        alertDialog.dismiss();
                    }
                });
                Glide.with(mycontext)
                        .load(trends.get(i).getBigphoto())
                        .placeholder(R.drawable.ic_community_trends)
                        .error(R.drawable.ic_community_trends)
                        .into(imageviewviewfull);
            }
        });

    }


    @Override
    public int getItemCount() {
        return trends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        CardView trend_card;

ImageView home_category_offer_image;



        ViewHolder(View view, int i) {
            super(view);


            trend_card = itemView.findViewById(R.id.trend_card);
            home_category_offer_image = itemView.findViewById(R.id.home_category_offer_image);












        }
    }

    private RecyclerView.LayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(mycontext, LinearLayoutManager.HORIZONTAL, false);
    }
}
