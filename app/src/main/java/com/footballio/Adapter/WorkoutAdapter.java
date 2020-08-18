package com.footballio.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.footballio.Activities.FBPaymentActivity;
import com.footballio.Activities.FBWorkoutViewActivity;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.Utils.Utils;
import com.footballio.model.BasicResponse;
import com.footballio.model.dashboard.checkworkout.CheckWorkoutResponse;
import com.footballio.model.workout.paymentcheck.WorkoutCheckPaymentResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {
    public static final String TAG = ArticleAdapter.class.getSimpleName();

    Context mycontext;
    CheckWorkoutResponse checkworkoutResponse;


    public WorkoutAdapter(Context mycontext, CheckWorkoutResponse checkworkoutResponse) {

        this.mycontext = mycontext;
        this.checkworkoutResponse = checkworkoutResponse;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_item_workout, viewGroup, false);
        return new ViewHolder(view, i) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        Glide.with(mycontext)
                .load(checkworkoutResponse.getData().get(i).getImage())
                .error(R.color.colorTransparent)
                .placeholder(R.color.colorTransparent)
                .into(viewHolder.imgProduct);

        viewHolder.workoutName.setText(checkworkoutResponse.getData().get(i).getProgramName());
        viewHolder.workoutStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutCheckPayment(checkworkoutResponse.getData().get(i).getPid());

            }
        });
        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWorkout(checkworkoutResponse.getData().get(i).getWorkoutId(),i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return checkworkoutResponse.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.img_product)
        ImageView imgProduct;
        @BindView(R.id.img_delete)
        ImageView imgDelete;
        @BindView(R.id.workout_name)
        TextView workoutName;
        @BindView(R.id.workout_start)
        Button workoutStart;

        ViewHolder(View view, int i) {
            super(view);
            ButterKnife.bind(this,view);

        }
    }


    Call<BasicResponse> deleteWorkoutResponseCall;

    private void deleteWorkout(String workid, int position) {

        Loader.showLoad(mycontext,true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        deleteWorkoutResponseCall = apiService.deleteworkout(workid);


        deleteWorkoutResponseCall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                Log.d(TAG, "onResponse: allCategoryResponse" + new Gson().toJson(response.body()));
                if (response.body().isStatus() == true) {
                    removeAt(position);
                    Toast.makeText(mycontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mycontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                Loader.showLoad(mycontext,false);
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext,false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });


    }

    Call<WorkoutCheckPaymentResponse> workoutCheckPaymentResponseCall;

    private void workoutCheckPayment(String pid) {

        Loader.showLoad(mycontext,true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        workoutCheckPaymentResponseCall = apiService.workoutCheckPament(Prefs.getString(APPConst.TOKEN,""));


        workoutCheckPaymentResponseCall.enqueue(new Callback<WorkoutCheckPaymentResponse>() {
            @Override
            public void onResponse(Call<WorkoutCheckPaymentResponse> call, Response<WorkoutCheckPaymentResponse> response) {
                Log.d(TAG, "onResponse: workoutCheckPaymentResponseCall" + new Gson().toJson(response.body()));
                if (response.body().getSubscription().get(0).getStatus().equals("Y")) {
                    Intent intent = new Intent(mycontext, FBWorkoutViewActivity.class);
                    intent.putExtra(APPConst.PID,pid);
                    mycontext.startActivity(intent);

                } else {
                    Intent intent = new Intent(mycontext, FBPaymentActivity.class);

                    mycontext.startActivity(intent);
                }
                Loader.showLoad(mycontext,false);
            }

            @Override
            public void onFailure(Call<WorkoutCheckPaymentResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext,false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void removeAt(int position) {
        checkworkoutResponse.getData().remove(position);
        notifyItemRemoved(position);
    }
}
