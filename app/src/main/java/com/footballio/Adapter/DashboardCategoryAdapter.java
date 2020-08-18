package com.footballio.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.footballio.Activities.FBDashboardViewActivity;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.model.dashboard.allcategory.AllCategoryResponse;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DashboardCategoryAdapter extends RecyclerView.Adapter<DashboardCategoryAdapter.ViewHolder> {
    public static final String TAG = ArticleAdapter.class.getSimpleName();
    Context contextArticleAdapter;


//  public HelpListAdapter(Context contextHelpListAdapter) {
//    this.contextHelpListAdapter = contextHelpListAdapter;

    AllCategoryResponse allCategoryResponse;

    boolean is_fav = true;

    public DashboardCategoryAdapter(Context mycontext, AllCategoryResponse allCategoryResponse) {

        this.contextArticleAdapter = mycontext;
        this.allCategoryResponse = allCategoryResponse;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_dashboard_in_category, viewGroup, false);
        return new ViewHolder(view, i) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTitle.setText(allCategoryResponse.getPrograms().get(position).getProgramName());
        holder.txtDesc1.setText(allCategoryResponse.getPrograms().get(position).getBigdesc());
        //  holder.txtArticleTitle.setText(allCategoryResponse.getPrograms().get(position).getProgramName());

        holder.imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_fav){
                    holder.imgFavourite.setImageDrawable(contextArticleAdapter.getResources().getDrawable(R.drawable.ic_favorite_on));
                    is_fav=false;
                }else{
                    holder.imgFavourite.setImageDrawable(contextArticleAdapter.getResources().getDrawable(R.drawable.ic_favorite_off));
                    is_fav=true;
                }
            }
        });

        Glide.with(contextArticleAdapter)
                .load(allCategoryResponse.getPrograms().get(position).getMediumphoto())
                .placeholder(R.color.colorTransparent)
                .error(R.color.colorTransparent)
                .into(holder.fbViewImage);

        holder.cardCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contextArticleAdapter, FBDashboardViewActivity.class);
                intent.putExtra(APPConst.PID, allCategoryResponse.getPrograms().get(position).getProgramId());
                contextArticleAdapter.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return allCategoryResponse.getPrograms().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.fb_view_image)
        ImageView fbViewImage;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_desc1)
        TextView txtDesc1;
        @BindView(R.id.btn_name)
        TextView btnName;
        @BindView(R.id.card_category)
        CardView cardCategory;
        @BindView(R.id.img_favourite)
        ImageView imgFavourite;

        ViewHolder(View view, int i) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
