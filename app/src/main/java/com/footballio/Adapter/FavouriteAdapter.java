package com.footballio.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.footballio.R;
import com.footballio.model.community.FavoritenItem;

import java.util.List;


public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    public static final String TAG = FavouriteAdapter.class.getSimpleName();
    Context mycontext;
    List<FavoritenItem> favoriten;

    public FavouriteAdapter(Context mycontext, List<FavoritenItem> favoriten) {

        this.mycontext=mycontext;
        this.favoriten=favoriten;
    }

    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_item_comm_favourite, viewGroup, false);
        return new FavouriteAdapter.ViewHolder(view,i) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder viewHolder, final int i) {

        Glide.with(mycontext)
                .load(favoriten.get(i).getBigphoto())
                .placeholder(R.color.colorTransparent)
                .error(R.color.colorTransparent)
                .into(viewHolder.img_fav_community);

    }


    @Override
    public int getItemCount() {
        return favoriten.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        TextView favourite_storename;
        TextView favourite_storeaddress;
        ImageView img_fav_community;





        ViewHolder(View view, int i) {
            super(view);

            img_fav_community = view.findViewById(R.id.img_fav_community);






        }
    }
}
