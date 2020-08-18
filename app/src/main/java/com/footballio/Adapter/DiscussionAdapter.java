package com.footballio.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.footballio.Activities.FBDiscussionFullViewActivity;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.model.community.DiscussionUnlikeRequest;
import com.footballio.model.community.DiscussionUnlikeResponse;
import com.footballio.model.community.discussion.DiscussionMainResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.ViewHolder> {
    public static final String TAG = DiscussionAdapter.class.getSimpleName();
    Context mycontext;
    DiscussionMainResponse discussionResponse;
    private DiscussionUnlikeResponse discussionunlikeResponse;


    public DiscussionAdapter(Context mycontext, DiscussionMainResponse discussionResponse) {

        this.mycontext = mycontext;
        this.discussionResponse = discussionResponse;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_item_comm_discussion, viewGroup, false);
        return new ViewHolder(view, i) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Glide.with(mycontext)
                .load(discussionResponse.getData().get(i).getPhoto())
                .placeholder(R.color.colorTransparent)
                .error(R.color.colorTransparent)
                .into(viewHolder.imgDisc);
        viewHolder.txtTitleDisc.setText(discussionResponse.getData().get(i).getTitle());
        viewHolder.txtDiscCount.setText(discussionResponse.getData().get(i).getCommentCount());
        viewHolder.txtFavCount.setText(discussionResponse.getData().get(i).getFavouriteCount());
        viewHolder.txtDateDisc.setText(discussionResponse.getData().get(i).getCreatedOn());
        viewHolder.txtDiscTag1.setVisibility(View.GONE);
        viewHolder.txtDiscTag2.setVisibility(View.GONE);
        viewHolder.txtDiscTag3.setVisibility(View.GONE);
        if (discussionResponse.getData().get(i).getFavouriteStatus().equals("true")) {
            viewHolder.categoryViewCheckbox.setChecked(true);
        }else{
            viewHolder.categoryViewCheckbox.setChecked(false);
        }

        viewHolder.categoryViewCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Discussionlike(discussionResponse.getData().get(i).getDiscussionId(),viewHolder.categoryViewCheckbox,
                            viewHolder.txtFavCount,discussionResponse.getData().get(i).getFavouriteCount());
                }else{
                    DiscussionUnlike(discussionResponse.getData().get(i).getDiscussionId(),viewHolder.categoryViewCheckbox,
                            viewHolder.txtFavCount,discussionResponse.getData().get(i).getFavouriteCount());
                }
            }
        });

        String currentString = discussionResponse.getData().get(i).getTags();
        String[] separated = currentString.split(",");
        Log.d(TAG, "onBindViewHolder: "+separated.length);
        if (separated.length > 0) {
            viewHolder.txtDiscTag1.setVisibility(View.VISIBLE);
            viewHolder.txtDiscTag1.setText(separated[0]);
            Log.d(TAG, "onBindViewHolder:1 "+separated.length);
        }  if (separated.length > 1) {
            viewHolder.txtDiscTag2.setVisibility(View.VISIBLE);
            viewHolder.txtDiscTag2.setText(separated[1]);
            Log.d(TAG, "onBindViewHolder:2 "+separated.length);
        }  if (separated.length > 2) {
            viewHolder.txtDiscTag3.setVisibility(View.VISIBLE);
            viewHolder.txtDiscTag3.setText(separated[2]);
            Log.d(TAG, "onBindViewHolder:3 "+separated.length);
        }
        viewHolder.discussionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mycontext, FBDiscussionFullViewActivity.class);
                intent.putExtra(APPConst.DISCUSSIONID,discussionResponse.getData().get(i).getDiscussionId());
                intent.putExtra(APPConst.DISCUSSIONTAGS,discussionResponse.getData().get(i).getTags());
                mycontext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return discussionResponse.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        @BindView(R.id.img_disc)
        ImageView imgDisc;
        @BindView(R.id.txt_title_disc)
        TextView txtTitleDisc;
        @BindView(R.id.txt_fav_count)
        TextView txtFavCount;
        @BindView(R.id.category_view_checkbox)
        CheckBox categoryViewCheckbox;
        @BindView(R.id.txt_disc_count)
        TextView txtDiscCount;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_date_disc)
        TextView txtDateDisc;
        @BindView(R.id.txt_disc_tag1)
        TextView txtDiscTag1;
        @BindView(R.id.txt_disc_tag2)
        TextView txtDiscTag2;
        @BindView(R.id.txt_disc_tag3)
        TextView txtDiscTag3;
        @BindView(R.id.discussion_layout)
        LinearLayout discussionLayout;

        ViewHolder(View view, int i) {
            super(view);
            ButterKnife.bind(this, view);






        }
    }


    Call<DiscussionUnlikeResponse> discussionUnlikeResponseCall;

    private void DiscussionUnlike(String discussionId, CheckBox categoryViewCheckbox, TextView txtFavCount,
                                  String favouriteCount) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        DiscussionUnlikeRequest discussionUnlikeRequest = new DiscussionUnlikeRequest();
        discussionUnlikeRequest.setTopicid(discussionId);
        discussionUnlikeRequest.setUserid(Prefs.getString(APPConst.TOKEN,""));
        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        discussionUnlikeResponseCall = apiService.discussionunlike(discussionUnlikeRequest);


        discussionUnlikeResponseCall.enqueue(new Callback<DiscussionUnlikeResponse>() {
            @Override
            public void onResponse(Call<DiscussionUnlikeResponse> call, Response<DiscussionUnlikeResponse> response) {
                Log.d(TAG, "onResponse:DiscussionFullViewResponse " + new Gson().toJson(response.body()));
                discussionunlikeResponse = response.body();

                if (response.body().isStatus()){
                    categoryViewCheckbox.setChecked(false);
                    int likecount = Integer.parseInt(favouriteCount)-1;
                    txtFavCount.setText(""+likecount);
                    Toast.makeText(mycontext, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mycontext, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<DiscussionUnlikeResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: DiscussionFullViewResponse" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    Call<DiscussionUnlikeResponse> discussionlikeResponseCall;

    private void Discussionlike(String discussionId, CheckBox categoryViewCheckbox, TextView txtFavCount, String favouriteCount) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        DiscussionUnlikeRequest discussionUnlikeRequest = new DiscussionUnlikeRequest();
        discussionUnlikeRequest.setTopicid(discussionId);
        discussionUnlikeRequest.setUserid(Prefs.getString(APPConst.TOKEN,""));
        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        discussionlikeResponseCall = apiService.discussionlike(discussionUnlikeRequest);


        discussionlikeResponseCall.enqueue(new Callback<DiscussionUnlikeResponse>() {
            @Override
            public void onResponse(Call<DiscussionUnlikeResponse> call, Response<DiscussionUnlikeResponse> response) {
                Log.d(TAG, "onResponse:DiscussionUnlikeResponse " + new Gson().toJson(response.body()));
                discussionunlikeResponse = response.body();

                if (response.body().isStatus()){
                    categoryViewCheckbox.setChecked(true);
                    int likecount = Integer.parseInt(favouriteCount)+1;
                    txtFavCount.setText(""+likecount);
                    Toast.makeText(mycontext, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mycontext, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<DiscussionUnlikeResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: DiscussionUnlikeResponse" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
