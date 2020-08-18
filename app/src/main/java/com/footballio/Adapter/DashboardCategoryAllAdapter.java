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
import com.footballio.Activities.FBWorkoutViewActivity;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.model.dashboard.allcategory.AllCategoryResponse;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DashboardCategoryAllAdapter extends RecyclerView.Adapter<DashboardCategoryAllAdapter.ViewHolder> {
    public static final String TAG = ArticleAdapter.class.getSimpleName();
    Context contextArticleAdapter;

    AllCategoryResponse allCategoryResponse;



//  public HelpListAdapter(Context contextHelpListAdapter) {
//    this.contextHelpListAdapter = contextHelpListAdapter;


    public DashboardCategoryAllAdapter(Context mycontext, AllCategoryResponse allCategoryResponse) {

        this.contextArticleAdapter = mycontext;
        this.allCategoryResponse = allCategoryResponse;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_dashboard_category, viewGroup, false);
        return new ViewHolder(view, i) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTitle.setText(allCategoryResponse.getPrograms().get(position).getProgramName());
        holder.txtDesc.setText(allCategoryResponse.getPrograms().get(position).getProgramdec());
       // holder.btnName.setText(allCategoryResponse.getPrograms().get(position).getProgramName());


        Glide.with(contextArticleAdapter)
                .load(allCategoryResponse.getPrograms().get(position).getSmallphoto())
                 .placeholder(R.color.colorTransparent)
                .error(R.color.colorTransparent)
                .into(holder.fbViewImage);
        holder.cardCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contextArticleAdapter, FBDashboardViewActivity.class);
                intent.putExtra(APPConst.PID,allCategoryResponse.getPrograms().get(position).getProgramId());
                contextArticleAdapter.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return allCategoryResponse.getPrograms().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView favourite_storename;
        TextView favourite_storeaddress;
        ImageView favourite_store_full_image;
        @BindView(R.id.fb_view_image)
        ImageView fbViewImage;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_desc)
        TextView txtDesc;
        @BindView(R.id.btn_name)
        TextView btnName;
        @BindView(R.id.card_category)
        CardView cardCategory;
        ViewHolder(View view, int i) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
