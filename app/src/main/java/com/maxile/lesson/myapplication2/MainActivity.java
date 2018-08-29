package com.maxile.lesson.myapplication2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.maxile.lesson.myapplication2.adapter.MyAdapter;
import com.maxile.lesson.myapplication2.adapter.OnLoadMoreListener;
import com.maxile.lesson.myapplication2.adapter.model.RecyclerAdapterModel;
import com.maxile.lesson.myapplication2.services.NewsService;
import com.maxile.lesson.myapplication2.services.model.NewsItem;
import com.maxile.lesson.myapplication2.services.model.NewsModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private List<RecyclerAdapterModel> models;
    private MyAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    Retrofit retrofit;
    NewsService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mwa.co.th/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(NewsService.class);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        findViewById(R.id.contact_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.recycler_view).setVisibility(View.GONE);
                findViewById(R.id.line_chart).setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.main_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.recycler_view).setVisibility(View.VISIBLE);
                findViewById(R.id.line_chart).setVisibility(View.GONE);
//                Locale current = getResources().getConfiguration().locale;
//                String currentL = current.getLanguage();
//                String lang;
//                if (current.getLanguage() == "th")
//                    lang = "en";
//                else
//                    lang = "th";
//                MainActivity.this.setLanguage(lang);
            }
        });
        requestPermission();
        models = new ArrayList<RecyclerAdapterModel>();
        for (int i =0;i<20;i++){
            models.add(new RecyclerAdapterModel("title","detail",""));
        }
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyAdapter(models, recyclerView, this);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                models.add(null);
                adapter.notifyItemInserted(models.size() - 1);
                service.listNews(lastDate).enqueue(new Callback<NewsModel>() {
                    @Override
                    public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                        models.remove(models.size() - 1);
                        adapter.notifyItemRemoved(models.size());
                        for (NewsItem n :
                                response.body().new_list) {
                            models.add(new RecyclerAdapterModel(n.title,n.news,n.cover_picture));
                            lastDate = n.pubDate +  " "+n.pubTime;
                        }
                        adapter.setLoaded();
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<NewsModel> call, Throwable t) {
                        int i =0;
                    }
                });

            }
        });
        recyclerView.setAdapter(adapter);

        LineChart lineChart = (LineChart)findViewById(R.id.line_chart);

        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        });

        findViewById(R.id.left_view).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });
    }




    private String lastDate = "";
    @Override
    protected void onResume() {
        super.onResume();
        int i = 0;



        Date currentTime = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ////////
        showProgress();
        service.listNews(df.format(currentTime)).enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                models.clear();
                for (NewsItem n :
                        response.body().new_list) {
                    models.add(new RecyclerAdapterModel(n.title,n.news,n.cover_picture));
                    lastDate = n.pubDate +  " "+n.pubTime;
                }
                adapter.notifyDataSetChanged();
                hideProgress();
                //////
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                int i =0;
                hideProgress();
                /////
            }
        });
    }

    public void showProgress(){
        ProgressBar p = (ProgressBar)findViewById(R.id.progressBarCenter);
        p.setVisibility(View.VISIBLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void hideProgress(){

        ProgressBar p = (ProgressBar)findViewById(R.id.progressBarCenter);
        p.setVisibility(View.GONE);
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        int i = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        int i = 0;
    }

    public void setLanguage(String language) {
        Resources res = MainActivity.this.getResources();

        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(language));

        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(MainActivity.this, MainActivity.class);
        refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(refresh);
        finish();
    }

    public final String[] listPermission = new String[]{
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.WAKE_LOCK,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.VIBRATE,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    };
    private final static int MY_PERMISSIONS_REQUEST_PHONE_STATE = 999;//any number
    public List<String> listRequest;

    private void requestPermission() {
        listRequest = new ArrayList<String>();
        for (String permission : listPermission) {
            if (ContextCompat.checkSelfPermission(this,
                    permission)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    listRequest.add(permission);
                }
            }
        }
        Log.d("request permission", listRequest.size() + "");
        if (listRequest.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    listRequest.toArray(new String[0]),
                    MY_PERMISSIONS_REQUEST_PHONE_STATE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_PHONE_STATE: {
                boolean isPass = true;
                if (grantResults.length == permissions.length) {
                    for (int g : grantResults) {
                        if (g != PackageManager.PERMISSION_GRANTED) {
                            isPass = false;
                        }
                    }
                }
                if (!isPass) {
                    //permission not pass
                } else {
                    //permission pass
                }
                break;
            }
        }
    }
}
