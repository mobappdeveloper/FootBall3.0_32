package com.footballio.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.footballio.Activities.FBWorkoutVideoViewActivity;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.CustomExpListView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutViewItemAdapter extends BaseExpandableListAdapter {
    private static final String TAG = WorkoutViewItemAdapter.class.getSimpleName();
    private Context mContext;

    private List<WorkouttypesItem> workouttypesItems;
    private List<WarmUpItem> warmUpItems;
    private Map<String, String> mListData_SecondLevel_Map;
    String pid;
   // private Map<String, List<Item>> mListData_ThirdLevel_Map;
    Button btn_checkout;
    private String url = "";
    private WorkoutRecycleViewAdapter workoutRecycleViewAdapter;
    private String type = "";
    private WorkoutWarmAdapter workoutWarmAdapter;
    CustomExpListView secondLevelExpListView;

    public WorkoutViewItemAdapter(Context mContext, List<WorkouttypesItem> workouttypesItems, List<WarmUpItem> warmUpItems, String pid) {
        this.mContext = mContext;
        this.workouttypesItems = workouttypesItems;
        this.warmUpItems = warmUpItems;
        this.pid = pid;
        Log.d(TAG, "WorkoutViewItemAdapter: "+new Gson().toJson(warmUpItems));
        /*this.mListDataHeader = new ArrayList<>();
        this.mListDataHeader.addAll(mListDataHeader);*/
        // Init second level data
        String[] mItemHeaders;
        mListData_SecondLevel_Map = new HashMap<>();
      //  mListData_ThirdLevel_Map = new HashMap<>();
        int parentCount = workouttypesItems.size();
        for (int i = 0; i < parentCount; i++) {
            mListData_SecondLevel_Map.put(String.valueOf(i),workouttypesItems.get(i).getType());


           // String content = dashboardResponses.get(i);
       /*     switch (content) {
                case "Level 1.1":
                    mItemHeaders = mContext.getResources().getStringArray(R.array.items_array_expandable_level_one_one_child);
                    break;
                case "Level 1.2":
                    mItemHeaders = mContext.getResources().getStringArray(R.array.items_array_expandable_level_one_two_child);
                    break;
                default:
                    mItemHeaders = mContext.getResources().getStringArray(R.array.items_array_expandable_other_child);
            }*/
           // mListData_SecondLevel_Map.put(mListDataHeader.get(i), Arrays.asList(mItemHeaders));
        }
    /*    for (int j = 0; j < mListData_SecondLevel_Map.size(); j++) {
            mListData_ThirdLevel_Map.put(String.valueOf(j),mListData_SecondLevel_Map.get(j).getItem());
        }*/
        // THIRD LEVEL
        String[] mItemChildOfChild;
        List<String> listChild;
      //  mListData_ThirdLevel_Map = new HashMap<>();
/*        for (Object o : mListData_SecondLevel_Map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            Object object = entry.getValue();
            if (object instanceof List) {
                List<String> stringList = new ArrayList<>();
                Collections.addAll(stringList, (String[]) ((List) object).toArray());
                for (int i = 0; i < stringList.size(); i++) {
                    mItemChildOfChild = mContext.getResources().getStringArray(R.array.items_array_expandable_level_three);
                    listChild = Arrays.asList(mItemChildOfChild);
                    mListData_ThirdLevel_Map.put(stringList.get(i), listChild);
                }
            }
        }*/
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
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
       secondLevelExpListView = new CustomExpListView(this.mContext);
     //   String parentNode = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.li_item_warmup, parent, false);
        }

        LinearLayout itemlisttitle = convertView.findViewById(R.id.workout_warmup_layout);
        TextView workout_warmup_name = convertView.findViewById(R.id.workout_warmup_name);
        TextView workout_warmup_description = convertView.findViewById(R.id.workout_warmup_description);
        ImageView vendor_status_image = convertView.findViewById(R.id.vendor_status_image);

       workout_warmup_name.setText(warmUpItems.get(childPosition).getTitle());
        workout_warmup_description.setText(warmUpItems.get(childPosition).getDescription());
        Glide.with(mContext)
                .load(warmUpItems.get(childPosition).getSticker())
                .placeholder(R.color.transparent)
                .error(R.color.transparent)
                .into(vendor_status_image);

        itemlisttitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: videourl"+warmUpItems.get(childPosition).getVideo());
                Intent intent = new Intent(mContext, FBWorkoutVideoViewActivity.class);
                intent.putExtra(APPConst.PVIDEOID,warmUpItems.get(childPosition).getVideo());
                mContext.startActivity(intent);
            }
        });

  /*      type = workouttypesItems.get(groupPosition).getType();
        //url = Uri.encode(url,"UTF-8");
        // workoutTypeView(pid,type , viewHolder.fbWorkoutViewWarmUpRecycler);
      //  new JSONAsyncTask().execute();

        Log.d(TAG, "getChildView: user");
        type = workouttypesItems.get(groupPosition).getType();
        Log.d(TAG, "getChildView: user"+type);
      //  workoutRecycleViewAdapter = new WorkoutRecycleViewAdapter(mContext, pid,type);
           url = "http://mailserver.football3.io/v4/api/workouts/viewworkouttracks.php?pid=" + pid + "&type=" + type;
        url = url.replace(" ", "%20");
        //url = Uri.encode(url,"UTF-8");
        // workoutTypeView(pid,type , viewHolder.fbWorkoutViewWarmUpRecycler);

        new JSONAsyncTask().execute();
   *//*     if (warmUpItems.size() > 0) {
        } else {
            Log.d(TAG, "onResponse:workoutTypeViewResponse empty data");
        }
*//*
        secondLevelExpListView.setAdapter(new WorkoutExpItemAdapter(mContext, workouttypesItems,warmUpItems,pid,type));

        secondLevelExpListView.setGroupIndicator(null);*/
        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return warmUpItems.size();
    }
    @Override
    public Object getGroup(int groupPosition) {
        return this.workouttypesItems.get(groupPosition);
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
        // String headerTitle = (String) getGroup(groupPosition);
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
        lblListHeader.setTypeface(null, Typeface.BOLD);
        // lblListHeader.setTextColor(Color.CYAN);
        lblListHeader.setText(workouttypesItems.get(groupPosition).getType());
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
            Loader.showLoad(mContext, false);
            workoutRecycleViewAdapter = new WorkoutRecycleViewAdapter(mContext, pid,type);


          //  workoutWarmAdapter.notifyDataSetChanged();

        }
    }
}