package com.footballio.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.footballio.R;
import com.footballio.model.community.discussionview.CommentsItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DiscussionFullAdapter extends RecyclerView.Adapter<DiscussionFullAdapter.ViewHolder> {
    public static final String TAG = DiscussionFullAdapter.class.getSimpleName();
    Context mycontext;
    List<CommentsItem> comments;



    public DiscussionFullAdapter(Context mycontext, List<CommentsItem> comments) {

        this.mycontext = mycontext;
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_item_comm_discussion_full_view, viewGroup, false);
        return new ViewHolder(view, i) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.vendorAdsProductName.setText(comments.get(i).getFristName());
        viewHolder.txtDateDisc.setText(comments.get(i).getDate());
        viewHolder.txtDiscDesc.setText(Html.fromHtml(comments.get(i).getComment()));
        Glide.with(mycontext)
                .load(comments.get(i).getPhoto())
                .placeholder(R.color.colorTransparent)
                .error(R.color.colorTransparent)
                .into(viewHolder.vendorAdsImage);
    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.vendor_ads_image)
        ImageView vendorAdsImage;
        @BindView(R.id.vendor_ads_product_name)
        TextView vendorAdsProductName;
        @BindView(R.id.txt_disc_desc)
        TextView txtDiscDesc;
        @BindView(R.id.category_view_checkbox)
        CheckBox categoryViewCheckbox;
        LinearLayout discussion_layout;
        @BindView(R.id.txt_date_disc)
        TextView txtDateDisc;

        ViewHolder(View view, int i) {
            super(view);
            ButterKnife.bind(this, view);
            discussion_layout = itemView.findViewById(R.id.discussion_layout);


        }
    }
}
