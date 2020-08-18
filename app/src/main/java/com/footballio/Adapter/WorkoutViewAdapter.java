package com.footballio.Adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.footballio.R;
import com.footballio.Utils.Loader;
import com.footballio.model.workout.WorkouttypesItem;
import com.footballio.model.workout.type.WarmUpItem;
import com.footballio.model.workout.type.WorkoutTypeViewResponse;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.footballio.retrofit.ApiClient.BASE_URL_VERSION;


public class WorkoutViewAdapter extends RecyclerView.Adapter<WorkoutViewAdapter.ViewHolder> {
    public static final String TAG = WorkoutViewAdapter.class.getSimpleName();
    Context mycontext;

    private WorkoutTypeViewResponse workoutTypeViewResponse;
    List<WorkouttypesItem> workouttypes;
    String pid;
    WorkoutWarmAdapter workoutWarmAdapter;
    private String type = "";
    private String url = "";
    List<WarmUpItem> warmUpItems;
    List<WarmUpItem> warmUpItemsdata;

    public WorkoutViewAdapter(Context mycontext, List<WorkouttypesItem> workouttypes, String pid) {

        this.mycontext = mycontext;
        this.workouttypes = workouttypes;
        this.pid = pid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_item_workout_view, viewGroup, false);
        return new ViewHolder(view, i) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.itemView.setText(workouttypes.get(i).getType());
        viewHolder.fbWorkoutViewWarmUpRecycler.setVisibility(View.GONE);
        viewHolder.itemView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_right_arrow, 0);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.fbWorkoutViewWarmUpRecycler.setVisibility(View.VISIBLE);
                viewHolder.itemView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_arrow, 0);

                type = workouttypes.get(i).getType();
                url = "http://mailserver.football3.io/v4/api/workouts/viewworkouttracks.php?pid=" + pid + "&type=" + type;
                url = url.replace(" ", "%20");
                //url = Uri.encode(url,"UTF-8");
                // workoutTypeView(pid,type , viewHolder.fbWorkoutViewWarmUpRecycler);
                workoutWarmAdapter = new WorkoutWarmAdapter(mycontext, warmUpItems);
                viewHolder.fbWorkoutViewWarmUpRecycler.setLayoutManager(getLinearLayoutManager());
                viewHolder.fbWorkoutViewWarmUpRecycler.setAdapter(workoutWarmAdapter);
                new JSONAsyncTask().execute();
                if (warmUpItems.size() > 0) {
                    workoutWarmAdapter = new WorkoutWarmAdapter(mycontext, warmUpItems);
                    viewHolder.fbWorkoutViewWarmUpRecycler.setLayoutManager(getLinearLayoutManager());
                    viewHolder.fbWorkoutViewWarmUpRecycler.setAdapter(workoutWarmAdapter);
                } else {
                    Log.d(TAG, "onResponse:workoutTypeViewResponse empty data");
                }

            }
        });
    }

    public void updateData(List<WarmUpItem> movie_list) {
        this.warmUpItems = movie_list;
    }

    @Override
    public int getItemCount() {
        return workouttypes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_view)
        TextView itemView;
        @BindView(R.id.fb_workout_view_warm_up_recycler)
        RecyclerView fbWorkoutViewWarmUpRecycler;
        @BindView(R.id.list_view)
        ExpandableListView listView;

        ViewHolder(View view, int i) {
            super(view);
            ButterKnife.bind(this, view);
            warmUpItems = new ArrayList<>();
            warmUpItemsdata = new ArrayList<>();
        }
    }

    Call<ResponseBody> workoutTypeViewResponseCall;

    private void workoutTypeView(String pid, String type, RecyclerView fbWorkoutViewWarmUpRecycler) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String url = BASE_URL_VERSION + "workouts/viewworkouttracks.php";
        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        workoutTypeViewResponseCall = apiService.workouttypeView(url, pid, type);


        workoutTypeViewResponseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    Log.d(TAG, "onResponse: workoutTypeViewResponsestring" + new Gson().toJson(response.body().string()));
                    Gson gson = new Gson();
                    WarmUpItem entity = gson.fromJson(response.body().string(), WarmUpItem.class);

                    WorkoutTypeViewResponse msg = gson.fromJson(response.body().string(), WorkoutTypeViewResponse.class);
                    Log.d(TAG, "onResponse: workoutTypeViewResponsestringdata" + new Gson().toJson(entity));
                } catch (IOException e) {
                    Log.d(TAG, "onResponse: workoutTypeViewResponsestring" + e.getMessage().toString());
                    e.printStackTrace();
                }

                Log.d(TAG, "onResponse: workoutTypeViewResponse" + new Gson().toJson(response.body()));
                //  workoutTypeViewResponse = response.body();
                // String json = getClient().getInfo().execute().body().string();
                JSONObject object = null;
                JSONArray array1 = null;
                try {
                    String result = response.body().string();
                    object = new JSONObject(result);
                    Log.d(TAG, "onResponse:jsonobject workoutTypeViewResponse" + new Gson().toJson(object));
                    array1 = new JSONArray(result);
                } catch (JSONException e) {
                    Log.d(TAG, "onResponse:errorjsonobject workoutTypeViewResponse" + e.getMessage().toString());
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "onResponse: workoutTypeViewResponse12" + new Gson().toJson(array1));

                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private RecyclerView.LayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(mycontext, LinearLayoutManager.VERTICAL, false);
    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {


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

                warmUpItemsdata = new Gson().fromJson(dynamicValue.toString(), new TypeToken<List<WarmUpItem>>() {
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

            updateData(warmUpItemsdata);
            workoutWarmAdapter.notifyDataSetChanged();

        }
    }
}
