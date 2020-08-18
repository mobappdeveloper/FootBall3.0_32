package com.footballio.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.footballio.R;
import com.footballio.Utils.Loader;
import com.footballio.model.workout.WorkouttypesItem;
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
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class WorkoutExpItemAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "WorkoutExpItemAdapter";
    private Context mContext;
    public static float count = 0;
    // private  List<Category> mListDataHeader;
    //private  Map<String, List<Item>> mListDataChild;
    private List<WorkouttypesItem> workouttypesItems;
    private List<WarmUpItem> warmUpItems;
    /*   public DashboardExpItemAdapter(Context mContext, List<Category> mListDataHeader, Map<String, List<Item>> mListDataChild) {
           this.mContext = mContext;
           this.mListDataHeader = mListDataHeader;
           this.mListDataChild = mListDataChild;
       }*/
    String pid;String type;
    private String isoffer="0";
    private String url ="";
    private WorkoutWarmAdapter workoutWarmAdapter;

    public WorkoutExpItemAdapter(Context mContext, List<WorkouttypesItem> workouttypesItems, List<WarmUpItem> warmUpItems, String pid,String type) {
        this.mContext = mContext;
        this.workouttypesItems = workouttypesItems;
        this.workouttypesItems = workouttypesItems;
        this.pid = pid;
        this.type = type;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return warmUpItems;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        // final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.li_item_recycleview, parent, false);
        }


        RecyclerView itemlisttitle = convertView.findViewById(R.id.recycleview);
        workoutWarmAdapter = new WorkoutWarmAdapter(mContext, warmUpItems);
        itemlisttitle.setLayoutManager(getLinearLayoutManager());
        itemlisttitle.setAdapter(workoutWarmAdapter);
        /*url = "http://mailserver.football3.io/v4/api/workouts/viewworkouttracks.php?pid=" + pid + "&type=" + type;
        url = url.replace(" ", "%20");
        //url = Uri.encode(url,"UTF-8");
        // workoutTypeView(pid,type , viewHolder.fbWorkoutViewWarmUpRecycler);
        workoutWarmAdapter = new WorkoutWarmAdapter(mContext, warmUpItems);
        itemlisttitle.setLayoutManager(getLinearLayoutManager());
        itemlisttitle.setAdapter(workoutWarmAdapter);
        new JSONAsyncTask().execute();
        if (warmUpItems.size() > 0) {

        } else {
            Log.d(TAG, "onResponse:workoutTypeViewResponse empty data");
        }*/

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        try {
            return warmUpItems.size();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.workouttypesItems;
    }

    @Override
    public int getGroupCount() {
        return this.workouttypesItems.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        //  String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.workout_head_item, parent, false);
        }
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.item_view);
        ImageView ivGroupIndicator =  convertView
                .findViewById(R.id.ivGroupIndicator);
        ivGroupIndicator.setSelected(isExpanded);
        lblListHeader.setText(workouttypesItems.get(groupPosition).getType());
        lblListHeader.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private RecyclerView.LayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Loader.showLoad(mContext, true);
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
            Loader.showLoad(mContext, false);


            workoutWarmAdapter.notifyDataSetChanged();

        }
    }
}