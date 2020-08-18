package com.footballio.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.footballio.R;
import com.footballio.model.community.MeistgeleseneArtikelItem;

import java.util.List;


public class ArticleAdapter  extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    public static final String TAG = ArticleAdapter.class.getSimpleName();
    Context mycontext;
     List<MeistgeleseneArtikelItem> meistgeleseneArtikel;
    public ArticleAdapter(Context mycontext, List<MeistgeleseneArtikelItem> meistgeleseneArtikel) {

        this.mycontext=mycontext;
        this.meistgeleseneArtikel=meistgeleseneArtikel;
    }

    @NonNull
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_item_comm_articles_2, viewGroup, false);
        return new ArticleAdapter.ViewHolder(view,i) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: "+meistgeleseneArtikel.get(i).getBigphoto());
        Glide.with(mycontext)
                .load(meistgeleseneArtikel.get(i).getBigphoto())
                .placeholder(R.color.colorTransparent)
                .error(R.color.colorTransparent)
                .into(viewHolder.community_read_articles_2_image);

    }


    @Override
    public int getItemCount() {
        return meistgeleseneArtikel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView community_read_articles_2_image;



        ViewHolder(View view, int i) {
            super(view);


            community_read_articles_2_image=view.findViewById(R.id.community_read_articles_2_image);





        }
    }
}
