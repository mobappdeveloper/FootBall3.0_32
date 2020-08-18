package com.footballio.Adapter;

import android.app.Activity;
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
import com.footballio.Activities.FBBlogActivity;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.model.community.BlogsItem;
import com.footballio.model.community.MeistgeleseneArtikelItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BlogCategoryAdapter extends RecyclerView.Adapter<BlogCategoryAdapter.ViewHolder> {
    public static final String TAG = BlogCategoryAdapter.class.getSimpleName();
    Context mycontext;
    List<BlogsItem> blogsItems;


    public BlogCategoryAdapter(Context mycontext, List<BlogsItem> blogsItems) {

        this.mycontext = mycontext;
        this.blogsItems = blogsItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_item_nutrition, viewGroup, false);
        return new ViewHolder(view, i) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        Glide.with(mycontext)
                .load(blogsItems.get(i).getPhoto())

                .into(viewHolder.imgCatimage);
        viewHolder.txtCatname.setText(blogsItems.get(i).getBlogTitle());
        viewHolder.cardCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mycontext, FBBlogActivity.class);
                intent.putExtra(APPConst.BLOGID,blogsItems.get(i).getBlogCategoryId());
                mycontext.startActivity(intent);
                ((Activity)mycontext).finish();
            }
        });

    }


    @Override
    public int getItemCount() {
        return blogsItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_catimage)
        ImageView imgCatimage;
        @BindView(R.id.txt_catname)
        TextView txtCatname;
        @BindView(R.id.card_cat)
        CardView cardCat;

        ViewHolder(View view, int i) {
            super(view);
ButterKnife.bind(this,view);




        }
    }
}
