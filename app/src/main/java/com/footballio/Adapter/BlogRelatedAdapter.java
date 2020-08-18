package com.footballio.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.footballio.Activities.FBBlogActivity;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.model.community.MeistgeleseneArtikelItem;
import com.footballio.model.community.blogview.BlogViewResponse;

import java.util.List;


public class BlogRelatedAdapter extends RecyclerView.Adapter<BlogRelatedAdapter.ViewHolder> {
    public static final String TAG = BlogRelatedAdapter.class.getSimpleName();
    Context mycontext;
    BlogViewResponse blogViewResponse;
    public BlogRelatedAdapter(Context mycontext, BlogViewResponse blogViewResponse) {

        this.mycontext=mycontext;
        this.blogViewResponse=blogViewResponse;
    }

    @NonNull
    @Override
    public BlogRelatedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_item_blog, viewGroup, false);
        return new BlogRelatedAdapter.ViewHolder(view,i) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull BlogRelatedAdapter.ViewHolder viewHolder, final int i) {

        Glide.with(mycontext)
                .load(blogViewResponse.getAlsoIntersting().get(i).getPhoto())
                .placeholder(R.color.colorTransparent)
                .error(R.color.colorTransparent)
                .into(viewHolder.community_read_articles_2_image);
viewHolder.community_favourite_card.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mycontext, FBBlogActivity.class);
        intent.putExtra(APPConst.BLOGID,blogViewResponse.getAlsoIntersting().get(i).getBlogId());
        mycontext.startActivity(intent);
        ((Activity)mycontext).finish();
    }
});
    }


    @Override
    public int getItemCount() {
        return blogViewResponse.getAlsoIntersting().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView community_read_articles_2_image;

CardView community_favourite_card;

        ViewHolder(View view, int i) {
            super(view);


            community_read_articles_2_image=view.findViewById(R.id.community_read_articles_2_image);
            community_favourite_card=view.findViewById(R.id.community_favourite_card);





        }
    }
}
