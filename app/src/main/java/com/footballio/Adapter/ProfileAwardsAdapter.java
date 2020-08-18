package com.footballio.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.footballio.R;


public class ProfileAwardsAdapter extends RecyclerView.Adapter<ProfileAwardsAdapter.ViewHolder> {
    public static final String TAG = ProfileAwardsAdapter.class.getSimpleName();
    Context context;



//  public HelpListAdapter(Context contextHelpListAdapter) {
//    this.contextHelpListAdapter = contextHelpListAdapter;



    public ProfileAwardsAdapter(Context mycontext) {

        this.context=mycontext;
    }

    @NonNull
    @Override
    public ProfileAwardsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_item_profie_awards, viewGroup, false);
        return new ProfileAwardsAdapter.ViewHolder(view,i) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAwardsAdapter.ViewHolder viewHolder, final int i) {



    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        TextView favourite_storename;
        TextView favourite_storeaddress;
        ImageView favourite_store_full_image;





        ViewHolder(View view, int i) {
            super(view);








        }
    }
}
