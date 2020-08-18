package com.footballio.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.Utils.Utils;
import com.footballio.model.dashboard.loader.CalendarDataItem;
import com.footballio.model.dashboard.loader.ReportLoaderResponse;
import com.footballio.model.dashboard.loader.loadercalendar.CalendarLoaderResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.gson.Gson;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.co.recruit_mp.android.lightcalendarview.LightCalendarView;
import jp.co.recruit_mp.android.lightcalendarview.WeekDay;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FBLoaderActivity extends AppCompatActivity {

    private static final String TAG = FBLoaderActivity.class.getSimpleName();
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.fb_workout_video_view_back)
    LinearLayout fbWorkoutVideoViewBack;
    @BindView(R.id.progressgreen)
    ProgressBar progressgreen;
    @BindView(R.id.progressgreen_primary)
    CircularProgressBar progressgreenPrimary;
    @BindView(R.id.progressgreen_light)
    CircularProgressBar progressgreenLight;
    @BindView(R.id.txt_techique)
    TextView txtTechique;
    @BindView(R.id.progressyellow_primary)
    CircularProgressBar progressyellowPrimary;
    @BindView(R.id.progressyellow_light)
    CircularProgressBar progressyellowLight;
    @BindView(R.id.txt_fitness)
    TextView txtFitness;
    @BindView(R.id.progressred_primary)
    CircularProgressBar progressredPrimary;
    @BindView(R.id.progressred_light)
    CircularProgressBar progressredLight;
    @BindView(R.id.txt_body)
    TextView txtBody;
    @BindView(R.id.progressblue)
    ProgressBar progressblue;
    @BindView(R.id.progressblue_primary)
    CircularProgressBar progressbluePrimary;
    @BindView(R.id.progressblue_light)
    CircularProgressBar progressblueLight;
    @BindView(R.id.txt_mental)
    TextView txtMental;
    @BindView(R.id.progressorange_primary)
    CircularProgressBar progressorangePrimary;
    @BindView(R.id.progressorgane_light)
    CircularProgressBar progressorganeLight;
    @BindView(R.id.txt_health)
    TextView txtHealth;
    @BindView(R.id.progresslander_primary)
    CircularProgressBar progresslanderPrimary;
    @BindView(R.id.progresslander_light)
    CircularProgressBar progresslanderLight;
    @BindView(R.id.txt_behaiour)
    TextView txtBehaiour;
    Activity mycontext;
    @BindView(R.id.calendarView)
    LightCalendarView calendarView;
    @BindView(R.id.calendar)
    LinearLayout calendar;
    @BindView(R.id.img_prev_month)
    ImageView imgPrevMonth;
    @BindView(R.id.txt_current_month)
    TextView txtCurrentMonth;
    @BindView(R.id.img_next_month)
    ImageView imgNextMonth;
    private ReportLoaderResponse reportloaderResponse;
    private Handler handler;
    private CalendarLoaderResponse calendarResponse;

    ArrayList<String> monthaArray;
    String currentDate = "";
    String currentDay = "", currentMonth ="", currentYear = "";
    int currentDayNum = 0, currentMonthNum =0, currentYearNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        ButterKnife.bind(this);

        initUi();
    }

    private void initUi() {
        mycontext = this;
        handler = new Handler();
        calendarView.setWeekDayFilterColor(WeekDay.SUNDAY, getResources().getColor(R.color.colorGray));
        calendarView.setWeekDayFilterColor(WeekDay.SATURDAY, getResources().getColor(R.color.colorGray));
        calendarView.setDayFilterColor(WeekDay.SUNDAY, getResources().getColor(R.color.colorGray));
        calendarView.setDayFilterColor(WeekDay.SATURDAY, getResources().getColor(R.color.colorGray));

        monthaArray = new ArrayList<>();
        monthaArray.add("January");
        monthaArray.add("February");
        monthaArray.add("March");
        monthaArray.add("April");
        monthaArray.add("May");
        monthaArray.add("June");
        monthaArray.add("July");
        monthaArray.add("August");
        monthaArray.add("September");
        monthaArray.add("October");
        monthaArray.add("November");
        monthaArray.add("December");

        currentDate = Utils.CurrentDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        currentDay =(String) DateFormat.format("dd", date);
        currentYear =(String)  DateFormat.format("yyyy", date);
        currentMonth = (String) DateFormat.format("MM", date);

        currentDayNum = Integer.parseInt(currentDay);
        currentYearNum = Integer.parseInt(currentYear);
        currentMonthNum = Integer.parseInt(currentMonth);

        Log.d(TAG, "initUi: currentDay" + currentDay);
        Log.d(TAG, "initUi: currentMonth" + currentMonth);
        Log.d(TAG, "initUi: currentYear" + currentYear);

        txtCurrentMonth.setText(monthaArray.get(currentMonthNum-1));
        // currentDay = (String) DateFormat.format("dd",   currentDate);

        getReportLoader();

        fbWorkoutVideoViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void createCalendar(int countofMonth, String weekDayStart, List<CalendarDataItem> calendarData) {
        calendar.removeAllViews();
        int start = 1, end = countofMonth + 1;
        boolean firsttime = true;


        String day = weekDayStart;
        switch (day) {
            case "Mon":
                start = start - 0;
                break;
            case "Tue":
                start = start - 1;
                break;
            case "Wed":
                start = start - 2;
                break;
            case "Thu":
                start = start - 3;
                break;
            case "Fri":
                start = start - 4;
                break;
            case "Sat":
                start = start - 5;
                break;

            case "Sun":
                start = start - 6;
                break;

        }
        ArrayList<String> dayarray = new ArrayList<>();
        ArrayList<Integer> green = new ArrayList<>();
        ArrayList<Integer> blue = new ArrayList<>();
        for (int i = 0; i < calendarData.size(); i++) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = format.parse(calendarData.get(i).getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String daystr = (String) DateFormat.format("dd", date); // 20
            if (daystr.substring(0, 1).equals("0")) {
                daystr = daystr.substring(1);
            }
            Log.d(TAG, "createCalendar: " + daystr);
            dayarray.add(daystr);
            green.add(calendarData.get(i).getGreenLoaderValue());
            blue.add(calendarData.get(i).getBlueLoaderValue());

        }

        for (int i = 0; i < 6; i++) {
            TextView[] tv = new TextView[7];
            CircularProgressBar[] circularProgressBarsBigs = new CircularProgressBar[7];
            CircularProgressBar[] circularProgressBarsSmall = new CircularProgressBar[7];
            LinearLayout layout2 = new LinearLayout(mycontext);
            LinearLayout[] layout1 = new LinearLayout[7];
            RelativeLayout[] relativeLayout = new RelativeLayout[7];
            layout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layout2.setOrientation(LinearLayout.HORIZONTAL);
            layout2.setGravity(Gravity.START);
            layout2.setPadding(0, 0, 0, 16);
            for (int j = 0; j < 7; j++) {
                circularProgressBarsBigs[j] = new CircularProgressBar(mycontext, null);
                circularProgressBarsSmall[j] = new CircularProgressBar(mycontext, null);
                relativeLayout[j] = new RelativeLayout(mycontext);
                layout1[j] = new LinearLayout(mycontext);
                if (firsttime) {

                    firsttime = false;
                }

                layout1[j].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                layout1[j].setOrientation(LinearLayout.HORIZONTAL);
                layout1[j].setGravity(Gravity.START);


                ViewGroup.LayoutParams params = relativeLayout[j].getLayoutParams();
                if (params != null) {
                    params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                } else
                    params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                relativeLayout[j].setLayoutParams(params);
                relativeLayout[j].setGravity(Gravity.START);

             /*   RelativeLayout.LayoutParams layoutParams =
                        (RelativeLayout.LayoutParams)  relativeLayout[j].getLayoutParams();
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                circularProgressBarsBigs[j].setLayoutParams(layoutParams);*/
                RelativeLayout.LayoutParams Params11 = new RelativeLayout.LayoutParams(100, 100);
                Params11.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                circularProgressBarsBigs[j].setLayoutParams(Params11);
           /*     circularProgressBarsBigs[j].height =  30;
                circularProgressBarsBigs[j].getLayoutParams().width  = 30;*/


                circularProgressBarsBigs[j].setBackgroundProgressBarWidth(3);
                circularProgressBarsBigs[j].setBackgroundProgressBarColor(getResources().getColor(R.color.colorMajor_light));
                circularProgressBarsBigs[j].setProgressBarColor(getResources().getColor(R.color.colorMajor));
                circularProgressBarsBigs[j].setProgressBarWidth(3);
                circularProgressBarsBigs[j].setRoundBorder(true);
                circularProgressBarsBigs[j].setIndeterminateMode(false);
                circularProgressBarsBigs[j].setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);


                RelativeLayout.LayoutParams Params111 = new RelativeLayout.LayoutParams(70, 70);
                Params111.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                circularProgressBarsSmall[j].setLayoutParams(Params111);

                // circularProgressBarsSmall[j].setLayoutParams(layoutParams);
                //  params1.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                //   circularProgressBarsSmall[j].setLayoutParams(params1);

                circularProgressBarsSmall[j].setBackgroundProgressBarWidth(3);
                circularProgressBarsSmall[j].setBackgroundProgressBarColor(getResources().getColor(R.color.colorMajorblue_light));
                circularProgressBarsSmall[j].setProgressBarColor(getResources().getColor(R.color.colorMajorblue));
                circularProgressBarsSmall[j].setProgressBarWidth(3);
                circularProgressBarsSmall[j].setRoundBorder(true);
                circularProgressBarsSmall[j].setIndeterminateMode(false);
                circularProgressBarsSmall[j].setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);

                tv[j] = new TextView(mycontext);
                RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                Params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                tv[j].setLayoutParams(Params);
                tv[j].setGravity(Gravity.CENTER);

                //  tv[j].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                tv[j].setTextColor(getResources().getColor(R.color.colorWhite));
                tv[j].setTextSize(10);
                if (start > 0 && start < end)
                    tv[j].setText("" + start);

              /*  if (start > end) {
                    break;
                }*/


                //tv[j].layout(12);
                relativeLayout[j].addView(circularProgressBarsBigs[j]);
                relativeLayout[j].addView(circularProgressBarsSmall[j]);
                circularProgressBarsBigs[j].setVisibility(View.INVISIBLE);
                circularProgressBarsSmall[j].setVisibility(View.INVISIBLE);
                for (int k = 0; k < dayarray.size(); k++) {
                    if (dayarray.get(k).equals("" + start)) {
                        Log.d(TAG, "createCalendar: start" + start);
                        circularProgressBarsBigs[j].setVisibility(View.VISIBLE);
                        circularProgressBarsSmall[j].setVisibility(View.VISIBLE);

                        circularProgressBarsSmall[j].setProgressWithAnimation(100, (long) 700);
                        circularProgressBarsBigs[j].setProgressWithAnimation(100, (long) 700);

                        int finalJ = j;
                        int finalK = k;
                        final Runnable r = new Runnable() {
                            public void run() {

                                circularProgressBarsSmall[finalJ].setProgressWithAnimation(blue.get(finalK), (long) 500);
                                circularProgressBarsBigs[finalJ].setProgressWithAnimation(green.get(finalK), (long) 500);

                                handler.postDelayed(this, 1000);
                            }
                        };
                        handler.postDelayed(r, 1000);
                    }
                }

                relativeLayout[j].addView(tv[j]);
                layout1[j].addView(relativeLayout[j]);

                layout2.addView(layout1[j]);

                start++;

            }
          /*  if (start>end){
                break;
            }*/
            calendar.addView(layout2);
        }
    }

    Call<ReportLoaderResponse> loaderResponseCall;

    private void getReportLoader() {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        loaderResponseCall = apiService.getReportLoader(Prefs.getString(APPConst.TOKEN, ""));


        loaderResponseCall.enqueue(new Callback<ReportLoaderResponse>() {
            @Override
            public void onResponse(Call<ReportLoaderResponse> call, Response<ReportLoaderResponse> response) {
                Log.d(TAG, "onResponse:categoryResponse " + new Gson().toJson(response.body()));
                reportloaderResponse = response.body();
                txtTitle.setText(reportloaderResponse.getData().getTodayDate());
                txtTechique.setText(reportloaderResponse.getData().getTechnique());
                txtFitness.setText(reportloaderResponse.getData().getFitness());
                txtBody.setText(reportloaderResponse.getData().getBody());
                txtMental.setText(reportloaderResponse.getData().getMentalStrength());
                txtHealth.setText(reportloaderResponse.getData().getHealth());
                txtBehaiour.setText(reportloaderResponse.getData().getBehaviour());
                progressgreen.setProgress((int) reportloaderResponse.getData().getGreenLoaderValue());
                progressblue.setProgress((int) reportloaderResponse.getData().getBlureLoaderValue());
                progressgreenPrimary.setProgressWithAnimation(100, (long) 700);
                progressyellowPrimary.setProgressWithAnimation(100, (long) 700);
                progressredPrimary.setProgressWithAnimation(100, (long) 700);
                progressbluePrimary.setProgressWithAnimation(100, (long) 700);
                progressorangePrimary.setProgressWithAnimation(100, (long) 700);
                progresslanderPrimary.setProgressWithAnimation(100, (long) 700);

                final Runnable r = new Runnable() {
                    public void run() {

                        progressgreenPrimary.setProgressWithAnimation(reportloaderResponse.getData().getTechniquePetcentageVale(), (long) 500);
                        progressyellowPrimary.setProgressWithAnimation(reportloaderResponse.getData().getFitnessPetcentageVale(), (long) 500);
                        progressredPrimary.setProgressWithAnimation(reportloaderResponse.getData().getBodyPetcentageVale(), (long) 500);
                        progressbluePrimary.setProgressWithAnimation(reportloaderResponse.getData().getMentalPetcentageVale(), (long) 500);
                        progressorangePrimary.setProgressWithAnimation(reportloaderResponse.getData().getHealthPetcentageVale(), (long) 500);
                        progresslanderPrimary.setProgressWithAnimation(reportloaderResponse.getData().getBehaviourPetcentageVale(), (long) 500);
                        handler.postDelayed(this, 1000);
                    }
                };
                handler.postDelayed(r, 1000);
                createCalendar(reportloaderResponse.getCal().get(0).getCountofMonth(), reportloaderResponse.getCal().get(0).getWeekDayStart(), reportloaderResponse.getCalendarData());

                imgNextMonth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "createCalendar:nextmonth currentMonthNum "+currentMonthNum);
                        currentMonthNum++;
                        if (currentMonthNum>12){
                            currentMonthNum=1;
                            currentYearNum++;
                            Log.d(TAG, "createCalendar:nextmonth currentYearNum if"+currentMonthNum);
                        }
                        txtCurrentMonth.setText(monthaArray.get(currentMonthNum-1));
                        getCalendarLoader(String.valueOf(currentMonthNum),String.valueOf(currentYearNum));
                        Log.d(TAG, "createCalendar:nextmonth currentMonthNum after"+currentMonthNum);
                        Log.d(TAG, "createCalendar:nextmonth currentYearNum after"+currentYearNum);
                    }
                });

                imgPrevMonth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "createCalendar:prevmonth currentMonthNum "+currentMonthNum);
                        currentMonthNum--;
                        if (currentMonthNum<1){
                            currentMonthNum=12;
                            currentYearNum--;
                            Log.d(TAG, "createCalendar:prevmonth currentYearNum if"+currentMonthNum);
                        }
                        txtCurrentMonth.setText(monthaArray.get(currentMonthNum-1));
                        getCalendarLoader(String.valueOf(currentMonthNum),String.valueOf(currentYearNum));
                        Log.d(TAG, "createCalendar:prevmonth currentMonthNum after"+currentMonthNum);
                        Log.d(TAG, "createCalendar:prevmonth currentYearNum after"+currentYearNum);
                    }
                });

                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<ReportLoaderResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    Call<CalendarLoaderResponse> calendarLoaderResponseCall;

    private void getCalendarLoader(String month, String year) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        calendarLoaderResponseCall = apiService.getCalendarData(Prefs.getString(APPConst.TOKEN, ""), month, year);


        calendarLoaderResponseCall.enqueue(new Callback<CalendarLoaderResponse>() {
            @Override
            public void onResponse(Call<CalendarLoaderResponse> call, Response<CalendarLoaderResponse> response) {
                Log.d(TAG, "onResponse:categoryResponse " + new Gson().toJson(response.body()));
                calendarResponse = response.body();
if (calendarResponse.getCalendarData()!=null) {
    createCalendar(calendarResponse.getData().get(0).getCountofMonth(), calendarResponse.getData().get(0).getWeekDayStart(), calendarResponse.getCalendarData());
}
                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<CalendarLoaderResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
