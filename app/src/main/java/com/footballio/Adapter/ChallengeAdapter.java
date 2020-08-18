package com.footballio.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.footballio.Activities.FBChallengeFullViewActivity;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Utils;
import com.footballio.model.chanllenge.ChanllengeResponse;

import static com.footballio.Utils.Utils.setupItem;

public class ChallengeAdapter extends PagerAdapter {

    private final Utils.LibraryObject[] LIBRARIES = new Utils.LibraryObject[]{
            new Utils.LibraryObject(
                    R.drawable.ic_challenge_view,
                    "Triff die Latte"
            ),
            new Utils.LibraryObject(
                    R.drawable.ic_challenge_view,
                    "Triff die Latte"
            ),
            new Utils.LibraryObject(
                    R.drawable.ic_challenge_view,
                    "Triff die Latte"
            )
    };

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private CardView challenge_card;
    private TextView txt_item,txt_item_desc,txt_success;
    private ImageView img_item;
    private Button btn_challenge;
    private LinearLayout layout_challenge;

    private boolean mIsTwoWay;
    ChanllengeResponse chanllengeResponse;

    public ChallengeAdapter(final Context context, ChanllengeResponse chanllengeResponse, final boolean isTwoWay) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mIsTwoWay = isTwoWay;
        this.chanllengeResponse = chanllengeResponse;
    }

    @Override
    public int getCount() {
        return mIsTwoWay ? 6 : chanllengeResponse.getData().size();
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view;

        view = mLayoutInflater.inflate(R.layout.li_item_challenge, container, false);
        //setupItem(view, LIBRARIES[position]);

        container.addView(view);
        challenge_card=view.findViewById(R.id.challenge_card);
        btn_challenge=view.findViewById(R.id.btn_challenge);
        layout_challenge=view.findViewById(R.id.layout_challenge);
        txt_item=view.findViewById(R.id.txt_item);
        txt_item_desc=view.findViewById(R.id.txt_item_desc);
        img_item=view.findViewById(R.id.img_item);
        txt_success=view.findViewById(R.id.txt_success);

        if (chanllengeResponse.getData().get(position).getStatus().equals("ACTIVE")){
            btn_challenge.setVisibility(View.VISIBLE);
            txt_success.setVisibility(View.GONE);
        }else{
            btn_challenge.setVisibility(View.GONE);
            txt_success.setVisibility(View.VISIBLE);

        }

        txt_item.setText(chanllengeResponse.getData().get(position).getChallengeTitle());
        txt_item_desc.setText(chanllengeResponse.getData().get(position).getDescription());
        Glide.with(mContext)
                .load(chanllengeResponse.getData().get(position).getPhoto())
                .error(R.color.colorTransparent)
                .placeholder(R.color.colorTransparent)
                .into(img_item);
   txt_success.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, FBChallengeFullViewActivity.class);
                    intent.putExtra(APPConst.CHANLLENGEID,chanllengeResponse.getData().get(position).getChallengeId());
                    intent.putExtra(APPConst.FULLID,chanllengeResponse.getData().get(position).getStatus());
                    mContext.startActivity(intent);
                }
            });
        btn_challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FBChallengeFullViewActivity.class);
                intent.putExtra(APPConst.CHANLLENGEID,chanllengeResponse.getData().get(position).getChallengeId());
                intent.putExtra(APPConst.FULLID,chanllengeResponse.getData().get(position).getStatus());
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);

    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}
