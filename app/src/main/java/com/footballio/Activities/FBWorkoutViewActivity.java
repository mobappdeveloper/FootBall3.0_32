package com.footballio.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.footballio.Adapter.WorkoutExpItemAdapter;
import com.footballio.Adapter.WorkoutRecycleViewAdapter;
import com.footballio.Adapter.WorkoutViewItemAdapter;
import com.footballio.Adapter.WorkoutWarmAdapter;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.model.workout.WorkoutViewResponse;
import com.footballio.model.workout.type.WarmUpItem;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FBWorkoutViewActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = FBWorkoutViewActivity.class.getSimpleName();

    @BindView(R.id.fb_workout_view_title)
    TextView fbWorkoutViewTitle;
    @BindView(R.id.fb_workout_view_image)
    ImageView fbWorkoutViewImage;
    @BindView(R.id.fb_workout_view_category)
    TextView fbWorkoutViewCategory;
    @BindView(R.id.fitness)
    TextView fitness;
    @BindView(R.id.technique)
    TextView technique;
    @BindView(R.id.body)
    TextView body;
    @BindView(R.id.mental)
    TextView mental;
    @BindView(R.id.health)
    TextView health;
    @BindView(R.id.behaviour)
    TextView behaviour;
    @BindView(R.id.fb_workout_view_description)
    TextView fbWorkoutViewDescription;

    @BindView(R.id.fb_workout_view_warm_up_recycler)
    RecyclerView fbWorkoutViewWarmUpRecycler;

    @BindView(R.id.btn_fb_workout_view)
    Button btnFbWorkoutView;
    @BindView(R.id.fb_workout_view_back)
    LinearLayout fbWorkoutViewBack;
    @BindView(R.id.exp_list_view)
    ExpandableListView expListView;

    private Context mycontext;
    /*    private ImageView workout_work_img, workout_view_back;
        private TextView workout_view_title, workout_view_description, workout_view_warmup, workout_view_specific, workout_view_technique,
                workout_view_fitness, workout_view_cooldown;
        private Button btn_workout_view;
        private RecyclerView workout_view_recycle;*/
    private WorkoutWarmAdapter workoutWarmAdapter;
    private WorkoutViewResponse workoutViewResponse;
    WorkoutViewItemAdapter workoutViewAdapter;
    private String pid = "0";
    private List<WarmUpItem> warmUpItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_view);
        ButterKnife.bind(this);

        initUi();
    }

    private void initUi() {
        mycontext = this;
        warmUpItems  = new ArrayList<>();

        fbWorkoutViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnFbWorkoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mycontext, FBWorkoutTechniqueActivity.class);
                intent.putExtra(APPConst.PID, pid);
                startActivity(intent);
                finish();
            }
        });
      /*  btnFbWorkoutView.setOnClickListener(this);
        fbWorkoutViewTechnique.setOnClickListener(this);*/

        getBundle();


    }

    private void getBundle() {
        Intent intent = getIntent();
        if (intent.getStringExtra(APPConst.PID) != null) {
            pid = intent.getStringExtra(APPConst.PID);
            workoutView(pid);
        }
    }

    private RecyclerView.LayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(mycontext, LinearLayoutManager.VERTICAL, false);
    }

  /*  @Override(R.id.fb_workout_view_back)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fb_workout_view_back:

                break;

            case R.id.btn_fb_workout_view:
                Intent i = new Intent(mycontext, FBWorkoutAccomplishedActivity.class);
                startActivity(i);
                break;
            case R.id.fb_workout_view_technique:
                Intent intent = new Intent(mycontext, FBWorkoutTechniqueActivity.class);
                startActivity(intent);
                break;

        }


    }*/


    Call<WorkoutViewResponse> workoutViewResponseCall;

    private void workoutView(String pid) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        workoutViewResponseCall = apiService.workoutView(pid);


        workoutViewResponseCall.enqueue(new Callback<WorkoutViewResponse>() {
            @Override
            public void onResponse(Call<WorkoutViewResponse> call, Response<WorkoutViewResponse> response) {
                Log.d(TAG, "onResponse: allCategoryResponse" + new Gson().toJson(response.body()));
                workoutViewResponse = response.body();

                Glide.with(mycontext)
                        .load(workoutViewResponse.getProgram().get(0).getPhoto())
                        .placeholder(R.color.transparent)
                        .error(R.color.transparent)
                        .into(fbWorkoutViewImage);
                body.setText(workoutViewResponse.getProgram().get(0).getBody());
                fitness.setText(workoutViewResponse.getProgram().get(0).getFitness());
                technique.setText(workoutViewResponse.getProgram().get(0).getTechnique());
                mental.setText(workoutViewResponse.getProgram().get(0).getMental());
                health.setText(workoutViewResponse.getProgram().get(0).getHealth());
                behaviour.setText(workoutViewResponse.getProgram().get(0).getBehaviour());
                fbWorkoutViewCategory.setText(workoutViewResponse.getProgram().get(0).getProgramName());
                fbWorkoutViewTitle.setText(workoutViewResponse.getProgram().get(0).getProgramName());
                fbWorkoutViewDescription.setText(workoutViewResponse.getProgram().get(0).getDescription());
                workoutViewAdapter = new WorkoutViewItemAdapter(mycontext, workoutViewResponse.getWorkouttypes(),warmUpItems, pid);
              //  fbWorkoutViewWarmUpRecycler.setLayoutManager(getLinearLayoutManager());
                expListView.setAdapter(workoutViewAdapter);

                expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                        String type =workoutViewResponse.getWorkouttypes().get(groupPosition).getType() ;
                        String url=ApiClient.BASE_URL+ApiClient.BASE_URL_VERSION+"workouts/viewworkouttracks.php?pid=" + pid + "&type=" + type;
                        url = url.replace(" ", "%20");
                        Log.d(TAG, "onGroupClick: "+url);
                        new JSONAsyncTask(url,type,groupPosition).execute();

                        return false;
                    }
                });
                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<WorkoutViewResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View v) {

    }



    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {
        String url=null; String type=null;
        int groupPosition=1;
        public JSONAsyncTask(String url, String type, int groupPosition) {
            this.url = url;
            this.type = type;
            this.groupPosition = groupPosition;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Loader.showLoad(mycontext, true);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpPost = new HttpGet(url);
                // httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResp = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResp.getEntity();
                String data = EntityUtils.toString(httpEntity);


                JSONObject jsono = new JSONObject(data);
                Log.d(TAG, "doInBackground: " + new Gson().toJson(jsono));
                JSONArray dynamicValue = jsono.getJSONArray(type);
                Log.d(TAG, "doInBackground:array " + new Gson().toJson(dynamicValue));

                warmUpItems = new Gson().fromJson(dynamicValue.toString(), new TypeToken<List<WarmUpItem>>() {
                }.getType());


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {

                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            Loader.showLoad(mycontext, false);
            if (warmUpItems.size() > 0) {
                workoutViewAdapter = new WorkoutViewItemAdapter(mycontext, workoutViewResponse.getWorkouttypes(),warmUpItems, pid);
                expListView.setAdapter(workoutViewAdapter);
                workoutViewAdapter.notifyDataSetChanged();
                expListView.expandGroup(groupPosition);
            //    expListView.sm
                Log.d(TAG, "onResponse:workoutTypeViewResponse "+new Gson().toJson(warmUpItems));
            } else {
                Log.d(TAG, "onResponse:workoutTypeViewResponse empty data");
            }

          /*  workoutRecycleViewAdapter = new WorkoutRecycleViewAdapter(mContext, pid,type);
            secondLevelExpListView.setAdapter(new WorkoutExpItemAdapter(mContext, workouttypesItems,warmUpItems,pid,type));

*/
            //  workoutWarmAdapter.notifyDataSetChanged();

        }
    }
}
