package com.footballio.Adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.footballio.R;
import com.footballio.Utils.Loader;
import com.footballio.model.workout.type.WarmUpItem;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WorkoutRecycleViewAdapter extends RecyclerView.Adapter<WorkoutRecycleViewAdapter.ViewHolder> {
    public static final String TAG = WorkoutRecycleViewAdapter.class.getSimpleName();
    Context context;
    private String url = "";
    private List<WarmUpItem> warmUpItems;


//  public HelpListAdapter(Context contextHelpListAdapter) {
//    this.contextHelpListAdapter = contextHelpListAdapter;
String pid= ""; String type ="";
    private WorkoutWarmAdapter workoutWarmAdapter;

    public WorkoutRecycleViewAdapter(Context mycontext, String pid, String type) {

        this.context = mycontext;
        this.pid = pid;
        this.type = type;
        //this.warmUpItems = warmUpItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_item_recycleview, viewGroup, false);
        return new ViewHolder(view, i) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {


    }


    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recycleview)
        RecyclerView recycleview;

        ViewHolder(View view, int i) {
            super(view);

            ButterKnife.bind(this,view);
        }
    }


    private RecyclerView.LayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Loader.showLoad(context, true);
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
            Loader.showLoad(context, false);


            workoutWarmAdapter.notifyDataSetChanged();

        }
    }
}
